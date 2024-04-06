package cleancodecleanarchitecture16.course.aula1.service;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;

import java.util.UUID;

public interface AccountService {
    void validateEmailAlreadyExists(AccountDTO accountDTO) throws Exception;

    UUID saveAccount(AccountDTO accountDTO);
}
