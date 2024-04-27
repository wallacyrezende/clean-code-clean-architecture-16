package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.IntegrationTest;
import cleancodecleanarchitecture16.course.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignupTest extends IntegrationTest {

    @Autowired
    private Signup signup;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MailerGateway mailerGateway;

    @Test
    @DisplayName("Should create a account to passenger")
    void shouldSignupAPassenger() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var input = new Signup.Input(name, email,  cpf, null, isPassenger, null);

        final var output = signup.execute(input);

        assertNotNull(output);
    }

    @Test
    @DisplayName("Should create a account to driver")
    void shouldSignupADriver() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var carPlate = "ABC1234";
        final var isDriver = true;
        final var input = new Signup.Input(name, email,  cpf, carPlate, null, isDriver);

        final var output = signup.execute(input);

        assertNotNull(output);
    }

    @Test
    @DisplayName("Should not create a account with invalid name")
    void shouldNotSignupAAccountWithInvalidName() {
        final var name = "John1 Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var input = new Signup.Input(name, email,  cpf, null, isPassenger, null);

        final var error = assertThrows(BusinessException.class, () -> signup.execute(input));

        assertEquals("Name is invalid", error.getMessage());
    }

    @Test
    @DisplayName("Should not create a account with invalid email")
    void shouldNotSignupAAccountWithInvalidEmail() {
        final var name = "John Doe";
        final var email = "email" + random();
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var input = new Signup.Input(name, email,  cpf, null, isPassenger, null);

        final var error = assertThrows(BusinessException.class, () -> signup.execute(input));

        assertEquals("Email is invalid", error.getMessage());
    }

    @Test
    @DisplayName("Should not create a account with invalid cpf")
    void shouldNotSignupAAccountWithInvalidCpf() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "123.456.789-10";
        final var isPassenger = true;
        final var input = new Signup.Input(name, email,  cpf, null, isPassenger, null);

        final var error = assertThrows(BusinessException.class, () -> signup.execute(input));

        assertEquals("Cpf is invalid", error.getMessage());
    }

    @Test
    @DisplayName("Should not create a account to driver with invalid name")
    void shouldNotSignupAAccountToDriverWithInvalidName() {
        final var name = "John1 Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var carPlate = "ABC1234";
        final var isDriver = true;
        final var input = new Signup.Input(name, email,  cpf, carPlate, null, isDriver);

        final var error = assertThrows(BusinessException.class, () -> signup.execute(input));

        assertEquals("Name is invalid", error.getMessage());
    }
}