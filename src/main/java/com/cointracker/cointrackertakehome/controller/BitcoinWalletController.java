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

/**
 * Controller for managing Bitcoin wallet operations.
 * <p>
 * Provides endpoints for adding and removing Bitcoin addresses, synchronizing transactions,
 * retrieving balances, and transaction details for addresses.
 */
@Controller
@RequestMapping("/api/bitcoin-wallet")
public class BitcoinWalletController {

    private final BitcoinWalletService bitcoinWalletService;

    /**
     * Constructs the BitcoinWalletController with the required BitcoinWalletService.
     *
     * @param bitcoinWalletService the service to manage Bitcoin wallet operations.
     */
    @Autowired
    public BitcoinWalletController(BitcoinWalletService bitcoinWalletService){
        this.bitcoinWalletService = bitcoinWalletService;
    }

    /**
     * Retrieves all tracked Bitcoin addresses and their information.
     *
     * @return a ResponseEntity containing a list of AddressResponse objects.
     */
    @GetMapping("")
    public ResponseEntity<?> getWallet(){
        List<AddressResponse> response = bitcoinWalletService.getWallet();
        return ResponseEntity.ok().body(response);
    }

    /**
     * Adds a new Bitcoin address to be tracked.
     *
     * @param addressRequest the request containing the Bitcoin address to add.
     * @return a ResponseEntity with a confirmation message.
     */
    @PostMapping("/address/add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest addressRequest){
        bitcoinWalletService.addAddress(addressRequest);
        return ResponseEntity.ok().body("address "+addressRequest.getAddress()+" added");
    }

    /**
     * Removes a tracked Bitcoin address and its associated transactions.
     *
     * @param address the Bitcoin address to remove.
     * @return a ResponseEntity with a confirmation message.
     */
    @DeleteMapping("/address/{address}/remove")
    public ResponseEntity<?> removeAddress(@PathVariable String address){
        bitcoinWalletService.removeAddress(address);
        return ResponseEntity.ok().body("address "+address +" removed");
    }

    /**
     * Retrieves transaction details for a specific Bitcoin address.
     *
     * @param address the Bitcoin address for which transactions are requested.
     * @return a ResponseEntity containing the TransactionResponse.
     */
    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable String address){
        TransactionResponse response = bitcoinWalletService.getTransactions(address);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Retrieves the balance for a specific Bitcoin address.
     *
     * @param address the Bitcoin address for which the balance is requested.
     * @return a ResponseEntity containing the BalanceResponse.
     */
    @GetMapping("/address/{address}/balance")
    public ResponseEntity<?> getBalance(@PathVariable String address){
        BalanceResponse response = bitcoinWalletService.getBalance(address);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Triggers the synchronization of transactions for all tracked Bitcoin addresses.
     *
     * @return a ResponseEntity with a confirmation message that transactions have been synchronized.
     */
    @PostMapping("/synchronize")
    public ResponseEntity<?> synchronizeTransactions() {
        bitcoinWalletService.synchronizeTransactions();
        return ResponseEntity.ok().body("Transactions have been synchronized.");
    }
}
