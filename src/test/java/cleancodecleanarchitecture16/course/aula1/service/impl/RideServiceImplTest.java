package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.RideMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Ride;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.repository.RideRepository;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import cleancodecleanarchitecture16.course.aula1.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Ride service tests")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class RideServiceImplTest {

    private RideService rideService;
    private RideRepository rideRepository;
    private AccountService accountService;
    private RideMapper rideMapper;

    @BeforeEach
    void setUp() {
        rideRepository = mock(RideRepository.class);
        accountService = mock(AccountService.class);
        rideMapper = mock(RideMapper.class);

        rideService = new RideServiceImpl(accountService, rideRepository, rideMapper);

        when(accountService.findByAccountId(any(UUID.class))).thenReturn(AccountDTO.builder().isPassenger(true).build());
        when(rideRepository.hasActiveRideByPassengerId(any(UUID.class))).thenReturn(false);
    }

    @Test
    @DisplayName("Should create a ride")
    void shouldCreateARide() {
        var requestRideDTO = buildRequestRideDTO();
        var ride = buildRide();

        when(rideMapper.buildRide(any(RequestRideDTO.class))).thenReturn(ride);
        when(rideRepository.save(any(Ride.class))).thenAnswer(invocation -> {
           Ride rideSaved = invocation.getArgument(0);
           rideSaved.setRideId(UUID.randomUUID());
           return rideSaved;
        });

        var rideId = rideService.create(requestRideDTO);

        assertNotNull(rideId);
        assertEquals(ride.getRideId(), rideId);
    }

    @Test
    @DisplayName("Should throw account is not from a passenger error when create ride")
    void shouldThrowAccountIsNotFromAPassengerErrorWhenCreateRide() {
        var rideDTO = buildRequestRideDTO();

        when(accountService.findByAccountId(any(UUID.class))).thenReturn(AccountDTO.builder().isPassenger(false).build());

        var error = assertThrows(BusinessException.class, () -> rideService.create(rideDTO));

        assertEquals(error.getMessage(), "Account is not from a passenger");
        verify(rideRepository, never()).save(any());
        verify(rideMapper, never()).buildRide(any(RequestRideDTO.class));
    }

    @Test
    @DisplayName("Should throw passenger has an active ride error when create ride")
    void shouldThrowPassengerHasAnActiveRideErrorWhenCreateRide() {
        var rideDTO = buildRequestRideDTO();

        when(rideRepository.hasActiveRideByPassengerId(any(UUID.class))).thenReturn(true);

        var error = assertThrows(BusinessException.class, () -> rideService.create(rideDTO));

        assertEquals(error.getMessage(), "Passenger has an active ride");
        verify(rideRepository, never()).save(any());
        verify(rideMapper, never()).buildRide(any(RequestRideDTO.class));
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
                .build();
    }
}