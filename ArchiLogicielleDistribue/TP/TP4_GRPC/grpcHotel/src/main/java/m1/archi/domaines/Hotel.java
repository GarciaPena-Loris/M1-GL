package m1.archi.domaines;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Hotel {
    @Id
    @GeneratedValue
    private Long idHotel;
    private String nom;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Adresse adresse;
    private int nombreEtoiles;
    private String imageHotel;
    @OneToMany
    private List<Chambre> chambres;
    @OneToMany
    private List<Reservation> reservations;
    @OneToMany
    private List<Offre> offres;

    public Hotel(m1.archi.models.HotelOuterClass.Hotel hotel) {
        this.nom = hotel.getNom();
        this.adresse = new Adresse(hotel.getAdresse());
        this.nombreEtoiles = hotel.getNombreEtoiles();
        this.imageHotel = hotel.getImageHotel();
        this.chambres = hotel.getChambresList().stream().map(Chambre::new).collect(Collectors.toList());
        this.reservations = hotel.getReservationsList().stream().map(Reservation::new).collect(Collectors.toList());
        this.offres = hotel.getOffresList().stream().map(Offre::new).collect(Collectors.toList());
    }

    public m1.archi.models.HotelOuterClass.Hotel toProto() {
        return m1.archi.models.HotelOuterClass.Hotel.newBuilder()
                .setNom(this.nom)
                .setAdresse(this.adresse.toProto())
                .setNombreEtoiles(this.nombreEtoiles)
                .setImageHotel(this.imageHotel)
                .addAllChambres(this.chambres.stream().map(Chambre::toProto).collect(Collectors.toList()))
                .addAllReservations(this.reservations.stream().map(Reservation::toProto).collect(Collectors.toList()))
                .addAllOffres(this.offres.stream().map(Offre::toProto).collect(Collectors.toList()))
                .build();
    }

}

