package cleancodecleanarchitecture16.ride.domain.vo;

public record Segment(Coord from, Coord to) {
    public double getDistance() {
        final double earthRadius = 6371;
        final double degreesToRadians = Math.PI / 180;
        double deltaLat = (to.latitude() - from.latitude()) * degreesToRadians;
        double deltaLon = (to.longitude() - from.longitude()) * degreesToRadians;
        double a =
                Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                        Math.cos(from.latitude() * degreesToRadians) *
                                Math.cos(to.latitude() * degreesToRadians) *
                                Math.sin(deltaLon / 2) *
                                Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return Math.round(distance);
    }
}
