package cleancodecleanarchitecture16.ride.application.exceptions.handler;

import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class ExceptionHandlerResponseUtils {
    public static ExceptionResponse buildExceptionResponse(Exception ex, WebRequest request){
        return ExceptionResponse.builder().dateTime(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
    }

    public static ExceptionResponse buildGenericExceptionResponse(WebRequest request){
        return ExceptionResponse.builder().dateTime(LocalDateTime.now())
                .message("There was an error trying to process the request")
                .details(request.getDescription(false))
                .build();
    }
}
