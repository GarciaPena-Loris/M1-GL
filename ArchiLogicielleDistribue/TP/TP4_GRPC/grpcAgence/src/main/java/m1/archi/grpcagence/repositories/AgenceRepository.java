package m1.archi.grpcagence.repositories;

import m1.archi.grpcagence.models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
