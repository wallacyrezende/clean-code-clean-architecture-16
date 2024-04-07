package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account buildAccount(AccountDTO accountDTO) {
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
