package cleancodecleanarchitecture16.course.aula1.infra.repository.impl;

import cleancodecleanarchitecture16.course.aula1.domain.Account;
import cleancodecleanarchitecture16.course.aula1.infra.jpa.entities.AccountEntity;
import cleancodecleanarchitecture16.course.aula1.infra.jpa.repositories.AccountJpaRepository;
import cleancodecleanarchitecture16.course.aula1.infra.repository.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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
    public Optional<Account> findByEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return accountJpaRepository.findByEmail(email)
                .map(AccountEntity::toAccount);
    }
}
