package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.ride.application.exceptions.NotFoundException;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import java.util.UUID;

import static java.lang.Boolean.TRUE;

public class RequestRide extends UseCase<RequestRide.Input, RequestRide.Output> {

    private final AccountGateway accountGateway;
    private final RideRepository rideRepository;

    public RequestRide(AccountGateway accountGateway, RideRepository rideRepository) {
        this.accountGateway = accountGateway;
        this.rideRepository = rideRepository;
    }

    @Override
    public Output execute(Input input) {
        var account = accountGateway.getAccountById(UUID.fromString(input.passengerId));
        if (account.isEmpty())
            throw new NotFoundException("Account not found with id: " + input.passengerId);
        if (!account.get().getIsPassenger())
            throw new BusinessException("Account is not from a passenger");
        if (TRUE.equals(rideRepository.hasActiveRideByPassengerId(UUID.fromString(input.passengerId))))
            throw new BusinessException("Passenger has an active ride");
        var ride = Ride.create(UUID.fromString(input.passengerId), input.fromLat(), input.fromLong(), input.toLat(), input.toLong());
        var savedRide = rideRepository.saveRide(ride);
        return new Output(savedRide.rideId().value());
    }

    public record Input(String passengerId, Double fromLat, Double fromLong, Double toLat, Double toLong) {}

    public record Output(String rideId) {}
}
