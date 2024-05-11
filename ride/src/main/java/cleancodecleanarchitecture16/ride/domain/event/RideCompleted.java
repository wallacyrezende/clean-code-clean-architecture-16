package cleancodecleanarchitecture16.ride.domain.event;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import lombok.Getter;

@Getter
public class RideCompleted implements DomainEvent {
    private final String eventName;
    private final Ride data;

    public RideCompleted (Ride data) {
        this.eventName = "rideCompleted";
        this.data = data;
    }
}
