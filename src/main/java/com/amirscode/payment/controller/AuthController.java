package com.amirscode.payment.controller;

import com.amirscode.payment.model.UserDTO;
import com.amirscode.payment.model.UserInDTO;
import com.amirscode.payment.service.AuthService;
import com.amirscode.payment.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final UserServiceImpl authService;

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authService.signUp(userDTO));
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserInDTO userDTO){
        return ResponseEntity.ok(authService.signIn(userDTO));
    }
}
