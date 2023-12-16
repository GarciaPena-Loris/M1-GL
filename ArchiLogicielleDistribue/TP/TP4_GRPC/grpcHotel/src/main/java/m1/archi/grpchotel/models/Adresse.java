package m1.archi.grpchotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.models.AdresseOuterClass;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Adresse {
    @Id
    @GeneratedValue
    private Long idAdresse;
    private String numero;
    private String rue;
    private String ville;
    private String pays;
    private String position;

    public Adresse(AdresseOuterClass.Adresse adresse) {
        this.idAdresse = adresse.getIdAdresse();
        this.numero = adresse.getNumero();
        this.rue = adresse.getRue();
        this.ville = adresse.getVille();
        this.pays = adresse.getPays();
        this.position = adresse.getPosition();
    }

    public AdresseOuterClass.Adresse toProto() {
        return AdresseOuterClass.Adresse.newBuilder()
                .setIdAdresse(this.idAdresse)
                .setNumero(this.numero)
                .setRue(this.rue)
                .setVille(this.ville)
                .setPays(this.pays)
                .setPosition(this.position)
                .build();
    }

}
