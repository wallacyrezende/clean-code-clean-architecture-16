package cleancodecleanarchitecture16.ride.infra.database.repositories;

import cleancodecleanarchitecture16.ride.infra.database.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionJpaRepository extends JpaRepository<PositionEntity, UUID> {
}
