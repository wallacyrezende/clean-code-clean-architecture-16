package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.application.gateway.PaymentGateway;
import cleancodecleanarchitecture16.ride.infra.mediator.Mediator;
import cleancodecleanarchitecture16.ride.infra.queue.Queue;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import java.util.NoSuchElementException;

public class FinishRide extends VoidUseCase<FinishRide.Input> {

    private final RideRepository rideRepository;
    private final PaymentGateway paymentGateway;
    private final Mediator mediator;
    private final Queue queue;

    public FinishRide(RideRepository rideRepository, PaymentGateway paymentGateway, Mediator mediator, Queue queue) {
        this.rideRepository = rideRepository;
        this.paymentGateway = paymentGateway;
        this.mediator = mediator;
        this.queue = queue;
    }

    @Override
    public void execute(Input input) {
        final var ride = rideRepository.findRideById(RideId.with(input.rideId))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Ride not found"));
        ride.register("rideCompleted", domainEvent -> {
            var rideCompleted = (Ride) domainEvent.getData();
//            mediator.publish(domainEvent.getEventName(), domainEvent.getData());
            queue.publish(domainEvent.getEventName(), new PaymentGateway.Input(rideCompleted.rideId().value(), rideCompleted.fare()));
        });
        ride.finish();
        rideRepository.saveRide(ride);
//        paymentGateway.processPayment(new PaymentGateway.Input(ride.rideId().value(), ride.fare()));
    }

    public record Input(String rideId) {}
}
