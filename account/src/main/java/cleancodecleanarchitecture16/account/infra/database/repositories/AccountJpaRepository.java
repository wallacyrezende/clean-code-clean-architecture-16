package cleancodecleanarchitecture16.account.infra.database.repositories;

import cleancodecleanarchitecture16.account.infra.database.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmail(String email);
}
