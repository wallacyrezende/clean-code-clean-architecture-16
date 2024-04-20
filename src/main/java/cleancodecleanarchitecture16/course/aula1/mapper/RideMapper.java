package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Ride;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class RideMapper extends Mapper {

    public Ride buildRide(RequestRideDTO requestRideDTO) {
        if (isNullOrEmpty(requestRideDTO))
            throw new BusinessException("Request ride dto cannot be null", 0);
        return Ride.builder()
                .passengerId(requestRideDTO.getPassengerId())
                .fromLat(requestRideDTO.getFromLat())
                .fromLong(requestRideDTO.getFromLong())
                .toLat(requestRideDTO.getToLat())
                .toLong(requestRideDTO.getToLong())
                .build();
    }
}
