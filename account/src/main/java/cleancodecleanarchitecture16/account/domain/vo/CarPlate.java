package cleancodecleanarchitecture16.account.domain.vo;

import cleancodecleanarchitecture16.account.application.exceptions.BusinessException;

public record CarPlate(String value) {

    public CarPlate {
        if(value != null && !value.matches("[A-Z]{3}[0-9]{4}")) {
            throw new BusinessException("Car plate is invalid");
        }
    }
}
