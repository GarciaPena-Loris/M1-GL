package m1.archi.grpccomparateur.services;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import m1.archi.grpccomparateur.exceptions.EntityNotFoundException;
import m1.archi.grpccomparateur.exceptions.InternalErrorException;
import m1.archi.grpccomparateur.exceptions.NoRoomAvailableException;
import m1.archi.grpccomparateur.models.Comparateur;
import m1.archi.grpccomparateur.models.hotelModels.Offre;
import m1.archi.grpccomparateur.repositories.ComparateurRepository;
import m1.archi.proto.models.AgenceOuterClass;
import m1.archi.proto.models.ComparateurOuterClass;
import m1.archi.proto.models.HotelOuterClass;
import m1.archi.proto.models.ReservationOuterClass;
import m1.archi.proto.services.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@GrpcService
public class ComparateurServiceImpl extends ComparateurServiceGrpc.ComparateurServiceImplBase {
    private final ComparateurRepository comparateurRepository;

    // Proxy
    private final AgenceServiceGrpc.AgenceServiceBlockingStub agenceServiceBlockingStub;

    @Autowired
    public ComparateurServiceImpl(ComparateurRepository comparateurRepository) {
        this.comparateurRepository = comparateurRepository;

        try {
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 9090)
                    .usePlaintext()
                    .build();

            agenceServiceBlockingStub = AgenceServiceGrpc.newBlockingStub(channel);
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de se connecter au serveur agence : " + e.getMessage(), e);
        }
    }

    @Override
    public void getFirstComparateur(Empty request, StreamObserver<ComparateurOuterClass.Comparateur> responseObserver) {
        try {
            Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));
            responseObserver.onNext(comparateur.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de récupérer le premier comparateur : " + e.getMessage(), e);
        }
    }

    @Override
    public void getFirstIdComparateur(Empty request, StreamObserver<IdComparateurResponse> responseObserver) {
        try {
            Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));
            responseObserver.onNext(IdComparateurResponse.newBuilder().setIdComparateur(comparateur.getIdComparateur()).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de récupérer l'id du premier comparateur : " + e.getMessage(), e);
        }
    }

    @Override
    public void countAgences(Empty request, StreamObserver<CountResponse> responseObserver) {
        try {
            Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));
            responseObserver.onNext(CountResponse.newBuilder().setCount(comparateur.getIdAgences().size()).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de compter le nombre d'agence " + e.getMessage(), e);
        }
    }

    @Override
    public void getAgenceById(GetAgenceByIdRequest request, StreamObserver<AgenceOuterClass.Agence> responseObserver) {
        try {
            Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));
            long idAgence = request.getIdAgence();

            if (!comparateur.getIdAgences().contains(idAgence)) {
                throw new EntityNotFoundException("Agency", idAgence);
            }

            AgenceOuterClass.Agence agence = agenceServiceBlockingStub.getAgenceById(request);

            responseObserver.onNext(agence);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de récupérer l'agence : " + e.getMessage(), e);
        }
    }

    @Override
    public void getHotelById(GetHotelAgenceByIdRequest request, StreamObserver<HotelOuterClass.Hotel> responseObserver) {
        try {
            Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));
            long idAgence = request.getIdAgence();

            if (!comparateur.getIdAgences().contains(idAgence)) {
                throw new EntityNotFoundException("Agency", idAgence);
            }

            HotelOuterClass.Hotel hotel = agenceServiceBlockingStub.getHotelById(request);

            responseObserver.onNext(hotel);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            throw new InternalErrorException("Impossible de récupérer l'hotel : " + e.getMessage(), e);
        }
    }


    // Partie Recherche et Reservation
    @Override
    public void rechercheChambresComparateur(RechercherChambresComparateurRequest request, StreamObserver<RechercherChambresComparateurResponse> responseObserver) {
        Comparateur comparateur = comparateurRepository.findFirst().orElseThrow(() -> new EntityNotFoundException("Comparator", "first"));

        try {
            Map<Long, List<List<Offre>>> mapOffresParAgences = new HashMap<>();
            List<Long> idAgences = comparateur.getIdAgences();


            for (Long idAgence : idAgences) {
                RechercherChambresAgenceRequest agenceRequest = RechercherChambresAgenceRequest.newBuilder()
                        .setIdAgence(idAgence)
                        .setVille(request.getVille())
                        .setDateArrivee(request.getDateArrivee())
                        .setDateDepart(request.getDateDepart())
                        .setPrixMin(request.getPrixMin())
                        .setPrixMax(request.getPrixMax())
                        .setNombreEtoiles(request.getNombreEtoiles())
                        .setNombrePersonne(request.getNombrePersonne())
                        .build();


                RechercherChambresAgenceResponse agenceResponse = agenceServiceBlockingStub.rechercherChambresAgence(agenceRequest);

                List<List<Offre>> offresParAgences = agenceResponse.getOffresParHotelList().stream()
                        .map(offresParHotel -> offresParHotel.getOffresList().stream().map(Offre::new).collect(Collectors.toList()))
                        .collect(Collectors.toList());

                if (!offresParAgences.isEmpty()) {
                    // Trier par hotel (nombre etoiles)
                    offresParAgences.sort(Comparator.comparing(o -> o.getFirst().getNombreEtoiles()));

                    // Trier les offres par prix
                    for (List<Offre> offres : offresParAgences) {
                        offres.sort(Comparator.comparing(Offre::getPrixAvecReduction));
                    }

                    mapOffresParAgences.put(idAgence, offresParAgences);
                }
            }

            if (mapOffresParAgences.isEmpty()) {
                throw new NoRoomAvailableException("No offers found");
            }

            // Convertir la map en proto
            Map<Long, OffresParAgence> offresParAgenceMap = mapOffresParAgences.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> OffresParAgence.newBuilder()
                            .addAllOffresParHotel(entry.getValue().stream().map(offres -> OffresParHotel.newBuilder()
                                    .addAllOffres(offres.stream().map(Offre::toProto).collect(Collectors.toList()))
                                    .build()).collect(Collectors.toList()))
                            .build()));

            RechercherChambresComparateurResponse response = RechercherChambresComparateurResponse.newBuilder()
                    .putAllOffresParAgence(offresParAgenceMap)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de rechercher les chambres : " + e.getMessage(), e);
        }
    }

    @Override
    public void reserverChambresComparateur(ReserverChambresComparateurRequest request, StreamObserver<ReservationOuterClass.Reservation> responseObserver) {
        try {
            // Créer la requête pour l'appel gRPC
            ReserverChambresAgenceRequest agenceRequest = ReserverChambresAgenceRequest.newBuilder()
                    .setEmail(request.getEmail())
                    .setMotDePasse(request.getMotDePasse())
                    .setIdAgence(request.getIdAgence())
                    .setIdOffre(request.getIdOffre())
                    .setPetitDejeuner(request.getPetitDejeuner())
                    .setNomClient(request.getNomClient())
                    .setPrenomClient(request.getPrenomClient())
                    .setTelephone(request.getTelephone())
                    .setNomCarte(request.getNomCarte())
                    .setNumeroCarte(request.getNumeroCarte())
                    .setExpirationCarte(request.getExpirationCarte())
                    .setCCVCarte(request.getCCVCarte())
                    .build();

            // Appeler le service gRPC
            ReservationChambresAgenceResponse agenceResponse = agenceServiceBlockingStub.reserverChambresAgence(agenceRequest);

            // Récupérer la réservation
            ReservationOuterClass.Reservation reservation = agenceResponse.getReservation();

            // Envoyer la réponse
            responseObserver.onNext(reservation);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de réserver les chambres : " + e.getMessage(), e);
        }
    }

}
