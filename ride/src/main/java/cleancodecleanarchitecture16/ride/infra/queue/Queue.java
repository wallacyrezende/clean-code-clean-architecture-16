package cleancodecleanarchitecture16.ride.infra.queue;

public interface Queue {
    void connect();
    void publish(String exchangeName, Object data);
    void consume(String queueName, EventHandlerFunction<Object> callback);
}
