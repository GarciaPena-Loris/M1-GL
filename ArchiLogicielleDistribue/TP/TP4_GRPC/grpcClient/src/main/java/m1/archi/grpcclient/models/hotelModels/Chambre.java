package m1.archi.grpcclient.models.hotelModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.proto.models.ChambreOuterClass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chambre {
    private Long idChambre;
    private int numero;
    private double prix;
    private int nombreLits;
    private Long idHotel;
    private String imageChambre;

    public Chambre(ChambreOuterClass.Chambre chambre) {
        this.idChambre = chambre.getIdChambre();
        this.numero = chambre.getNumero();
        this.prix = chambre.getPrix();
        this.nombreLits = chambre.getNombreLits();
        this.idHotel = chambre.getIdHotel();
        this.imageChambre = chambre.getImageChambre();
    }

    public ChambreOuterClass.Chambre toProto() {
        return ChambreOuterClass.Chambre.newBuilder()
                .setIdChambre(this.idChambre)
                .setNumero(this.numero)
                .setPrix(this.prix)
                .setNombreLits(this.nombreLits)
                .setIdHotel(this.idHotel)
                .setImageChambre(this.imageChambre)
                .build();
    }

}

