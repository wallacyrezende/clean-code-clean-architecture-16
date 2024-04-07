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
    @Autowired
    MockMvc mvc;
    @MockBean
    AccountService accountService;

    @Test
    @DisplayName("Should signup a account and generate account id")
    void shouldSignupAndGenerateAccountId() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenReturn(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Should validate car plate and return -5")
    void shouldValidateCarPlate() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("Car plate is invalid",-5));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Error: Car plate is invalid, Code: -5"));
    }

    @Test
    @DisplayName("Should validate account already exists and return -4")
    void shouldValidateAccountAlreadyExists() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("",-4));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-4"));
    }

    @Test
    @DisplayName("Should validate name and return -3")
    void shouldValidateName() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("",-3));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-3"));
    }

    @Test
    @DisplayName("Should validate email and return -2")
    void shouldValidateEmail() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("",-2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-2"));
    }

    @Test
    @DisplayName("Should validate cpf and return -1")
    void shouldValidateCpf() throws Exception {
        when(accountService.create(any(AccountDTO.class))).thenThrow(new BusinessException("",-1));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildAccountDTO()));

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-1"));
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