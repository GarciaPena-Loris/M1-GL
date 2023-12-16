package m1.archi.grpchotel.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message, Exception e) {
        super(message + " : " + e.getMessage());
    }
}
