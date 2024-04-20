package cleancodecleanarchitecture16.course.aula1.service.impl;

import cleancodecleanarchitecture16.course.aula1.mapper.RideMapper;
import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.model.dto.RideDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.repository.RideRepository;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import cleancodecleanarchitecture16.course.aula1.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final AccountService accountService;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @Override
    public UUID create(RequestRideDTO requestRideDTO) {
        var accountDTO = accountService.findByAccountId(requestRideDTO.getPassengerId());
        if (!accountDTO.getIsPassenger())
            throw new BusinessException("Account is not from a passenger");
        Boolean hasActiveRide = rideRepository.hasActiveRideByPassengerId(requestRideDTO.getPassengerId());
        if (Boolean.TRUE.equals(hasActiveRide))
            throw new BusinessException("Passenger has an active ride");
        return rideRepository.save(rideMapper.buildRequestedRide(requestRideDTO)).getRideId();
    }

    @Override
    public RideDTO findByRideId(UUID rideId) {
        var ride = rideRepository.findById(rideId).stream().iterator().next();
        var accountDTO = accountService.findByAccountId(ride.getPassengerId());
        return rideMapper.buildRideDTO(ride, accountDTO);
    }
}
