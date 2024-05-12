package cleancodecleanarchitecture16.payment.application.usecase;

import cleancodecleanarchitecture16.payment.domain.entity.Transaction;
import cleancodecleanarchitecture16.payment.infra.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProcessPayment extends VoidUseCase<ProcessPayment.Input> {

    private final TransactionRepository transactionRepository;

    @Override
    public void execute(Input input) {
        transactionRepository.saveTransaction(Transaction.create(input.rideId(), input.amount()));
        System.out.printf("Registration transaction with id: %s in the value: %s %n", input.rideId(), input.amount());
    }

    public record Input(String rideId, Double amount) {
    }
}
