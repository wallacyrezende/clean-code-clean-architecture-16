package cleancodecleanarchitecture16.course.infra.database.entities;

import cleancodecleanarchitecture16.course.domain.entity.Position;
import cleancodecleanarchitecture16.course.domain.vo.PositionId;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
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
@Table(name = "position")
public class PositionEntity {

    @Id
    private UUID positionId;

    @Column
    private UUID rideId;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private LocalDateTime date;

    public static PositionEntity of(final Position position) {
        return new PositionEntity(UUID.fromString(position.positionId().value()), UUID.fromString(position.rideId().value()),
                position.coord().latitude(), position.coord().longitude(), position.date());
    }

    public Position toPosition() {
        return Position.restore(PositionId.with(positionId.toString()), RideId.with(rideId.toString()), latitude, longitude, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionEntity rideEntity = (PositionEntity) o;
        return Objects.equals(positionId, rideEntity.positionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId);
    }
}
