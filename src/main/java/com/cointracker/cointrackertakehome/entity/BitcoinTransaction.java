package com.cointracker.cointrackertakehome.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a Bitcoin transaction.
 * <p>
 * Stores details of individual transactions associated with Bitcoin addresses,
 * including transaction hash, fees, amounts, and timestamps.
 */
@Table(name = "bitcoin_transaction", indexes = {
        @Index(name = "idx_address", columnList = "address")
})
@Entity
public class BitcoinTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private String hashId;

    private BigDecimal fee;

    private BigDecimal amount;

    private LocalDateTime transactionTime;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BitcoinTransaction(){

    }



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}