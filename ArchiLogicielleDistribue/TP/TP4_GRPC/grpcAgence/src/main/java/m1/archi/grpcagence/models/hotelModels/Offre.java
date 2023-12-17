package m1.archi.grpcagence.models.hotelModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.grpcagence.data.TimeConverter;
import m1.archi.proto.models.OffreOuterClass;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Offre {
    @Id
    @GeneratedValue
    private Long idOffre;
    private int nombreLitsTotal;
    private double prix;
    private double prixAvecReduction;
    private Timestamp dateArrivee;
    private Timestamp dateDepart;
    private Timestamp dateExpiration;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Chambre> chambres;
    private Long idHotel;
    private int nombreEtoiles;

    public Offre(OffreOuterClass.Offre offre) {
        this.idOffre = offre.getIdOffre();
        this.nombreLitsTotal = offre.getNombreLitsTotal();
        this.prix = offre.getPrix();
        this.prixAvecReduction = offre.getPrixAvecReduction();
        this.dateArrivee = TimeConverter.convertTimestamp(offre.getDateArrivee());
        this.dateDepart = TimeConverter.convertTimestamp(offre.getDateDepart());
        this.dateExpiration = TimeConverter.convertTimestamp(offre.getDateExpiration());
        this.chambres = offre.getChambresList().stream().map(Chambre::new).collect(java.util.stream.Collectors.toList());
        this.idHotel = offre.getIdHotel();
        this.nombreEtoiles = offre.getNombreEtoiles();
    }

    public OffreOuterClass.Offre toProto() {
        return OffreOuterClass.Offre.newBuilder()
                .setIdOffre(this.idOffre)
                .setNombreLitsTotal(this.nombreLitsTotal)
                .setPrix(this.prix)
                .setPrixAvecReduction(this.prixAvecReduction)
                .setDateArrivee(TimeConverter.convertTimestamp(this.dateArrivee))
                .setDateDepart(TimeConverter.convertTimestamp(this.dateDepart))
                .setDateExpiration(TimeConverter.convertTimestamp(this.dateExpiration))
                .addAllChambres(this.chambres.stream().map(Chambre::toProto).collect(java.util.stream.Collectors.toList()))
                .setIdHotel(this.idHotel)
                .setNombreEtoiles(this.nombreEtoiles)
                .build();
    }

}

