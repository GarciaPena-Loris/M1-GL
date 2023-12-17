package m1.archi.grpcagence.exceptions;

import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler(EntityNotFoundException.class)
    public StatusException handleEntityNotFoundException(EntityNotFoundException e) {
        return Status.NOT_FOUND.withDescription(e.getMessage()).asException();
    }

    @GrpcExceptionHandler(InternalErrorException.class)
    public StatusException handleInternalErrorException(InternalErrorException e) {
        return Status.INTERNAL.withDescription(e.getMessage()).asException();
    }

    @GrpcExceptionHandler(DateInvalidException.class)
    public StatusException handleDateInvalidException(DateInvalidException e) {
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asException();
    }

    @GrpcExceptionHandler(OffreExpiredException.class)
    public StatusException handleOffreExpiredException(OffreExpiredException e) {
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asException();
    }

}
