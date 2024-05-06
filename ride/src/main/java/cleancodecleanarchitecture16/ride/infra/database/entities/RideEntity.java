package cleancodecleanarchitecture16.ride.infra.database.entities;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.domain.vo.AccountId;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ride")
public class RideEntity {

    @Id
    private UUID rideId;

    @Column
    private UUID passengerId;

    @Column
    private UUID driverId;

    @Column
    private Double fromLat;

    @Column
    private Double fromLong;

    @Column
    private Double toLat;

    @Column
    private Double toLong;

    @Column
    private Double fare;

    @Column
    private Double distance;

    @Column
    private String status;

    @Column
    private LocalDateTime date;

    @Column
    private Double lastLat;

    @Column
    private Double lastLong;

    public static RideEntity of(final Ride ride) {
        final var driverId = ride.driverId() != null ? UUID.fromString(ride.driverId().value()) : null;
        return new RideEntity(UUID.fromString(ride.rideId().value()), UUID.fromString(ride.passengerId().value()),
                driverId, ride.fromLat(), ride.fromLong(), ride.toLat(), ride.toLong(), ride.fare(), ride.distance(),
                ride.status(), ride.date(), ride.lastPosition().latitude(), ride.lastPosition().longitude());
    }

    public Ride toRide() {
        final var driverId = this.driverId != null ? AccountId.with(this.driverId.toString()) : null;
        return Ride.restore(RideId.with(rideId.toString()), AccountId.with(passengerId.toString()), driverId, fromLat,
                fromLong, toLat, toLong, fare, distance, status, date, lastLat, lastLong);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideEntity rideEntity = (RideEntity) o;
        return Objects.equals(rideId, rideEntity.rideId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rideId);
    }
}
