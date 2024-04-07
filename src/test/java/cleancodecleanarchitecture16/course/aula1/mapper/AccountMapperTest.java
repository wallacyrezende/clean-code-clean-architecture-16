package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}