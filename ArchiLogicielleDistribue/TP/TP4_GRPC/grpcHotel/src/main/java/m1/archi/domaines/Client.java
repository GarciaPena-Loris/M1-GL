package m1.archi.domaines;

import jakarta.persistence.*;
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
public class Client {
    @Id
    @GeneratedValue
    private Long idClient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @OneToOne
    private Carte carte;
    @OneToMany
    private List<Reservation> historiqueReservations;

    public Client(m1.archi.models.ClientOuterClass.Client client) {
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.email = client.getEmail();
        this.telephone = client.getTelephone();
        this.carte = new Carte(client.getCarte());
    }

    public m1.archi.models.ClientOuterClass.Client toProto() {
        return m1.archi.models.ClientOuterClass.Client.newBuilder()
                .setNom(this.nom)
                .setPrenom(this.prenom)
                .setEmail(this.email)
                .setTelephone(this.telephone)
                .setCarte(this.carte.toProto())
                .build();
    }
}

