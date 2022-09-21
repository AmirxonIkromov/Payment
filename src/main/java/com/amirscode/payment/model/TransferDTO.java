package com.amirscode.payment.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    private String incomeCardNumber;

    private String outcomeCardNumber;

    private Double amount;
}
