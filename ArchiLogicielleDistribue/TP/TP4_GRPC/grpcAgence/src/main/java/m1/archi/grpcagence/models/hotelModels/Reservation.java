package m1.archi.grpcagence.models.hotelModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.grpchotel.data.TimeConverter;
import m1.archi.models.ReservationOuterClass;

import java.sql.Timestamp;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Chambre> chambresReservees;
    private Long idClientPrincipal;
    private Timestamp dateArrivee;
    private Timestamp dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation(ReservationOuterClass.Reservation reservation) {
        this.idReservation = reservation.getIdReservation();
        this.idHotel = reservation.getIdHotel();
        this.chambresReservees = reservation.getChambresReserveesList().stream().map(Chambre::new).collect(java.util.stream.Collectors.toList());
        this.idClientPrincipal = reservation.getIdClientPrincipal();
        this.dateArrivee = TimeConverter.convertTimestamp(reservation.getDateArrivee());
        this.dateDepart = TimeConverter.convertTimestamp(reservation.getDateDepart());
        this.nombrePersonnes = reservation.getNombrePersonnes();
        this.montantReservation = reservation.getMontantReservation();
        this.petitDejeuner = reservation.getPetitDejeuner();
    }

    public ReservationOuterClass.Reservation toProto() {
        return ReservationOuterClass.Reservation.newBuilder()
                .setIdReservation(this.idReservation)
                .setIdHotel(this.idHotel)
                .addAllChambresReservees(this.chambresReservees.stream().map(Chambre::toProto).collect(java.util.stream.Collectors.toList()))
                .setIdClientPrincipal(this.idClientPrincipal)
                .setDateArrivee(TimeConverter.convertTimestamp(this.dateArrivee))
                .setDateDepart(TimeConverter.convertTimestamp(this.dateDepart))
                .setNombrePersonnes(this.nombrePersonnes)
                .setMontantReservation(this.montantReservation)
                .setPetitDejeuner(this.petitDejeuner)
                .build();
    }
}
