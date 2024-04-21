package cleancodecleanarchitecture16.course.aula1.infra.jpa.repositories;

import cleancodecleanarchitecture16.course.aula1.infra.jpa.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByEmail(String email);
}
