package cleancodecleanarchitecture16.ride.domain.event;

public interface DomainEvent {
    String getEventName();
    Object getData();
}
