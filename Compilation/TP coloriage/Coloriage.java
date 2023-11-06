import java.util.*;

import org.antlr.v4.runtime.misc.Pair;

// GARCIA-PENA Loris
// MAZERAND Elliot

class Sommet {
    private String nom;
    private int couleur;

    public Sommet(String nom) {
        this.nom = nom;
        this.couleur = 0;
    }

    public String getNom() {
        return nom;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }
}

class Graphe {
    private ArrayList<Sommet> sommets;
    private ArrayList<Pair<Sommet, Sommet>> aretesInterferences;
    private ArrayList<Pair<Sommet, Sommet>> aretesPreference;

    public Graphe() {
        sommets = new ArrayList<>();
        aretesInterferences = new ArrayList<>();
        aretesPreference = new ArrayList<>();
    }

    public ArrayList<Sommet> getSommets() {
        return sommets;
    }

    public void addSommet(Sommet sommet) {
        sommets.add(sommet);
    }

    public void removeSommet(Sommet sommet) {
        sommets.remove(sommet);
    }

    public ArrayList<Pair<Sommet, Sommet>> getAretesInterferences() {
        return aretesInterferences;
    }

    public void removeAretesInterferences(Pair<Sommet, Sommet> arete) {
        aretesInterferences.remove(arete);
    }

    public ArrayList<Pair<Sommet, Sommet>> getAretesPreference() {
        return aretesPreference;
    }

    public void removeAretesPreference(Pair<Sommet, Sommet> arete) {
        aretesPreference.remove(arete);
    }

    public void addInterference(Sommet sommet, Sommet voisin) {
        if (!aretesInterferences.contains(new Pair<>(sommet, voisin))) {
            aretesInterferences.add(new Pair<>(sommet, voisin));
        }
    }

    public void addPreference(Sommet sommet, Sommet voisin) {
        if (!aretesPreference.contains(new Pair<>(sommet, voisin))) {
            aretesPreference.add(new Pair<>(sommet, voisin));
        }
    }

    public ArrayList<Pair<Sommet, Sommet>> getArretesInterferences() {
        return aretesInterferences;
    }

    public ArrayList<Pair<Sommet, Sommet>> getArretesPreference() {
        return aretesPreference;
    }

    public Graphe newGrapheSansSommet(Sommet sommet) {
        Graphe newGraphe = new Graphe();
        newGraphe.sommets = new ArrayList<>(this.sommets);
        newGraphe.aretesInterferences = new ArrayList<>(this.aretesInterferences);
        newGraphe.aretesPreference = new ArrayList<>(this.aretesPreference);

        newGraphe.removeSommet(sommet);

        for (Pair<Sommet, Sommet> arete : aretesInterferences) {
            if (arete.a == sommet || arete.b == sommet) {
                newGraphe.removeAretesInterferences(arete);
            }
        }
        for (Pair<Sommet, Sommet> arete : aretesPreference) {
            if (arete.a == sommet || arete.b == sommet) {
                newGraphe.removeAretesPreference(arete);
            }
        }
        return newGraphe;
    }

    public ArrayList<Sommet> getVoisins(Sommet sommet) {
        ArrayList<Sommet> voisins = new ArrayList<>();
        for (Pair<Sommet, Sommet> arete : aretesInterferences) {
            if (arete.a == sommet)
                voisins.add(arete.b);
            if (arete.b == sommet)
                voisins.add(arete.a);
        }
        return voisins;
    }

    // Fonctions coloration
    public Sommet getSommetPlusHautDegre() {
        Sommet sommet = sommets.get(0);
        for (Sommet s : sommets) {
            if (getVoisins(s).size() > getVoisins(sommet).size()) {
                sommet = s;
            }
        }
        return sommet;
    }

    public int calculerDegre(Sommet sommet) {
        return getVoisins(sommet).size();
    }

    // Vérifie si un sommet est trivialement colorable
    public boolean estTrivialementColoriable(Sommet sommet, int k) {
        if (calculerDegre(sommet) < k) {
            return true;
        }
        return false;
    }

    public int attribuerCouleur(Sommet sommet, int k) {
        ArrayList<Integer> couleurUtilisees = new ArrayList<Integer>();
        for (Sommet voisins : getVoisins(sommet)) {
            couleurUtilisees.add(voisins.getCouleur());
        }
        for (int couleur = 1; couleur <= k; couleur++) {
            if (!couleurUtilisees.contains(couleur)) {
                sommet.setCouleur(couleur);

                // Changer la couleur dans les inferferences
                for (Pair<Sommet, Sommet> arete : aretesInterferences) {
                    if (arete.a == sommet) {
                        arete.a.setCouleur(couleur);
                    }
                    if (arete.b == sommet) {
                        arete.b.setCouleur(couleur);
                    }
                }

                // Changer la couleur dans les preferences
                for (Pair<Sommet, Sommet> arete : aretesPreference) {
                    if (arete.a == sommet) {
                        arete.a.setCouleur(couleur);
                    }
                    if (arete.b == sommet) {
                        arete.b.setCouleur(couleur);
                    }
                }
                return couleur;
            }
        }
        return 0;
    }

    public void spillSommet(Sommet sommet) {
        sommets.get(sommets.indexOf(sommet)).setCouleur(-1);
    }

    @Override
    public String toString() {
        String res = "\n-- Sommets --\n";
        for (Sommet sommet : sommets) {
            res += "\033[3" + sommet.getCouleur() + "m" + sommet.getNom() + " (" + sommet.getCouleur() + ")"
                    + "\033[0m\n";
        }
        res += "-- Interferences --\n";
        for (Sommet sommet : sommets) {
            String voisinsInterference = "";
            for (Pair<Sommet, Sommet> arete : aretesInterferences) {
                if (arete.a == sommet) {
                    if (arete.b.getCouleur() == -1)
                        voisinsInterference += "\033[30m(" + arete.b.getNom() + ")\033[0m ";
                    else
                        voisinsInterference += "\033[3" + arete.b.getCouleur() + "m" + arete.b.getNom() + " ("
                                + arete.b.getCouleur() + ")\033[0m ";
                }
            }
            if (voisinsInterference.length() > 0) {
                if (sommet.getCouleur() == -1)
                    res += "\033[30m(" + sommet.getNom() + ")\033[0m -> " + voisinsInterference + "\n";
                else
                    res += "\033[3" + sommet.getCouleur() + "m" + sommet.getNom() + " (" + sommet.getCouleur() + ")"
                            + "\033[0m -> " + voisinsInterference
                            + "\n";

            }
        }
        res += "-- Preferences --\n";
        for (Sommet sommet : sommets) {
            String voisinsPreference = "";
            for (Pair<Sommet, Sommet> arete : aretesPreference) {
                if (arete.a == sommet) {
                    if (arete.b.getCouleur() == -1)
                        voisinsPreference += "\033[30m(" + arete.b.getNom() + ")\033[0m ";
                    else
                        voisinsPreference += "\033[3" + arete.b.getCouleur() + "m" + arete.b.getNom() + " ("
                                + arete.b.getCouleur() + ")\033[0m ";
                }
            }
            if (voisinsPreference.length() > 0) {
                if (sommet.getCouleur() == -1)
                    res += "\033[30m(" + sommet.getNom() + ")\033[0m -> " + voisinsPreference + "\n";
                else
                    res += "\033[3" + sommet.getCouleur() + "m" + sommet.getNom() + " (" + sommet.getCouleur() + ")"
                            + "\033[0m -> " + voisinsPreference
                            + "\n";
            }
        }
        return res;
    }
}

class Colalising {
    private Sommet nouveauSommet;
    private ArrayList<Sommet> sommetsSauvegardes = new ArrayList<>();
    private ArrayList<Pair<Sommet, Sommet>> sauvegarderPairs = new ArrayList<>();
    private ArrayList<Pair<Sommet, Sommet>> nouvellesPairs = new ArrayList<>();

    public Sommet getNouveauSommet() {
        return nouveauSommet;
    }

    public void setNouveauSommet(Sommet nouveauSommet) {
        this.nouveauSommet = nouveauSommet;
    }

    public ArrayList<Sommet> getSommetsSauvegardes() {
        return sommetsSauvegardes;
    }

    public void setSommetsSauvegardes(ArrayList<Sommet> sommetsSauvegardes) {
        this.sommetsSauvegardes = sommetsSauvegardes;
    }

    public ArrayList<Pair<Sommet, Sommet>> getSauvegarderPairs() {
        return sauvegarderPairs;
    }

    public void setSauvegarderPairs(ArrayList<Pair<Sommet, Sommet>> sauvegarderPairs) {
        this.sauvegarderPairs = sauvegarderPairs;
    }

    public ArrayList<Pair<Sommet, Sommet>> getNouvellesPairs() {
        return nouvellesPairs;
    }

    public void setNouvellesPairs(ArrayList<Pair<Sommet, Sommet>> nouvellesPairs) {
        this.nouvellesPairs = nouvellesPairs;
    }

}

class ColoriageGraphe {
    public static ArrayList<Colalising> listeColalisings = new ArrayList<>();

    public static void coalisingGraphe(Graphe graphe) {
        for (Pair<Sommet, Sommet> aretePreference : graphe.getAretesPreference()) {
            ArrayList<Sommet> sommetsSauvegardes = new ArrayList<>();
            ArrayList<Pair<Sommet, Sommet>> sauvegarderPairs = new ArrayList<>();
            ArrayList<Pair<Sommet, Sommet>> nouvellesPairs = new ArrayList<>();

            Sommet sommetCoalise = new Sommet(aretePreference.a.getNom() + aretePreference.b.getNom());
            graphe.addSommet(sommetCoalise);

            for (Pair<Sommet, Sommet> areteInterference : graphe.getAretesInterferences()) {
                if (areteInterference.a == aretePreference.a || areteInterference.a == aretePreference.b) {
                    nouvellesPairs.add(new Pair<>(sommetCoalise, areteInterference.b));
                    sauvegarderPairs.add(areteInterference);
                }
                if (areteInterference.b == aretePreference.a || areteInterference.b == aretePreference.b) {
                    nouvellesPairs.add(new Pair<>(sommetCoalise, areteInterference.a));
                    sauvegarderPairs.add(areteInterference);
                }
            }

            for (Pair<Sommet, Sommet> pair : nouvellesPairs) {
                graphe.addInterference(pair.a, pair.b);
            }

            for (Pair<Sommet, Sommet> pair : sauvegarderPairs) {
                graphe.removeAretesInterferences(pair);
            }

            sommetsSauvegardes.add(aretePreference.a);
            sommetsSauvegardes.add(aretePreference.b);

            graphe.removeSommet(aretePreference.a);
            graphe.removeSommet(aretePreference.b);

            Colalising coalising = new Colalising();
            coalising.setNouveauSommet(sommetCoalise);
            coalising.setNouvellesPairs(nouvellesPairs);
            coalising.setSauvegarderPairs(sauvegarderPairs);
            coalising.setSommetsSauvegardes(sommetsSauvegardes);

            listeColalisings.add(coalising);
        }
    }

    public static void decolisingGraphe(Graphe graphe) {
        for (Colalising coalising : listeColalisings) {
            for (Sommet sommet : coalising.getSommetsSauvegardes()) {
                sommet.setCouleur(coalising.getNouveauSommet().getCouleur());
                graphe.addSommet(sommet);
                graphe.removeSommet(coalising.getNouveauSommet());
            }

            for (Pair<Sommet, Sommet> arete : coalising.getSauvegarderPairs()) {
                graphe.addInterference(arete.a, arete.b);
            }

            for (Pair<Sommet, Sommet> arete : coalising.getNouvellesPairs()) {
                graphe.removeAretesInterferences(arete);
            }
        }
        listeColalisings.clear();
    }

    public static Graphe colorierGraphe(Graphe graphe, int k) {
        if (graphe.getSommets().isEmpty()) {
            // Cas de base : tous les sommets sont coloriés
            return graphe;
        }

        // Parcourez les sommets pour trouver un sommet trivialement coloriable
        for (Sommet sommet : graphe.getSommets()) {
            if (graphe.estTrivialementColoriable(sommet, k)) {
                colorierGraphe(graphe.newGrapheSansSommet(sommet), k);
                System.out.println("Le sommet " + sommet.getNom()
                        + " est trivialement coloriable, on lui attribut la couleur \033[3"
                        + graphe.attribuerCouleur(sommet, k) + "m" + graphe.attribuerCouleur(sommet, k) + "\033[0m");
                return graphe;
            }
        }

        // Si aucun sommet trivialement coloriable n'est trouvé, choisissez le sommet de
        // plus haut degré
        Sommet sommet = graphe.getSommetPlusHautDegre();
        System.out.println("Spillage du sommet " + sommet.getNom() + " (degré : " + graphe.calculerDegre(sommet) + ")");
        colorierGraphe(graphe.newGrapheSansSommet(sommet), k);
        graphe.spillSommet(sommet);
        return graphe;
    }

    public static void main(String[] args) {
        System.out.println("---- Graphe vue en TD (k = 3)----");

        Graphe graphe = new Graphe();

        Sommet t = new Sommet("t");
        Sommet u = new Sommet("u");
        Sommet v = new Sommet("v");
        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");

        graphe.addSommet(t);
        graphe.addSommet(u);
        graphe.addSommet(v);
        graphe.addSommet(x);
        graphe.addSommet(y);
        graphe.addSommet(z);

        graphe.addInterference(t, v);
        graphe.addInterference(t, y);
        graphe.addInterference(u, x);
        graphe.addInterference(u, y);
        graphe.addInterference(v, x);
        graphe.addInterference(v, z);
        graphe.addInterference(x, y);
        graphe.addPreference(u, t);

        System.out.println(graphe);
        coalisingGraphe(graphe);

        System.out.println("---- On coalise le graphe ----");
        System.out.println(graphe);
        colorierGraphe(graphe, 3);

        System.out.println("---- On colorie le graphe ----");
        System.out.println(graphe);

        System.out.println("---- On décoalise le graphe ----");
        decolisingGraphe(graphe);
        System.out.println(graphe);

        // #################################################################

        System.out.println("\n\n---- Graphe vue en TD (k = 2)----");
        coalisingGraphe(graphe);

        System.out.println("---- On coalise le graphe ----");
        System.out.println(graphe);
        colorierGraphe(graphe, 2);

        System.out.println("---- On colorie le graphe ----");
        System.out.println(graphe);

        System.out.println("---- On décoalise le graphe ----");
        decolisingGraphe(graphe);
        System.out.println(graphe);

        // #################################################################

        System.out.println("\n\n---- Graphe diamand ----");

        Graphe diamand = new Graphe();

        Sommet a = new Sommet("a");
        Sommet b = new Sommet("b");
        Sommet c = new Sommet("c");
        Sommet d = new Sommet("d");

        diamand.addSommet(a);
        diamand.addSommet(b);
        diamand.addSommet(c);
        diamand.addSommet(d);
        diamand.addInterference(a, b);
        diamand.addInterference(a, c);
        diamand.addInterference(b, d);
        diamand.addInterference(c, d);
        diamand.addPreference(a, d);

        System.out.println(diamand);
        coalisingGraphe(diamand);

        System.out.println("---- On coalise le diamand ----");
        System.out.println(diamand);
        colorierGraphe(diamand, 3);

        System.out.println("---- On colorie le diamand ----");
        System.out.println(diamand);

        System.out.println("---- On décoalise le diamand ----");
        decolisingGraphe(diamand);
        System.out.println(diamand);

    }
}
