package cleancodecleanarchitecture16.ride.infra.repository;

import cleancodecleanarchitecture16.ride.domain.entity.Position;

public interface PositionRepository {
    Position savePosition(Position position);
}
