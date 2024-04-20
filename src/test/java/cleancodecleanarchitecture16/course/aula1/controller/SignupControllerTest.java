package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Signup Controller tests")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SignupController.class)
@AutoConfigureMockMvc
class SignupControllerTest {

    private static final String API = "/api";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    public static final String SIGNUP_ENDPOINT = "/signup";
    @Autowired
    MockMvc mvc;
    @MockBean
    AccountService accountService;

    @Test
    @DisplayName("Should signup a account and generate account id")
    void shouldSignupAndGenerateAccountId() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenReturn(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Should return bad request when signup")
    void shouldValidateCarPlate() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException(""));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should get account by account id")
    void shouldGetAccountByAccountId() throws Exception {
        var accountDTO = buildAccountDTO();
        when(accountService.findByAccountId(any(UUID.class))).thenReturn(accountDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API + "/" + UUID.randomUUID())
                .contentType(JSON)
                .accept(JSON);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(accountDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(accountDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(accountDTO.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("carPlate").value(accountDTO.getCarPlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("isPassenger").value(accountDTO.getIsPassenger()))
                .andExpect(MockMvcResultMatchers.jsonPath("isDriver").value(accountDTO.getIsDriver()));
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