package cleancodecleanarchitecture16.ride.domain.entity;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.ride.domain.service.FareCalculatorFactory;
import cleancodecleanarchitecture16.ride.domain.vo.AccountId;
import cleancodecleanarchitecture16.ride.domain.vo.Coord;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.domain.vo.RideStatus;
import cleancodecleanarchitecture16.ride.domain.vo.RideStatusFactory;
import cleancodecleanarchitecture16.ride.domain.vo.Segment;

import java.time.LocalDateTime;
import java.util.Objects;
// Aggregate, Aggregate Root <AR>, Entity
public class Ride {

    private final RideId rideId;
    private AccountId passengerId;
    private AccountId driverId;
    private Segment segment;
    private Double fare;
    private Double distance;
    private RideStatus status;
    private LocalDateTime date;
    private Coord lastPosition;

    private Ride(final RideId rideId, final AccountId passengerId, final AccountId driverId, final Segment segment, final Double fare,
                 final Double distance, String status, final LocalDateTime date, final Coord lastPosition) {
        if (rideId == null)
            throw new BusinessException("Invalid rideId for Ride");
        this.status = RideStatusFactory.create(this, status);
        this.rideId = rideId;
        this.setPassengerId(passengerId);
        this.setDriverId(driverId);
        this.setSegment(segment);
        this.setFare(fare);
        this.setDistance(distance);
        this.setDate(date);
        this.setLastPosition(lastPosition);
    }

    public static Ride create(final AccountId passengerId, final Double fromLat, final Double fromLong, final Double toLat,
                              final Double toLong) {
        final var status = "requested";
        final var date = LocalDateTime.now();
        final var lastPosition = new Coord(fromLat, fromLong);
        final var distance = 0d;
        final var fare = 0d;
        return new Ride(RideId.unique(), passengerId, null, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)),
                fare, distance, status, date, lastPosition);
    }

    public static Ride restore(final RideId rideId, final AccountId passengerId, final AccountId driverId, final Double fromLat,
                               final Double fromLong, final Double toLat, final Double toLong, final Double fare, final Double distance,
                               final String status, final LocalDateTime date, final Double lastLat, final Double lastLong) {
        return new Ride(rideId, passengerId, driverId, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)),
                fare, distance, status, date, new Coord(lastLat, lastLong));
    }

    public void accept (String driverId) {
        this.status.accept();
        this.driverId = AccountId.with(driverId);
    }

    public void start () {
        this.status.start();
    }

    public void finish () {
        this.status.finish();
    }

    public void updatePosition (Double latitude, Double longitude, LocalDateTime date) {
        var newPosition = new Coord(latitude, longitude);
		var distance = new Segment(this.lastPosition, newPosition).getDistance();
        this.distance += distance;
        this.fare += FareCalculatorFactory.create(date).calculate(distance);
        this.lastPosition = newPosition;
    }
    public RideId rideId() {
        return rideId;
    }
    public AccountId passengerId() {
        return passengerId;
    }

    public AccountId driverId() {
        return driverId;
    }

    public Segment segment() {
        return segment;
    }

    public Double fromLat() {
        return segment.from().latitude();
    }

    public Double fromLong() {
        return segment.from().longitude();
    }

    public Double toLat() {
        return segment.to().latitude();
    }

    public Double toLong() {
        return segment.to().longitude();
    }

    public Double fare() {
        return fare;
    }

    public Double distance() {
        return distance;
    }

    public String status() {
        return status.getValue();
    }

    public LocalDateTime date() {
        return date;
    }

    public Coord lastPosition() {
        return lastPosition;
    }

    private void setPassengerId(final AccountId passengerId) {
        if (passengerId == null) {
            throw new BusinessException("Invalid passengerId for Ride");
        }
        this.passengerId = passengerId;
    }

    private void setDriverId(AccountId driverId) {
        this.driverId = driverId;
    }

    private void setSegment(Segment segment) {
        this.segment = segment;
    }

    private void setFare(Double fare) {
        this.fare = fare;
    }

    private void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setStatus(String status) {
        this.status = RideStatusFactory.create(this, status);
    }

    private void setDate(LocalDateTime date) {
        this.date = date;
    }

    private void setLastPosition(Coord lastPosition) {
        this.lastPosition = lastPosition;
    }

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
