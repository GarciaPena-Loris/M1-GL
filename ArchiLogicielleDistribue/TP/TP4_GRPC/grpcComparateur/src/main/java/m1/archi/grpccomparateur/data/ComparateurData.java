package m1.archi.grpccomparateur.data;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import m1.archi.grpccomparateur.exceptions.InternalErrorException;
import m1.archi.grpccomparateur.models.Comparateur;
import m1.archi.grpccomparateur.repositories.ComparateurRepository;
import m1.archi.proto.services.AgenceServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ComparateurData {

    private final Logger logger = LoggerFactory.getLogger(ComparateurData.class);

    @Bean
    public CommandLineRunner initDatabase(ComparateurRepository comparateurRepository) {
        return args -> {
            // Génération d'un Comparateur aléatoire
            logger.info("Génération d'un Comparateur aléatoires...");

            // Génération du Comparateur
            Comparateur comparateur = RandomDonneesComparateur.randomComparateur();

            comparateurRepository.save(comparateur);

            try {
                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress("localhost", 9090)
                        .usePlaintext()
                        .build();

                AgenceServiceGrpc.AgenceServiceBlockingStub agenceServiceBlockingStub = AgenceServiceGrpc.newBlockingStub(channel);

                List<Long> idAgences = agenceServiceBlockingStub.getAllIdAgences(Empty.newBuilder().build()).getIdAgenceList();
                if (idAgences.isEmpty()) {
                    throw new InternalErrorException("Aucun agence n'est disponible");
                }

                for (Long idAgence : idAgences) {
                    comparateur.getIdAgences().add(idAgence);
                }

                comparateurRepository.save(comparateur);

                logger.info("Preloading database with " + comparateur);
                logger.info("Server comparateur ready!");

                channel.shutdown();

            } catch (Exception e) {
                logger.error("Impossible de se connecter au serveur agence : " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
