package com.amirscode.payment.model;

import lombok.Data;

@Data
public class CardDTO {

    private String cardName;

    private String number;

    private String validityPeriod;

}
