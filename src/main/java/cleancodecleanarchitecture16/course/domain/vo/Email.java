package cleancodecleanarchitecture16.course.domain.vo;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^(.+)@(.+)$")) {
            throw new BusinessException("Email is invalid");
        }
    }
}
