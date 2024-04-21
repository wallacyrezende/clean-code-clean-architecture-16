package cleancodecleanarchitecture16.course.infra.repository;


import cleancodecleanarchitecture16.course.domain.Account;

import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> findByEmail(String email);
}
