package cleancodecleanarchitecture16.course.infra.config;

import cleancodecleanarchitecture16.course.application.usecase.Signup;
import cleancodecleanarchitecture16.course.infra.gateway.MailerGateway;
import cleancodecleanarchitecture16.course.infra.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final AccountRepository accountRepository;
    private final MailerGateway mailerGateway;

    public UseCaseConfig(AccountRepository accountRepository, MailerGateway mailerGateway) {
        this.accountRepository = accountRepository;
        this.mailerGateway = mailerGateway;
    }

    @Bean
    public Signup signup() {
        return new Signup(accountRepository, mailerGateway);
    }
}
