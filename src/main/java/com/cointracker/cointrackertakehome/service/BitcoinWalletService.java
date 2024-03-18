package com.cointracker.cointrackertakehome.service;

import java.math.BigDecimal;
import java.util.List;

public interface BitcoinWalletService {
    void addAddress(String address);
    void removeAddress(String address);
    List<String> getTransactions(String address);
    BigDecimal getBalance(String address);

    void synchronizeTransactions();
}
