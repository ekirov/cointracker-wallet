package com.cointracker.cointrackertakehome.repository;

import com.cointracker.cointrackertakehome.entity.BitcoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BitcoinTransactionRepository extends JpaRepository<BitcoinTransaction, Long> {
    List<BitcoinTransaction> findByAddress(String address);

}
