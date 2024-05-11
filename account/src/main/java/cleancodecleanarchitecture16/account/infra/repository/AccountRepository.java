package cleancodecleanarchitecture16.account.infra.repository;


import cleancodecleanarchitecture16.account.domain.entity.Account;
import cleancodecleanarchitecture16.account.domain.vo.AccountId;
import cleancodecleanarchitecture16.account.domain.vo.Email;

import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> findByEmail(Email email);
    Optional<Account> findById(AccountId id);
}
