package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

public class StartRide extends VoidUseCase<StartRide.Input> {

    private final RideRepository rideRepository;

    public StartRide(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public void execute(Input input) {
        rideRepository.findRideById(RideId.with(input.rideId))
                .map(ride -> {
                    ride.start();
                    return rideRepository.saveRide(ride);
                });
    }

    public record Input(String rideId) {}
}
