package cleancodecleanarchitecture16.course.domain.vo;

import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public record Coord(Double latitude, Double longitude) {

    public Coord {
        if (latitude == null || longitude == null)
            throw new BusinessException("Invalid coordinates");
        if (latitude < -90 || latitude > 90)
            throw new BusinessException("Invalid latitude");
        if (longitude < -180 || longitude > 180)
            throw new BusinessException("Invalid longitude");
    }
}
