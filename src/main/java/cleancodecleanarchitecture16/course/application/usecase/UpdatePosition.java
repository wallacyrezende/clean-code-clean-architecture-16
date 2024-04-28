package cleancodecleanarchitecture16.course.application.usecase;

import cleancodecleanarchitecture16.course.domain.entity.Position;
import cleancodecleanarchitecture16.course.domain.vo.RideId;
import cleancodecleanarchitecture16.course.infra.repository.PositionRepository;

public class UpdatePosition extends VoidUseCase<UpdatePosition.Input> {

    private final PositionRepository positionRepository;

    public UpdatePosition(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public void execute(Input input) {
        var position = Position.create(RideId.with(input.rideId), input.latitude(), input.longitude());
        positionRepository.savePosition(position);
    }

    public record Input(String rideId, Double latitude, Double longitude) {}
}
