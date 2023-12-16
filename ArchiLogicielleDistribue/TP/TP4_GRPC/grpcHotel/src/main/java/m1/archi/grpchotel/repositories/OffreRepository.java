package m1.archi.grpchotel.repositories;

import m1.archi.grpchotel.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffreRepository extends JpaRepository<Offre, Long> {
}
