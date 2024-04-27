package cleancodecleanarchitecture16.course.model.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException  {

    @Serial
    private static final long serialVersionUID = 3372675067599282433L;

    public NotFoundException(String message) {
        super(message);
    }
}
