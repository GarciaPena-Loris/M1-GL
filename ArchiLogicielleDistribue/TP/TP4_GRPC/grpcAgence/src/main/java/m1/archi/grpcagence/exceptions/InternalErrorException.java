package m1.archi.grpcagence.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }
    public InternalErrorException(String message, Exception e) {
        super(message + " : " + e.getMessage());
    }
}
