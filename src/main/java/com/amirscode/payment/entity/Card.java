package com.amirscode.payment.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private String cardName;

    private String number;

    private Double balance;

    private String validityPeriod;

    private boolean expiredDate;

    private boolean isActive;

    @ManyToOne
    private User user;
}
