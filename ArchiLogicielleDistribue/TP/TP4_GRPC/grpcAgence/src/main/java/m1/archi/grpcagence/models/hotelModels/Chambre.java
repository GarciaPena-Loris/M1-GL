package m1.archi.grpcagence.models.hotelModels;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Chambre {
    @Id
    @GeneratedValue
    private Long idChambre;
    private int numero;
    private double prix;
    private int nombreLits;
    private Long idHotel;
    private String imageChambre;

    public Chambre(m1.archi.models.ChambreOuterClass.Chambre chambre) {
        this.idChambre = chambre.getIdChambre();
        this.numero = chambre.getNumero();
        this.prix = chambre.getPrix();
        this.nombreLits = chambre.getNombreLits();
        this.idHotel = chambre.getIdHotel();
        this.imageChambre = chambre.getImageChambre();
    }

    public m1.archi.models.ChambreOuterClass.Chambre toProto() {
        return m1.archi.models.ChambreOuterClass.Chambre.newBuilder()
                .setIdChambre(this.idChambre)
                .setNumero(this.numero)
                .setPrix(this.prix)
                .setNombreLits(this.nombreLits)
                .setIdHotel(this.idHotel)
                .setImageChambre(this.imageChambre)
                .build();
    }

}

