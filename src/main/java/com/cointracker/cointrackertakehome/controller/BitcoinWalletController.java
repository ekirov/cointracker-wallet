package com.cointracker.cointrackertakehome.controller;

import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/bitcoin-wallet")
public class BitcoinWalletController {

    private final BitcoinWalletService bitcoinWalletService;

    @Autowired
    public BitcoinWalletController(BitcoinWalletService bitcoinWalletService){
        this.bitcoinWalletService = bitcoinWalletService;
    }

    @PostMapping("/address/add")
    public ResponseEntity<?> addAddress(@RequestBody String address){
        bitcoinWalletService.addAddress(address);
        return ResponseEntity.ok().body("address added");
    }

    @PostMapping("/address/remove")
    public ResponseEntity<?> removeAddress(@RequestBody String address){
        bitcoinWalletService.removeAddress(address);
        return ResponseEntity.ok().body("address removed");
    }

    @GetMapping("/address/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String address){
        List<String> transactions = bitcoinWalletService.getTransactions(address);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/address/balance")
    public ResponseEntity<?> getBalance(@PathVariable String address){
        BigDecimal balance = bitcoinWalletService.getBalance(address);
        return ResponseEntity.ok().body(balance);
    }
}
