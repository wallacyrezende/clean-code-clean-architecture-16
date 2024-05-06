package cleancodecleanarchitecture16.ride.domain.vo;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^(.+)@(.+)$")) {
            throw new BusinessException("Email is invalid");
        }
    }
}
