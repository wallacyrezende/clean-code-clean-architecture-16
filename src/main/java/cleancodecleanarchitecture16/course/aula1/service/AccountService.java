package cleancodecleanarchitecture16.course.aula1.service;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;

import java.util.UUID;

public interface AccountService {
    void validateEmailAlreadyExists(AccountDTO accountDTO) throws BusinessException;

    UUID saveAccount(AccountDTO accountDTO);
    UUID signup(AccountDTO accountDTO) throws BusinessException;
}
