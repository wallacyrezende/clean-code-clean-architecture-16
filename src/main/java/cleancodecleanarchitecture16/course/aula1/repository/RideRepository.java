package cleancodecleanarchitecture16.course.aula1.repository;

import cleancodecleanarchitecture16.course.aula1.model.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Ride r WHERE r.passengerId = :passengerId AND r.status <> 'completed'")
    Boolean hasActiveRideByPassengerId(@Param("passengerId") UUID passengerId);
}
