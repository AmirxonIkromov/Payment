package com.amirscode.payment.controller;

import com.amirscode.payment.model.TransferDTO;
import com.amirscode.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cabinet")
public class UserController {

    private final UserService userService;

    @PostMapping
    public HttpEntity<?> transfer(@RequestBody TransferDTO transferDTO){
        return ResponseEntity.ok().body(userService.transfer(transferDTO));
    }

    @GetMapping("/{id}")
    public HttpEntity<?> history(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.history(id));
    }
}
