package com.cointracker.cointrackertakehome.service.impl;

import com.cointracker.cointrackertakehome.entity.BitcoinAddress;
import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;
import com.cointracker.cointrackertakehome.repository.BitcoinAddressRepository;
import com.cointracker.cointrackertakehome.repository.BitcoinTransactionRepository;
import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BitcoinWalletServiceImpl implements BitcoinWalletService {
    @Autowired
    private BitcoinAddressRepository bitcoinAddressRepository;
    @Autowired
    private BitcoinTransactionRepository bitcoinTransactionRepository;

    @Override
    public void addAddress(String address) {
        BitcoinAddress bitcoinAddress = new BitcoinAddress(address);
        try {
            bitcoinAddressRepository.save(bitcoinAddress);
        } catch (DataIntegrityViolationException e) {
            // Handle the exception, e.g., log it or throw a custom exception
        }
    }

    @Override
    public void removeAddress(String address) {
        BitcoinAddress bitcoinAddress = this.bitcoinAddressRepository.findByAddress(address).orElse(null);
        if(bitcoinAddress != null){
            List<BitcoinTransaction> transactions = bitcoinTransactionRepository.findByAddress(address);
            if(transactions != null){
                this.bitcoinTransactionRepository.deleteAll(transactions);
            }
            this.bitcoinAddressRepository.delete(bitcoinAddress);
        } else {
            //handle exception
        }

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
