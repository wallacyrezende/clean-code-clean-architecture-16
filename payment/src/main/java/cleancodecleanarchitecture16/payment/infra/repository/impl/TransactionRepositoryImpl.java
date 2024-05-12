package cleancodecleanarchitecture16.payment.infra.repository.impl;

import cleancodecleanarchitecture16.payment.domain.entity.Transaction;
import cleancodecleanarchitecture16.payment.infra.database.entities.TransactionEntity;
import cleancodecleanarchitecture16.payment.infra.database.repositories.TransactionJpaRepository;
import cleancodecleanarchitecture16.payment.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionJpaRepository.save(TransactionEntity.of(transaction))
                .toTransaction();
    }
}
