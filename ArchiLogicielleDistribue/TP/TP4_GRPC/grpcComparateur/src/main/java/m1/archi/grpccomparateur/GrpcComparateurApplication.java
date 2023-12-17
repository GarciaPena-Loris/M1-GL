package m1.archi.grpccomparateur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"m1.archi.grpccomparateur.models"})
@EnableJpaRepositories(basePackages = {"m1.archi.grpccomparateur.repositories"})

@SpringBootApplication(scanBasePackages = {
		"m1.archi.grpccomparateur.data",
		"m1.archi.grpccomparateur.services",
		"m1.archi.grpccomparateur.exceptions",
})
public class GrpcComparateurApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcComparateurApplication.class, args);
	}

}
