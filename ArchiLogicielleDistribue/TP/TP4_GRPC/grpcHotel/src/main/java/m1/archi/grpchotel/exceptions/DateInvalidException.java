package m1.archi.grpchotel.exceptions;

public class DateInvalidException extends RuntimeException {
    public DateInvalidException(String message) {
        super(message);
    }
}
