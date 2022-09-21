package com.amirscode.payment.repository;

import com.amirscode.payment.entity.Card;
import com.amirscode.payment.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Integer> {

//    List<Income> findByFromCardIdAndToCardId(Card fromCardId, Card toCardId);
//

}
