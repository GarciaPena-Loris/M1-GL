package m1.archi.grpchotel.repositories;

import m1.archi.grpchotel.models.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
