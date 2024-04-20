package cleancodecleanarchitecture16.course.aula1.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID rideId;

    @Column
    private UUID passengerId;

    @Column
    private UUID driverId;

    @Column
    private String status;

    @Column
    private Long fare;

    @Column
    private Long distance;

    @Column
    private Long fromLat;

    @Column
    private Long fromLong;

    @Column
    private Long toLat;

    @Column
    private Long toLong;

    @Column
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return Objects.equals(rideId, ride.rideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rideId);
    }
}
