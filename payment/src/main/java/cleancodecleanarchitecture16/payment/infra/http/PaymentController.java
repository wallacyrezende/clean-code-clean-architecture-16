package cleancodecleanarchitecture16.payment.infra.http;

import cleancodecleanarchitecture16.payment.application.usecase.ProcessPayment;
import cleancodecleanarchitecture16.payment.infra.dto.ProcessPaymentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment Controller")
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPayment processPayment;

    @Operation(summary = "Process a payment")
    @PostMapping("/process")
    @ResponseStatus(HttpStatus.OK)
    public void processPayment(@RequestBody ProcessPaymentDTO processPaymentDTO) {
        processPayment.execute(new ProcessPayment.Input(processPaymentDTO.getRideId(), processPaymentDTO.getAmount()));
    }
}