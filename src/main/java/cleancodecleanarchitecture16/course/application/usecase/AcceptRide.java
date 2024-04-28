package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.model.exceptions.NotFoundException;

public class AcceptRide extends VoidUseCase<AcceptRide.Input> {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;

    public AcceptRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public void execute(Input input) {
        var account = accountRepository.findById(AccountId.with(input.driverId));
        if (account.isEmpty())
            throw new NotFoundException("Account not found with id: " + input.driverId);
        if (!account.get().isDriver())
            throw new BusinessException("Account is not from a driver");
        rideRepository.findRideById(RideId.with(input.rideId))
                .map(ride -> {
                    ride.accept(input.driverId);
                    return rideRepository.saveRide(ride);
                });
    }

    public record Input(String rideId, String driverId) {}
}
