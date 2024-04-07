package cleancodecleanarchitecture16.course.aula1.service;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;

import java.util.UUID;

public interface AccountService {
    UUID create(AccountDTO accountDTO);
    AccountDTO findByAccountId(UUID accountId);
}
