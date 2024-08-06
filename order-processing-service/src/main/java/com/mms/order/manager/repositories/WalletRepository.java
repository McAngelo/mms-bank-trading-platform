package com.mms.order.manager.repositories;

import com.mms.order.manager.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByOwnerId(Long ownerId);
}
