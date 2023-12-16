package m1.archi.grpchotel.repositories;

import m1.archi.grpchotel.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
