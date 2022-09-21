package com.amirscode.payment.service;

import com.amirscode.payment.config.SecurityConfig;
import com.amirscode.payment.entity.Card;
import com.amirscode.payment.entity.Income;
import com.amirscode.payment.entity.Outcome;
import com.amirscode.payment.model.TransferDTO;
import com.amirscode.payment.repository.CardRepository;
import com.amirscode.payment.repository.IncomeRepository;
import com.amirscode.payment.repository.OutcomeRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CardRepository cardRepository;
    private final SecurityConfig securityConfig;

    private final JdbcTemplate jdbcTemplate;
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public ResponseEntity<?> transfer(TransferDTO transferDTO) {
        Double percent = transferDTO.getAmount() / 100;
        Double total = percent + transferDTO.getAmount();
        Card inCard = cardRepository.findByNumber(transferDTO.getIncomeCardNumber()).orElseThrow();

        Card ownCard = cardRepository.findByNumber(transferDTO.getOutcomeCardNumber()).orElseThrow();
        if (ownCard.getUser().getId().equals((securityConfig.getCurrentUser().getId()))) {
            if (ownCard.getBalance() >= total) {
                Double ownCardBalance = ownCard.getBalance() - total;
                ownCard.setBalance(ownCardBalance);
                cardRepository.save(ownCard);

                Double inCardBalance = inCard.getBalance() + transferDTO.getAmount();
                inCard.setBalance(inCardBalance);
                cardRepository.save(inCard);

                var income = new Income();
//                income.setToCardId(inCard);
                income.setFromCardId(ownCard);
                income.setAmount((transferDTO.getAmount()));
                income.setDate(new Date());
                incomeRepository.save(income);

                var outcome = new Outcome();
                outcome.setToCardId(inCard);
//                outcome.setFromCardId(inCard);
                outcome.setAmount(total);
                outcome.setDate(new Date());
                outcomeRepository.save(outcome);
            } else {
                return ResponseEntity.ok().body("transfer error");
            }
        }
        return ResponseEntity.ok().body("transfer was success");
    }

    public ResponseEntity<?> history(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow();

//        List<Income> incomes = incomeRepository.findByFromCardIdAndToCardId(card, card);
//        List<Outcome> outcomes = outcomeRepository.findByFromCardIdAndToCardId(card, card);
//        List<?> transferHistories = Arrays.asList(incomes, outcomes);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from income left join card on card.id=from_card_id_id where card.id =" + card.getId());


        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select * from outcome left join card on card.id=to_card_id_id where card.id =" + card.getId());




        List<List<Map<String, Object>>> orgMaps = new ArrayList<>();
        orgMaps.add(maps);
        orgMaps.add(maps1);


        return ResponseEntity.ok().body(orgMaps);
    }

    public ResponseEntity<?> existCards() {
        int currentUserId = securityConfig.getCurrentUser().getId();
        List cardByUserId = cardRepository.findCardByUserId(currentUserId);

        return ResponseEntity.ok().body(cardByUserId);
    }

}
