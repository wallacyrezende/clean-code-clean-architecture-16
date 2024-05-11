package cleancodecleanarchitecture16.account.domain.vo;

import cleancodecleanarchitecture16.account.application.exceptions.BusinessException;

public record Name(String value) {

    public Name {
        if (value == null || !value.matches("[a-zA-Z]+ [a-zA-Z]+")) {
            throw new BusinessException("Name is invalid");
        }
    }
}
