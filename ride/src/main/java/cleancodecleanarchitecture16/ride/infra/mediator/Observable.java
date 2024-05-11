package cleancodecleanarchitecture16.ride.infra.mediator;

import cleancodecleanarchitecture16.ride.domain.event.DomainEvent;
import cleancodecleanarchitecture16.ride.infra.queue.EventHandlerFunction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Observable {
    private final Map<String, List<EventHandlerFunction<DomainEvent>>> observers;

    public Observable() {
        this.observers = new HashMap<>();
    }

    public void register(String eventName, EventHandlerFunction<DomainEvent> callback) {
        List<EventHandlerFunction<DomainEvent>> handlers = observers.computeIfAbsent(eventName, k -> new ArrayList<>());
        handlers.add(callback);
    }

    public void notify(DomainEvent event) {
        List<EventHandlerFunction<DomainEvent>> handlers = observers.get(event.getEventName());
        if (handlers != null) {
            for (EventHandlerFunction<DomainEvent> handler : handlers) {
                handler.apply(event);
            }
        }
    }
}

