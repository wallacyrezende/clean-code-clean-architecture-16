package cleancodecleanarchitecture16.ride.domain.service;

import cleancodecleanarchitecture16.ride.domain.entity.Position;
import cleancodecleanarchitecture16.ride.domain.vo.Segment;

import java.util.List;
import java.util.stream.IntStream;

public class DistanceCalculator {
    public static double calculate(List<Position> positions) {
        return IntStream.range(0, positions.size() - 1)
                .mapToDouble(i -> {
                    Position currentPosition = positions.get(i);
                    Position nextPosition = positions.get(i + 1);
                    Segment segment = new Segment(currentPosition.coord(), nextPosition.coord());
                    return segment.getDistance();
                })
                .sum();
    }
}
