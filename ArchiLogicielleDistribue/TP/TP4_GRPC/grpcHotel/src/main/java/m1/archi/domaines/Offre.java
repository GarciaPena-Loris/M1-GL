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
    @ManyToMany
    private List<Chambre> chambres;
    private Long idHotel;

    public Offre(m1.archi.models.OffreOuterClass.Offre offre) {
        this.nombreLitsTotal = offre.getNombreLitsTotal();
        this.prix = offre.getPrix();
        this.prixAvecReduction = offre.getPrixAvecReduction();
        this.dateArrivee = offre.getDateArrivee();
        this.dateDepart = offre.getDateDepart();
        this.dateExpiration = offre.getDateExpiration();
        this.chambres = offre.getChambresList().stream().map(Chambre::new).collect(java.util.stream.Collectors.toList());
        this.idHotel = offre.getIdHotel();
    }

    public m1.archi.models.OffreOuterClass.Offre toProto() {
        return m1.archi.models.OffreOuterClass.Offre.newBuilder()
                .setNombreLitsTotal(this.nombreLitsTotal)
                .setPrix(this.prix)
                .setPrixAvecReduction(this.prixAvecReduction)
                .setDateArrivee(this.dateArrivee)
                .setDateDepart(this.dateDepart)
                .setDateExpiration(this.dateExpiration)
                .addAllChambres(this.chambres.stream().map(Chambre::toProto).collect(java.util.stream.Collectors.toList()))
                .setIdHotel(this.idHotel)
                .build();
    }

}

