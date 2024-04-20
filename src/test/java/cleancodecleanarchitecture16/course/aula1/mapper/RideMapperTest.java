package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Ride;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RideMapperTest {

    RideMapper rideMapper;

    @BeforeEach
    void setUp() {
        rideMapper = new RideMapper();
    }

    @Test
    @DisplayName("Should build a requested ride")
    void shouldBuildARequestedRide() {
        var requestRideDTO = buildRequestRideDTO();

        var ride = rideMapper.buildRequestedRide(requestRideDTO);

        assertEquals(requestRideDTO.getPassengerId(), ride.getPassengerId());
        assertEquals("requested", ride.getStatus());
        assertNotNull(ride.getDate());
        assertEquals(requestRideDTO.getFromLat(), ride.getFromLat());
        assertEquals(requestRideDTO.getFromLong(), ride.getFromLong());
        assertEquals(requestRideDTO.getToLat(), ride.getToLat());
        assertEquals(requestRideDTO.getToLong(), ride.getToLong());
    }

    @Test
    @DisplayName("Should throw error when try build a ride with null request ride dto")
    void shouldThrowErrorWhenTryBuildARideWithNullRequestRideDto() {
        var error = assertThrows(BusinessException.class, () -> rideMapper.buildRequestedRide(null));
        assertEquals("Request ride dto cannot be null", error.getMessage());
        assertEquals(0, error.getCode());
    }

    @Test
    @DisplayName("Should build a ride dto")
    void shouldBuildARideDTO() {
        var ride = buildRide();
        var accountDTO = buildAccountDTO();

        var rideDTO = rideMapper.buildRideDTO(ride, accountDTO);

        assertEquals(ride.getRideId(), rideDTO.getRideId());
        assertEquals(ride.getPassengerId(), rideDTO.getPassengerId());
        assertEquals(ride.getStatus(), rideDTO.getStatus());
        assertEquals(ride.getFromLat(), rideDTO.getFromLat());
        assertEquals(ride.getFromLong(), rideDTO.getFromLong());
        assertEquals(ride.getToLat(), rideDTO.getToLat());
        assertEquals(ride.getToLong(), rideDTO.getToLong());
        assertEquals(accountDTO.getName(), rideDTO.getPassengerName());
        assertEquals(accountDTO.getEmail(), rideDTO.getPassengerEmail());
    }

    @Test
    @DisplayName("Should throw error when try build a ride dto with null ride")
    void shouldThrowErrorWhenTryBuildARideDtoWithNullRide() {
        var error = assertThrows(BusinessException.class, () -> rideMapper.buildRideDTO(null, buildAccountDTO()));
        assertEquals("Ride cannot be null", error.getMessage());
        assertEquals(0, error.getCode());
    }

    @Test
    @DisplayName("Should throw error when try build a ride dto with null account dto")
    void shouldThrowErrorWhenTryBuildARideDtoWithNullAccountDto() {
        var error = assertThrows(BusinessException.class, () -> rideMapper.buildRideDTO(buildRide(), null));
        assertEquals("Account dto cannot be null", error.getMessage());
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

    private Ride buildRide() {
        return Ride.builder()
                .passengerId(UUID.randomUUID())
                .fromLat(0L)
                .fromLong(0L)
                .toLat(0L)
                .toLong(0L)
                .status("requested")
                .date(LocalDateTime.now())
                .build();
    }

    private AccountDTO buildAccountDTO() {
        return AccountDTO.builder()
                .name("John Doe")
                .email("email@email.com")
                .build();
    }
}