package com.cointracker.cointrackertakehome.repository;

import com.cointracker.cointrackertakehome.entity.BitcoinAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BitcoinAddressRepository extends JpaRepository<BitcoinAddress, Long> {
    Optional<BitcoinAddress> findByAddress(String address);
}
