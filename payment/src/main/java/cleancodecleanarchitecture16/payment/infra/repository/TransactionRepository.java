package cleancodecleanarchitecture16.payment.infra.repository;

import cleancodecleanarchitecture16.payment.domain.entity.Transaction;

public interface TransactionRepository {
    Transaction saveTransaction(Transaction transaction);
}
