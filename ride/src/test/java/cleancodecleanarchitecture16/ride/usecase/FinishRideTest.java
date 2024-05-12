package cleancodecleanarchitecture16.ride.usecase;

import cleancodecleanarchitecture16.ride.IntegrationTest;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.application.usecase.AcceptRide;
import cleancodecleanarchitecture16.ride.application.usecase.FinishRide;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;
import cleancodecleanarchitecture16.ride.application.usecase.StartRide;
import cleancodecleanarchitecture16.ride.application.usecase.UpdatePosition;
import cleancodecleanarchitecture16.ride.application.gateway.PaymentGateway;
import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import cleancodecleanarchitecture16.ride.infra.mediator.Mediator;
import cleancodecleanarchitecture16.ride.infra.queue.Queue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;

import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinishRideTest extends IntegrationTest {

    @Autowired
    private AccountGateway accountGateway;
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
    @Autowired
    private FinishRide finishRide;
    @Autowired
    private Mediator mediator;
    @Autowired
    private PaymentGateway paymentGateway;
    @Autowired
    @Qualifier("rabbitMQAdapter")
    private Queue queue;

    @Test
    void shouldFinishARide() {
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
        final var inputStartRide = new StartRide.Input(outputRequestRide.rideId());
        startRide.execute(inputStartRide);
        final var inputUpdatePosition1 = new UpdatePosition.Input(outputRequestRide.rideId(), -27.584905257808835, -48.545022195325124, LocalDateTime.of(2023, 3, 1, 21, 30, 0));
        updatePosition.execute(inputUpdatePosition1);
        final var inputUpdatePosition2 = new UpdatePosition.Input(outputRequestRide.rideId(), -27.496887588317275, -48.522234807851476, LocalDateTime.of(2023, 3, 1, 22, 30, 0));
        updatePosition.execute(inputUpdatePosition2);
        final var inputUpdatePosition3 = new UpdatePosition.Input(outputRequestRide.rideId(), -27.584905257808835, -48.545022195325124, LocalDateTime.of(2023, 3, 1, 23, 30, 0));
        updatePosition.execute(inputUpdatePosition3);
//        mediator.register("rideCompleted", (data -> {
//            final var ride = (Ride) data;
//            paymentGateway.processPayment(new PaymentGateway.Input(ride.rideId().value(), ride.fare()));
//        }));
        final var inputFinishRide = new FinishRide.Input(outputRequestRide.rideId());
        finishRide.execute(inputFinishRide);
//        queue.consume("rideCompleted", (data -> {
//            final var ride = (Ride) data;
//            paymentGateway.processPayment(new PaymentGateway.Input(ride.rideId().value(), ride.fare()));
//        }));
        final var inputGetRide = new GetRide.Input(outputRequestRide.rideId());
        final var outputGetRide = getRide.execute(inputGetRide);
        assertTrue(outputGetRide.isPresent());
        assertEquals(outputRequestRide.rideId(), outputGetRide.get().rideId());
        assertEquals(63, outputGetRide.get().fare());
        assertEquals("completed", outputGetRide.get().status());
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