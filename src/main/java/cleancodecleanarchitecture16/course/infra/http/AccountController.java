package cleancodecleanarchitecture16.course.infra.http;

import cleancodecleanarchitecture16.course.application.usecase.GetAccount;
import cleancodecleanarchitecture16.course.application.usecase.Signup;
import cleancodecleanarchitecture16.course.model.dto.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "Account Controller")
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final Signup signup;
    private final  GetAccount getAccount;

    @Operation(summary = "Create a new Account")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountDTO accountDTO) {
        final var input =
                new Signup.Input(accountDTO.getName(), accountDTO.getEmail(), accountDTO.getCpf(), accountDTO.getCarPlate(),
                        accountDTO.getIsPassenger(), accountDTO.getIsDriver());
        final var output = signup.execute(input);
        return ResponseEntity.created(URI.create("/api/account/" + output.accountId())).body(output);
    }

    @Operation(summary = "Get account by account id")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getById(@PathVariable String accountId) {
        return getAccount.execute(new GetAccount.Input(accountId))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
