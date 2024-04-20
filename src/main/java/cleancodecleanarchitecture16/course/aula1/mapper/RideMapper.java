package cleancodecleanarchitecture16.course.aula1.mapper;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RideDTO;
import cleancodecleanarchitecture16.course.aula1.model.entities.Ride;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RideMapper extends Mapper {

    public Ride buildRequestedRide(RequestRideDTO requestRideDTO) {
        if (isNullOrEmpty(requestRideDTO))
            throw new BusinessException("Request ride dto cannot be null", 0);
        return Ride.builder()
                .passengerId(requestRideDTO.getPassengerId())
                .status("requested")
                .date(LocalDateTime.now())
                .fromLat(requestRideDTO.getFromLat())
                .fromLong(requestRideDTO.getFromLong())
                .toLat(requestRideDTO.getToLat())
                .toLong(requestRideDTO.getToLong())
                .build();
    }

    public RideDTO buildRideDTO(Ride ride, AccountDTO accountDTO) {
        if (isNullOrEmpty(ride))
            throw new BusinessException("Ride cannot be null", 0);
        if (isNullOrEmpty(accountDTO))
            throw new BusinessException("Account dto cannot be null", 0);
        return RideDTO.builder()
                .rideId(ride.getRideId())
                .passengerId(ride.getPassengerId())
                .fromLat(ride.getFromLat())
                .fromLong(ride.getFromLong())
                .toLat(ride.getToLat())
                .toLong(ride.getToLong())
                .status(ride.getStatus())
                .passengerName(accountDTO.getName())
                .passengerEmail(accountDTO.getEmail())
                .build();
    }
}
