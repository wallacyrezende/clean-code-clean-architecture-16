package cleancodecleanarchitecture16.ride.infra.http;

import cleancodecleanarchitecture16.ride.IntegrationTest;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;
import cleancodecleanarchitecture16.ride.infra.database.repositories.RideJpaRepository;
import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import cleancodecleanarchitecture16.ride.infra.dto.RequestRideDTO;
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

import java.util.UUID;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Ride Controller tests")
@AutoConfigureMockMvc
class RideControllerTest extends IntegrationTest {

    private static final String API = "/api/ride";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    public static final String REQUEST_RIDE_ENDPOINT = "/request";
    public static final String GET_RIDE_ENDPOINT = "/{rideId}";
    @Autowired
    MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RideJpaRepository rideJpaRepository;
    @Autowired
    private AccountGateway accountGateway;

    @BeforeEach
    void setUp() {
        rideJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Should request a ride and generate ride id")
    void shouldRequestARideAndGenerateRideId() throws Exception {
        final var passengerId = accountGateway.signup(buildAccountPassengerDTO()).stream().iterator().next().getAccountId().toString();
        var requestRideDTO = buildRequestRideDTOWithoutPassengerId();
        requestRideDTO.setPassengerId(passengerId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(REQUEST_RIDE_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(requestRideDTO));
        final var result = mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rideId").isString())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should return bad request when trying request a ride with invalid passenger id")
    void shouldReturnBadRequestWhenTryingRequestARideWithInvalidPassengerId() throws Exception {
        var requestRideDTO = buildRequestRideDTOWithoutPassengerId();
        requestRideDTO.setPassengerId(UUID.randomUUID().toString());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(REQUEST_RIDE_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(requestRideDTO));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(REQUEST_RIDE_ENDPOINT))));
    }

    @Test
    @DisplayName("Should find a ride by ride id")
    void shouldFindARideByRideId() throws Exception {
        final var accountDTO = buildAccountPassengerDTO();
        final var passengerId = accountGateway.signup(accountDTO).stream().iterator().next().getAccountId().toString();
        final var requestRideDTO = buildRequestRideDTOWithoutPassengerId();
        requestRideDTO.setPassengerId(passengerId);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(REQUEST_RIDE_ENDPOINT))
                .contentType(JSON)
                .content(mapper.writeValueAsString(requestRideDTO));
        final var resultRequestRideResult = mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsByteArray();
        final var rideId = mapper.readValue(resultRequestRideResult, RequestRide.Output.class).rideId();

        final var getRideRequest = MockMvcRequestBuilders
                .get(API.concat(GET_RIDE_ENDPOINT), rideId);
        final var getRideResult = mvc
                .perform(getRideRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        final var getRideResponse = mapper.readValue(getRideResult, GetRide.Output.class);

        assertEquals(accountDTO.getName(), getRideResponse.passengerName());
        assertEquals(accountDTO.getEmail(), getRideResponse.passengerEmail());
        assertEquals(requestRideDTO.getPassengerId(), getRideResponse.passengerId());
        assertEquals(requestRideDTO.getFromLat(), getRideResponse.fromLat());
    }

    private RequestRideDTO buildRequestRideDTOWithoutPassengerId() {
        return RequestRideDTO.builder()
                .toLat(0d)
                .toLong(0d)
                .fromLat(0d)
                .fromLong(0d)
                .build();

    }

    private static AccountDTO buildAccountPassengerDTO() {
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