package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.IntegrationTest;
import cleancodecleanarchitecture16.course.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestRideTest extends IntegrationTest {

    @Autowired
    private Signup signup;
    @Autowired
    private RequestRide requestRide;
    @Autowired
    private GetRide getRide;
    @Autowired
    private MailerGateway mailerGateway;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RideRepository rideRepository;

    @Test
    @DisplayName("Should request a ride")
    void shouldRequestARide() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var inputSignup = new Signup.Input(name, email, cpf, null, isPassenger, null);
        final var outputSignup = signup.execute(inputSignup);
        final var passengerId = outputSignup.accountId();
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
        assertEquals(outputGetRide.rideId(), outputRequestRide.rideId());
        assertEquals(outputGetRide.status(), "requested");
        assertEquals(outputGetRide.passengerId(), outputSignup.accountId());
        assertEquals(outputGetRide.fromLat(), inputRequestRide.fromLat());
        assertEquals(outputGetRide.fromLong(), inputRequestRide.fromLong());
        assertEquals(outputGetRide.toLat(), inputRequestRide.toLat());
        assertEquals(outputGetRide.toLong(), inputRequestRide.toLong());
        assertEquals(outputGetRide.passengerName(), inputSignup.name());
        assertEquals(outputGetRide.passengerEmail(), inputSignup.email());
    }
}