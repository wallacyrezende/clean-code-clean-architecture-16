package cleancodecleanarchitecture16.account.application.usecase;

import cleancodecleanarchitecture16.account.domain.vo.AccountId;
import cleancodecleanarchitecture16.account.infra.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetAccount extends UseCase<GetAccount.Input, Optional<GetAccount.Output>> {

    private final AccountRepository accountRepository;

    public GetAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return accountRepository.findById(AccountId.with(input.accountId))
                .map(account -> new Output(account.accountId().value(),
                        account.name().value(),
                        account.email().value(),
                        account.cpf().value(),
                        account.carPlate().value(),
                        account.isPassenger(),
                        account.isDriver()));
    }

    public record Input(String accountId) {}

    public record Output(String accountId, String name, String email, String cpf, String carPlate, Boolean isPassenger, Boolean isDriver) {
    }
}
