package cleancodecleanarchitecture16.course.infra.database.entities;

import cleancodecleanarchitecture16.course.domain.entity.Account;
import cleancodecleanarchitecture16.course.domain.vo.AccountId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "account")
public class AccountEntity {

    @Id
    private UUID accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column
    private String carPlate;

    @Column
    private Boolean isPassenger;

    @Column
    private Boolean isDriver;

    public static AccountEntity of(final Account account) {
        return new AccountEntity(UUID.fromString(account.accountId().value()), account.name().value(), account.email().value(),
                account.cpf().value(), account.carPlate().value(), account.isPassenger(), account.isDriver());
    }

    public Account toAccount() {
        return Account.restore(AccountId.with(accountId.toString()), name, email, cpf, carPlate, isPassenger, isDriver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity accountEntity = (AccountEntity) o;
        return Objects.equals(accountId, accountEntity.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
