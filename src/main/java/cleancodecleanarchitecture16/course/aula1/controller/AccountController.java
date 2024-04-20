package cleancodecleanarchitecture16.course.aula1.controller;

import cleancodecleanarchitecture16.course.aula1.model.dto.AccountDTO;
import cleancodecleanarchitecture16.course.aula1.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Account Controller")
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new Account")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@RequestBody AccountDTO accountDTO) {
        return SignupResponse.builder().id(accountService.create(accountDTO)).build();
    }

    @Operation(summary = "Return information of a account by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccount(@PathVariable UUID id) {
        return accountService.findByAccountId(id);
    }
}
