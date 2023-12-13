package m1.archi.services;

import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import m1.archi.dao.HotelDao;
import m1.archi.dao.OffreDao;
import m1.archi.domaines.Hotel;
import m1.archi.domaines.Offre;
import m1.archi.models.ChambreOuterClass;
import m1.archi.models.HotelOuterClass;
import m1.archi.models.OffreOuterClass;
import m1.archi.models.ReservationOuterClass;

import java.util.*;
import java.util.stream.Collectors;

public class RechercherChambresServiceImpl extends RechercherChambresServiceGrpc.RechercherChambresServiceImplBase {
    private final HotelOuterClass.Hotel hotel;

    public RechercherChambresServiceImpl(HotelOuterClass.Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void rechercherChambres(
            Rechercher.RechercherChambresRequest request,
            StreamObserver<Rechercher.RechercherChambresResponse> responseObserver
    ) {
        try {
            // Vérifie que la date d'arrivée est avant la date de départ
            Timestamp dateArrivee = request.getDateArrivee();
            Timestamp dateDepart = request.getDateDepart();

            if (dateArrivee.getSeconds() > dateDepart.getSeconds()) {
                // Générer une exception gRPC et l'envoyer au client
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("La date d'arrivée doit être avant la date de départ").asRuntimeException());
                return;
            }

            // Appelle la fonction de recherche
            ArrayList<OffreOuterClass.Offre> offres = rechercherChambres(
                    request.getVille(),
                    request.getDateArrivee(),
                    request.getDateDepart(),
                    request.getPrixMin(),
                    request.getPrixMax(),
                    request.getNombreEtoiles(),
                    request.getNombrePersonne()
            );

            // Ajouter les offres dans la base de données
            OffreDao offreDao = new OffreDao();

            for (OffreOuterClass.Offre offreProto : offres) {
                Offre offre = new Offre(offreProto);
                // Enregistrer l'offre dans la base de données
                offreDao.create(offre);
            }

            // Ajoute les offres a la liste des offres de l'hotel
            HotelDao hotelDao = new HotelDao();

            // Obtenez l'hôtel par son ID (assurez-vous d'avoir l'ID de l'hôtel dans votre protocole)
            Hotel hotelDB = hotelDao.findById(hotel.getIdHotel());

            // Mettez à jour la liste d'offres de l'hôtel avec les nouvelles offres
            hotelDB.getOffres().addAll(offres.stream().map(Offre::new).toList());

            // Enregistrez la mise à jour dans la base de données
            hotelDao.update(hotelDB);


            // Construit la réponse avec les résultats
            Rechercher.RechercherChambresResponse response = Rechercher.RechercherChambresResponse.newBuilder()
                    .addAllOffre(offres)
                    .build();

            // Envoie la réponse au client
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Gère les erreurs et renvoie une réponse d'erreur au client si nécessaire
            responseObserver.onError(Status.INTERNAL.withDescription("Erreur lors de la recherche : " + e.getMessage()).asRuntimeException());
        }
    }

    // Fonction récursive pour trouver des combinaisons de chambres
    private void chercherCombinaison(ArrayList<ChambreOuterClass.Chambre> chambresDisponibles, int personnesRestantes,
                                     ArrayList<ChambreOuterClass.Chambre> combinaisonActuelle,
                                     Set<List<Integer>> combinaisonsDeLits, List<ArrayList<ChambreOuterClass.Chambre>> listeCombinaisonsChambres) {
        if (personnesRestantes == 0) {
            if (!combinaisonActuelle.isEmpty()) {
                combinaisonActuelle.sort(Comparator.comparing(ChambreOuterClass.Chambre::getNombreLits));
                // Générer une liste du nombre de lits dans la combinaison actuelle
                List<Integer> lits = combinaisonActuelle.stream()
                        .map(ChambreOuterClass.Chambre::getNombreLits)
                        .collect(Collectors.toList());

                if (!combinaisonsDeLits.contains(lits)) {
                    combinaisonsDeLits.add(lits);
                    listeCombinaisonsChambres.add(new ArrayList<>(combinaisonActuelle));
                }
            }
        } else if (personnesRestantes > 0) {
            for (ChambreOuterClass.Chambre chambre : chambresDisponibles) {
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

    private ArrayList<OffreOuterClass.Offre> rechercherChambres(String ville, Timestamp dateArrivee, Timestamp dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne) {
        ArrayList<OffreOuterClass.Offre> offres = new ArrayList<>();
        if (this.hotel.getAdresse().getVille().equals(ville) && this.hotel.getNombreEtoiles() >= nombreEtoiles) {

            ArrayList<ChambreOuterClass.Chambre> chambresDisponibles = new ArrayList<>();

            // On ajoute toute les chambre qui correspondent aux critères
            for (ChambreOuterClass.Chambre chambre : this.hotel.getChambresList()) {
                if (chambre.getPrix() >= prixMin && chambre.getPrix() <= prixMax
                        && chambre.getNombreLits() <= nombrePersonne) {
                    chambresDisponibles.add(chambre);
                }
            }

            // On supprime les chambre déja reservées
            List<ChambreOuterClass.Chambre> chambresARetirer = new ArrayList<>();

            for (ReservationOuterClass.Reservation reservation : this.hotel.getReservationsList()) {
                if (reservation.getDateArrivee().getSeconds() < dateDepart.getSeconds()
                        && reservation.getDateDepart().getSeconds() > dateArrivee.getSeconds()) {
                    chambresARetirer.addAll(reservation.getChambresReserveesList());
                }
            }
            chambresDisponibles.removeIf(chambre -> chambresARetirer.stream().anyMatch(ch -> ch.getNumero() == chambre.getNumero()));

            if (!chambresDisponibles.isEmpty()) {
                // Vérifier si il y a une chambre avec le nombre de lits correspondant
                for (ChambreOuterClass.Chambre chambre : chambresDisponibles) {
                    if (chambre.getNombreLits() == nombrePersonne) {
                        int nombreNuits = (int) (dateDepart.getSeconds() - dateArrivee.getSeconds()) / (60 * 60 * 24);
                        double prix = Math.round(chambre.getPrix() * nombreNuits * 10.0) / 10.0;

                        OffreOuterClass.Offre offre = OffreOuterClass.Offre.newBuilder()
                                .setNombreLitsTotal(chambre.getNombreLits())
                                .setPrix(prix)
                                .setDateArrivee(dateArrivee)
                                .setDateDepart(dateDepart)
                                .addAllChambres(Collections.singletonList(chambre))
                                .build();

                        offres.add(offre);
                        return offres;
                    }
                }

                // Vérifier s'il existe une combinaison de chambres qui correspond au nombre de
                // personnes
                ArrayList<ArrayList<ChambreOuterClass.Chambre>> listeCombinaisonsChambres = new ArrayList<>();
                Set<List<Integer>> combinaisonsDeLits = new HashSet<>();

                chercherCombinaison(chambresDisponibles, nombrePersonne, new ArrayList<>(),
                        combinaisonsDeLits, listeCombinaisonsChambres);

                for (ArrayList<ChambreOuterClass.Chambre> combinaisonChambresDisponibles : listeCombinaisonsChambres) {
                    int nombreNuits = (int) (dateDepart.getSeconds() - dateArrivee.getSeconds()) / (60 * 60 * 24);
                    double prix = Math.round(combinaisonChambresDisponibles.stream().mapToDouble(ChambreOuterClass.Chambre::getPrix).sum() * nombreNuits * 10.0) / 10.0;

                    OffreOuterClass.Offre offre = OffreOuterClass.Offre.newBuilder()
                            .setNombreLitsTotal(nombrePersonne)
                            .setPrix(prix)
                            .setDateArrivee(dateArrivee)
                            .setDateDepart(dateDepart)
                            .addAllChambres(combinaisonChambresDisponibles)
                            .build();

                    offres.add(offre);
                }

                return offres;
            }
        }
        return offres;
    }
}
