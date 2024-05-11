package cleancodecleanarchitecture16.payment.infra.queue;


public interface Queue {
    void connect();
    void publish(String exchangeName, Object data);
    void consume(String queueName, EventHandlerFunction<Object> callback);
}
