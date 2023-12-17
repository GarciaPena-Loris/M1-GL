package m1.archi.grpccomparateur.models.agenceModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import m1.archi.proto.models.AgenceOuterClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agence {
    @Id
    @GeneratedValue
    private Long idAgence;
    private String nom;
    @ElementCollection
    private Map<Long, Integer> reductionHotels;
    @ManyToMany
    private List<Utilisateur> listeUtilisateurs;

    public Agence(AgenceOuterClass.Agence agence) {
        this.idAgence = agence.getIdAgence();
        this.nom = agence.getNom();
        this.reductionHotels = agence.getReductionHotelsMap();
        this.listeUtilisateurs = agence.getListeUtilisateursList().stream()
                .map(Utilisateur::new)
                .collect(Collectors.toList());
    }

    public AgenceOuterClass.Agence toProto() {
        return AgenceOuterClass.Agence.newBuilder()
                .setIdAgence(this.idAgence)
                .setNom(this.nom)
                .putAllReductionHotels(this.reductionHotels)
                .addAllListeUtilisateurs(this.listeUtilisateurs.stream()
                        .map(Utilisateur::toProto)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("L'" + this.nom + " (" + this.getIdAgence() + ") possède " + this.getReductionHotels().size() + " hotels partenaires :\n");

        int compteur = 1;
        for (Map.Entry<Long, Integer> reductionHotel : this.getReductionHotels().entrySet()) {
            res.append("\t").append(compteur).append("- L'hotel (").append(reductionHotel.getKey()).append(") avec une reduction de ").append(reductionHotel.getValue()).append("% sur les chambres.\n");
            compteur++;
        }
        return res.toString();
    }
}
