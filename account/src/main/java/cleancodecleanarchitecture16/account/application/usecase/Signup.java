package cleancodecleanarchitecture16.account.application.usecase;

import cleancodecleanarchitecture16.account.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.account.domain.entity.Account;
import cleancodecleanarchitecture16.account.domain.vo.Email;
import cleancodecleanarchitecture16.account.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.account.infra.repository.AccountRepository;
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
        if (accountRepository.findByEmail(new Email(input.email())).isPresent())
            throw new BusinessException("Email already exists");
    }

    public record Input(String name, String email, String cpf, String carPlate, Boolean isPassenger, Boolean isDriver) {
    }

    public record Output(String accountId) {
    }
}
