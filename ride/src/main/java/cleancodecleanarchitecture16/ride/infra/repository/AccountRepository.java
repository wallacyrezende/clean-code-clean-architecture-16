package cleancodecleanarchitecture16.ride.infra.repository;


import cleancodecleanarchitecture16.ride.domain.entity.Account;
import cleancodecleanarchitecture16.ride.domain.vo.AccountId;
import cleancodecleanarchitecture16.ride.domain.vo.Email;

import java.util.Optional;

public interface AccountRepository {
    Account saveAccount(Account account);
    Optional<Account> findByEmail(Email email);
    Optional<Account> findById(AccountId id);
}
