package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.exceptions.WalletException;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletService {
    void createWallet(long userId, BigDecimal balance) throws WalletException;

    Optional<BigDecimal> getBalanceByWalletId(long walletId);

    Optional<BigDecimal> getBalanceByUserId(long userId);

    boolean creditWallet(long walletId, BigDecimal amount);

    boolean debitWallet(long walletId, BigDecimal amount);

    boolean enableWallet(long walletId);

    boolean disableWallet(long walletId);
}
