package cleancodecleanarchitecture16.ride.infra.queue;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediatorAdapter implements Queue {

    private final Map<String, List<EventHandlerFunction<Object>>> handlers;

    public MediatorAdapter() {
        this.handlers = new HashMap<>();
    }

    @Override
    public void connect() {
    }

    @Override
    public void publish(String queueName, Object data) {
        List<EventHandlerFunction<Object>> handlers = this.handlers.get(queueName);
        if (handlers != null) {
            for (EventHandlerFunction<Object> handler : handlers) {
                handler.apply(data);
            }
        }
    }

    @Override
    public void consume(String eventName, EventHandlerFunction<Object> callback) {
        List<EventHandlerFunction<Object>> handlers = this.handlers.computeIfAbsent(eventName, _ -> new ArrayList<>());
        handlers.add(callback);
    }
}
