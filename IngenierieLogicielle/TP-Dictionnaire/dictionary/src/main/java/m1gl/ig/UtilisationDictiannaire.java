package m1gl.ig;

import java.util.ArrayList;
import java.util.Arrays;

public class UtilisationDictiannaire {

    public static void testDictionnaire(IDictionary dictionary) {
        System.out.println("\n####################################\n");

        System.out.println("Test de la " + dictionary.getClass() + " : ");

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + dictionary.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + dictionary.size());

        System.out.println("## Ajout d'éléments dans le dictionnaire ##");
        // Test de la méthode put
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        dictionary.put("Voiture", "Un véhicule à moteur à quatre roues utilisé pour le transport.");
        dictionary.put("Chien", "Un mammifère domestique de l'espèce Canis lupus familiaris.");
        dictionary.put("Chat", "Petit mammifère familier à poil doux.");
        dictionary.put("Banane", "Fruit oblong à pulpe farineuse.");
        dictionary.put("Avion", "Appareil capable de se déplacer en l'air.");
        dictionary.put("Ordinateur", "Qui ordonne, met en ordre.");
        dictionary.put("Trottoir", "Chemin surélevé réservé à la circulation des piétons.");
        dictionary.put("Pansement", "Linge, compresse servant à protéger une plaie.");
        dictionary.put("Lunette", "Instrument d'optique destiné à l'observation des objets éloignés.");

        // Test de la méthode affichage
        System.out.println("\t" + dictionary + "\n");

        // Test de la méthode size
        System.out.println("\tTaille : " + dictionary.size());

        // Test de la méthode get
        System.out.println("\tPomme : " + dictionary.get("Pomme"));
        System.out.println("\tLivre : " + dictionary.get("Livre"));
        System.out.println("\tVoiture : " + dictionary.get("Voiture"));

        System.out.println("## Modification d'un élément du dictionnaire et ajout d'éléments ##");
        // Test de la méthode put
        dictionary.put("Livre", "Pas un e-book.");
        dictionary.put("Maison", "Un bâtiment destiné à l'habitation humaine.");
        dictionary.put("maison", "Un bâtiment destiné à l'habitation humaine.");
        dictionary.put("Poil", "Production filiforme sur la peau de certains animaux.");
        dictionary.put("Orage", "Perturbation atmosphérique violente.");
        dictionary.put("OpenAIEngineer", "Un engenieur d'open AI");
        dictionary.put("SoftwareMaster", "Un master en Software");
        dictionary.put("Lampes", "Un objet qui éclaire.");
        dictionary.put("Zoo", "Un endroit où il y a des animaux.");
        dictionary.put("Pomme", "Un fruit généralement de couleur jaune.");
        dictionary.put("Canard", "Un animal qui fait coin coin.");
        dictionary.put("Cadre", "Un objet qui permet de mettre une photo.");
        dictionary.put("Lavoisiere", "Un batiment de l'INSA de Lyon.");
        dictionary.put("Assiette", "Un objet qui permet de manger.");
        dictionary.put("Couteau", "Un objet qui permet de couper.");
        dictionary.put("Fourchette", "Un objet qui permet de piquer.");
        dictionary.put("Creme-solaire", "Un objet qui permet de se protéger du soleil.");
        dictionary.put("Crayon", "Un objet qui permet d'écrire.");
        dictionary.put("Stylo", "Un objet qui permet d'écrire.");
        dictionary.put("Cahier", "Un objet rempli de feuilles.");
        dictionary.put("Feuille", "Un objet composé avec du papier.");
        dictionary.put("Papier", "Un objet a base de bois.");
        dictionary.put("Bois", "Un objet qui pousse dans les arbres.");
        dictionary.put("Arbre", "Un objet qui pousse dans la terre.");
        dictionary.put("Terre", "Un objet qui est dans l'univers.");
        dictionary.put("Univers", "Tout ce qui est dans l'espace.");
        dictionary.put("Espace", "Représente ce qui est dans le vide.");
        dictionary.put("Vide", "Ce qui n'existe pas.");
        dictionary.put("Existe", "Un objet qui est dans la réalité.");

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + dictionary.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + dictionary.size());

        // Test de la méthode affichage
        System.out.println("\t" + dictionary + "\n");

        // Test de la méthode get
        System.out.println("\tPomme : " + dictionary.get("Pomme"));
        System.out.println("\tLivre : " + dictionary.get("Livre"));
        System.out.println("\tMaison : " + dictionary.get("Maison"));
        System.out.println("\tTrottoir : " + dictionary.get("Trottoir"));
        System.out.println("\tPoire (null) : " + dictionary.get("Poire"));

        System.out.println("## Test fonction containsKey ##");

        // Test de la méthode containsKey
        System.out.println("\tPomme (oui)? " + dictionary.containsKey("Pomme"));
        System.out.println("\tLivre (oui)? " + dictionary.containsKey("Livre"));
        System.out.println("\tPoire (non)? " + dictionary.containsKey("Poire"));
        System.out.println("\tTrottoir (oui)? " + dictionary.containsKey("Trottoir"));
    }

    public static void main(String[] args) {
        testDictionnaire((IDictionary) new OrderedDictionary());
        testDictionnaire((IDictionary) new FastDictionary());
        testDictionnaire((IDictionary) new SortedDictionary());
        testDictionnaire((IDictionary) new SortedDictionaryDichoto());

    }
}
