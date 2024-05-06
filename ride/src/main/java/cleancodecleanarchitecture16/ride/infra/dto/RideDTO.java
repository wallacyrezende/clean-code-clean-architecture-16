package cleancodecleanarchitecture16.ride.infra.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RideDTO {
    private UUID rideId;
    private UUID passengerId;
    private Long fromLat;
    private Long fromLong;
    private Long toLat;
    private Long toLong;
    private String status;
    private String passengerName;
    private String passengerEmail;
}
