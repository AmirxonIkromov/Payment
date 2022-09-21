package com.amirscode.payment.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserInDTO {


    @NotNull
    private String username;

    @NotNull
    private String password;

}
