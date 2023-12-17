package m1.archi.grpcagence.repositories;

import m1.archi.grpcagence.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}
