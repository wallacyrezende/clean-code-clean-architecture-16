package cleancodecleanarchitecture16.payment.infra.database.entities;

import cleancodecleanarchitecture16.payment.domain.entity.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    private UUID transactionId;

    @Column
    private UUID rideId;

    @Column
    private Double amount;

    @Column
    private LocalDateTime date;

    @Column
    private String status;

    public static TransactionEntity of(final Transaction transaction) {
        return new TransactionEntity(transaction.getTransactionId(), transaction.getRideId(),
                transaction.getAmount(), transaction.getDate(), transaction.getStatus());
    }

    public Transaction toTransaction() {
        return Transaction.restore(transactionId, rideId, amount, date, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity transactionEntity = (TransactionEntity) o;
        return transactionId.equals(transactionEntity.transactionId);
    }

    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }
}
