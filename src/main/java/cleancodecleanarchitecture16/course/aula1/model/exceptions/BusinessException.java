package cleancodecleanarchitecture16.course.aula1.model.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException  {

    @Serial
    private static final long serialVersionUID = 3372675067599282433L;
    private final int code;

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = 100;
    }
}
