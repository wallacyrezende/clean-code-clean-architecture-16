package cleancodecleanarchitecture16.ride.infra.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAdapter implements Queue {

    private ConnectionFactory connectionFactory;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void connect() {
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    @Override
    public void publish(String exchangeName, Object data) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.basicPublish(exchangeName, "", null, mapper.writeValueAsString(data).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void consume(String queueName, EventHandlerFunction<Object> callback) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueBind(queueName, "rideCompleted", "");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> callback.apply(mapper.writeValueAsString(delivery.getBody()));
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
