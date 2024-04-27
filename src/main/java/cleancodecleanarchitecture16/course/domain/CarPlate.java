package cleancodecleanarchitecture16.course.domain;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public record CarPlate(String value) {

    public CarPlate {
        if(value != null && !value.matches("[A-Z]{3}[0-9]{4}")) {
            throw new BusinessException("Car plate is invalid");
        }
    }
}
