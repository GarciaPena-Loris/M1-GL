package m1.archi.grpcagence.exceptions;

public class OffreExpiredException extends RuntimeException {
    public OffreExpiredException(String message) {
        super(message);
    }
}
