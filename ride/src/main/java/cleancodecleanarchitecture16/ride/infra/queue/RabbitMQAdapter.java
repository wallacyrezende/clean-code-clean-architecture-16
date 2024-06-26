package cleancodecleanarchitecture16.ride.infra.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAdapter implements Queue {

    private final ObjectMapper mapper = new ObjectMapper();
    private ConnectionFactory connectionFactory;

    @Override
    public void connect() {
        this.connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    @Override
    public void publish(String exchangeName, Object data) {
        connect();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.basicPublish(exchangeName, "", null, mapper.writeValueAsString(data).getBytes());
        } catch (Exception ex) {
            System.out.println("Error when trying publish message on exchange: " + exchangeName);
        }
    }

    @Override
    public void consume(String queueName, EventHandlerFunction<Object> callback) {
        connect();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueBind(queueName, "rideCompleted", "");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> callback.apply(mapper.writeValueAsString(delivery.getBody()));
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        } catch (Exception ex) {
            System.out.println("Error when trying consume message on queue: " + queueName);
        }
    }

}
