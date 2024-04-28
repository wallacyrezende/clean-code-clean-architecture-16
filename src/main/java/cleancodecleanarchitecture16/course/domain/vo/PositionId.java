package cleancodecleanarchitecture16.course.domain.vo;

import java.util.UUID;

public record PositionId(String value) {

    public PositionId {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid value for PositionId");
        }
    }

    public static PositionId unique() {
        return new PositionId(UUID.randomUUID().toString());
    }

    public static PositionId with(final String value) {
        try {
            return new PositionId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for PositionId");
        }
    }
}
