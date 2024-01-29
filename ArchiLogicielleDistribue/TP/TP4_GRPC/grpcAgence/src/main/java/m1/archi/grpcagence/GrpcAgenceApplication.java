package m1.archi.grpcagence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"m1.archi.grpcagence.models"})
@EnableJpaRepositories(basePackages = {"m1.archi.grpcagence.repositories"})

@SpringBootApplication(scanBasePackages = {
        "m1.archi.grpcagence.data",
        "m1.archi.grpcagence.services",
        "m1.archi.grpcagence.exceptions",
})
public class GrpcAgenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcAgenceApplication.class, args);
    }

}
