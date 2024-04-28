package cleancodecleanarchitecture16.course.infra.repository.impl;

import cleancodecleanarchitecture16.course.domain.entity.Position;
import cleancodecleanarchitecture16.course.infra.database.entities.PositionEntity;
import cleancodecleanarchitecture16.course.infra.database.repositories.PositionJpaRepository;
import cleancodecleanarchitecture16.course.infra.repository.PositionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PositionRepositoryImpl implements PositionRepository {

    private final PositionJpaRepository positionJpaRepository;

    public PositionRepositoryImpl(PositionJpaRepository positionJpaRepository) {
        this.positionJpaRepository = positionJpaRepository;
    }

    @Override
    @Transactional
    public Position savePosition(Position position) {
        return positionJpaRepository.save(PositionEntity.of(position))
                .toPosition();
    }
}
