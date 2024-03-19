package com.cointracker.cointrackertakehome.service;

import com.cointracker.cointrackertakehome.dto.AddressRequest;
import com.cointracker.cointrackertakehome.dto.AddressResponse;
import com.cointracker.cointrackertakehome.dto.BalanceResponse;
import com.cointracker.cointrackertakehome.dto.TransactionResponse;
import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface BitcoinWalletService {
    void addAddress(AddressRequest address);
    void removeAddress(String address);
    TransactionResponse getTransactions(String address);
    BalanceResponse getBalance(String address);

    void synchronizeTransactions();

    List<AddressResponse> getWallet();
}
