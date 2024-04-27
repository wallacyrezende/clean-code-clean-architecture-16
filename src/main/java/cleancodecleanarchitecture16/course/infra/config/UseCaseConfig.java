package cleancodecleanarchitecture16.course.infra.config;

import cleancodecleanarchitecture16.course.application.usecase.GetAccount;
import cleancodecleanarchitecture16.course.application.usecase.RequestRide;
import cleancodecleanarchitecture16.course.application.usecase.Signup;
import cleancodecleanarchitecture16.course.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import cleancodecleanarchitecture16.course.infra.repository.RideRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.requireNonNull;

@Configuration
public class UseCaseConfig {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;
    private final MailerGateway mailerGateway;

    public UseCaseConfig(AccountRepository accountRepository, RideRepository rideRepository, MailerGateway mailerGateway) {
        this.accountRepository = requireNonNull(accountRepository);
        this.rideRepository = requireNonNull(rideRepository);
        this.mailerGateway = requireNonNull(mailerGateway);
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
}
