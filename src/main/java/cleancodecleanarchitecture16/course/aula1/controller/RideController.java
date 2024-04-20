package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.model.dto.RequestRideDTO;
import cleancodecleanarchitecture16.course.aula1.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Ride Controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @Operation(summary = "Request a ride")
    @PostMapping("/request-ride")
    @ResponseStatus(HttpStatus.CREATED)
    public RideResponse requestRide(@RequestBody RequestRideDTO requestRideDTO) {
        return RideResponse.builder().id(rideService.create(requestRideDTO)).build();
    }
}
