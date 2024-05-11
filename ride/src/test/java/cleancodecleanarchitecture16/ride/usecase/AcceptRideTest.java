package cleancodecleanarchitecture16.ride.usecase;

import cleancodecleanarchitecture16.ride.IntegrationTest;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.application.usecase.AcceptRide;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;
import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AcceptRideTest extends IntegrationTest {

    @Autowired
    private AccountGateway accountGateway;
    @Autowired
    private RequestRide requestRide;
    @Autowired
    private AcceptRide acceptRide;
    @Autowired
    private GetRide getRide;

    @Test
    void shouldAcceptARide() {
        final var passenger = buildPassenger();
        final var outputSignup = accountGateway.signup(passenger);
        final var driver = buildDriver();
        final var outputSignupDriver = accountGateway.signup(driver);
        final var passengerId = outputSignup.stream().iterator().next().getAccountId().toString();
        final var driverId = outputSignupDriver.stream().iterator().next().getAccountId().toString();
        final var fromLat = -27.584905257808835;
        final var fromLong = -48.545022195325124;
        final var toLat = -27.496887588317275;
        final var toLong = -48.522234807851476;
        final var inputRequestRide = new RequestRide.Input(passengerId, fromLat, fromLong, toLat, toLong);
        final var outputRequestRide = requestRide.execute(inputRequestRide);
        final var inputAcceptRide = new AcceptRide.Input(outputRequestRide.rideId(), driverId);
        acceptRide.execute(inputAcceptRide);
        final var inputGetRide = new GetRide.Input(outputRequestRide.rideId());
        final var outputGetRide = getRide.execute(inputGetRide);
        assertTrue(outputGetRide.isPresent());
        assertEquals(outputRequestRide.rideId(), outputGetRide.get().rideId());
        assertEquals("accepted", outputGetRide.get().status());
        assertEquals(driver.getName(), outputGetRide.get().driverName());
    }

    private AccountDTO buildDriver() {
        return AccountDTO.builder()
                .name("John Driver")
                .email("email" + random() + "@email.com")
                .cpf("188.058.750-58")
                .carPlate("ABC1234")
                .isDriver(true)
                .build();
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