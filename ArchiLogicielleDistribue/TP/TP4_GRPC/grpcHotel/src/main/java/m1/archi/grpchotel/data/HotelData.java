package m1.archi.grpchotel.data;

import m1.archi.grpchotel.models.Adresse;
import m1.archi.grpchotel.models.Chambre;
import m1.archi.grpchotel.models.Hotel;
import m1.archi.grpchotel.repositories.AdresseRepository;
import m1.archi.grpchotel.repositories.ChambreRepository;
import m1.archi.grpchotel.repositories.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelData {
    private final Logger logger = LoggerFactory.getLogger(HotelData.class);

    @Bean
    public CommandLineRunner initDatabase(HotelRepository hotelRepository, AdresseRepository adresseRepository, ChambreRepository chambreRepository) {
        return args -> {
            // Génération de plusieurs hotel aléatoire
            int nombreHotels = (int) (Math.random() * 100) + 50;
//            int nombreHotels = 5;

            logger.info("Génération de " + nombreHotels + " hôtels aléatoires ...");

            for (int i = 0; i < nombreHotels; i++) {
                // Génération des adresses
                Adresse adresse = RandomDonneesHotel.generateRandomAdresse();
                adresse = adresseRepository.save(adresse);

                // Génération de l'hôtels
                Hotel hotel = RandomDonneesHotel.generateRandomHotel(adresse);

                // Sauvegarde de l'hôtel
                hotel = hotelRepository.save(hotel);

                // Génération des chambres
                int nombreChambres = RandomDonneesHotel.randomNombreChambres();
                for (int j = 1; j <= nombreChambres; j++) {
                    if (j != 13) {
                        Chambre chambre = RandomDonneesHotel.generateRandomChambres(hotel, j);
                        chambre = chambreRepository.save(chambre);

                        hotel.getChambres().add(chambre);
                    }
                }

                // Mise à jour de l'hôtel avec la liste complète de chambres
                hotelRepository.save(hotel);

                // Sauvegarde de l'hôtel
                logger.info("[" + (i+1) + "] Preloading database with " + hotel);
            }
            logger.info("Server hotel ready on port 8080!");

        };
    }
}