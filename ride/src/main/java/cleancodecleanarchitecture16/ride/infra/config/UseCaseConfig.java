package cleancodecleanarchitecture16.ride.infra.config;

import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.application.gateway.PaymentGateway;
import cleancodecleanarchitecture16.ride.application.usecase.AcceptRide;
import cleancodecleanarchitecture16.ride.application.usecase.FinishRide;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;
import cleancodecleanarchitecture16.ride.application.usecase.StartRide;
import cleancodecleanarchitecture16.ride.application.usecase.UpdatePosition;
import cleancodecleanarchitecture16.ride.infra.mediator.Mediator;
import cleancodecleanarchitecture16.ride.infra.queue.Queue;
import cleancodecleanarchitecture16.ride.infra.repository.PositionRepository;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

@Configuration
public class UseCaseConfig {

    private final AccountGateway accountGateway;
    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;
    private final PaymentGateway paymentGateway;
    private final Mediator mediator;
    private final Queue queue;

    public UseCaseConfig(AccountGateway accountGateway, RideRepository rideRepository, PositionRepository positionRepository,
                         PaymentGateway paymentGateway, Mediator mediator, @Qualifier("rabbitMQAdapter") Queue queue) {
        this.accountGateway = requireNonNull(accountGateway);
        this.rideRepository = requireNonNull(rideRepository);
        this.positionRepository = requireNonNull(positionRepository);
        this.paymentGateway = requireNonNull(paymentGateway);
        this.mediator = requireNonNull(mediator);
        this.queue = queue;
    }

    @Bean
    public RequestRide requestRide() {
        return new RequestRide(accountGateway, rideRepository);
    }

    @Bean
    public GetRide  getRide() {
        return new GetRide(rideRepository, accountGateway);
    }

    @Bean
    public AcceptRide acceptRide() {
        return new AcceptRide(accountGateway, rideRepository);
    }

    @Bean
    public StartRide startRide() {
        return new StartRide(rideRepository);
    }

    @Bean
    public UpdatePosition updatePosition() {
        return new UpdatePosition(positionRepository, rideRepository);
    }

    @Bean
    public FinishRide finishRide() {
        return new FinishRide(rideRepository, paymentGateway, mediator, queue);
    }
}
