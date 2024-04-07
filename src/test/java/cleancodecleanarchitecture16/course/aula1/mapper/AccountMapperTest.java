package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        accountMapper = new AccountMapper();
    }

    @Test
    @DisplayName("Should build a account")
    void shouldBuildAAccount() {
        var accountDTO = buildAccountDTO();

        Account account = accountMapper.buildAccount(accountDTO);

        assertEquals(accountDTO.getName(), account.getName());
        assertEquals(accountDTO.getCpf(), account.getCpf());
        assertEquals(accountDTO.getEmail(), account.getEmail());
        assertEquals(accountDTO.getIsDriver(), account.getIsDriver());
        assertEquals(accountDTO.getIsPassenger(), account.getIsPassenger());
    }

    @Test
    @DisplayName("Should throw error when try build a null account")
    void shouldThrowErrorWhenTryBuildANullAccount() {
         var error = assertThrows(BusinessException.class, () -> accountMapper.buildAccount(null));
         assertEquals("Account dto cannot be null", error.getMessage());
         assertEquals(0, error.getCode());
    }

    @Test
    @DisplayName("Should build a account dto")
    void shouldBuildAAccountDTO() {
        var account = buildAccount();

        AccountDTO accountDTO = accountMapper.buildAccountDTO(account);

        assertEquals(account.getName(), accountDTO.getName());
        assertEquals(account.getCpf(), accountDTO.getCpf());
        assertEquals(account.getEmail(), accountDTO.getEmail());
        assertEquals(account.getIsDriver(), accountDTO.getIsDriver());
        assertEquals(account.getIsPassenger(), accountDTO.getIsPassenger());
    }

    @Test
    @DisplayName("Should throw error when try build a null account dto")
    void shouldThrowErrorWhenTryBuildANullAccountDto() {
        var error = assertThrows(BusinessException.class, () -> accountMapper.buildAccountDTO(null));
        assertEquals("Account cannot be null", error.getMessage());
        assertEquals(0, error.getCode());
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