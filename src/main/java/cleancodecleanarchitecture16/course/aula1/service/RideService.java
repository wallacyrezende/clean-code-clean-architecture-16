package cleancodecleanarchitecture16.course.aula1.service;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RideDTO;

import java.util.UUID;

public interface RideService {
    UUID create(RequestRideDTO requestRideDTO);
    RideDTO findByRideId(UUID rideId);
}
