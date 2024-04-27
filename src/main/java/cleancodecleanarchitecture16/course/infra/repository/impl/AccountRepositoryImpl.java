package cleancodecleanarchitecture16.course.infra.repository.impl;

import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.Email;
import cleancodecleanarchitecture16.course.infra.database.entities.AccountEntity;
import cleancodecleanarchitecture16.course.infra.database.repositories.AccountJpaRepository;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    public AccountRepositoryImpl(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    @Transactional
    public Account saveAccount(Account account) {
        return accountJpaRepository.save(AccountEntity.of(account))
                .toAccount();
    }

    @Override
    public Optional<Account> findByEmail(Email email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return accountJpaRepository.findByEmail(email.value())
                .map(AccountEntity::toAccount);
    }

    @Override
    public Optional<Account> findById(AccountId id) {
        Objects.requireNonNull(id, "AccountId cannot be null");
        return accountJpaRepository.findById(UUID.fromString(id.value()))
                .map(AccountEntity::toAccount);
    }
}
