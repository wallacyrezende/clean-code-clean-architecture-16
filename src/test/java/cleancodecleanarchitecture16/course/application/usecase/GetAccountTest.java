package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.IntegrationTest;
import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAccountTest extends IntegrationTest {

    @Autowired
    GetAccount getAccount;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void shouldGetAccountById() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var savedAccount = accountRepository.saveAccount(Account.create(name, email, cpf, null, isPassenger, null));
        final var input = new GetAccount.Input(savedAccount.accountId().value());

        final var output = getAccount.execute(input).get();

        assertEquals(savedAccount.accountId().value(), output.accountId());
        assertEquals(name, output.name());
        assertEquals(email, output.email());
        assertEquals(cpf, output.cpf());
        assertEquals(isPassenger, output.isPassenger());
    }
}
