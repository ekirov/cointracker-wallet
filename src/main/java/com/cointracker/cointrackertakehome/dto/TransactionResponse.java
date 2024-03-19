package com.cointracker.cointrackertakehome.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionResponse {
    private String address;

    private List<Transaction> transactions;
    public static class Transaction {
        private String hashId;

        private BigDecimal fee;

        private BigDecimal amount;

        private LocalDateTime transactionTime;



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

        public Transaction() {
        }

        public Transaction(String hashId, BigDecimal fee, BigDecimal amount, LocalDateTime transactionTime) {
            this.hashId = hashId;
            this.fee = fee;
            this.amount = amount;
            this.transactionTime = transactionTime;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionResponse() {
    }

    public TransactionResponse(String address, List<Transaction> transactions) {
        this.address = address;
        this.transactions = transactions;
    }
}
