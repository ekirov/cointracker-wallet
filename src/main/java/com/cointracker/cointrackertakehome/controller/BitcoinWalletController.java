package com.cointracker.cointrackertakehome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/bitcoin-wallet")
public class BitcoinWalletController {
    @PostMapping("/address/add")
    public ResponseEntity<?> addAddress(@RequestBody String address){
        return ResponseEntity.ok().body("address added");
    }

    @PostMapping("/address/remove")
    public ResponseEntity<?> removeAddress(@RequestBody String address){
        return ResponseEntity.ok().body("address removed");
    }

    @GetMapping("/address/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String address){
        return ResponseEntity.ok().body("transaction data for address");
    }

    @GetMapping("/address/balance")
    public ResponseEntity<?> getBalance(@PathVariable String address){
        return ResponseEntity.ok().body("balance for address");
    }
}
