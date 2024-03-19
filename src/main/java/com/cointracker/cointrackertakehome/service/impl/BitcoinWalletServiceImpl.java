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

/**
 * Service implementation for managing Bitcoin wallet operations.
 * <p>
 * This service handles the addition and removal of Bitcoin addresses, synchronization of transactions,
 * retrieval of balance and transaction details, and offers a consolidated view of the wallet.
 */
@Service
public class BitcoinWalletServiceImpl implements BitcoinWalletService {
    @Autowired
    private BitcoinAddressRepository bitcoinAddressRepository;
    @Autowired
    private BitcoinTransactionRepository bitcoinTransactionRepository;
    @Autowired
    private BlockchainExplorerService blockchainExplorerService;

    /**
     * Adds a new Bitcoin address to be tracked.
     *
     * @param addressRequest The request containing the Bitcoin address to add.
     */
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
            throw new RuntimeException("address "+addressRequest.getAddress()+" already exists in wallet");
        }
    }

    /**
     * Removes a tracked Bitcoin address and its associated transactions.
     *
     * @param address The Bitcoin address to remove.
     */
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

    /**
     * Retrieves transaction details for a specific Bitcoin address.
     *
     * @param address The Bitcoin address for which transactions are requested.
     * @return A {@link TransactionResponse} containing the transaction details.
     */
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

    /**
     * Converts a list of {@link BitcoinTransaction} entities to a {@link TransactionResponse}.
     *
     * @param transactions The list of transactions to convert.
     * @param address      The Bitcoin address associated with the transactions.
     * @return The {@link TransactionResponse} containing the converted transactions.
     */
    private TransactionResponse convertTransactionsToDTO(List<BitcoinTransaction> transactions, String address) {
        TransactionResponse dto = new TransactionResponse();
        dto.setAddress(address);

        List<TransactionResponse.Transaction> transactionList = transactions.stream()
                .map(this::convertToTransactionDto)
                .collect(Collectors.toList());

        dto.setTransactions(transactionList);
        return dto;
    }

    /**
     * Converts a single {@link BitcoinTransaction} entity to a {@link TransactionResponse.Transaction} DTO.
     *
     * @param entity The transaction entity to convert.
     * @return The converted {@link TransactionResponse.Transaction} DTO.
     */
    private TransactionResponse.Transaction convertToTransactionDto(BitcoinTransaction entity) {
        TransactionResponse.Transaction transaction = new TransactionResponse.Transaction();
        transaction.setHashId(entity.getHashId());
        transaction.setFee(entity.getFee());
        transaction.setAmount(entity.getAmount());
        transaction.setTransactionTime(entity.getTransactionTime());
        return transaction;
    }


    /**
     * Retrieves the balance for a specific Bitcoin address.
     *
     * @param address The Bitcoin address for which the balance is requested.
     * @return A {@link BalanceResponse} containing the balance.
     */
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

    /**
     * Periodically/manually synchronizes transactions for all tracked Bitcoin addresses.
     * <p>
     * This method can be scheduled to run at fixed intervals to ensure the wallet data is up-to-date.
     */
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



    /**
     * Parses the transaction data fetched from the blockchain explorer and persists it to the database.
     *
     * @param response     The transaction data fetched from the blockchain explorer.
     * @param bca          The {@link BitcoinAddress} entity associated with the transactions.
     */
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

    /**
     * Retrieves a consolidated view of the wallet, including all tracked Bitcoin addresses and their information.
     *
     * @return A list of {@link AddressResponse} containing details of each tracked Bitcoin address.
     */
    @Override
    public List<AddressResponse> getWallet() {
        List<BitcoinAddress> bitcoinAddresses = this.bitcoinAddressRepository.findAll();
        List<AddressResponse> response = new ArrayList<>();
        for(BitcoinAddress bitcoinAddress : bitcoinAddresses){
            response.add(new AddressResponse(bitcoinAddress.getAddress(), bitcoinAddress.getBalance(), bitcoinAddress.getLastSynchronized()));
        }
        return response;
    }


}
