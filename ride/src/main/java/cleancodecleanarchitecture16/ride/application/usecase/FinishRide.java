package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.infra.gateway.PaymentGateway;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import java.util.NoSuchElementException;

public class FinishRide extends VoidUseCase<FinishRide.Input> {

    private final RideRepository rideRepository;
    private final PaymentGateway paymentGateway;

    public FinishRide(RideRepository rideRepository, PaymentGateway paymentGateway) {
        this.rideRepository = rideRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void execute(Input input) {
        final var ride = rideRepository.findRideById(RideId.with(input.rideId))
                .stream()
                .peek(Ride::finish)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Ride not found"));
        rideRepository.saveRide(ride);
        paymentGateway.processPayment(new PaymentGateway.Input(ride.rideId().value(), ride.fare()));
    }

    public record Input(String rideId) {}
}
