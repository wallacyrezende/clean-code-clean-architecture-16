package cleancodecleanarchitecture16.course.infra.database.repositories;

import cleancodecleanarchitecture16.course.infra.database.entities.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideJpaRepository extends JpaRepository<RideEntity, UUID> {
}
