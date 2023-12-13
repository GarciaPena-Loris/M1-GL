package m1.archi.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import m1.archi.models.HotelOuterClass;
import m1.archi.models.ReservationOuterClass;

public class ReserverChambresServiceImpl extends ReserverChambreServiceGrpc.ReserverChambreServiceImplBase {
    private final HotelOuterClass.Hotel hotel;

    public ReserverChambresServiceImpl(HotelOuterClass.Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void reserverChambres(Reserver.ReserverChambresRequest request, StreamObserver<Reserver.ReserverChambresResponse> responseObserver) {
        try {
            // Appelle la fonction de réservation
            ReservationOuterClass.Reservation reservation = reserverChambres(
                    request.getIdOffre(),
                    request.getPetitDejeuner(),
                    request.getNomClient(),
                    request.getPrenomClient(),
                    request.getEmail(),
                    request.getTelephone(),
                    request.getNomCarte(),
                    request.getNumeroCarte(),
                    request.getExpirationCarte(),
                    request.getCCVCarte()
            );

            // Construit la réponse avec les résultats
            Reserver.ReserverChambresResponse response = Reserver.ReserverChambresResponse.newBuilder()
                    .setReservation(reservation)
                    .build();

            // Envoie la réponse au client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Gère les erreurs et renvoie une réponse d'erreur au client si nécessaire
            responseObserver.onError(Status.INTERNAL.withDescription("Erreur lors de la réservation : " + e.getMessage()).asRuntimeException());
        }
    }

    private ReservationOuterClass.Reservation reserverChambres(long idOffre, boolean petitDejeuner, String nomClient, String prenomClient, String email, String telephone, String nomCarte, String numeroCarte, String expirationCarte, String CCVCarte) {
        return null;
    }
}
