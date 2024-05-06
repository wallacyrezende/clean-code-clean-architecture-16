package cleancodecleanarchitecture16.payment.infra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessPaymentDTO {
    private String rideId;
    private Double amount;
}
