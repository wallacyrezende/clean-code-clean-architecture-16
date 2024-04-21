package cleancodecleanarchitecture16.course.domain;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

import java.util.Objects;

import static java.lang.Boolean.TRUE;

public class Account {

    private final AccountId accountId;
    private Name name;
    private Email email;
    private Cpf cpf;
    private String carPlate;
    private Boolean isPassenger;
    private Boolean isDriver;

    private Account(final AccountId accountId, final String name, final String email, final String cpf, final String carPlate,
                    final Boolean isPassenger, final Boolean isDriver) {
        if (accountId == null)
            throw new BusinessException("Invalid accountId for Account");
        if (TRUE.equals(isDriver) && carPlate != null && !carPlate.matches("[A-Z]{3}[0-9]{4}"))
            throw new BusinessException("Car plate is invalid");
        this.accountId = accountId;
        this.setName(name);
        this.setEmail(email);
        this.setCpf(cpf);
        this.setCarPlate(carPlate);
        this.setIsPassenger(isPassenger);
        this.setIsDriver(isDriver);
    }

    public static Account create(final String name, final String email, final String cpf, final String carPlate,
                                 final Boolean isPassenger, final Boolean isDriver) {
        return new Account(AccountId.unique(), name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public static Account restore(final AccountId accountId, final String name, final String email, final String cpf,
                                  final String carPlate, final Boolean isPassenger, final Boolean isDriver) {
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public AccountId accountId() {
        return accountId;
    }

    public Name name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public Cpf cpf() {
        return cpf;
    }

    public String carPlate() {
        return carPlate;
    }

    public Boolean isPassenger() {
        return isPassenger;
    }

    public Boolean isDriver() {
        return isDriver;
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public void setCpf(String cpf) {
        this.cpf = new Cpf(cpf);
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public void setIsPassenger(Boolean isPassenger) {
        this.isPassenger = isPassenger;
    }

    public void setIsDriver(Boolean isDriver) {
        this.isDriver = isDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
}
