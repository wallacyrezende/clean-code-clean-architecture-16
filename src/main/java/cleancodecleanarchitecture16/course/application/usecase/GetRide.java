package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;

import java.util.Optional;

public class GetRide extends UseCase<GetRide.Input, Optional<GetRide.Output>> {

    private final RideRepository  rideRepository;
    private final AccountRepository accountRepository;

    public GetRide(RideRepository rideRepository, AccountRepository accountRepository) {
        this.rideRepository = rideRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return rideRepository.findRideById(RideId.with(input.rideId))
                .map(ride -> {
                    var passenger = accountRepository.findById(ride.passengerId()).get();
                    Account driver;
                    String driverName = null;
                    String driverEmail = null;
                    if (ride.driverId() != null) {
                        driver = accountRepository.findById(AccountId.with(ride.driverId().value())).get();
                        driverName = driver.name().value();
                        driverEmail = driver.email().value();
                    }
                    return new Output(ride.rideId().value(), ride.passengerId().value(), ride.fromLat(), ride.fromLong(),
                            ride.toLat(), ride.toLong(), ride.status(), passenger.name().value(), passenger.email().value(),
                            driverName, driverEmail, ride.fare());
                });
    }

    public record Input(String rideId) {}

    public record Output(String rideId, String passengerId, Double fromLat, Double fromLong, Double toLat, Double toLong,
                         String status, String passengerName, String passengerEmail, String driverName, String driverEmail,
                         Double fare) {
    }
}
