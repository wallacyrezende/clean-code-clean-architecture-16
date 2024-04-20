package cleancodecleanarchitecture16.course.aula1.service;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;

import java.util.UUID;

public interface RideService {
    UUID create(RequestRideDTO requestRideDTO);
}
