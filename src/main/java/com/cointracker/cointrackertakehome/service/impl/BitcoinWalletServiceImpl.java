package com.cointracker.cointrackertakehome.service.impl;

import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class BitcoinWalletServiceImpl implements BitcoinWalletService {
    @Override
    public void addAddress(String address) {

    }

    @Override
    public void removeAddress(String address) {

    }

    @Override
    public List<String> getTransactions(String address) {
        return null;
    }

    @Override
    public BigDecimal getBalance(String address) {
        return null;
    }

    @Override
    public void synchronizeTransactions() {

    }
}
