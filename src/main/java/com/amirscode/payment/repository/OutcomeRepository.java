package com.amirscode.payment.repository;

import com.amirscode.payment.entity.Card;
import com.amirscode.payment.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {

//    List<Outcome> findByFromCardIdAndToCardId(Card fromCardId, Card toCardId);
}
