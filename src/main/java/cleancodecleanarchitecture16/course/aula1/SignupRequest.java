package cleancodecleanarchitecture16.course.aula1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private Boolean isPassenger;
    private Boolean isDriver;
}
