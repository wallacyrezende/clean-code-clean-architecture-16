package cleancodecleanarchitecture16.payment.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Transaction {

    private UUID transactionId;
    private UUID rideId;
    private Double amount;
    private LocalDateTime date;
    private String status;

    public static Transaction restore(final UUID transactionId, final UUID rideId, final Double amount,
                                      final LocalDateTime date, final String status) {
        return new Transaction(transactionId, rideId, amount, date, status);
    }

    public static Transaction create(final String rideId, final Double amount) {
        return new Transaction(UUID.randomUUID(), UUID.fromString(rideId), amount, LocalDateTime.now(), "success");
    }
}
