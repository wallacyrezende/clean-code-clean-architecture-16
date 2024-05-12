package cleancodecleanarchitecture16.payment.infra.database.repositories;

import cleancodecleanarchitecture16.payment.infra.database.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
}
