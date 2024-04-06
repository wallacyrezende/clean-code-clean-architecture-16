package cleancodecleanarchitecture16.course.aula1;

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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Signup tests")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = SignupController.class)
@AutoConfigureMockMvc
class SignupControllerTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private static final String API = "/api";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    @Autowired
    MockMvc mvc;
    @MockBean
    DataSource dataSource;

    @BeforeEach
    void setup() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
    }

    @Test
    @DisplayName("Should signup a client and generate account id")
    void signup() throws Exception {
        var requestDTO = buildSignupRequest();


        String requestBody = new ObjectMapper().writeValueAsString(requestDTO);


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

    private SignupRequest buildSignupRequest() {
        return SignupRequest.builder()
                .name("John Doe")
                .email("exemplo@email.com")
                .cpf("188.058.750-58")
                .carPlate("ABC1234")
                .isPassenger(true)
                .isDriver(false)
                .build();
    }
}