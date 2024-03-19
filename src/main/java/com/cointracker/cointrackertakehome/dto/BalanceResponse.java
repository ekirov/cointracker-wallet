package com.cointracker.cointrackertakehome.dto;

import java.math.BigDecimal;

public class BalanceResponse {
    private String address;

    private BigDecimal balance;

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

    public BalanceResponse(String address, BigDecimal balance) {
        this.address = address;
        this.balance = balance;
    }
}
