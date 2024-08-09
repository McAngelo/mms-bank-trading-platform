package com.mms.order.manager.services.impl;

import com.mms.order.manager.exceptions.WalletException;
import com.mms.order.manager.models.Wallet;
import com.mms.order.manager.repositories.WalletRepository;
import com.mms.order.manager.services.interfaces.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void createWallet(long userId, BigDecimal balance) throws WalletException {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);

        if (optionalWallet.isPresent()) {
            throw new WalletException("Wallet already exists");
        }

        var wallet = Wallet.builder()
                .userId(userId)
                .balance(balance)
                .isActive(true)
                .build();

        walletRepository.save(wallet);
    }

    @Override
    public Optional<BigDecimal> getBalanceByWalletId(long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isEmpty()) {
            return Optional.empty();
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return Optional.empty();
        }

        return Optional.of(wallet.getBalance());
    }

    @Override
    public Optional<BigDecimal> getBalanceByUserId(long userId) {
        Optional<Wallet> optionalWallet =  walletRepository.findByUserId(userId);

        if (optionalWallet.isEmpty()) {
            return Optional.empty();
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return Optional.empty();
        }

        return Optional.of(wallet.getBalance());
    }

    @Override
    public boolean creditWalletByUserId(long userId, BigDecimal amount){
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return false;
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean creditWallet(long walletId, BigDecimal amount) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return false;
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean debitWallet(long walletId, BigDecimal amount) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return false;
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean debitWalletByUsedId(long userId, BigDecimal amount) {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(userId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return false;
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean enableWallet(long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (wallet.isActive()) {
            return true;
        }

        wallet.setActive(true);
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean disableWallet(long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);

        if (optionalWallet.isEmpty()) {
            return false;
        }

        var wallet = optionalWallet.get();

        if (!wallet.isActive()) {
            return true;
        }

        wallet.setActive(false);
        walletRepository.save(wallet);

        return true;
    }
}
