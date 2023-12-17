package m1.archi.grpcagence.services;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import m1.archi.grpchotel.data.TimeConverter;
import m1.archi.grpchotel.exceptions.DateInvalidException;
import m1.archi.grpchotel.exceptions.EntityNotFoundException;
import m1.archi.grpchotel.exceptions.InternalErrorException;
import m1.archi.grpchotel.exceptions.OffreExpiredException;
import m1.archi.grpchotel.models.*;
import m1.archi.grpchotel.repositories.*;
import m1.archi.services.RechercherChambresResponse;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@GrpcService
public class HotelServiceImpl extends HotelServiceGrpc.HotelServiceImplBase {
    private final HotelRepository hotelRepository;
    private final OffreRepository offreRepository;
    private final ClientRepository clientRepository;
    private final CarteRepository carteRepository;
    private final ReservationRepository reservationRepository;


    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, OffreRepository offreRepository, ClientRepository clientRepository, CarteRepository carteRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.offreRepository = offreRepository;
        this.clientRepository = clientRepository;
        this.carteRepository = carteRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void getAllHotels(Empty request, StreamObserver<HotelListResponse> responseObserver) {
        List<Hotel> hotels = hotelRepository.findAll();
        // Convertir la liste d'hôtels en réponse gRPC
        HotelListResponse response = HotelListResponse.newBuilder().addAllHotels(hotels.stream().map(Hotel::toProto).toList()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllIdHotels(Empty request, StreamObserver<IdHotelListResponse> responseObserver) {
        try {
            List<Long> idHotels = hotelRepository.findAll().stream().map(Hotel::getIdHotel).collect(Collectors.toList());
            // Convertir la liste d'hôtels en réponse gRPC
            IdHotelListResponse response = IdHotelListResponse.newBuilder().addAllIdHotel(idHotels).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération des id des hotels", e);
        }
    }

    @Override
    public void getHotelById(GetHotelByIdRequest request, StreamObserver<HotelResponse> responseObserver) {
        long idHotel = request.getIdHotel();
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new EntityNotFoundException("Hotel", idHotel));
        try {
            HotelResponse response = HotelResponse.newBuilder().setHotel(hotel.toProto()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la récupération de l'hotel", e);
        }
    }

    @Override
    public void createHotel(HotelRequest request, StreamObserver<HotelResponse> responseObserver) {
        try {
            Hotel newHotel = new Hotel(request.getHotel());
            Hotel savedHotel = hotelRepository.save(newHotel);
            HotelResponse response = HotelResponse.newBuilder().setHotel(savedHotel.toProto()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la création de l'hotel", e);
        }
    }

    @Override
    public void updateHotel(UpdateHotelRequest request, StreamObserver<HotelResponse> responseObserver) {
        try {
            long hotelId = request.getIdHotel();
            Hotel newHotel = new Hotel(request.getHotel());
            Hotel updatedHotel = hotelRepository.findById(hotelId).map(hotel -> {
                hotel.setNom(newHotel.getNom());
                hotel.setAdresse(newHotel.getAdresse());
                hotel.setNombreEtoiles(newHotel.getNombreEtoiles());
                hotel.setImageHotel(newHotel.getImageHotel());
                return hotelRepository.save(hotel);
            }).orElseGet(() -> {
                newHotel.setIdHotel(hotelId);
                return hotelRepository.save(newHotel);
            });

            HotelResponse response = HotelResponse.newBuilder().setHotel(updatedHotel.toProto()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la mise à jour de l'hotel", e);
        }
    }

    @Override
    public void deleteHotel(DeleteHotelRequest request, StreamObserver<Empty> responseObserver) {
        long idHotel = request.getIdHotel();
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new EntityNotFoundException("Hotel", idHotel));
        try {
            hotelRepository.delete(hotel);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la suppression de l'hotel", e);
        }
    }

    // Focntion de Recherche
    @Override
    public void rechercherChambres(
            RechercherChambresRequest request,
            StreamObserver<RechercherChambresResponse> responseObserver
    ) {
        long idHotel = request.getIdHotel();
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new EntityNotFoundException("Hotel", idHotel));

        // Vérifie que la date d'arrivée est avant la date de départ
        Timestamp dateArrivee = request.getDateArrivee();
        Timestamp dateDepart = request.getDateDepart();

        if (dateArrivee.getSeconds() > dateDepart.getSeconds()) {
            // Générer une exception gRPC et l'envoyer au client
            responseObserver.onError(new DateInvalidException("La date d'arrivée doit être avant la date de départ"));
            return;
        }

        try {
            // Appelle la fonction de recherche
            ArrayList<Offre> offres = rechercherChambres(
                    hotel,
                    request.getVille(),
                    request.getDateArrivee(),
                    request.getDateDepart(),
                    request.getPrixMin(),
                    request.getPrixMax(),
                    request.getNombreEtoiles(),
                    request.getNombrePersonne()
            );

            if (offres.isEmpty()) {
                RechercherChambresResponse response = RechercherChambresResponse.newBuilder().addAllOffre(new ArrayList<>()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                for (Offre offre : offres) {
                    offreRepository.save(offre);
                    hotel.getOffres().add(offre);
                }
                hotelRepository.save(hotel);

                // Construit la réponse avec les résultats
                RechercherChambresResponse response = RechercherChambresResponse.newBuilder()
                        .addAllOffre(offres.stream().map(Offre::toProto).toList())
                        .build();

                // Envoie la réponse au client
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la recherche de chambres", e);
        }
    }

    // Fonction de reservation
    @Override
    public void reserverChambres(ReserverChambresRequest request, StreamObserver<ReserverChambresResponse> responseObserver) {
        long idOffre = request.getIdOffre();
        Offre storedOffre = offreRepository.findById(idOffre).orElseThrow(() -> new EntityNotFoundException("Offre", idOffre));
        long idHotel = storedOffre.getIdHotel();
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new EntityNotFoundException("Hotel", idHotel));

        if (storedOffre.getDateExpiration().toInstant().toEpochMilli() < System.currentTimeMillis()) {
            throw new OffreExpiredException("L'offre a expiré");
        }

        try {
            Carte carte = carteRepository.findByNumero(request.getNumeroCarte()).orElseGet(() -> carteRepository.save(new Carte(null, request.getNomCarte(), request.getNumeroCarte(), request.getExpirationCarte(), request.getCCVCarte())));

            Client clientPrincipal = clientRepository.findByEmail(request.getEmail()).orElseGet(() -> clientRepository.save(new Client(null, request.getNomClient(), request.getPrenomClient(), request.getEmail(), request.getTelephone(), carte, new ArrayList<>())));

            double montantReservation = storedOffre.getPrixAvecReduction();
            if (request.getPetitDejeuner()) {
                int nombreNuits = (int) ChronoUnit.DAYS.between(
                        storedOffre.getDateArrivee().toLocalDateTime(),
                        storedOffre.getDateDepart().toLocalDateTime()
                );

                double montantPetitDejeuner = (hotel.getNombreEtoiles() * 4) *
                        storedOffre.getNombreLitsTotal() * nombreNuits;
                montantReservation += montantPetitDejeuner;
            }
            Reservation reservation = new Reservation(null, hotel.getIdHotel(), new ArrayList<>(), clientPrincipal.getIdClient(), storedOffre.getDateArrivee(), storedOffre.getDateDepart(), storedOffre.getNombreLitsTotal(), (double) Math.round(montantReservation * 10) / 10, request.getPetitDejeuner());
            reservationRepository.save(reservation);

            for (Chambre chambre : storedOffre.getChambres()) {
                reservation.getChambresReservees().add(chambre);
            }
            reservationRepository.save(reservation);

            hotel.getReservations().add(reservation);
            hotelRepository.save(hotel);

            clientPrincipal.getHistoriqueReservations().add(reservation);
            clientRepository.save(clientPrincipal);

            ReserverChambresResponse response = ReserverChambresResponse.newBuilder()
                    .setReservation(reservation.toProto())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la réservation de chambres", e);
        }
    }

    // Fonction d'offre
    @Override
    public void updateOffreHotel(UpdateOffreRequest request, StreamObserver<UpdateOffreResponse> responseObserver) {
        long idHotel = request.getIdHotel();
        long idOffre = request.getIdOffre();
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(() -> new EntityNotFoundException("Hotel", idHotel));

        try {
            Offre newOffre = new Offre(request.getOffre());
            Offre updatedOffre = offreRepository.findById(idOffre).map(offre -> {
                offre.setNombreLitsTotal(newOffre.getNombreLitsTotal());
                offre.setPrix(newOffre.getPrix());
                offre.setPrixAvecReduction(newOffre.getPrixAvecReduction());
                offre.setDateArrivee(newOffre.getDateArrivee());
                offre.setDateDepart(newOffre.getDateDepart());
                offre.setDateExpiration(newOffre.getDateExpiration());
                offre.setChambres(newOffre.getChambres());
                offre.setIdHotel(hotel.getIdHotel());
                return offreRepository.save(offre);
            }).orElseGet(() -> {
                newOffre.setIdHotel(hotel.getIdHotel());
                return offreRepository.save(newOffre);
            });

            UpdateOffreResponse response = UpdateOffreResponse.newBuilder()
                    .setOffre(updatedOffre.toProto())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            throw new InternalErrorException("Erreur lors de la mise à jour de l'offre", e);
        }
    }


    // ### Fonctions de recherche ###
    // Fonction récursive pour trouver des combinaisons de chambres
    private void chercherCombinaison(ArrayList<Chambre> chambresDisponibles, int personnesRestantes,
                                     ArrayList<Chambre> combinaisonActuelle,
                                     Set<List<Integer>> combinaisonsDeLits, List<ArrayList<Chambre>> listeCombinaisonsChambres) {
        if (personnesRestantes == 0) {
            if (!combinaisonActuelle.isEmpty()) {
                combinaisonActuelle.sort(Comparator.comparing(Chambre::getNombreLits));
                // Générer une liste du nombre de lits dans la combinaison actuelle
                List<Integer> lits = combinaisonActuelle.stream()
                        .map(Chambre::getNombreLits)
                        .collect(Collectors.toList());

                if (!combinaisonsDeLits.contains(lits)) {
                    combinaisonsDeLits.add(lits);
                    listeCombinaisonsChambres.add(new ArrayList<>(combinaisonActuelle));
                }
            }
        } else if (personnesRestantes > 0) {
            for (Chambre chambre : chambresDisponibles) {
                if (chambre.getNombreLits() <= personnesRestantes) {
                    if (!combinaisonActuelle.contains(chambre)) {
                        combinaisonActuelle.add(chambre);
                        chercherCombinaison(chambresDisponibles, personnesRestantes - chambre.getNombreLits(),
                                combinaisonActuelle, combinaisonsDeLits, listeCombinaisonsChambres);
                        combinaisonActuelle.remove(chambre);
                    }
                }
            }
        }
    }

    private ArrayList<Offre> rechercherChambres(Hotel hotel, String ville, Timestamp dateArrivee, Timestamp dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne) {
        ArrayList<Offre> offres = new ArrayList<>();
        if (hotel.getAdresse().getVille().equals(ville) && hotel.getNombreEtoiles() >= nombreEtoiles) {

            ArrayList<Chambre> chambresDisponibles = new ArrayList<>();

            // On ajoute toute les chambre qui correspondent aux critères
            for (Chambre chambre : hotel.getChambres()) {
                if (chambre.getPrix() >= prixMin && chambre.getPrix() <= prixMax
                        && chambre.getNombreLits() <= nombrePersonne) {
                    chambresDisponibles.add(chambre);
                }
            }

            // On supprime les chambre déja reservées
            List<Chambre> chambresARetirer = new ArrayList<>();

            for (Reservation reservation : hotel.getReservations()) {
                if (reservation.getDateArrivee().toInstant().toEpochMilli() < dateDepart.getSeconds() * 1000L
                        && reservation.getDateDepart().toInstant().toEpochMilli() > dateArrivee.getSeconds() * 1000L) {
                    chambresARetirer.addAll(reservation.getChambresReservees());
                }
            }
            chambresDisponibles.removeIf(chambre -> chambresARetirer.stream().anyMatch(ch -> ch.getNumero() == chambre.getNumero()));

            if (!chambresDisponibles.isEmpty()) {
                // Vérifier si il y a une chambre avec le nombre de lits correspondant
                for (Chambre chambre : chambresDisponibles) {
                    if (chambre.getNombreLits() == nombrePersonne) {
                        int nombreNuits = (int) (dateDepart.getSeconds() - dateArrivee.getSeconds()) / (60 * 60 * 24);
                        double prix = Math.round(chambre.getPrix() * nombreNuits * 10.0) / 10.0;

                        java.sql.Timestamp dateExpiration = java.sql.Timestamp.from(Instant.now().plusSeconds(3600));
                        Offre offre = new Offre(null, chambre.getNombreLits(), prix, prix, TimeConverter.convertTimestamp(dateArrivee), TimeConverter.convertTimestamp(dateDepart), dateExpiration, new ArrayList<>(Collections.singletonList(chambre)), hotel.getIdHotel());

                        offres.add(offre);
                        return offres;
                    }
                }

                // Vérifier s'il existe une combinaison de chambres qui correspond au nombre de
                // personnes
                ArrayList<ArrayList<Chambre>> listeCombinaisonsChambres = new ArrayList<>();
                Set<List<Integer>> combinaisonsDeLits = new HashSet<>();

                chercherCombinaison(chambresDisponibles, nombrePersonne, new ArrayList<>(),
                        combinaisonsDeLits, listeCombinaisonsChambres);

                for (ArrayList<Chambre> combinaisonChambresDisponibles : listeCombinaisonsChambres) {
                    int nombreNuits = (int) (dateDepart.getSeconds() - dateArrivee.getSeconds()) / (60 * 60 * 24);
                    double prix = Math.round(combinaisonChambresDisponibles.stream().mapToDouble(Chambre::getPrix).sum() * nombreNuits * 10.0) / 10.0;

                    java.sql.Timestamp dateExpiration = java.sql.Timestamp.from(Instant.now().plusSeconds(3600));
                    Offre offre = new Offre(null, nombrePersonne, prix, prix, TimeConverter.convertTimestamp(dateArrivee), TimeConverter.convertTimestamp(dateDepart), dateExpiration, combinaisonChambresDisponibles, hotel.getIdHotel());

                    offres.add(offre);
                }

                return offres;
            }
        }
        return offres;
    }
}
