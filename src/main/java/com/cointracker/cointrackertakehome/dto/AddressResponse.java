package com.cointracker.cointrackertakehome.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AddressResponse {
    private String address;
    private BigDecimal balance;
    private LocalDateTime lastSynchronized;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public AddressResponse(String address, BigDecimal balance, LocalDateTime lastSynchronized) {
        this.address = address;
        this.balance = balance;
        this.lastSynchronized = lastSynchronized;
    }
}
