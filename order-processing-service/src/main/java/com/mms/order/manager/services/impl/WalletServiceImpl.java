package com.mms.order.manager.services.impl;

import com.mms.order.manager.models.User;
import com.mms.order.manager.models.Wallet;
import com.mms.order.manager.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public boolean createWallet(long userId, BigDecimal balance) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return false;
        }

        var wallet = Wallet.builder()
                .user(userOptional.get())
                .balance(balance)
                .isActive(true)
                .build();

        walletRepository.save(wallet);
        return true;
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
