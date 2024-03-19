package com.cointracker.cointrackertakehome.controller;

import com.cointracker.cointrackertakehome.dto.AddressRequest;
import com.cointracker.cointrackertakehome.dto.AddressResponse;
import com.cointracker.cointrackertakehome.dto.BalanceResponse;
import com.cointracker.cointrackertakehome.dto.TransactionResponse;
import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;
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

    @GetMapping("")
    public ResponseEntity<?> getWallet(){
        List<AddressResponse> response = bitcoinWalletService.getWallet();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/address/add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest addressRequest){
        bitcoinWalletService.addAddress(addressRequest);
        return ResponseEntity.ok().body("address "+addressRequest.getAddress()+" added");
    }

    @DeleteMapping("/address/{address}/remove")
    public ResponseEntity<?> removeAddress(@PathVariable String address){
        bitcoinWalletService.removeAddress(address);
        return ResponseEntity.ok().body("address "+address +" removed");
    }


    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String address){
        TransactionResponse response = bitcoinWalletService.getTransactions(address);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/address/{address}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String address){
        BalanceResponse response = bitcoinWalletService.getBalance(address);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/synchronize")
    public ResponseEntity<?> synchronizeTransactions() {
        bitcoinWalletService.synchronizeTransactions();
        return ResponseEntity.ok().body("Transactions have been synchronized.");
    }
}
