package m1.archi.grpccomparateur.repositories;

import m1.archi.grpccomparateur.models.Comparateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ComparateurRepository extends JpaRepository<Comparateur, Long> {
    @Query("SELECT c FROM Comparateur c ORDER BY c.idComparateur ASC")
    Optional<Comparateur> findFirst();
}
