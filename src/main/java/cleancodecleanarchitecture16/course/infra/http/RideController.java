package cleancodecleanarchitecture16.course.infra.http;

import cleancodecleanarchitecture16.course.application.usecase.GetRide;
import cleancodecleanarchitecture16.course.application.usecase.RequestRide;
import cleancodecleanarchitecture16.course.model.dto.RequestRideDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Account Controller")
@RestController
@RequestMapping("/api/ride")
@RequiredArgsConstructor
public class RideController {

    private final RequestRide requestRide;
    private final GetRide getRide;

    @Operation(summary = "Request a ride")
    @PostMapping("/request-ride")
    public ResponseEntity<?> requestRide(@RequestBody RequestRideDTO requestRideDTO) {
        final var input = new RequestRide.Input(requestRideDTO.getPassengerId(), requestRideDTO.getFromLat(),
                requestRideDTO.getFromLong(), requestRideDTO.getToLat(), requestRideDTO.getToLong());
        final var output = requestRide.execute(input);
        return ResponseEntity.created(URI.create("/api/ride/" + output.rideId())).body(output);
    }

    @Operation(summary = "Get ride by ride id")
    @GetMapping("/{rideId}")
    public ResponseEntity<?> getById(@PathVariable String rideId) {
        return getRide.execute(new GetRide.Input(rideId))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
