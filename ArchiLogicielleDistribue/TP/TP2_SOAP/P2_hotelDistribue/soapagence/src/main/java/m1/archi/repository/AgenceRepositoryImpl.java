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
        // Création d'un nombre aléatoire d'agence (entre 3 et 6)
        int nombreAgence = new Random().nextInt(3) + 3;

        System.out.println("Génération de " + nombreAgence + " agence aléatoires : \n");

        // Récupération de la liste des identifiants des hôtels
        URL url = new URL("http://localhost:8080/hotelservice/identifiantHotels");
        HotelServiceIdentificationImplService hotelService = new HotelServiceIdentificationImplService(url);
        HotelServiceIdentification proxy = hotelService.getHotelServiceIdentificationImplPort();
        this.proxy = proxy;

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

    public String afficherAgence(String identifiant) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiant + " not found");

        return agence.getAgenceInfo();
    }

    public String afficherHotel(String identifiantHotel) throws HotelNotFoundException_Exception {
        return proxy.afficherHotels(identifiantHotel);
    }

    public boolean deleteAgence(String identifiant) throws AgenceNotFoundException {
        Agence agence = agences.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (agence == null)
            throw new AgenceNotFoundException("Error: Agence " + identifiant + " not found");

        return agences.remove(agence);
    }
}
