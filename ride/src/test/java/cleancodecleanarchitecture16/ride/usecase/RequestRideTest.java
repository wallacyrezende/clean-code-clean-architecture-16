package cleancodecleanarchitecture16.ride.usecase;

import cleancodecleanarchitecture16.ride.IntegrationTest;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;

import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestRideTest extends IntegrationTest {

    @Autowired
    private AccountGateway accountGateway;
    @Autowired
    private RequestRide requestRide;
    @Autowired
    private GetRide getRide;

    @Test
    @DisplayName("Should request a ride")
    void shouldRequestARide() {
        final var passenger = buildPassenger();
        final var outputSignup = accountGateway.signup(passenger);
        final var passengerId = outputSignup.stream().iterator().next().getAccountId().toString();
        final var fromLat = -27.584905257808835;
        final var fromLong = -48.545022195325124;
        final var toLat = -27.496887588317275;
        final var toLong = -48.522234807851476;
        final var inputRequestRide = new RequestRide.Input(passengerId, fromLat, fromLong, toLat, toLong);
        final var outputRequestRide = requestRide.execute(inputRequestRide);
        assertNotNull(outputRequestRide);
        final var inputGetRide = new GetRide.Input(outputRequestRide.rideId());
        final var outputGetRide = getRide.execute(inputGetRide).get();
        assertNotNull(outputGetRide);
        assertEquals(outputRequestRide.rideId(), outputGetRide.rideId());
        assertEquals("requested", outputGetRide.status());
        assertEquals(passengerId, outputGetRide.passengerId());
        assertEquals(inputRequestRide.fromLat(), outputGetRide.fromLat());
        assertEquals(inputRequestRide.fromLong(), outputGetRide.fromLong());
        assertEquals(inputRequestRide.toLat(), outputGetRide.toLat());
        assertEquals(inputRequestRide.toLong(), outputGetRide.toLong());
        assertEquals(passenger.getName(), outputGetRide.passengerName());
        assertEquals(passenger.getEmail(), outputGetRide.passengerEmail());
    }

    private static AccountDTO buildPassenger() {
        return AccountDTO.builder()
                .name("John Doe")
                .email("email" + random() + "@email.com")
                .cpf("188.058.750-58")
                .isPassenger(true)
                .build();
    }
}