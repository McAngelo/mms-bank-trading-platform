package com.mms.order.manager.services.impl;

import com.mms.order.manager.exceptions.WalletException;
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
    public void createWallet(long userId, BigDecimal balance) throws WalletException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new WalletException("User does not exist, could not create wallet");
        }

        var wallet = Wallet.builder()
                .owner(userOptional.get())
                .balance(balance)
                //.isActive(true)
                .status(Wallet.Status.ACTIVE)
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

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
            return Optional.empty();
        }

        return Optional.of(wallet.getBalance());
    }

    @Override
    public Optional<BigDecimal> getBalanceByOwnerId(long userId) {
        Optional<Wallet> optionalWallet =  walletRepository.findByOwnerId(userId);

        if (optionalWallet.isEmpty()) {
            return Optional.empty();
        }

        var wallet = optionalWallet.get();

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
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

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
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

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
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

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
            return true;
        }

        wallet.setStatus(Wallet.Status.ACTIVE);
        //wallet.setActive(true);
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

        if (wallet.getStatus() != Wallet.Status.ACTIVE) {
            return true;
        }

        wallet.setStatus(Wallet.Status.DISABLED);
        //wallet.setActive(false);
        walletRepository.save(wallet);

        return true;
    }
}
