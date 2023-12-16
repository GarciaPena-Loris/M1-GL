package m1.archi.grpchotel.repositories;

import m1.archi.grpchotel.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
