package m1.archi.domaines;

import com.google.protobuf.Timestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.models.ReservationOuterClass;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long idReservation;
    private Long idHotel;
    @ManyToMany
    private List<Chambre> chambresReservees;
    private Long idClientPrincipal;
    private Timestamp dateArrivee;
    private Timestamp dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation(ReservationOuterClass.Reservation reservation) {
        this.idHotel = reservation.getIdHotel();
        this.chambresReservees = reservation.getChambresReserveesList().stream().map(Chambre::new).collect(java.util.stream.Collectors.toList());
        this.idClientPrincipal = reservation.getIdClientPrincipal();
        this.dateArrivee = reservation.getDateArrivee();
        this.dateDepart = reservation.getDateDepart();
        this.nombrePersonnes = reservation.getNombrePersonnes();
        this.montantReservation = reservation.getMontantReservation();
        this.petitDejeuner = reservation.getPetitDejeuner();
    }

    public ReservationOuterClass.Reservation toProto() {
        return ReservationOuterClass.Reservation.newBuilder()
                .setIdHotel(this.idHotel)
                .addAllChambresReservees(this.chambresReservees.stream().map(Chambre::toProto).collect(java.util.stream.Collectors.toList()))
                .setIdClientPrincipal(this.idClientPrincipal)
                .setDateArrivee(this.dateArrivee)
                .setDateDepart(this.dateDepart)
                .setNombrePersonnes(this.nombrePersonnes)
                .setMontantReservation(this.montantReservation)
                .setPetitDejeuner(this.petitDejeuner)
                .build();
    }
}
