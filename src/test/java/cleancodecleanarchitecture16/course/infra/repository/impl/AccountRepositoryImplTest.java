package cleancodecleanarchitecture16.course.infra.repository.impl;

import cleancodecleanarchitecture16.course.IntegrationTest;
import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.domain.vo.Email;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryImplTest extends IntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldSaveAccount() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var account = Account.create(name, email, cpf, null, isPassenger, null);
        final var savedAccount = accountRepository.saveAccount(account);

        assertNotNull(savedAccount.accountId());
        assertEquals(name, savedAccount.name().value());
        assertEquals(email, savedAccount.email().value());
        assertEquals(cpf, savedAccount.cpf().value());
        assertEquals(isPassenger, savedAccount.isPassenger());
    }

    @Test
    void shouldFindByEmail() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var account = Account.create(name, email, cpf, null, isPassenger, null);
        accountRepository.saveAccount(account);
        final var foundAccount = accountRepository.findByEmail(new Email(email));

        assertTrue(foundAccount.isPresent());
    }

    @Test
    void shouldFindById() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var account = Account.create(name, email, cpf, null, isPassenger, null);
        final var savedAccount = accountRepository.saveAccount(account);
        final var foundAccount = accountRepository.findById(savedAccount.accountId());

        assertTrue(foundAccount.isPresent());
        assertEquals(savedAccount.accountId(), foundAccount.get().accountId());
    }
}