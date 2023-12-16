package m1.archi.grpchotel.models;

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
public class Carte {
    @Id
    @GeneratedValue
    private Long idCarte;
    private String nom;
    private String numero;
    private String dateExpiration;
    private String CCV;

    public Carte(m1.archi.models.CarteOuterClass.Carte carte) {
        this.idCarte = carte.getIdCarte();
        this.nom = carte.getNom();
        this.numero = carte.getNumero();
        this.dateExpiration = carte.getDateExpiration();
        this.CCV = carte.getCCV();
    }

    public m1.archi.models.CarteOuterClass.Carte toProto() {
        return m1.archi.models.CarteOuterClass.Carte.newBuilder()
                .setIdCarte(this.idCarte)
                .setNom(this.nom)
                .setNumero(this.numero)
                .setDateExpiration(this.dateExpiration)
                .setCCV(this.CCV)
                .build();
    }
}
