package cleancodecleanarchitecture16.course.infra.database.repositories;

import cleancodecleanarchitecture16.course.infra.database.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RideJpaRepository extends JpaRepository<RideEntity, UUID> {

    @Query("SELECT r FROM RideEntity r WHERE r.passengerId = :passengerId AND r.status <> 'completed'")
    Optional<RideEntity> findActiveRideByPassengerId(@Param("passengerId") UUID passengerId);
}
