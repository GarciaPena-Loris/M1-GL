package m1.archi.restagence.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HotelExceptionAdvice {
    @ExceptionHandler(HotelException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String hotelExceptionHandler(HotelException ex) {
        return String.format("{\"%s\": \"%s\"}", "error", ex.getMessage());
    }
}
