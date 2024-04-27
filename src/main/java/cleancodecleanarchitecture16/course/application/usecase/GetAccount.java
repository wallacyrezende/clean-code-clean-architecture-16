package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public class GetAccount extends UseCase<GetAccount.Input, GetAccount.Output> {

    private final AccountRepository accountRepository;

    public GetAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Output execute(final Input input) {
        return accountRepository.findById(AccountId.with(input.accountId))
                .map(account -> new Output(account.accountId().value(),
                        account.name().value(),
                        account.email().value(),
                        account.cpf().value(),
                        account.carPlate().value(),
                        account.isPassenger(),
                        account.isDriver()))
                .orElseThrow(() -> new BusinessException("Account not found"));
    }

    public record Input(String accountId) {}

    public record Output(String accountId, String name, String email, String cpf, String carPlate, Boolean isPassenger, Boolean isDriver) {
    }
}
