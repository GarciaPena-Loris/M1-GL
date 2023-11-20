package m1.archi.resthotel.repositories;

import m1.archi.resthotel.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
