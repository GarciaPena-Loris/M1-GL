package m1.archi.grpcclient.models.agenceModels;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.proto.models.UtilisateurOuterClass;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Utilisateur {
    private Long idUtilisateur;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private List<Long> idReservations;

    public Utilisateur(UtilisateurOuterClass.Utilisateur utilisateur) {
        this.idUtilisateur = utilisateur.getIdUtilisateur();
        this.email = utilisateur.getEmail();
        this.motDePasse = utilisateur.getMotDePasse();
        this.nom = utilisateur.getNom();
        this.prenom = utilisateur.getPrenom();
        this.idReservations = new ArrayList<>(utilisateur.getIdReservationsList());
    }

    public UtilisateurOuterClass.Utilisateur toProto() {
        return UtilisateurOuterClass.Utilisateur.newBuilder()
                .setIdUtilisateur(this.idUtilisateur)
                .setEmail(this.email)
                .setMotDePasse(this.motDePasse)
                .setNom(this.nom)
                .setPrenom(this.prenom)
                .addAllIdReservations(this.idReservations)
                .build();
    }
}