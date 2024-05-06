package cleancodecleanarchitecture16.ride.domain.service;

public interface FareCalculator {
    double calculate(double distance);
}

class NormalFareCalculator implements FareCalculator {
    @Override
    public double calculate(double distance) {
        return distance * 2.1;
    }
}

class OvernightFareCalculator implements FareCalculator {
    @Override
    public double calculate(double distance) {
        return distance * 4.2;
    }
}

