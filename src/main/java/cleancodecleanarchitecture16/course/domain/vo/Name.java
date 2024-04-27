package cleancodecleanarchitecture16.course.domain.vo;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public record Name(String value) {

    public Name {
        if (value == null || !value.matches("[a-zA-Z]+ [a-zA-Z]+")) {
            throw new BusinessException("Name is invalid");
        }
    }
}
