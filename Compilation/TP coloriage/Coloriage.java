import java.util.*;

import org.antlr.v4.runtime.misc.Pair;

class Couleur {
    private int couleur;

    public Couleur(int couleur) {
        this.couleur = couleur;
    }

    public int getCouleur() {
        return couleur;
    }
}

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

    public void addSommet(Sommet sommet) {
        sommets.add(sommet);
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

    // Fonctions coloration
    public int chooseSpillVertex(List<Integer> spillCandidates) {
        int bestVertex = -1;
        int bestCost = Integer.MAX_VALUE;

        for (int vertex : spillCandidates) {
            int degree = graph.get(vertex).size(); // Degré du sommet
            int numConflictingRegisters = countConflictingRegisters(vertex); // Comptage des registres en conflit

            // Définissez votre fonction de coût ici en fonction du degré et du nombre de
            // registres en conflit
            int cost = computeCost(degree, numConflictingRegisters);

            if (cost < bestCost) {
                bestCost = cost;
                bestVertex = vertex;
            }
        }

        return bestVertex;
    }

    // Exemple d'une fonction de coût simple, vous pouvez personnaliser la vôtre
    private int computeCost(int degree, int numConflictingRegisters) {
        // Par exemple, vous pouvez attribuer un coût qui dépend du degré et du nombre
        // de registres en conflit
        return degree + numConflictingRegisters;
    }

    // Compte le nombre de registres en conflit pour un sommet donné
    private int countConflictingRegisters(int vertex) {
        int count = 0;
        int currentColor = colors.get(vertex);

        for (int neighbor : graph.get(vertex)) {
            if (colors.get(neighbor) == currentColor) {
                count++;
            }
        }

        return count;
    }

    public void colorierGraphe(Graphe graphe, int k) {
        if (graphe.sommets.size() == 1) {
            return attribuerCouleur(graphe, graphe.sommets.get(0));
        }
        for (int i = 0; i < sommets.size(); i++) {
            if (estTrivialementColoriable(i)) {
                System.out.println("Le sommet " + i + " est trivialement coloriable, on le colorie");
                graphe.sommets.remove(i);
                colorierGraphe(graphe, k);
                return attribuerCouleur(graphe, graphe.sommets.get(i));
            } else {
                graphe.sommets.remove(i);
                colorierGraphe(graphe, k);
                return spillVertex(graphe, graphe.sommets.get(i));
            }
        }
    }

    // Vérifie si un sommet est trivialement colorable
    private boolean estTrivialementColoriable(int vertex) {
        for (int neighbor : graph.get(vertex)) {
            if (colors.get(neighbor) == colors.get(vertex)) {
                return false;
            }
        }
        return true;
    }

    private int getAvailableColor(int vertex) {
        ArrayList<Integer> couleur = new ArrayList<Integer>();
        ArrayList<Integer> usedColors = new ArrayList()<Integer>();
        for (int aretes : aretesInterference) {
            if (aretes.a == vertex || aretes.b == vertex){
                
            }
            if (aretes.get(sommet).getCouleur() != -1) {
                sommet.add(couleur.get(neighbor));
            }
        }
        for (int color = 0; ; color++) {
            if (!usedColors.contains(color)) {
                return color;
            }
        }
    }

    private void spillVertex(int vertex) {

        colors.add(vertex, -1);
    }

    public List<Integer> getColors() {
        return colors;
    }

    public void afficherGrahpe() {
        for (Sommet sommet : sommets) {
            String voisinsInterference = "";
            String voisinsPreference = "";
            for (Pair<Sommet, Sommet> arete : aretesInterferences) {
                if (arete.a == sommet) {
                    voisinsInterference += arete.b.getNom() + " ";
                }
            }
            for (Pair<Sommet, Sommet> arete : aretesPreference) {
                if (arete.a == sommet) {
                    voisinsPreference += arete.b.getNom() + " ";
                }
            }
            if (voisinsInterference.length() > 0)
                System.out.print(sommet.getNom() + " -----> " + voisinsInterference + "\n");
            if (voisinsPreference.length() > 0) {
                System.out.print(sommet.getNom() + " -.-.-> " + voisinsPreference + "\n");
            }
        }
    }

    public static void main(String[] args) {
        Graphe Graphe = new Graphe();

        Sommet t = new Sommet("t");
        Sommet u = new Sommet("u");
        Sommet v = new Sommet("v");
        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");

        Graphe.addSommet(t);
        Graphe.addSommet(u);
        Graphe.addSommet(v);
        Graphe.addSommet(x);
        Graphe.addSommet(y);
        Graphe.addSommet(z);

        Graphe.addInterference(t, v);
        Graphe.addInterference(t, y);
        Graphe.addInterference(u, x);
        Graphe.addInterference(u, y);
        Graphe.addInterference(v, x);
        Graphe.addInterference(v, z);
        Graphe.addInterference(x, y);
        Graphe.addPreference(u, t);

        Graphe.afficherGrahpe();

    }
}
