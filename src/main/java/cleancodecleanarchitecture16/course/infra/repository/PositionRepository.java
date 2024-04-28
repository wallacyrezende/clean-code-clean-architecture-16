package cleancodecleanarchitecture16.course.infra.repository;

import cleancodecleanarchitecture16.course.domain.entity.Position;

public interface PositionRepository {
    Position savePosition(Position position);
}
