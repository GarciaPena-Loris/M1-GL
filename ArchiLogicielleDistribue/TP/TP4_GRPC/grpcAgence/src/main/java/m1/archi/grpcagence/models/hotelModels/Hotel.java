package m1.archi.grpcagence.models.hotelModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.proto.models.HotelOuterClass;

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
    @OneToOne
    private Adresse adresse;
    private int nombreEtoiles;
    private String imageHotel;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Chambre> chambres;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Reservation> reservations;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Offre> offres;

    public Hotel(HotelOuterClass.Hotel hotel) {
        this.idHotel = hotel.getIdHotel();
        this.nom = hotel.getNom();
        this.adresse = new Adresse(hotel.getAdresse());
        this.nombreEtoiles = hotel.getNombreEtoiles();
        this.imageHotel = hotel.getImageHotel();
        this.chambres = hotel.getChambresList().stream().map(Chambre::new).collect(Collectors.toList());
        this.reservations = hotel.getReservationsList().stream().map(Reservation::new).collect(Collectors.toList());
        this.offres = hotel.getOffresList().stream().map(Offre::new).collect(Collectors.toList());
    }

    public HotelOuterClass.Hotel toProto() {
        return HotelOuterClass.Hotel.newBuilder()
                .setIdHotel(this.idHotel)
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

