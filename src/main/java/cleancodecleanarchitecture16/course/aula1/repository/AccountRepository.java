package cleancodecleanarchitecture16.course.aula1.repository;

import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByEmail(String email);
}
