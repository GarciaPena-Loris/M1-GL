package m1.archi.repository;

import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;
import m1.archi.model.Adresse;
import m1.archi.model.Chambre;
import m1.archi.model.Hotel;
import m1.archi.service.HotelServiceConsultationImpl;
import m1.archi.service.HotelServiceReservationImpl;
import m1.archi.service.RandomDonneStockage;
import org.apache.commons.codec.binary.Base64;

import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class HotelRepositoryImpl implements HotelRepository {

    /* ATTRIBUTES */
    private ArrayList<Hotel> hotels = new ArrayList<>();

    /* CONSTRUCTORS */
    public HotelRepositoryImpl() throws IOException {
        // Création d'un nombre aléatoire d'hôtels (entre 50 et 150)
        int nombreHotels = (int) (Math.random() * 100) + 50;

        System.out.println("Génération de " + nombreHotels + " hôtels aléatoires : \n");

        for (int i = 0; i < nombreHotels; i++) {
            Hotel hotel = randomHotel();

            // Créez et publiez le service de consultation
            HotelServiceConsultationImpl consultationService = new HotelServiceConsultationImpl(hotel);
            String adresse = "http://localhost:8080/hotelservice/" + hotel.getIdentifiant() + "/consultation";
            System.out.println("Adresse du service de consultation : " + adresse);
            Endpoint.publish(adresse, consultationService);

            // Créez et publiez le service de réservation
            HotelServiceReservationImpl reservationService = new HotelServiceReservationImpl(hotel);
            adresse = "http://localhost:8080/hotelservice/" + hotel.getIdentifiant() + "/reservation";
            System.out.println("Adresse du service de réservation : " + adresse);
            Endpoint.publish(adresse, reservationService);

            System.out.println("\n");

            // Ajoutez l'hôtel à la liste des hôtels
            hotels.add(hotel);
        }
        System.out.println("Fin de la génération des hôtels aléatoires.\n");
    }

    /* METHODS */
    private Hotel randomHotel() throws IOException {
        String identifiantHotel = RandomDonneStockage.randomIdentifiantHotel();

        System.out.println("Génération d'un hotel (" + identifiantHotel + ") :");
        // Créez une instance d'Hotel
        // -- Génération d'une adresse aléatoire
        String pays = RandomDonneStockage.randomPays();
        String ville = RandomDonneStockage.randomVille(pays);
        String rue = RandomDonneStockage.randomRue();
        String numero = RandomDonneStockage.randomNumero();
        String position = RandomDonneStockage.randomPositionGPS();
        Adresse adresseHotel = new Adresse(pays, ville, rue, numero, position);

        // -- Génération d'un hôtel aléatoire
        String nomHotel = RandomDonneStockage.randomNomHotel();
        int nombreEtoiles = RandomDonneStockage.randomNombreEtoiles();
        File imageHotel = RandomDonneStockage.randomImagePays(pays);
        String base64ImageHotel = "";
        if (!(imageHotel == null)) {
            byte[] imageBytes = readFileToByteArray(imageHotel);
            // Encoder l'image en base64
            base64ImageHotel = Base64.encodeBase64String(imageBytes);
        }

        Hotel hotel = new Hotel(identifiantHotel, nomHotel, adresseHotel, nombreEtoiles, base64ImageHotel);

        // -- Génération de chambres aléatoires
        File imageChambre = null;
        String base64ImageChambre = "";
        byte[] imageBytes = null;
        int nombreChambres = RandomDonneStockage.randomNombreChambres();
        for (int j = 1; j <= nombreChambres; j++) {
            if (j != 13) {
                int nombreLits = RandomDonneStockage.randomNombreLits();
                int prix = RandomDonneStockage.randomPrix(nombreEtoiles, nombreLits);
                imageChambre = RandomDonneStockage.randomImageChambre(nombreEtoiles);
                if (!(imageChambre == null)) {
                    imageBytes = readFileToByteArray(imageChambre);
                    base64ImageChambre = Base64.encodeBase64String(imageBytes);
                }
                Chambre chambre = new Chambre(j, prix, nombreLits, identifiantHotel, base64ImageChambre);
                hotel.addChambre(chambre);
            }
        }

        System.out.println(hotel.toString());

        return hotel;
    }

    @Override
    public ArrayList<String> getIdentifiantHotels() {
        return hotels.stream().map(Hotel::getIdentifiant).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public Hotel getHotel(String identifiant) throws HotelNotFoundException {
        Hotel hotel = hotels.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (hotel == null)
            throw new HotelNotFoundException("Error: Hotel " + identifiant + " not found");

        return hotel;
    }

    @Override
    public boolean addRandomHotel() throws HotelAlreadyExistsException, IOException {
        Hotel hotel = randomHotel();
        if (hotels.contains(hotel))
            throw new HotelAlreadyExistsException("Error: Hotel " + hotel.getIdentifiant() + " already exists");

        return hotels.add(hotel);
    }

    @Override
    public boolean deleteHotel(String identifiant) throws HotelNotFoundException {
        Hotel hotel = hotels.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (hotel == null)
            throw new HotelNotFoundException("Error: Hotel " + identifiant + " not found");

        return hotels.remove(hotel);
    }

    @Override
    public String afficherHotelSimple(String identifiant) throws HotelNotFoundException {
        Hotel hotel = hotels.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (hotel == null)
            throw new HotelNotFoundException("Error: Hotel " + identifiant + " not found");

        return hotel.toString();
    }

    @Override
    public String afficherHotel(String identifiant) throws HotelNotFoundException {
        Hotel hotel = hotels.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (hotel == null)
            throw new HotelNotFoundException("Error: Hotel " + identifiant + " not found");

        return hotel.getHotelInfo();
    }

    @Override
    public String afficherReservationsHotel(String identifiant) throws HotelNotFoundException {
        Hotel hotel = hotels.stream().filter(h -> h.getIdentifiant().equals(identifiant)).findFirst().orElse(null);
        if (hotel == null)
            throw new HotelNotFoundException("Error: Hotel " + identifiant + " not found");

        return hotel.getReservationHotel();
    }
}
