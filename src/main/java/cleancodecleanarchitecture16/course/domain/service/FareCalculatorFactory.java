package cleancodecleanarchitecture16.course.domain.service;

import java.time.LocalDateTime;

public class FareCalculatorFactory {
    public static FareCalculator create(LocalDateTime dateTime) {
        if (dateTime.getHour() > 22) {
            return new OvernightFareCalculator();
        } else {
            return new NormalFareCalculator();
        }
    }
}
