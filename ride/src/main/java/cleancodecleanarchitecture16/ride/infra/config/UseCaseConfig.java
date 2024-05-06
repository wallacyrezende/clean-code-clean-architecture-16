package cleancodecleanarchitecture16.ride.infra.config;

import cleancodecleanarchitecture16.ride.application.usecase.AcceptRide;
import cleancodecleanarchitecture16.ride.application.usecase.FinishRide;
import cleancodecleanarchitecture16.ride.application.usecase.GetAccount;
import cleancodecleanarchitecture16.ride.application.usecase.GetRide;
import cleancodecleanarchitecture16.ride.application.usecase.RequestRide;
import cleancodecleanarchitecture16.ride.application.usecase.Signup;
import cleancodecleanarchitecture16.ride.application.usecase.StartRide;
import cleancodecleanarchitecture16.ride.application.usecase.UpdatePosition;
import cleancodecleanarchitecture16.ride.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.ride.infra.gateway.PaymentGateway;
import cleancodecleanarchitecture16.ride.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.ride.infra.repository.PositionRepository;
import cleancodecleanarchitecture16.ride.infra.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

@Configuration
public class UseCaseConfig {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;
    private final MailerGateway mailerGateway;
    private final PositionRepository positionRepository;
    private final PaymentGateway paymentGateway;

    public UseCaseConfig(AccountRepository accountRepository, RideRepository rideRepository, MailerGateway mailerGateway,
                         PositionRepository positionRepository, PaymentGateway paymentGateway) {
        this.accountRepository = requireNonNull(accountRepository);
        this.rideRepository = requireNonNull(rideRepository);
        this.mailerGateway = requireNonNull(mailerGateway);
        this.positionRepository = requireNonNull(positionRepository);
        this.paymentGateway = requireNonNull(paymentGateway);
    }

    @Bean
    public Signup signup() {
        return new Signup(accountRepository, mailerGateway);
    }

    @Bean
    public GetAccount getAccount() {
        return new GetAccount(accountRepository);
    }

    @Bean
    public RequestRide requestRide() {
        return new RequestRide(accountRepository, rideRepository);
    }

    @Bean
    public GetRide  getRide() {
        return new GetRide(rideRepository, accountRepository);
    }

    @Bean
    public AcceptRide acceptRide() {
        return new AcceptRide(accountRepository, rideRepository);
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
        return new FinishRide(rideRepository, paymentGateway);
    }
}