package cleancodecleanarchitecture16.account.infra.gateway;

public interface MailerGateway {
    void send(String recipient, String subject, String content);
}
