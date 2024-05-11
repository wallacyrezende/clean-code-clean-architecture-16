package cleancodecleanarchitecture16.ride.infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private Boolean isPassenger;
    private Boolean isDriver;
}
