package cleancodecleanarchitecture16.course.domain.vo;

import java.util.UUID;

public record RideId(String value) {

    public RideId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid value for RideId");
        }
    }

    public static RideId unique() {
        return new RideId(UUID.randomUUID().toString());
    }

    public static RideId with(final String value) {
        try {
            return new RideId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for RideId");
        }
    }
}
