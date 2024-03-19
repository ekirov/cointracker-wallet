package com.cointracker.cointrackertakehome.service.impl;

import com.cointracker.cointrackertakehome.dto.AddressRequest;
import com.cointracker.cointrackertakehome.entity.BitcoinAddress;
import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;
import com.cointracker.cointrackertakehome.dto.TransactionData;
import com.cointracker.cointrackertakehome.repository.BitcoinAddressRepository;
import com.cointracker.cointrackertakehome.repository.BitcoinTransactionRepository;
import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import com.cointracker.cointrackertakehome.service.BlockchainExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BitcoinWalletServiceImpl implements BitcoinWalletService {
    @Autowired
    private BitcoinAddressRepository bitcoinAddressRepository;
    @Autowired
    private BitcoinTransactionRepository bitcoinTransactionRepository;
    @Autowired
    private BlockchainExplorerService blockchainExplorerService;

    @Override
    public void addAddress(AddressRequest addressRequest) {
        if(addressRequest == null){
            // handle
        }
        BitcoinAddress bitcoinAddress = new BitcoinAddress(addressRequest.getAddress());
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
    public List<BitcoinTransaction> getTransactions(String address) {
        //TODO: wrap return entity
        if(this.bitcoinAddressRepository.findByAddress(address).orElse(null) != null){
            return bitcoinTransactionRepository.findByAddress(address);
        } else {
            //address does not exist
        }

        return null;
    }

    @Override
    public BigDecimal getBalance(String address) {
        BitcoinAddress bitcoinAddress = this.bitcoinAddressRepository.findByAddress(address).orElse(null);
        if(bitcoinAddress != null){
            return bitcoinAddress.getBalance();
        } else {
            //address does not exist
        }

        return null;
    }

    @Override
    public void synchronizeTransactions() {
        List<BitcoinAddress> bitcoinAddresses = this.bitcoinAddressRepository.findAll();
        if(bitcoinAddresses != null){
            for(BitcoinAddress bca : bitcoinAddresses){
                TransactionData response = blockchainExplorerService.getTransactionData(bca.getAddress());
                if(response != null){
                    parseAndPersistTransactionData(response, bca);
                }
            }
        }
    }

    private void parseAndPersistTransactionData(TransactionData response, BitcoinAddress bca){
        LocalDateTime lastSynchronized = bca.getLastSynchronized();
        bca.setLastSynchronized(LocalDateTime.now());
        bca.setBalance(response.getFinalBalance());
        this.bitcoinAddressRepository.save(bca);

        for (TransactionData.Transaction transaction : response.getTransactions()) {
            LocalDateTime transactionDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(transaction.getTime()),
                    ZoneId.systemDefault());
            if(lastSynchronized == null || transactionDateTime.isAfter(lastSynchronized)){
                BitcoinTransaction bct = new BitcoinTransaction();
                bct.setAddress(bca.getAddress());
                bct.setHashId(transaction.getHashId());
                bct.setFee(transaction.getFee());
                bct.setTransactionTime(transactionDateTime);
                bct.setAmount(transaction.getResult());
                this.bitcoinTransactionRepository.save(bct);
            } else {
                //optimization can be done here
            }
        }
    }


}
