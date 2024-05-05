package cleancodecleanarchitecture16.course.infra.gateway;

import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayMemory implements PaymentGateway {

    @Override
    public void processPayment(Input input) {
        System.out.printf("Registration transaction with id: %s in the value: %s %n", input.rideId(), input.amount());
    }
}
