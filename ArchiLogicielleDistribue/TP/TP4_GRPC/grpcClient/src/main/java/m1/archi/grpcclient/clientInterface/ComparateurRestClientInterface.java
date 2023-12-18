package m1.archi.grpcclient.clientInterface;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import m1.archi.grpcclient.clientInterface.swingInterface.Interface;
import m1.archi.proto.services.ComparateurServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ComparateurRestClientInterface implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ComparateurRestClientInterface.class);

    @Override
    public void run(String... args) {
        try {
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress("localhost", 9100)
                    .usePlaintext()
                    .build();

            ComparateurServiceGrpc.ComparateurServiceBlockingStub comparateurServiceBlockingStub = ComparateurServiceGrpc.newBlockingStub(channel);

            logger.info("Connection au comparateur réussi, lancement de l'interface...");

            new Interface(comparateurServiceBlockingStub);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
