package com.cointracker.cointrackertakehome.service;

import com.cointracker.cointrackertakehome.dto.TransactionData;

public interface BlockchainExplorerService {
    TransactionData getTransactionData(String address);
}
