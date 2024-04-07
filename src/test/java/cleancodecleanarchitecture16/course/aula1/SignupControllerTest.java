package cleancodecleanarchitecture16.course.aula1;

import cleancodecleanarchitecture16.course.aula1.controller.SignupController;
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
    @DisplayName("Should validate car plate and return code -5")
    void shouldValidateCarPlate() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Car plate is invalid",-5));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Car plate is invalid"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-5"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should validate account already exists and return code -4")
    void shouldValidateAccountAlreadyExists() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Email already exists",-4));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Email already exists"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-4"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should validate name and return code -3")
    void shouldValidateName() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Name is invalid",-3));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Name is invalid"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-3"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should validate email and return code -2")
    void shouldValidateEmail() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Email is invalid",-2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Email is invalid"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-2"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should validate cpf and return code -1")
    void shouldValidateCpf() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Cpf is invalid",-1));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Cpf is invalid"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-1"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
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