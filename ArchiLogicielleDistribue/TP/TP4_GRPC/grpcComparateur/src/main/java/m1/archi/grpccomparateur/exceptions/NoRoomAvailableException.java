package m1.archi.grpccomparateur.exceptions;

public class NoRoomAvailableException extends RuntimeException {
    public NoRoomAvailableException(String message) {
        super(message);
    }
}
