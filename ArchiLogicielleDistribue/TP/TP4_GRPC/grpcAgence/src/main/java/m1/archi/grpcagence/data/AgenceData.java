package m1.archi.grpcagence.data;


import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import m1.archi.grpcagence.exceptions.InternalErrorException;
import m1.archi.grpcagence.models.Agence;
import m1.archi.grpcagence.models.hotelModels.Hotel;
import m1.archi.grpcagence.repositories.AgenceRepository;
import m1.archi.proto.models.HotelOuterClass;
import m1.archi.proto.services.HotelServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class AgenceData {
    private final Logger logger = LoggerFactory.getLogger(AgenceData.class);

    @Bean
    public CommandLineRunner initDatabase(AgenceRepository agenceRepository) {
        return args -> {
            // Génération de plusieurs agences aléatoire
            int nombreAgences = (int) (Math.random() * 10) + 5;

            logger.info("Génération de " + nombreAgences + " agences aléatoires...");

            try {
                // Créer un channel pour communiquer avec le serveur hotel
                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress("localhost", 9080)
                        .usePlaintext()
                        .build();

                // Créer un stub pour pouvoir communiquer avec le serveur hotel
                HotelServiceGrpc.HotelServiceBlockingStub hotelServiceBlockingStub = HotelServiceGrpc.newBlockingStub(channel);

                List<Long> idHotels = hotelServiceBlockingStub.getAllIdHotels(Empty.newBuilder().build()).getIdHotelList();
                if (idHotels.isEmpty()) {
                    throw new InternalErrorException("Aucun hotel n'est disponible");
                }

                for (int i = 0; i < nombreAgences; i++) {

                    // Génération de l'agence
                    Agence agence = RandomDonneesAgence.randomAgence();

                    // Sauvegarde de l'agence
                    agence = agenceRepository.save(agence);

                    // Association des hotels partenaire avec une réduction
                    Map<Long, Integer> hotelsPartenairesReduction = RandomDonneesAgence.randomReductionHotelPartenaire(idHotels);

                    for (Map.Entry<Long, Integer> entry : hotelsPartenairesReduction.entrySet()) {
                        agence.getReductionHotels().put(entry.getKey(), entry.getValue());
                    }
                    agenceRepository.save(agence);

                    // Sauvegarde de l'agence
                    logger.info("\n[" + (i + 1) + "] Preloading database with " + agence);
                }
                logger.info("Server agence ready!");

                channel.shutdown();

            } catch (Exception e) {
                logger.error("Impossible de se connecter au serveur hotel : " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}