package com.cointracker.cointrackertakehome.service.impl;

import com.cointracker.cointrackertakehome.dto.*;
import com.cointracker.cointrackertakehome.entity.BitcoinAddress;
import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;
import com.cointracker.cointrackertakehome.repository.BitcoinAddressRepository;
import com.cointracker.cointrackertakehome.repository.BitcoinTransactionRepository;
import com.cointracker.cointrackertakehome.service.BitcoinWalletService;
import com.cointracker.cointrackertakehome.service.BlockchainExplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public TransactionResponse getTransactions(String address) {
        //TODO: wrap return entity
        if(this.bitcoinAddressRepository.findByAddress(address).orElse(null) != null){
            List<BitcoinTransaction> transactions =  bitcoinTransactionRepository.findByAddress(address);
            return convertTransactionsToDTO(transactions, address);
        } else {
            //address does not exist
        }



        return null;
    }

    private TransactionResponse convertTransactionsToDTO(List<BitcoinTransaction> transactions, String address) {
        TransactionResponse dto = new TransactionResponse();
        dto.setAddress(address);

        // Convert the list of BitcoinTransaction entities to a list of TransactionDTO.Transaction
        List<TransactionResponse.Transaction> transactionList = transactions.stream()
                .map(this::convertToTransactionDto)
                .collect(Collectors.toList());

        // Set the converted list on the TransactionDTO
        dto.setTransactions(transactionList);
        return dto;
    }

    private TransactionResponse.Transaction convertToTransactionDto(BitcoinTransaction entity) {
        TransactionResponse.Transaction transaction = new TransactionResponse.Transaction();
        transaction.setHashId(entity.getHashId());
        transaction.setFee(entity.getFee());
        transaction.setAmount(entity.getAmount());
        transaction.setTransactionTime(entity.getTransactionTime());
        return transaction;
    }

    @Override
    public BalanceResponse getBalance(String address) {
        BitcoinAddress bitcoinAddress = this.bitcoinAddressRepository.findByAddress(address).orElse(null);
        if(bitcoinAddress != null){
            return new BalanceResponse(address, bitcoinAddress.getBalance());
        } else {
            //address does not exist
        }

        return null;
    }

    //@Scheduled(fixedRate = 300000)
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

    @Override
    public List<AddressResponse> getWallet() {
        List<BitcoinAddress> bitcoinAddresses = this.bitcoinAddressRepository.findAll();
        List<AddressResponse> response = new ArrayList<>();
        for(BitcoinAddress bitcoinAddress : bitcoinAddresses){
            response.add(new AddressResponse(bitcoinAddress.getAddress(), bitcoinAddress.getBalance(), bitcoinAddress.getLastSynchronized()));
        }
        return response;
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
