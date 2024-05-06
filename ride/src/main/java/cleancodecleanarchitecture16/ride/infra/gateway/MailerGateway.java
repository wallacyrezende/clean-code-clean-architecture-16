package cleancodecleanarchitecture16.ride.infra.gateway;

public interface MailerGateway {
    void send(String recipient, String subject, String content);
}
