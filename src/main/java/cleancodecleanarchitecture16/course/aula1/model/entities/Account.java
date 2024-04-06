package cleancodecleanarchitecture16.course.aula1.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String carPlate;

    @Column(nullable = false)
    private Boolean isPassenger;

    @Column(nullable = false)
    private Boolean isDriver;
}
