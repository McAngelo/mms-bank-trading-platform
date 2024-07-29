package com.mms.order.manager.services.interfaces;

import com.mms.order.manager.models.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> getWalletTransactions(long walletId);
    Optional<Transaction> getWalletTransaction(long transactionId);
}
