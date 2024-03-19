package com.cointracker.cointrackertakehome.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a Bitcoin address entity for tracking in the application.
 * <p>
 * This entity stores information about a Bitcoin address, including its unique identifier, address string,
 * current balance, creation time, and the last time transactions for this address were synchronized with the blockchain.
 */
@Entity
public class BitcoinAddress {
    /**
     * The primary key of the Bitcoin address entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The actual Bitcoin address string. This is marked unique to ensure that each address is only tracked once.
     */
    @Column(unique = true)
    private String address;

    /**
     * The current balance of the Bitcoin address. This value is updated upon synchronization with the blockchain.
     */
    private BigDecimal balance;

    /**
     * The timestamp when this Bitcoin address entity was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp of the last successful synchronization of transactions for this Bitcoin address with the blockchain.
     */
    private LocalDateTime lastSynchronized;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getLastSynchronized() {
        return lastSynchronized;
    }

    public void setLastSynchronized(LocalDateTime lastSynchronized) {
        this.lastSynchronized = lastSynchronized;
    }

    public BitcoinAddress() {

    }
    public BitcoinAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "BitcoinAddress{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
