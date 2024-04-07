package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.AccountMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.repository.AccountRepository;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Account service tests")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountMapper = mock(AccountMapper.class);
        accountService = new AccountServiceImpl(accountRepository, accountMapper);

        when(accountRepository.findByEmail(anyString())).thenReturn(null);
    }

    @Test
    @DisplayName("Should create a passenger account")
    void shouldCreateAccount() throws BusinessException {
        var accountDTO = buildAccountDTO();
        var account = buildAccount();

        when(accountMapper.buildAccount(any(AccountDTO.class))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account accountSaved = invocation.getArgument(0);
            accountSaved.setAccountId(UUID.randomUUID());
            return accountSaved;
        });

        UUID accountId = accountService.create(accountDTO);

        assertNotNull(accountId);
        assertEquals(account.getAccountId(), accountId);
    }

    @Test
    @DisplayName("Should throw invalid car plate error when save driver")
    void shouldThrowInvalidCarPlateErrorWhenSaveDriver() {
        var accountDTO = buildAccountDTO();
        accountDTO.setIsDriver(true);
        accountDTO.setIsPassenger(false);
        accountDTO.setCarPlate("QWE123");

        var error = assertThrows(BusinessException.class, () -> accountService.create(accountDTO));

        assertEquals(error.getMessage(), "Car plate is invalid");
        assertEquals(error.getCode(), -5);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).buildAccount(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should throw email already exists error when save passenger")
    void shouldThrowEmailAlreadyExistsErrorWhenSavePassenger() {
        var accountDTO = buildAccountDTO();

        when(accountRepository.findByEmail(anyString())).thenReturn(buildAccount());

        var error = assertThrows(BusinessException.class, () -> accountService.create(accountDTO));

        assertEquals(error.getMessage(), "Email already exists");
        assertEquals(error.getCode(), -4);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).buildAccount(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should throw invalid name error when save passenger")
    void shouldThrowInvalidNameErrorWhenSavePassenger() {
        var accountDTO = buildAccountDTO();
        accountDTO.setName("123");

        var error = assertThrows(BusinessException.class, () -> accountService.create(accountDTO));

        assertEquals(error.getMessage(), "Name is invalid");
        assertEquals(error.getCode(), -3);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).buildAccount(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should throw invalid email error when save passenger")
    void shouldThrowInvalidEmailErrorWhenSavePassenger() {
        var accountDTO = buildAccountDTO();
        accountDTO.setEmail("123.com");

        var error = assertThrows(BusinessException.class, () -> accountService.create(accountDTO));

        assertEquals(error.getMessage(), "Email is invalid");
        assertEquals(error.getCode(), -2);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).buildAccount(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should throw invalid cpf error when save passenger")
    void shouldThrowInvalidCpfErrorWhenSavePassenger() {
        var accountDTO = buildAccountDTO();
        accountDTO.setCpf("123.456.789-10");

        var error = assertThrows(BusinessException.class, () -> accountService.create(accountDTO));

        assertEquals(error.getMessage(), "Cpf is invalid");
        assertEquals(error.getCode(), -1);
        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).buildAccount(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should find account by account Id")
    void shouldFindByAccountId() {
        var account = buildAccount();
        var accountDTO = buildAccountDTO();

        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        when(accountMapper.buildAccountDTO(any(Account.class))).thenReturn(accountDTO);

        accountService.findByAccountId(UUID.randomUUID());

        verify(accountRepository, times(1)).findById(any(UUID.class));
        verify(accountMapper, times(1)).buildAccountDTO(any(Account.class));
    }

    private AccountDTO buildAccountDTO() {
        return AccountDTO.builder()
                .name("John Doe")
                .email("exemplo@email.com")
                .cpf("188.058.750-58")
                .carPlate("ABC1234")
                .isPassenger(true)
                .isDriver(false)
                .build();
    }

    private Account buildAccount() {
        return Account.builder()
                .name("John Doe")
                .email("exemplo@email.com")
                .cpf("188.058.750-58")
                .carPlate("ABC1234")
                .isPassenger(true)
                .isDriver(false)
                .build();
    }
}