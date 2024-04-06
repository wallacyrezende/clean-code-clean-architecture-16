package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.SignupResponse;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

import static cleancodecleanarchitecture16.course.aula1.validateCpf.validate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountDTO accountDTO) {
        try {
            accountService.validateEmailAlreadyExists(accountDTO);
            if (accountDTO.getName().matches("[a-zA-Z]+ [a-zA-Z]+")) {
                if (accountDTO.getEmail().matches("^(.+)@(.+)$")) {
                    if (validate(accountDTO.getCpf())) {
                        if (accountDTO.getIsDriver()) {
                            if (accountDTO.getCarPlate().matches("[A-Z]{3}[0-9]{4}")) {
                                SignupResponse obj = new SignupResponse(accountService.saveAccount(accountDTO));
                                return ResponseEntity.ok(obj);
                            } else {
                                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-5);
                            }
                        } else {
                            SignupResponse obj = new SignupResponse(accountService.saveAccount(accountDTO));
                            return ResponseEntity.ok(obj);
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-1);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-2);
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-4);
        }
    }
}
