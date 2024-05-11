package cleancodecleanarchitecture16.ride.application.gateway;

import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import cleancodecleanarchitecture16.ride.infra.dto.SignupResponseDTO;

import java.util.Optional;
import java.util.UUID;

public interface AccountGateway {
    Optional<SignupResponseDTO> signup(AccountDTO accountDTO);

    Optional<AccountDTO> getAccountById(UUID accountId);

}
