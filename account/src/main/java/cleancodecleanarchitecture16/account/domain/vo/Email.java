package cleancodecleanarchitecture16.account.domain.vo;

import cleancodecleanarchitecture16.account.application.exceptions.BusinessException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^(.+)@(.+)$")) {
            throw new BusinessException("Email is invalid");
        }
    }
}
