package cleancodecleanarchitecture16.course.infra.database.repositories;

import cleancodecleanarchitecture16.course.infra.database.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PositionJpaRepository extends JpaRepository<PositionEntity, UUID> {
}
