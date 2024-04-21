package cleancodecleanarchitecture16.course.infra.gateway;

import org.springframework.stereotype.Component;

@Component
public class MailerGatewayMemory implements MailerGateway {
    @Override
    public void send(String recipient, String subject, String content) {
        System.out.println("Sending email to " + recipient + " with subject " + subject + " and content " + content);
    }
}
