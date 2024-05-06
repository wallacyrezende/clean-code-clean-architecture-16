package cleancodecleanarchitecture16.ride.infra.database.repositories;

import cleancodecleanarchitecture16.ride.infra.database.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmail(String email);
}
