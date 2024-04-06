package cleancodecleanarchitecture16.course.aula1.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDTO {
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private Boolean isPassenger;
    private Boolean isDriver;
}
