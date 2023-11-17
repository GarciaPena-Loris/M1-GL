package m1.archi.repository;

import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.*;
import m1.archi.model.Agence;
import m1.archi.service.*;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class AgenceRepositoryImpl implements AgenceRepository {

    /* ATTRIBUTES */
    private ArrayList<Agence> agences = new ArrayList<>();
    private HotelServiceIdentification proxy;

    /* CONSTRUCTORS */
    public AgenceRepositoryImpl() throws MalformedURLException {
        // Création d'un nombre aléatoire d'agence (entre 4 et 10)
        int nombreAgence = new Random().nextInt(6) + 4;

        System.out.println("Génération de " + nombreAgence + " agence aléatoires : \n");

        // Récupération de la liste des identifiants des hôtels
        URL url = new URL("http://localhost:8080/hotelservice/identifiantHotels");
        HotelServiceIdentificationImplService hotelService = new HotelServiceIdentificationImplService(url);
        this.proxy = hotelService.getHotelServiceIdentificationImplPort();

        // Génération des agences
        for (int i = 0; i < nombreAgence; i++) {
            Agence agence = randomAgence();

            // Créez et publiez le service de consultation
            AgenceServiceConsultationImpl consultationServiceAgence = new AgenceServiceConsultationImpl(agence);
            String adresse = "http://localhost:8090/agencesservice/" + agence.getIdentifiant() + "/consultation";
            Endpoint.publish(adresse, consultationServiceAgence);
            System.out.println("Adresse du service de consultation : " + adresse);

            // Créez et publiez le service de réservation
            AgenceServiceReservationImpl reservationServiceAgence = new AgenceServiceReservationImpl(agence);
            adresse = "http://localhost:8090/agencesservice/" + agence.getIdentifiant() + "/reservation";
            Endpoint.publish(adresse, reservationServiceAgence);
            System.out.println("Adresse du service de réservation : " + adresse);

            // Créez et publiez le service d'inscription
            UserServiceInscriptionImpl inscriptionSericeUser = new UserServiceInscriptionImpl(agence);
            adresse = "http://localhost:8090/agencesservice/" + agence.getIdentifiant() + "/inscription";
            Endpoint.publish(adresse, inscriptionSericeUser);
            System.out.println("Adresse du service d'inscription : " + adresse);

            System.out.println("\n");

            // Ajoutez l'agence à la liste des agences
            agences.add(agence);
        }
        System.out.println("Fin de la génération des agences aléatoires.\n");
    }

    /* METHODS */
    private Agence randomAgence() {
        String identifiantAgence = RandomDonneesAgence.randomIdentifiantAgence();

        // Créez une instance d'Agence
        String nomAgence = RandomDonneesAgence.randomNomAgence();

        Agence agence = new Agence(identifiantAgence, nomAgence);

        // Ajoutez des hôtels à l'agence
        List<String> listeIdentifiantHotel = proxy.getIdentifiantHotels();
        int nombreHotels = RandomDonneesAgence.randomNombreHotelPartenaire(listeIdentifiantHotel.size());
        ArrayList<String> listeHotelPartenaireSelectionnes = RandomDonneesAgence.randomHotelPartenaire(listeIdentifiantHotel, nombreHotels);
        HashMap<String, Integer> mapIdentifiantsHotelsPartenairesReduction = RandomDonneesAgence.randomReductionHotelPartenaire(listeHotelPartenaireSelectionnes);
        agence.setMapIdentifiantsHotelsPartenairesReduction(mapIdentifiantsHotelsPartenairesReduction);

        System.out.println(agence.getAgenceInfo());

        return agence;
    }

    public ArrayList<String> getListeAgence() {
        return agences.stream().map(Agence::getIdentifiant).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public Agence getAgence(String identifiant) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiant + " not found");

        return agence;
    }

    public String afficherAgence(String identifiant) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiant + " not found");

        return agence.getAgenceInfo();
    }

    public ArrayList<String> getListeIdentifiantHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiantAgence)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiantAgence + " not found");

        return new ArrayList<>(agence.getIdentifiantHotelPartenaire().keySet());
    }

    public int getReduction(String identifiantAgence, String identifiantHotel) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiantAgence)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiantAgence + " not found");

        return agence.getIdentifiantHotelPartenaire().get(identifiantHotel);
    }

    public ArrayList<Hotel> getListeHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiantAgence)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiantAgence + " not found");

        ArrayList<Hotel> listeHotelsPartenaire = new ArrayList<>();
        for (String identifiantHotel : agence.getIdentifiantHotelPartenaire().keySet()) {
            try {
                listeHotelsPartenaire.add(proxy.getHotel(identifiantHotel));
            } catch (HotelNotFoundException_Exception e) {
                e.printStackTrace();
            }
        }
        return listeHotelsPartenaire;
    }

    public String afficherHotelSimple(String identifiantHotel) throws HotelNotFoundException_Exception {
        return proxy.afficherHotelSimple(identifiantHotel);
    }

    public Hotel getHotel(String identifiant) throws HotelNotFoundException_Exception {
        return proxy.getHotel(identifiant);
    }

    public String afficherHotel(String identifiantHotel) throws HotelNotFoundException_Exception {
        return proxy.afficherHotel(identifiantHotel);
    }

    public boolean deleteAgence(String identifiant) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiant + " not found");

        return agences.remove(agence);
    }

    public String afficherReservationsHotel(String identifiantHotel) throws HotelNotFoundException_Exception {
        return proxy.afficherReservationsHotel(identifiantHotel);
    }
}
