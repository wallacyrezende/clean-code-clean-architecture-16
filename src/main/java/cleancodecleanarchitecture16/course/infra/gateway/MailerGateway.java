package cleancodecleanarchitecture16.course.infra.gateway;

public interface MailerGateway {
    void send(String recipient, String subject, String content);
}
