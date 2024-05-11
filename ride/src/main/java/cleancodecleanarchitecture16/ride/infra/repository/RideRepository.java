package cleancodecleanarchitecture16.ride.infra.repository;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;

import java.util.Optional;
import java.util.UUID;

public interface RideRepository {
    Ride saveRide(Ride ride);
    Optional<Ride> findRideById(RideId rideId);
    Boolean hasActiveRideByPassengerId(UUID passengerId);
}
