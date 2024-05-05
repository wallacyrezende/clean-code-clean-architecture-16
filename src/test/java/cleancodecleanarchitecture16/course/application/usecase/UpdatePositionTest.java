package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdatePositionTest extends IntegrationTest {

    @Autowired
    private Signup signup;
    @Autowired
    private RequestRide requestRide;
    @Autowired
    private AcceptRide acceptRide;
    @Autowired
    private StartRide startRide;
    @Autowired
    private UpdatePosition updatePosition;
    @Autowired
    private GetRide getRide;

    @Test
    void shouldStartARide() {
        final var name = "John Doe";
        final var email = "email" + random() + "@email.com";
        final var cpf = "188.058.750-58";
        final var isPassenger = true;
        final var inputSignup = new Signup.Input(name, email,  cpf, null, isPassenger, null);
        final var outputSignup = signup.execute(inputSignup);
        final var driverName = "John Driver";
        final var driverEmail = "email" + random() + "@email.com";
        final var carPlate = "ABC1234";
        final var isDriver = true;
        final var inputSignupDriver = new Signup.Input(driverName, driverEmail, cpf, carPlate, null, isDriver);
        final var outputSignupDriver = signup.execute(inputSignupDriver);
        final var passengerId = outputSignup.accountId();
        final var fromLat = -27.584905257808835;
        final var fromLong = -48.545022195325124;
        final var toLat = -27.496887588317275;
        final var toLong = -48.522234807851476;
        final var inputRequestRide = new RequestRide.Input(passengerId, fromLat, fromLong, toLat, toLong);
        final var outputRequestRide = requestRide.execute(inputRequestRide);
        final var inputAcceptRide = new AcceptRide.Input(outputRequestRide.rideId(), outputSignupDriver.accountId());
        acceptRide.execute(inputAcceptRide);
        final var inputStartRide = new StartRide.Input(outputRequestRide.rideId());
        startRide.execute(inputStartRide);
        final var inputUpdatePosition1 = new UpdatePosition.Input(outputRequestRide.rideId(), -27.584905257808835, -48.545022195325124, LocalDateTime.now());
        updatePosition.execute(inputUpdatePosition1);
        final var inputUpdatePosition2 = new UpdatePosition.Input(outputRequestRide.rideId(), -27.496887588317275, -48.522234807851476, LocalDateTime.now());
        updatePosition.execute(inputUpdatePosition2);
        final var inputGetRide = new GetRide.Input(outputRequestRide.rideId());
        final var outputGetRide = getRide.execute(inputGetRide);
        assertTrue(outputGetRide.isPresent());
        assertEquals(outputRequestRide.rideId(), outputGetRide.get().rideId());
    }
}