package cleancodecleanarchitecture16.payment.application.usecase;

import org.springframework.stereotype.Component;

@Component
public class ProcessPayment extends VoidUseCase<ProcessPayment.Input> {

    @Override
    public void execute(Input input) {
        System.out.printf("Registration transaction with id: %s in the value: %s %n", input.rideId(), input.amount());
    }

    public record Input(String rideId, Double amount) {
    }
}
