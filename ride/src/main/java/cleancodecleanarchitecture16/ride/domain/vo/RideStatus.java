package cleancodecleanarchitecture16.ride.domain.vo;

import cleancodecleanarchitecture16.ride.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.ride.domain.entity.Ride;

public abstract class RideStatus {
    protected final Ride ride;
    protected String value;

    public RideStatus(Ride ride) {
        this.ride = ride;
    }

    public abstract void request();
    public abstract void accept();
    public abstract void start();
    public abstract void finish();

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

    @Override
    public void finish() {
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

    @Override
    public void finish() {
        throw new BusinessException("Invalid status");
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

    @Override
    public void finish() {
        this.ride.setStatus(new CompletedStatus(ride).getValue());
    }
}

class CompletedStatus extends RideStatus {
    public CompletedStatus(Ride ride) {
        super(ride);
        this.value = "completed";
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

    @Override
    public void finish() {
        throw new BusinessException("Invalid status");
    }
}

