package m1.archi.grpccomparateur.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import m1.archi.proto.models.ComparateurOuterClass;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Comparateur {
    @Id
    @GeneratedValue
    private Long idComparateur;
    private String nom;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> idAgences;

    public Comparateur(ComparateurOuterClass.Comparateur comparateur) {
        this.idComparateur = comparateur.getIdComparateur();
        this.nom = comparateur.getNom();
        this.idAgences = new ArrayList<>(comparateur.getIdAgencesList());
    }

    public ComparateurOuterClass.Comparateur toProto() {
        return ComparateurOuterClass.Comparateur.newBuilder()
                .setIdComparateur(this.idComparateur)
                .setNom(this.nom)
                .addAllIdAgences(this.idAgences)
                .build();
    }
}