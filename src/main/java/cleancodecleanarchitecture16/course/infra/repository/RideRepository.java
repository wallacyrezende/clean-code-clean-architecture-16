package cleancodecleanarchitecture16.course.infra.repository;

import cleancodecleanarchitecture16.course.domain.entity.Ride;
import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.RideId;

import java.util.Optional;

public interface RideRepository {
    Ride saveRide(Ride ride);
    Optional<Ride> findRideById(RideId rideId);
    Boolean hasActiveRideByPassengerId(AccountId passengerId);
}
