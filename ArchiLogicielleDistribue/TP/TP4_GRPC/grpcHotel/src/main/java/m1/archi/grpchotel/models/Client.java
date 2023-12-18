package m1.archi.grpchotel.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.proto.models.ClientOuterClass;

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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Reservation> historiqueReservations;

    public Client(ClientOuterClass.Client client) {
        this.idClient = client.getIdClient();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.email = client.getEmail();
        this.telephone = client.getTelephone();
        this.carte = new Carte(client.getCarte());
    }

    public ClientOuterClass.Client toProto() {
        return ClientOuterClass.Client.newBuilder()
                .setIdClient(this.idClient)
                .setNom(this.nom)
                .setPrenom(this.prenom)
                .setEmail(this.email)
                .setTelephone(this.telephone)
                .setCarte(this.carte.toProto())
                .build();
    }
}

