package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.SignupResponse;
import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.model.exceptions.BusinessException;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Signup Controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final AccountService accountService;

    @Operation(summary = "Create a new Account")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@RequestBody AccountDTO accountDTO) throws BusinessException {
        return SignupResponse.builder().id(accountService.create(accountDTO)).build();
    }
}
