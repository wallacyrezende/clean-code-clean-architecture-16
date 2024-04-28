package cleancodecleanarchitecture16.course.domain.vo;

import cleancodecleanarchitecture16.course.domain.entity.Ride;
import cleancodecleanarchitecture16.course.model.exceptions.BusinessException;

public abstract class RideStatus {
    protected final Ride ride;
    protected String value;

    public RideStatus(Ride ride) {
        this.ride = ride;
    }

    public abstract void request();
    public abstract void accept();
    public abstract void start();

    public String getValue() {
        return value;
    }
}

class RequestedStatus extends RideStatus {
    public RequestedStatus(Ride ride) {
        super(ride);
        this.value = "requested";
    }

    @Override
    public void request() {
        throw new BusinessException("Invalid status");
    }

    @Override
    public void accept() {
        ride.setStatus(new AcceptedStatus(ride).getValue());
    }

    @Override
    public void start() {
        throw new BusinessException("Invalid status");
    }
}

class AcceptedStatus extends RideStatus {
    public AcceptedStatus(Ride ride) {
        super(ride);
        this.value = "accepted";
    }

    @Override
    public void request() {
        throw new BusinessException("Invalid status");
    }

    @Override
    public void accept() {
        throw new BusinessException("Invalid status");
    }

    @Override
    public void start() {
        ride.setStatus(new InProgressStatus(ride).getValue());
    }
}

class InProgressStatus extends RideStatus {
    public InProgressStatus(Ride ride) {
        super(ride);
        this.value = "in_progress";
    }

    @Override
    public void request() {
        throw new BusinessException("Invalid status");
    }

    @Override
    public void accept() {
        throw new BusinessException("Invalid status");
    }

    @Override
    public void start() {
        throw new BusinessException("Invalid status");
    }
}

