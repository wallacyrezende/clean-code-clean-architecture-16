package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.ride.application.exceptions.NotFoundException;
import cleancodecleanarchitecture16.ride.domain.entity.Ride;
import cleancodecleanarchitecture16.ride.domain.vo.AccountId;
import cleancodecleanarchitecture16.ride.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import static java.lang.Boolean.TRUE;

public class RequestRide extends UseCase<RequestRide.Input, RequestRide.Output> {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;

    public RequestRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public Output execute(Input input) {
        var account = accountRepository.findById(AccountId.with(input.passengerId));
        if (account.isEmpty())
            throw new NotFoundException("Account not found with id: " + input.passengerId);
        if (!account.get().isPassenger())
            throw new BusinessException("Account is not from a passenger");
        if (TRUE.equals(rideRepository.hasActiveRideByPassengerId(AccountId.with(input.passengerId))))
            throw new BusinessException("Passenger has an active ride");
        var ride = Ride.create(AccountId.with(input.passengerId), input.fromLat(), input.fromLong(), input.toLat(), input.toLong());
        var savedRide = rideRepository.saveRide(ride);
        return new Output(savedRide.rideId().value());
    }

    public record Input(String passengerId, Double fromLat, Double fromLong, Double toLat, Double toLong) {}
    public record Output(String rideId) {}
}
