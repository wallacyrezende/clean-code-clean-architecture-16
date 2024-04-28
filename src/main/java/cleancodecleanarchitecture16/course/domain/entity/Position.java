package cleancodecleanarchitecture16.course.domain.entity;

import cleancodecleanarchitecture16.course.domain.vo.Coord;
import cleancodecleanarchitecture16.course.domain.vo.PositionId;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Position {

    private final PositionId positionId;
    private RideId rideId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime date;

    private Position(final PositionId positionId, final RideId rideId, final Coord coord, final LocalDateTime date) {
        if (positionId == null)
            throw new BusinessException("Invalid positionId for Position");
        this.positionId = positionId;
        this.setRideId(rideId);
        this.setCoord(coord);
        this.setDate(date);
    }

    public static Position create(final RideId rideId, final Double latitude, final Double longitude) {
        final var date = LocalDateTime.now();
        return new Position(PositionId.unique(), rideId, new Coord(latitude, longitude), date);
    }

    public static Position restore(final PositionId positionId, final RideId rideId, final Double latitude, final Double longitude, final LocalDateTime date) {
        return new Position(positionId, rideId, new Coord(latitude, longitude), date);
    }

    public PositionId positionId() {
        return positionId;
    }
    public RideId rideId() {
        return rideId;
    }

    public Coord coord() {
        return new Coord(latitude, longitude);
    }

    public LocalDateTime date() {
        return date;
    }

    private void setRideId(final RideId rideId) {
        if (rideId == null) {
            throw new BusinessException("Invalid rideId for Position");
        }
        this.rideId = rideId;
    }

    private void setCoord(final Coord coord) {
        this.latitude = coord.latitude();
        this.longitude = coord.longitude();
    }

    private void setDate(final LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(positionId, position.positionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId);
    }
}
