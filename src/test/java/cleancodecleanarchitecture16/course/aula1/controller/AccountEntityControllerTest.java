package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.infra.database.repositories.AccountJpaRepository;
import cleancodecleanarchitecture16.course.model.dto.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Account Controller tests")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class AccountEntityControllerTest {

    private static final String API = "/api/account";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    public static final String SIGNUP_ENDPOINT = "/signup";
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private AccountJpaRepository accountJpaRepository;

    @Test
    @DisplayName("Should signup a account and generate account id")
    void shouldSignupAndGenerateAccountId() throws Exception {
        var accountDTO = buildAccountDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDTO));
        final var result = mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return bad request when signup")
    void shouldValidateEmail() throws Exception {
        var accountDTO = buildAccountDTO();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(SIGNUP_ENDPOINT))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountDTO));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Email already exists"))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("-4"))
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