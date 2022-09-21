package com.amirscode.payment.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    private String fullName;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String phoneNumber;

}
