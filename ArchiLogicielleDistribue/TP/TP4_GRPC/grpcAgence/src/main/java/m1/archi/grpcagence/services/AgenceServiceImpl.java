package m1.archi.grpcagence.services;


import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import m1.archi.grpcagence.exceptions.EntityNotFoundException;
import m1.archi.grpcagence.exceptions.InternalErrorException;
import m1.archi.grpcagence.exceptions.UserException;
import m1.archi.grpcagence.models.Agence;
import m1.archi.grpcagence.models.Utilisateur;
import m1.archi.grpcagence.models.hotelModels.Hotel;
import m1.archi.grpcagence.models.hotelModels.Offre;
import m1.archi.grpcagence.models.hotelModels.Reservation;
import m1.archi.grpcagence.repositories.AgenceRepository;
import m1.archi.grpcagence.repositories.UtilisateurRepository;
import m1.archi.proto.models.AgenceOuterClass;
import m1.archi.proto.models.HotelOuterClass;
import m1.archi.proto.models.UtilisateurOuterClass;
import m1.archi.proto.services.*;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@GrpcService
public class AgenceServiceImpl extends AgenceServiceGrpc.AgenceServiceImplBase {
    private final AgenceRepository agenceRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Proxy
    private final HotelServiceGrpc.HotelServiceBlockingStub hotelServiceBlockingStub;

    @Autowired
    public AgenceServiceImpl(AgenceRepository hotelRepository, UtilisateurRepository offreRepository) {
        this.agenceRepository = hotelRepository;
        this.utilisateurRepository = offreRepository;

        try {
            // Créer un channel pour communiquer avec le serveur hotel
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 9080)
                    .usePlaintext()
                    .build();

            // Créer un stub pour pouvoir communiquer avec le serveur hotel
            hotelServiceBlockingStub = HotelServiceGrpc.newBlockingStub(channel);
        } catch (Exception e) {
            throw new InternalErrorException("Impossible de se connecter au serveur hotel : " + e.getMessage(), e);
        }
    }

    @Override
    public void getAllAgences(Empty request, StreamObserver<AgenceListResponse> responseObserver) {
        try {
            List<Agence> agences = agenceRepository.findAll();
            AgenceListResponse response = AgenceListResponse.newBuilder()
                    .addAllAgences(agences.stream().map(Agence::toProto).collect(Collectors.toList()))
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des agences", e);
        }
    }

    @Override
    public void getAllIdAgences(Empty request, StreamObserver<IdAgenceListResponse> responseObserver) {
        try {
            List<Long> idAgences = agenceRepository.findAll().stream().map(Agence::getIdAgence).collect(Collectors.toList());
            IdAgenceListResponse response = IdAgenceListResponse.newBuilder()
                    .addAllIdAgence(idAgences)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des id des agences", e);
        }
    }

    @Override
    public void getAgenceById(GetAgenceByIdRequest request, StreamObserver<AgenceOuterClass.Agence> responseObserver) {
        long idAgence = request.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
        try {
            responseObserver.onNext(agence.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération de l'agence", e);
        }
    }

    @Override
    public void createAgence(AgenceOuterClass.Agence request, StreamObserver<AgenceOuterClass.Agence> responseObserver) {
        try {
            Agence agence = new Agence(request);
            Agence savedAgence = agenceRepository.save(agence);
            responseObserver.onNext(savedAgence.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la création de l'agence", e);
        }
    }

    @Override
    public void updateAgence(UpdateAgenceRequest request, StreamObserver<AgenceOuterClass.Agence> responseObserver) {
        try {
            long agenceId = request.getIdAgence();
            Agence newAgence = new Agence(request.getAgence());
            Agence updatedAgence = agenceRepository.findById(agenceId).map(agence -> {
                agence.setNom(newAgence.getNom());
                agence.setReductionHotels(newAgence.getReductionHotels());
                agence.setListeUtilisateurs(newAgence.getListeUtilisateurs());
                return agenceRepository.save(agence);
            }).orElseGet(() -> {
                newAgence.setIdAgence(agenceId);
                return agenceRepository.save(newAgence);
            });

            responseObserver.onNext(updatedAgence.toProto());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la mise à jour de l'agence", e);
        }
    }

    @Override
    public void deleteAgence(DeleteAgenceRequest request, StreamObserver<Empty> responseObserver) {
        long idAgence = request.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
        try {
            agenceRepository.delete(agence);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la suppression de l'agence", e);
        }
    }

    @Override
    public void countHotels(CountRequest request, StreamObserver<CountResponse> responseObserver) {
        try {
            long idAgence = request.getIdAgence();
            Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
            responseObserver.onNext(CountResponse.newBuilder().setCount(agence.getReductionHotels().size()).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors du comptage des hotels", e);
        }
    }

    @Override
    public void getAllIdHotels(GetAllIdHotelsRequest request, StreamObserver<IdHotelListResponse> responseObserver) {
        try {
            long idAgence = request.getIdAgence();
            Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
            List<Long> idHotels = new ArrayList<>(agence.getReductionHotels().keySet());
            IdHotelListResponse response = IdHotelListResponse.newBuilder()
                    .addAllIdHotel(idHotels)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des id des hotels", e);
        }
    }

    @Override
    public void getHotelById(GetHotelAgenceByIdRequest request, StreamObserver<HotelOuterClass.Hotel> responseObserver) {
        try {
            long idAgence = request.getIdAgence();
            Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
            long idHotel = request.getIdHotel();
            if (!agence.getReductionHotels().containsKey(idHotel)) {
                throw new EntityNotFoundException("Hotel", idHotel);
            }
            GetHotelByIdRequest getHotelByIdRequest = GetHotelByIdRequest.newBuilder()
                    .setIdHotel(idHotel)
                    .build();
            HotelOuterClass.Hotel hotel = hotelServiceBlockingStub.getHotelById(getHotelByIdRequest);
            responseObserver.onNext(hotel);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération de l'hotel", e);
        }
    }


    // Fonction de Recherche
    @Override
    public void rechercherChambresAgence(RechercherChambresAgenceRequest request, StreamObserver<RechercherChambresAgenceResponse> responseObserver) {
        long idAgence = request.getIdAgence();
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

        try {
            List<List<Offre>> listeOffresParHotels = new ArrayList<>();
            // private Map<Integer, Hotel> reductionHotels;
            // Pour chaque element de a map
            for (Map.Entry<Long, Integer> reductionHotel : agence.getReductionHotels().entrySet()) {
                // Appel de la fonction de recherche de l'hotel via le proxy
                RechercherChambresHotelRequest requestHotel = RechercherChambresHotelRequest.newBuilder()
                        .setIdHotel(reductionHotel.getKey())
                        .setVille(request.getVille())
                        .setDateArrivee(request.getDateArrivee())
                        .setDateDepart(request.getDateDepart())
                        .setPrixMin(request.getPrixMin())
                        .setPrixMax(request.getPrixMax())
                        .setNombreEtoiles(request.getNombreEtoiles())
                        .setNombrePersonne(request.getNombrePersonne())
                        .build();

                RechercherChambresHotelResponse responseHotel = hotelServiceBlockingStub.rechercherChambresHotel(requestHotel);

                List<Offre> offresHotel = responseHotel.getOffreList().stream().map(Offre::new).collect(Collectors.toList());

                // Apply the reduction
                if (!offresHotel.isEmpty()) {
                    for (Offre offre : offresHotel) {
                        offre.setPrixAvecReduction(Math.round(offre.getPrix() * (1 - reductionHotel.getValue() / 100.0) * 100.0) / 100.0);

                        try {
                            UpdateOffreRequest updateOffreRequest = UpdateOffreRequest.newBuilder()
                                    .setIdHotel(offre.getIdHotel())
                                    .setIdOffre(offre.getIdOffre())
                                    .setOffre(offre.toProto())
                                    .build();

                            UpdateOffreResponse updateOffreResponse = hotelServiceBlockingStub.updateOffreHotel(updateOffreRequest);

                        } catch (Exception ex) {
                            throw new InternalErrorException("Hotel problem: " + ex.getMessage(), ex);
                        }
                    }
                    listeOffresParHotels.add(offresHotel);
                }
            }

            RechercherChambresAgenceResponse response = RechercherChambresAgenceResponse.newBuilder()
                    .addAllOffresParHotel(listeOffresParHotels.stream().map(offres -> OffresParHotel.newBuilder().addAllOffres(offres.stream().map(Offre::toProto).collect(Collectors.toList())).build()).collect(Collectors.toList()))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            throw new InternalErrorException("Hotel problem: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void reserverChambresAgence(ReserverChambresAgenceRequest request, StreamObserver<ReservationChambresAgenceResponse> responseObserver) {
        long idAgence = request.getIdAgence();
        String email = request.getEmail();
        String motDePasse = request.getMotDePasse();
        long idOffre = request.getIdOffre();
        boolean petitDejeuner = request.getPetitDejeuner();
        String nomClient = request.getNomClient();
        String prenomClient = request.getPrenomClient();
        String telephone = request.getTelephone();
        String nomCarte = request.getNomCarte();
        String numeroCarte = request.getNumeroCarte();
        String expirationCarte = request.getExpirationCarte();
        String CCVCarte = request.getCCVCarte();

        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

        try {
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Utilisateur", email));
            if (!utilisateur.getMotDePasse().equals(motDePasse)) {
                throw new UserException("Wrong password");
            }
            if (agence.getListeUtilisateurs().stream().noneMatch(utilisateurAgence -> Objects.equals(utilisateurAgence.getIdUtilisateur(), utilisateur.getIdUtilisateur()))) {
                throw new UserException("Utilisateur not registered in this agency");
            }

            ReserverChambresHotelRequest requestHotel = ReserverChambresHotelRequest.newBuilder()
                    .setIdOffre(idOffre)
                    .setPetitDejeuner(petitDejeuner)
                    .setNomClient(nomClient)
                    .setPrenomClient(prenomClient)
                    .setEmail(email)
                    .setTelephone(telephone)
                    .setNomCarte(nomCarte)
                    .setNumeroCarte(numeroCarte)
                    .setExpirationCarte(expirationCarte)
                    .setCCVCarte(CCVCarte)
                    .build();

            ReserverChambresHotelResponse responseHotel = hotelServiceBlockingStub.reserverChambresHotel(requestHotel);

            Reservation reservation = new Reservation(responseHotel.getReservation());

            utilisateur.getIdReservations().add(reservation.getIdReservation());
            utilisateurRepository.save(utilisateur);

            ReservationChambresAgenceResponse response = ReservationChambresAgenceResponse.newBuilder()
                    .setReservation(reservation.toProto())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            throw new InternalErrorException("Hotel problem: " + ex.getMessage(), ex);
        }
    }

    // Partie user
    @Override
    public void getUtilisateurById(GetUtilisateurByIdRequest request, StreamObserver<UtilisateurOuterClass.Utilisateur> responseObserver) {
        long idAgence = request.getIdAgence();
        long idUtilisateur = request.getIdUtilisateur();

        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

        Utilisateur utilisateur = agence.getListeUtilisateurs().stream()
                .filter(utilisateurAgence -> utilisateurAgence.getIdUtilisateur() == idUtilisateur)
                .findFirst()
                .orElseThrow(() -> new UserException("Utilisateur with id " + idUtilisateur + " not registered in this agency"));

        responseObserver.onNext(utilisateur.toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void getUtilisateurByEmailMotDePasse(GetUtilisateurByEmailMotDePasseRequest request, StreamObserver<UtilisateurOuterClass.Utilisateur> responseObserver) {
        long idAgence = request.getIdAgence();
        String email = request.getEmail();
        String motDePasse = request.getMotDePasse();

        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

        Utilisateur utilisateur = agence.getListeUtilisateurs().stream()
                .filter(utilisateurAgence -> utilisateurAgence.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UserException("Utilisateur with email " + email + " not registered in this agency"));

        if (!utilisateur.getMotDePasse().equals(motDePasse))
            throw new UserException("Wrong password");

        responseObserver.onNext(utilisateur.toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void createUtilisateur(CreateUtilisateurRequest request, StreamObserver<UtilisateurOuterClass.Utilisateur> responseObserver) {
        long idAgence = request.getIdAgence();
        Utilisateur utilisateur = new Utilisateur(request.getUtilisateur());

        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));
        utilisateurRepository.save(utilisateur);

        responseObserver.onNext(utilisateur.toProto());
        responseObserver.onCompleted();
    }

    @Override
    public void addAgenceUtilisateur(AddAgenceUtilisateurRequest request, StreamObserver<UtilisateurOuterClass.Utilisateur> responseObserver) {
        long idAgence = request.getIdAgence();
        Utilisateur utilisateur = new Utilisateur(request.getUtilisateur());

        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new EntityNotFoundException("Agence", idAgence));

        if (agence.getListeUtilisateurs().stream().anyMatch(utilisateurAgence -> utilisateurAgence.getEmail().equals(utilisateur.getEmail())))
            throw new UserException("Utilisateur " + utilisateur.getEmail() + " already registered in this agency");

        agence.getListeUtilisateurs().add(utilisateur);
        agenceRepository.save(agence);

        responseObserver.onNext(utilisateur.toProto());
        responseObserver.onCompleted();
    }
}
