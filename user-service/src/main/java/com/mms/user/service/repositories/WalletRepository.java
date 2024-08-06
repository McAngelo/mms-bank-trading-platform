package com.mms.user.service.repositories;

import com.mms.user.service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository  extends JpaRepository<Wallet, Integer>, JpaSpecificationExecutor<Wallet> {
    List<Wallet> findAllByOwnerId(Integer OwnerId);
}
