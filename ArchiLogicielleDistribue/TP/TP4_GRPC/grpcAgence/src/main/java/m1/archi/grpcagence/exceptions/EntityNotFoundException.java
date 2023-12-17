package m1.archi.grpcagence.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, long id) {
        super(entity + " not found with id " + id);
    }
    public EntityNotFoundException(String entity, String id) {
        super(entity + " not found with id " + id);
    }

}

