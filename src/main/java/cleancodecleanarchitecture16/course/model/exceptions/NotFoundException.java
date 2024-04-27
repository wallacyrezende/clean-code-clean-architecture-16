package cleancodecleanarchitecture16.course.model.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NotFoundException extends RuntimeException  {

    @Serial
    private static final long serialVersionUID = 3372675067599282433L;

    public NotFoundException(String message) {
        super(message);
    }
}
