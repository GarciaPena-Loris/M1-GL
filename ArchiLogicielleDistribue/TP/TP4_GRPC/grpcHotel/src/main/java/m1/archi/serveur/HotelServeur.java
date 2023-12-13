package m1.archi.serveur;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import m1.archi.dao.AdresseDao;
import m1.archi.dao.ChambreDao;
import m1.archi.dao.HotelDao;
import m1.archi.data.RandomDonneesHotel;
import m1.archi.domaines.Adresse;
import m1.archi.domaines.Hotel;
import m1.archi.services.RechercherChambresServiceImpl;
import m1.archi.services.ReserverChambresServiceImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelServeur {

    private static final Logger logger = Logger.getLogger(HotelServeur.class.getName());

    public static void main(String[] args) {
        AdresseDao adresseDao = new AdresseDao();
        HotelDao hotelDao = new HotelDao();
        ChambreDao chambreDao = new ChambreDao();

        int nombreHotel = 5;

        for (int i = 1; i <= nombreHotel; i++) {
            // Crée un port pour chaque serveur
            int port = 8080 + i;

            // Crée une adresse et un hotel
            Adresse adresse = RandomDonneesHotel.generateRandomAdresse();
            adresseDao.create(adresse);

            // Crée un hotel avec des chambres
            Hotel hotel = RandomDonneesHotel.generateRandomHotel(adresse);
            int nombreChambres = RandomDonneesHotel.randomNombreChambres();
            for (int j = 0; j < nombreChambres; j++) {
                chambreDao.create(RandomDonneesHotel.generateRandomChambres(hotel, j));
                hotel.getChambres().add(RandomDonneesHotel.generateRandomChambres(hotel, j));
            }
            hotelDao.create(hotel);

            // Crée un serveur sur le port 8080
            Server server = ServerBuilder.forPort(port)
                    .addService(new RechercherChambresServiceImpl(hotel.toProto()))
                    .addService(new ReserverChambresServiceImpl(hotel.toProto()))
                    .build();

            try {
                server.start();
                logger.log(Level.INFO, "Hotel Server [" + i+1 + "] started on port " + port);

                // server.awaitTermination(); // You might not want to call this immediately to keep the servers running
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Hotel Server did not start on port " + port);
            }
        }

        // server.awaitTermination();
    }
}
