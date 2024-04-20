package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RideDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.service.RideService;
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

@DisplayName("Ride Controller tests")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = RideController.class)
@AutoConfigureMockMvc
class RideControllerTest {

    private static final String API = "/api/ride";
    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    public static final String REQUEST_RIDE_ENDPOINT = "/request-ride";
    @Autowired
    MockMvc mvc;
    @MockBean
    RideService rideService;

    @Test
    @DisplayName("Should request ride and generate ride id")
    void shouldRequestRideAndGenerateRideId() throws Exception {
        when(rideService.create(any(RequestRideDTO.class))).thenReturn(UUID.randomUUID());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(REQUEST_RIDE_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildRequestRideDTO()));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Should return bad request when request ride")
    void shouldValidateCarPlate() throws Exception {
        when(rideService.create(any(RequestRideDTO.class))).thenThrow(new BusinessException(""));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API.concat(REQUEST_RIDE_ENDPOINT))
                .accept(JSON)
                .contentType(JSON)
                .content(new ObjectMapper().writeValueAsString(buildRequestRideDTO()));
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("code").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("details").value("uri=".concat(API.concat(REQUEST_RIDE_ENDPOINT))));
    }

    @Test
    @DisplayName("Should get ride by ride id")
    void shouldGetRideByRideId() throws Exception {
        var rideDTO = buildRideDTO();
        when(rideService.findByRideId(any(UUID.class))).thenReturn(rideDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API + "/" + UUID.randomUUID())
                .contentType(JSON)
                .accept(JSON);
        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private RideDTO buildRideDTO() {
        return RideDTO.builder()
                .rideId(UUID.randomUUID())
                .passengerId(UUID.randomUUID())
                .fromLat(0L)
                .fromLong(0L)
                .toLat(0L)
                .toLong(0L)
                .build();
    }

    private RequestRideDTO buildRequestRideDTO() {
        return RequestRideDTO.builder()
                .passengerId(UUID.randomUUID())
                .fromLat(0L)
                .fromLong(0L)
                .toLat(0L)
                .toLong(0L)
                .build();
    }
}