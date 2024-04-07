package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.AccountMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.repository.AccountRepository;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static cleancodecleanarchitecture16.course.aula1.validateCpf.validate;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public UUID create(AccountDTO accountDTO) throws BusinessException {
        validateFieldsAccount(accountDTO);
        return saveAccount(accountDTO);
    }

    @Override
    public AccountDTO findByAccountId(UUID accountId) {
        return accountMapper.buildAccountDTO(accountRepository.findById(accountId).get());
    }

    private void validateFieldsAccount(AccountDTO accountDTO) throws BusinessException {
        validateEmailAlreadyExists(accountDTO);
        if (!accountDTO.getName().matches("[a-zA-Z]+ [a-zA-Z]+"))
            throw new BusinessException("Name is invalid", -3);
        if (!accountDTO.getEmail().matches("^(.+)@(.+)$"))
            throw new BusinessException("Email is invalid", -2);
        if (!cpfIsValid(accountDTO))
            throw new BusinessException("Cpf is invalid", -1);
        if (accountDTO.getIsDriver()) {
            if (!accountDTO.getCarPlate().matches("[A-Z]{3}[0-9]{4}"))
                throw new BusinessException("Car plate is invalid", -5);
        }
    }

    private void validateEmailAlreadyExists(AccountDTO accountDTO) throws BusinessException {
        if (Objects.nonNull(accountRepository.findByEmail(accountDTO.getEmail())))
            throw new BusinessException("Email already exists", -4);
    }

    private UUID saveAccount(AccountDTO accountDTO) {
        return accountRepository.save(accountMapper.buildAccount(accountDTO)).getAccountId();
    }

    private static boolean cpfIsValid(AccountDTO accountDTO) {
        return validate(accountDTO.getCpf());
    }
}
