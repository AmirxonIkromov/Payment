package com.amirscode.payment.controller;

import com.amirscode.payment.model.CardDTO;
import com.amirscode.payment.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;


    @GetMapping()
    public HttpEntity<?> get(){
        return ResponseEntity.ok().body(cardService.getCards());
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody CardDTO cardDTO){
        return ResponseEntity.ok().body(cardService.addCard(cardDTO));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok().body(cardService.deleteCard(id));
    }
}
