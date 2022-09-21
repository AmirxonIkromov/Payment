package com.amirscode.payment.service;


import com.amirscode.payment.config.SecurityConfig;
import com.amirscode.payment.entity.Card;
import com.amirscode.payment.model.CardDTO;
import com.amirscode.payment.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final JdbcTemplate jdbcTemplate;
    private final CardRepository cardRepository;
    private final SecurityConfig securityConfig;

    public ResponseEntity<?> addCard(CardDTO cardDTO) {
        Optional<Card> byNumber = cardRepository.findByNumber(cardDTO.getNumber());
        if (byNumber.isEmpty()) {
            Card card = new Card();
            card.setCardName(cardDTO.getCardName());
            card.setNumber(cardDTO.getNumber());
            card.setValidityPeriod(cardDTO.getValidityPeriod());
            card.setActive(true);
            card.setUser(securityConfig.getCurrentUser());
            card.setBalance(500000.00);
            cardRepository.save(card);
            return ResponseEntity.ok().body("Card added");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Card is already exists");
    }

    public ResponseEntity<?> getCards() {

        String query = "select * from card where is_active = true and user_id = " + securityConfig.getCurrentUser().getId();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(query);
        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Card not found");
        }
        return ResponseEntity.ok().body(maps);

//        List<Card> cards = cardRepository.findCardByUserId(securityConfig.getCurrentUser().getId());
//        if(cards.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Card not found");
//        }
//        List<Map<String, Object>> maps =  jdbcTemplate.queryForList("select * from card where is_active = true");
//        if (maps.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Categoriala yoq", HttpStatus.CONFLICT.value()));
//        }
//        return ResponseEntity.ok(ApiResult.successResponce(maps));
//
//        return ResponseEntity

    }

    public ResponseEntity<?> deleteCard(Integer id){

        Card card = cardRepository.findById(id).orElseThrow(
                IllegalStateException::new);
        card.setActive(false);
        cardRepository.save(card);
        return ResponseEntity.ok().body("Card deleted");
    }
}
