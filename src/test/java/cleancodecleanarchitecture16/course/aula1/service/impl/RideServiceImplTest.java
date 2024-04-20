package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.RideMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RideDTO;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

        when(rideMapper.buildRequestedRide(any(RequestRideDTO.class))).thenReturn(ride);
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
        verify(rideMapper, never()).buildRequestedRide(any(RequestRideDTO.class));
    }

    @Test
    @DisplayName("Should throw passenger has an active ride error when create ride")
    void shouldThrowPassengerHasAnActiveRideErrorWhenCreateRide() {
        var rideDTO = buildRequestRideDTO();

        when(rideRepository.hasActiveRideByPassengerId(any(UUID.class))).thenReturn(true);

        var error = assertThrows(BusinessException.class, () -> rideService.create(rideDTO));

        assertEquals(error.getMessage(), "Passenger has an active ride");
        verify(rideRepository, never()).save(any());
        verify(rideMapper, never()).buildRequestedRide(any(RequestRideDTO.class));
    }

    @Test
    @DisplayName("Should find ride by ride Id")
    void shouldFindByRideId() {
        when(rideRepository.findById(any(UUID.class))).thenReturn(Optional.of(buildRide()));
        when(rideMapper.buildRideDTO(any(Ride.class), any(AccountDTO.class))).thenReturn(buildRideDTO());

        rideService.findByRideId(UUID.randomUUID());

        verify(rideRepository, times(1)).findById(any(UUID.class));
        verify(accountService, times(1)).findByAccountId(any(UUID.class));
        verify(rideMapper, times(1)).buildRideDTO(any(Ride.class), any(AccountDTO.class));
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
                .status("requested")
                .date(LocalDateTime.now())
                .fromLat(0L)
                .fromLong(0L)
                .toLat(0L)
                .toLong(0L)
                .build();
    }

    private RideDTO buildRideDTO() {
        return RideDTO.builder()
                .rideId(UUID.randomUUID())
                .passengerId(UUID.randomUUID())
                .fromLat(0L)
                .fromLong(0L)
                .toLat(0L)
                .toLong(0L)
                .status("requested")
                .passengerName("John Doe")
                .passengerEmail("email@email.com")
                .build();
    }
}