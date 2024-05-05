package cleancodecleanarchitecture16.course.infra.gateway;

public interface PaymentGateway {
    void processPayment(PaymentGateway.Input input);

    record Input(String rideId, Double amount) {}
}
