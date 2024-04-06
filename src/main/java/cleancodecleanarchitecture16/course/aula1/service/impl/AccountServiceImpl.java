package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.AccountMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.repository.AccountRepository;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public void validateEmailAlreadyExists(AccountDTO accountDTO) throws Exception {
        if (Objects.nonNull(accountRepository.findByEmail(accountDTO.getEmail())))
            throw new Exception("Email already exists");
    }

    @Override
    public UUID saveAccount(AccountDTO accountDTO) {
        return accountRepository.save(accountMapper.buildAccount(accountDTO)).getAccountId();
    }
}
