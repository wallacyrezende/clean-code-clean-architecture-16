package cleancodecleanarchitecture16.course.infra.repository.impl;

import cleancodecleanarchitecture16.course.domain.entity.Ride;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.infra.database.entities.RideEntity;
import cleancodecleanarchitecture16.course.infra.database.repositories.RideJpaRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Ride> findRideById(RideId rideId) {
        Objects.requireNonNull(rideId, "RideId cannot be null");
        return rideJpaRepository.findById(UUID.fromString(rideId.value()))
                .map(RideEntity::toRide);
    }
}
