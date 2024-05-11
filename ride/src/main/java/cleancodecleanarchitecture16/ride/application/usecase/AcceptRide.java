package cleancodecleanarchitecture16.ride.application.usecase;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.ride.application.exceptions.NotFoundException;
import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.domain.vo.RideId;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;

import java.util.UUID;

public class AcceptRide extends VoidUseCase<AcceptRide.Input> {

    private final AccountGateway accountGateway;
    private final RideRepository rideRepository;

    public AcceptRide(AccountGateway accountGateway, RideRepository rideRepository) {
        this.accountGateway = accountGateway;
        this.rideRepository = rideRepository;
    }

    @Override
    public void execute(Input input) {
        var account = accountGateway.getAccountById(UUID.fromString(input.driverId));
        if (account.isEmpty())
            throw new NotFoundException("Account not found with id: " + input.driverId);
        if (!account.get().getIsDriver())
            throw new BusinessException("Account is not from a driver");
        rideRepository.findRideById(RideId.with(input.rideId))
                .map(ride -> {
                    ride.accept(UUID.fromString(input.driverId));
                    return rideRepository.saveRide(ride);
                });
    }

    public record Input(String rideId, String driverId) {}
}
