package m1.archi.grpchotel.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, long id) {
        super(entity + " not found with id " + id);
    }
}

