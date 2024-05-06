package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.domain.entity.Position;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.infra.repository.PositionRepository;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class UpdatePosition extends VoidUseCase<UpdatePosition.Input> {

    private final PositionRepository positionRepository;
    private final RideRepository rideRepository;

    public UpdatePosition(PositionRepository positionRepository, RideRepository rideRepository) {
        this.positionRepository = positionRepository;
        this.rideRepository = rideRepository;
    }

    @Transactional
    @Override
    public void execute(Input input) {
        final var ride = rideRepository.findRideById(RideId.with(input.rideId))
                .stream()
                .peek(rideFound -> rideFound.updatePosition(input.latitude(), input.longitude(), input.date))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Ride not found"));

        rideRepository.saveRide(ride);
        var position = Position.create(RideId.with(input.rideId), input.latitude(), input.longitude());
        positionRepository.savePosition(position);
    }

    public record Input(String rideId, Double latitude, Double longitude, LocalDateTime date) {}
}
