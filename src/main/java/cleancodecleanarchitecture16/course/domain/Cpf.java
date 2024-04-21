package cleancodecleanarchitecture16.course.domain;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public record Cpf(String value) {

    public Cpf {
        if (value == null || !ValidateCpf.validate((value))) {
            throw new BusinessException("Cpf is invalid");
        }
    }
}
