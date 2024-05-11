package cleancodecleanarchitecture16.ride.infra.queue;

@FunctionalInterface
public interface EventHandlerFunction<T> {
    void apply(T event);
}
