package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RideMapperTest {

    RideMapper rideMapper;

    @BeforeEach
    void setUp() {
        rideMapper = new RideMapper();
    }

    @Test
    @DisplayName("Should build a ride")
    void shouldBuildARide() {
        var requestRideDTO = buildRequestRideDTO();

        var ride = rideMapper.buildRide(requestRideDTO);

        assertEquals(requestRideDTO.getPassengerId(), ride.getPassengerId());
        assertEquals(requestRideDTO.getFromLat(), ride.getFromLat());
        assertEquals(requestRideDTO.getFromLong(), ride.getFromLong());
        assertEquals(requestRideDTO.getToLat(), ride.getToLat());
        assertEquals(requestRideDTO.getToLong(), ride.getToLong());
    }

    @Test
    @DisplayName("Should throw error when try build a null request ride dto ")
    void shouldThrowErrorWhenTryBuildANullRequestRideDto() {
        var error = assertThrows(BusinessException.class, () -> rideMapper.buildRide(null));
        assertEquals("Request ride dto cannot be null", error.getMessage());
        assertEquals(0, error.getCode());
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