package m1.archi.grpchotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"m1.archi.grpchotel.models"})
@EnableJpaRepositories(basePackages = {"m1.archi.grpchotel.repositories"})

@SpringBootApplication(scanBasePackages = {
        "m1.archi.grpchotel.data",
        "m1.archi.grpchotel.services",
        "m1.archi.grpchotel.exceptions",
})
public class GrpcHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcHotelApplication.class, args);
    }

}
