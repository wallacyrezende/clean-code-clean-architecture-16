package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends Mapper{

    public Account buildAccount(AccountDTO accountDTO) {
        if (isNullOrEmpty(accountDTO))
            throw new BusinessException("Account dto cannot be null", 0);
        return Account.builder()
                .name(accountDTO.getName())
                .email(accountDTO.getEmail())
                .cpf(accountDTO.getCpf())
                .carPlate(accountDTO.getCarPlate())
                .isPassenger(accountDTO.getIsPassenger())
                .isDriver(accountDTO.getIsDriver())
                .build();
    }

    public AccountDTO buildAccountDTO(Account account) {
        if (isNullOrEmpty(account))
            throw new BusinessException("Account cannot be null", 0);
        return AccountDTO.builder()
                .name(account.getName())
                .email(account.getEmail())
                .cpf(account.getCpf())
                .carPlate(account.getCarPlate())
                .isPassenger(account.getIsPassenger())
                .isDriver(account.getIsDriver())
                .build();
    }
}
