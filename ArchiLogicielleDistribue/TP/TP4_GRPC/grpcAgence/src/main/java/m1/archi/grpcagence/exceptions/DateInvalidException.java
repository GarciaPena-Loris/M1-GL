package m1.archi.grpcagence.exceptions;

public class DateInvalidException extends RuntimeException {
    public DateInvalidException(String message) {
        super(message);
    }
}
