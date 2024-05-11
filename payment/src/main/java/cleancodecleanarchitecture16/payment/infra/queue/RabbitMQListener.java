package cleancodecleanarchitecture16.payment.infra.queue;

import cleancodecleanarchitecture16.payment.application.usecase.ProcessPayment;
import cleancodecleanarchitecture16.payment.infra.dto.ProcessPaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final MessageConverter jsonMessageConverter;
    private final ProcessPayment processPayment;

    @RabbitListener(queues = "#{queue.name}", ackMode = "AUTO")
    public void receiveMessage(Message amqpmessage) {
        var message = new String(amqpmessage.getBody());
        try {
            var processPaymentDTO = new ObjectMapper().readValue(message, ProcessPaymentDTO.class);
            processPayment.execute(new ProcessPayment.Input(processPaymentDTO.getRideId(), processPaymentDTO.getAmount()));
        } catch (JsonProcessingException e) {
            System.out.println("Cannot map the message: " + message);
        }
    }
}
