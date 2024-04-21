package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.Account;
import cleancodecleanarchitecture16.course.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class Signup extends UseCase<Signup.Input, Signup.Output> {

    private final AccountRepository accountRepository;
    private final MailerGateway mailerGateway;

    public Signup(AccountRepository accountRepository, MailerGateway mailerGateway) {
        this.accountRepository = accountRepository;
        this.mailerGateway = mailerGateway;
    }

    @Override
    public Output execute(final Input input) {
        validateEmailAlreadyExists(input);
        var account = accountRepository
                .saveAccount(Account.create(input.name, input.email, input.cpf, input.carPlate, input.isPassenger, input.isDriver));
        mailerGateway.send(account.email().value(), "Welcome " + account.name().value() + "!", "");
        return new Output(account.accountId().value());
    }

    private void validateEmailAlreadyExists(final Input input) {
        if (accountRepository.findByEmail(input.email()).isPresent())
            throw new BusinessException("Email already exists");
    }

    public record Input(String name, String email, String cpf, String carPlate, Boolean isPassenger, Boolean isDriver) {
    }

    public record Output(String id) {
    }
}
