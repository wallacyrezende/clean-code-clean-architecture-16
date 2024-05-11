package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import java.util.Optional;

public class GetRide extends UseCase<GetRide.Input, Optional<GetRide.Output>> {

    private final RideRepository  rideRepository;
    private final AccountGateway accountGateway;

    public GetRide(RideRepository rideRepository, AccountGateway accountGateway) {
        this.rideRepository = rideRepository;
        this.accountGateway = accountGateway;
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return rideRepository.findRideById(RideId.with(input.rideId))
                .map(ride -> accountGateway.getAccountById(ride.passengerId())
                        .map(passenger -> {
                            var driver = new AccountDTO();
                             if (ride.driverId() != null) {
                                 accountGateway.getAccountById(ride.driverId()).ifPresent(driverFound -> {
                                     driver.setName(driverFound.getName());
                                     driver.setEmail(driverFound.getEmail());
                                 });
                             }
                             return new Output(ride.rideId().value(), ride.passengerId().toString(), ride.fromLat(), ride.fromLong(),
                                     ride.toLat(), ride.toLong(), ride.status(), passenger.getName(), passenger.getEmail(), driver.getName(),
                                     driver.getEmail(), ride.fare());
                         })
                        .get());
    }

    public record Input(String rideId) {}

    public record Output(String rideId, String passengerId, Double fromLat, Double fromLong, Double toLat, Double toLong,
                         String status, String passengerName, String passengerEmail, String driverName, String driverEmail,
                         Double fare) {
    }
}
