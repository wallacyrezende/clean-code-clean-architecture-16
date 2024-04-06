package cleancodecleanarchitecture16.course.aula1;

import cleancodecleanarchitecture16.course.aula1.controller.SignupController;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@DisplayName("Signup tests")
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

    @BeforeEach
    void setup() throws Exception {
        doNothing().when(accountService).validateEmailAlreadyExists(any(AccountDTO.class));
    }

    @Test
    @DisplayName("Should signup a account and generate account id")
    void shouldSignupAndGenerateAccountId() throws Exception {
        var requestDTO = buildSignupRequest();
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        when(accountService.saveAccount(any(AccountDTO.class))).thenReturn(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Should validate car plate and return -5")
    void shouldValidateCarPlate() throws Exception {
        var requestDTO = buildSignupRequest();
        requestDTO.setIsDriver(true);
        requestDTO.setIsPassenger(false);
        requestDTO.setCarPlate("QWE123");
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-5"));
    }

    @Test
    @DisplayName("Should validate account already exists and return -4")
    void shouldValidateAccountAlreadyExists() throws Exception {
        var requestDTO = buildSignupRequest();
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        doThrow(Exception.class).when(accountService).validateEmailAlreadyExists(any(AccountDTO.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-4"));
    }

    @Test
    @DisplayName("Should validate name and return -3")
    void shouldValidateName() throws Exception {
        var requestDTO = buildSignupRequest();
        requestDTO.setName("123 123");
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-3"));
    }

    @Test
    @DisplayName("Should validate email and return -2")
    void shouldValidateEmail() throws Exception {
        var requestDTO = buildSignupRequest();
        requestDTO.setEmail("112321.com");
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-2"));
    }

    @Test
    @DisplayName("Should validate cpf and return -1")
    void shouldValidateCpf() throws Exception {
        var requestDTO = buildSignupRequest();
        requestDTO.setCpf("123.456.789.01");
        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat("/signup"))
                .accept(JSON)
                .contentType(JSON)
                .content(requestBody);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("-1"));
    }

    private AccountDTO buildSignupRequest() {
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