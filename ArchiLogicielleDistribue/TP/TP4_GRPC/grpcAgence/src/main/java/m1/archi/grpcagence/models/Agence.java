package m1.archi.grpcagence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.models.AgenceOuterClass;
import m1.archi.grpcagence.models.hotelModels.Hotel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Agence {
    @Id
    @GeneratedValue
    private long idAgence;
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "agence_reduction_hotels",
            joinColumns = @JoinColumn(name = "agence_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id"))
    @MapKeyColumn(name = "reduction")
    private Map<Integer, Hotel> reductionHotels;
    @ManyToMany
    private List<Utilisateur> listeUtilisateurs;

    public Agence(AgenceOuterClass.Agence agence) {
        this.idAgence = agence.getIdAgence();
        this.nom = agence.getNom();
        this.reductionHotels = agence.getReductionHotelsMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new Hotel(e.getValue())));
        this.listeUtilisateurs = agence.getListeUtilisateursList().stream()
                .map(Utilisateur::new)
                .collect(Collectors.toList());
    }

    public AgenceOuterClass.Agence toProto() {
        return AgenceOuterClass.Agence.newBuilder()
                .setIdAgence(this.idAgence)
                .setNom(this.nom)
                .putAllReductionHotels(this.reductionHotels.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toProto())))
                .addAllListeUtilisateurs(this.listeUtilisateurs.stream()
                        .map(Utilisateur::toProto)
                        .collect(Collectors.toList()))
                .build();
    }
}
