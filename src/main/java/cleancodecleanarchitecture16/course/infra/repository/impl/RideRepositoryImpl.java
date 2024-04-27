package cleancodecleanarchitecture16.course.infra.repository.impl;

import cleancodecleanarchitecture16.course.domain.entity.Ride;
import cleancodecleanarchitecture16.course.infra.database.entities.RideEntity;
import cleancodecleanarchitecture16.course.infra.database.repositories.RideJpaRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RideRepositoryImpl implements RideRepository {

    private final RideJpaRepository rideJpaRepository;

    public RideRepositoryImpl(RideJpaRepository rideJpaRepository) {
        this.rideJpaRepository = rideJpaRepository;
    }

    @Override
    @Transactional
    public Ride saveRide(Ride ride) {
        return rideJpaRepository.save(RideEntity.of(ride))
                .toRide();
    }
}
