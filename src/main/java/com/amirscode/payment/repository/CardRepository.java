package com.amirscode.payment.repository;

import com.amirscode.payment.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {

    Optional<Card> findByNumber(String number);

    List<Card> findCardByUserId(Integer user_id);
}
