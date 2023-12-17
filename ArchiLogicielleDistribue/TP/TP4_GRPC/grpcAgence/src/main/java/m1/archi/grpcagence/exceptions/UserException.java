package m1.archi.grpcagence.exceptions;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
