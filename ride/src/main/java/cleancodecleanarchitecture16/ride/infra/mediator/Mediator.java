package cleancodecleanarchitecture16.ride.infra.mediator;

import cleancodecleanarchitecture16.ride.infra.queue.EventHandlerFunction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Mediator {
    private final Map<String, List<EventHandlerFunction<Object>>> handlers;

    public Mediator() {
        this.handlers = new HashMap<>();
    }

    public void register(String eventName, EventHandlerFunction<Object> callback) {
        List<EventHandlerFunction<Object>> handlers = this.handlers.computeIfAbsent(eventName, k -> new ArrayList<>());
        handlers.add(callback);
    }

    public void publish(String eventName, Object data) {
        List<EventHandlerFunction<Object>> handlers = this.handlers.get(eventName);
        if (handlers != null) {
            for (EventHandlerFunction<Object> handler : handlers) {
                handler.apply(data);
            }
        }
    }
}

