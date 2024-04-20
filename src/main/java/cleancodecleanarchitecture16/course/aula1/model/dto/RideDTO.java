package cleancodecleanarchitecture16.course.aula1.model.dto;

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
}
