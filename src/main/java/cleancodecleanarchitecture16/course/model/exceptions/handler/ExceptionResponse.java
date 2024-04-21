package cleancodecleanarchitecture16.course.model.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExceptionResponse {
    private LocalDateTime dateTime;
    private String message;
    private String details;
    private int code;
}
