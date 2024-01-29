package m1.archi.grpcclient;

import m1.archi.grpcclient.clientInterface.ComparateurRestClientInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(
        scanBasePackages = {
                "m1.archi.grpcclient.clientInterface",
                "m1.archi.grpcclient.models"}
)
@EnableWebMvc
public class GrpcClientApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(GrpcClientApplication.class, args);
        new ComparateurRestClientInterface();
    }

}
