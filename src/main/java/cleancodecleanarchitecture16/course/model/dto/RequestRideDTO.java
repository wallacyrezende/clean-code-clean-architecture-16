package cleancodecleanarchitecture16.course.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RequestRideDTO {
    private String passengerId;
    private Double fromLat;
    private Double fromLong;
    private Double toLat;
    private Double toLong;
}
