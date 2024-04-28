package cleancodecleanarchitecture16.course.domain.entity;

import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.Coord;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.domain.vo.RideStatus;
import cleancodecleanarchitecture16.course.domain.vo.RideStatusFactory;
import cleancodecleanarchitecture16.course.domain.vo.Segment;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

import java.time.LocalDateTime;
import java.util.Objects;
// Aggregate, Aggregate Root <AR>, Entity
public class Ride {

    private final RideId rideId;
    private AccountId passengerId;
    private AccountId driverId;
    private Double fromLat;
    private Double fromLong;
    private Double toLat;
    private Double toLong;
    private Double fare;
    private Double distance;
    private RideStatus status;
    private LocalDateTime date;

    private Ride(final RideId rideId, final AccountId passengerId, final AccountId driverId, final Segment segment, final Double fare,
                 final Double distance, String status, final LocalDateTime date) {
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
    }

    public static Ride create(final AccountId passengerId, final Double fromLat, final Double fromLong, final Double toLat,
                              final Double toLong) {
        final var status = "requested";
        final var date = LocalDateTime.now();
        return new Ride(RideId.unique(), passengerId, null, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)),
                null, null, status, date);
    }

    public static Ride restore(final RideId rideId, final AccountId passengerId, final AccountId driverId, final Double fromLat,
                               final Double fromLong, final Double toLat, final Double toLong, final Double fare, final Double distance,
                               final String status, final LocalDateTime date) {
        return new Ride(rideId, passengerId, driverId, new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong)),
                fare, distance, status, date);
    }

    public void accept (String driverId) {
        this.status.accept();
        this.driverId = AccountId.with(driverId);
    }

    public void start () {
        this.status.start();
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
        return new Segment(new Coord(fromLat, fromLong), new Coord(toLat, toLong));
    }

    public Double fromLat() {
        return fromLat;
    }

    public Double fromLong() {
        return fromLong;
    }

    public Double toLat() {
        return toLat;
    }

    public Double toLong() {
        return toLong;
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
        this.fromLat = segment.from().latitude();
        this.fromLong = segment.from().longitude();
        this.toLat = segment.to().latitude();
        this.toLong = segment.to().longitude();
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
