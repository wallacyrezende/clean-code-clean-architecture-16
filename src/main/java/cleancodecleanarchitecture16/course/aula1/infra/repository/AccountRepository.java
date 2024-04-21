package cleancodecleanarchitecture16.course.aula1.infra.repository;


import cleancodecleanarchitecture16.course.aula1.domain.Account;

import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> findByEmail(String email);
}
