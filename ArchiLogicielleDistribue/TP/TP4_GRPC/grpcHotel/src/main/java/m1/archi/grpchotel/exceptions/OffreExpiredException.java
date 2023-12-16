package m1.archi.grpchotel.exceptions;

public class OffreExpiredException extends RuntimeException {
    public OffreExpiredException(String message) {
        super(message);
    }
}
