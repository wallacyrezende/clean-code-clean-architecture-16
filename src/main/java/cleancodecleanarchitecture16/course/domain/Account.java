package cleancodecleanarchitecture16.course.domain;

import java.util.Objects;

public class Account {

    private final AccountId accountId;
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private Boolean isPassenger;
    private Boolean isDriver;

    public Account(final AccountId accountId, final String name, final String email, final String cpf, final String carPlate,
                   final Boolean isPassenger, final Boolean isDriver) {
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

    public static Account restore(final AccountId accountId, final String name, final String email, final String cpf, final String carPlate,
                                  final Boolean isPassenger, final Boolean isDriver) {
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public AccountId accountId() {
        return accountId;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String cpf() {
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
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
