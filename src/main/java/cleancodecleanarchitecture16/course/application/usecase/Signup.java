package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.Account;
import cleancodecleanarchitecture16.course.domain.ValidateCpf;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class Signup extends UseCase<Signup.Input, Signup.Output> {

    private final AccountRepository accountRepository;

    public Signup(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Output execute(final Input input) {
        validateFieldsAccount(input);
        Account account = accountRepository.saveAccount(Account.create(input.name, input.email, input.cpf, input.carPlate,
                input.isPassenger, input.isDriver));
        return new Output(account.accountId().value());
    }

    private void validateFieldsAccount(final Input input) {
        validateEmailAlreadyExists(input);
        if (!input.name().matches("[a-zA-Z]+ [a-zA-Z]+"))
            throw new BusinessException("Name is invalid", -3);
        if (!input.email().matches("^(.+)@(.+)$"))
            throw new BusinessException("Email is invalid", -2);
        if (!cpfIsValid(input))
            throw new BusinessException("Cpf is invalid", -1);
        if (input.isDriver()) {
            if (!input.carPlate().matches("[A-Z]{3}[0-9]{4}"))
                throw new BusinessException("Car plate is invalid", -5);
        }
    }

    private void validateEmailAlreadyExists(final Input input) {
        if (accountRepository.findByEmail(input.email()).isPresent())
            throw new BusinessException("Email already exists", -4);
    }

    private static boolean cpfIsValid(final Input input) {
        return ValidateCpf.validate(input.cpf());
    }

    public record Input(String name, String email, String cpf, String carPlate, Boolean isPassenger, Boolean isDriver) {
    }

    public record Output(String id) {
    }
}
