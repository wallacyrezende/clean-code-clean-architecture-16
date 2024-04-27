package cleancodecleanarchitecture16.course.infra.repository;


import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.Email;

import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> findByEmail(Email email);
    Optional<Account> findById(AccountId id);
}
