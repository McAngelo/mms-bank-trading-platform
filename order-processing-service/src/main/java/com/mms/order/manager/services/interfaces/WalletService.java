package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.exceptions.WalletException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public interface WalletService {
    void createWallet(long userId, BigDecimal balance) throws WalletException;

    Optional<BigDecimal> getBalanceByWalletId(long walletId);

    Optional<BigDecimal> getBalanceByUserId(long userId);

    boolean creditWallet(long walletId, BigDecimal amount);

    boolean debitWallet(long walletId, BigDecimal amount);

    boolean debitWalletByUsedId(long userId, BigDecimal amount);

    boolean enableWallet(long walletId);

    boolean disableWallet(long walletId);
}
