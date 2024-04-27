package cleancodecleanarchitecture16.course.infra.repository;

import cleancodecleanarchitecture16.course.domain.entity.Ride;

public interface RideRepository {
    Ride saveRide(Ride ride);
}
