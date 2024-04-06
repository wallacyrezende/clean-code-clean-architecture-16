package cleancodecleanarchitecture16.course.aula1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static cleancodecleanarchitecture16.course.aula1.validateCpf.validate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final DataSource dataSource;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            UUID id = UUID.randomUUID();

            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM cccat16.account WHERE email = ?");
            selectStatement.setString(1, req.getEmail());
            ResultSet acc = selectStatement.executeQuery();
            if (!acc.next()) {
                if (req.getName().matches("[a-zA-Z]+ [a-zA-Z]+")) {
                    if (req.getEmail().matches("^(.+)@(.+)$")) {
                        if (validate(req.getCpf())) {
                            if (req.getIsDriver()) {
                                if (req.getCarPlate().matches("[A-Z]{3}[0-9]{4}")) {
                                    PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO cccat16.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) VALUES (?, ?, ?, ?, ?, ?, ?)");
                                    insertStatement.setObject(1, id);
                                    insertStatement.setString(2, req.getName());
                                    insertStatement.setString(3, req.getEmail());
                                    insertStatement.setString(4, req.getCpf());
                                    insertStatement.setString(5, req.getCarPlate());
                                    insertStatement.setBoolean(6, req.getIsPassenger());
                                    insertStatement.setBoolean(7, req.getIsDriver());
                                    insertStatement.executeUpdate();

                                    SignupResponse obj = new SignupResponse(id);
                                    return ResponseEntity.ok(obj);
                                } else {
                                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-5);
                                }
                            } else {
                                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO cccat16.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) VALUES (?, ?, ?, ?, ?, ?, ?)");
                                insertStatement.setObject(1, id);
                                insertStatement.setString(2, req.getName());
                                insertStatement.setString(3, req.getEmail());
                                insertStatement.setString(4, req.getCpf());
                                insertStatement.setString(5, req.getCarPlate());
                                insertStatement.setBoolean(6, req.getIsPassenger());
                                insertStatement.setBoolean(7, req.getIsDriver());
                                insertStatement.executeUpdate();

                                SignupResponse obj = new SignupResponse(id);
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
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(-4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
