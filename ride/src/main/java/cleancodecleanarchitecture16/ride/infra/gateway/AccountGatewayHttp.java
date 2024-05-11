package cleancodecleanarchitecture16.ride.infra.gateway;

import cleancodecleanarchitecture16.ride.application.gateway.AccountGateway;
import cleancodecleanarchitecture16.ride.infra.dto.AccountDTO;
import cleancodecleanarchitecture16.ride.infra.dto.SignupResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

@Component
public class AccountGatewayHttp implements AccountGateway {

    private static final String API_ACCOUNT = "http://localhost:8082/api/account";

    @Override
    public Optional<SignupResponseDTO> signup(AccountDTO accountDTO) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ACCOUNT + "/signup"))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(accountDTO)))
                    .build();
            HttpResponse<byte[]> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            SignupResponseDTO response = mapper.readValue(httpResponse.body(), SignupResponseDTO.class);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccountDTO> getAccountById(UUID accountId) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_ACCOUNT + "/" + accountId))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .GET()
                    .build();
            HttpResponse<byte[]> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            AccountDTO response = mapper.readValue(httpResponse.body(), AccountDTO.class);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return Optional.empty();
        }
    }
}
