package cleancodecleanarchitecture16.course.infra.http;

import cleancodecleanarchitecture16.course.IntegrationTest;
import cleancodecleanarchitecture16.course.application.usecase.GetAccount;
import cleancodecleanarchitecture16.course.application.usecase.Signup;
import cleancodecleanarchitecture16.course.infra.database.repositories.AccountJpaRepository;
import cleancodecleanarchitecture16.course.model.dto.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Account Controller tests")
@AutoConfigureMockMvc
class AccountControllerTest extends IntegrationTest {

    private static final String API = "/api/account";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    public static final String SIGNUP_ENDPOINT = "/signup";
    public static final String GET_ACCOUNT_ENDPOINT = "/{accountId}";
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @BeforeEach
    void setUp() {
        accountJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Should signup an account and generate account accountId")
    void shouldSignupAndGenerateAccountId() throws Exception {
        var accountDTO = buildAccountPassengerDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(accountDTO));
        final var result = mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").isString())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return bad request when trying signup with email already existent")
    void shouldReturnBadRequestWhenTryingSignupWithEmailAlreadyExistent() throws Exception {
        var accountDTO = buildAccountPassengerDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(accountDTO));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").isString())
                .andReturn().getResponse().getContentAsByteArray();
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Email already exists"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(SIGNUP_ENDPOINT))));
    }

    @Test
    @DisplayName("Should find a passenger by accountId")
    void shouldFindAnAccountByAccountId() throws Exception {
        final var accountDTO = buildAccountPassengerDTO();
        final var signupRequest = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(accountDTO));
        final var signupResult = mvc
                .perform(signupRequest)
                .andReturn()
                .getResponse()
                .getContentAsByteArray();
        final var accountId =  mapper.readValue(signupResult, Signup.Output.class).accountId();
        final var getAccountRequest = MockMvcRequestBuilders
                .get(API.concat(GET_ACCOUNT_ENDPOINT), accountId);
        final var getAccountResult = mvc
                .perform(getAccountRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        final var getAccountResponse = mapper.readValue(getAccountResult, GetAccount.Output.class);

        assertEquals(accountDTO.getName(), getAccountResponse.name());
        assertEquals(accountDTO.getEmail(), getAccountResponse.email());
        assertEquals(accountDTO.getCpf(), getAccountResponse.cpf());
        assertEquals(accountDTO.getIsPassenger(), getAccountResponse.isPassenger());
    }

    public static AccountDTO buildAccountPassengerDTO() {
        return AccountDTO.builder()
                .name("John Doe")
                .email("exemplo" + random() + "@email.com")
                .cpf("188.058.750-58")
                .carPlate(null)
                .isPassenger(true)
                .isDriver(null)
                .build();
    }
}