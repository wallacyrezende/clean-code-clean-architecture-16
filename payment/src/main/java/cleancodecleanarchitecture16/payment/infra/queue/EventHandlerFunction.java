package cleancodecleanarchitecture16.payment.infra.queue;

@FunctionalInterface
public interface EventHandlerFunction<T> {
    void apply(T event);
}
