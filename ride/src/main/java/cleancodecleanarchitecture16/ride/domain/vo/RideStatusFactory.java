package cleancodecleanarchitecture16.ride.domain.vo;

import cleancodecleanarchitecture16.ride.domain.entity.Ride;

public class RideStatusFactory {
    public static RideStatus create(Ride ride, String status) {
        return switch (status) {
            case "requested" -> new RequestedStatus(ride);
            case "accepted" -> new AcceptedStatus(ride);
            case "in_progress" -> new InProgressStatus(ride);
            case "completed" -> new CompletedStatus(ride);
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }
}
