public class UtilisationDictiannaire {
    public static void main(String[] args) {
        System.out.println("Test de la classe OrderedDictionary : \n");

        System.out.println("## Création d'un dictionnaire vide ##");
        IDictionary orderedDic = new OrderedDictionary();

        // Implémentation des 5 méthodes publique de IDictionary

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + orderedDic.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + orderedDic.size());

        System.out.println("## Ajout d'éléments dans le dictionnaire ##");
        // Test de la méthode put
        orderedDic.put("a", 1).put("b", 2).put("c", 3).put("d", 4);

        // Test de la méthode affichage
        System.out.println("\t" + orderedDic);

        // Test de la méthode size
        System.out.println("\tTaille : " + orderedDic.size());

        // Test de la méthode get
        System.out.println("\ta : " + orderedDic.get("a"));
        System.out.println("\tb : " + orderedDic.get("b"));

        System.out.println("## Modification d'un élément du dictionnaire et ajout d'un élément ##");
        // Test de la méthode put
        orderedDic.put("b", 5).put("e", 6);

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + orderedDic.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + orderedDic.size());

        // Test de la méthode affichage
        System.out.println("\t" + orderedDic);

        // Test de la méthode get
        System.out.println("\tb : " + orderedDic.get("b"));
        System.out.println("\te : " + orderedDic.get("e"));
        System.out.println("\tf : " + orderedDic.get("f"));

        System.out.println("## Test fonction containsKey ##");

        // Test de la méthode containsKey
        System.out.println("\ta ? " + orderedDic.containsKey("a"));
        System.out.println("\tb ? " + orderedDic.containsKey("b"));
        System.out.println("\tf ? " + orderedDic.containsKey("f"));

        // ################################################################################################################

        System.out.println("\n####################################\n");

        System.out.println("Test de la classe FastDictionary : \n");

        System.out.println("## Création d'un dictionnaire vide ##");
        IDictionary FastDic = new FastDictionary();

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + FastDic.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + FastDic.size());

        System.out.println("## Ajout d'éléments dans le dictionnaire ##");
        // Test de la méthode put
        FastDic.put("Pomme", "Un fruit généralement de couleur rouge.");
        FastDic.put("Livre", "Une oeuvre écrite ou imprimée.");
        FastDic.put("Voiture", "Un véhicule à moteur à quatre roues utilisé pour le transport.");
        FastDic.put("Chien", "Un mammifère domestique de l'espèce Canis lupus familiaris.");
        FastDic.put("Chat", "Petit mammifère familier à poil doux.");
        FastDic.put("Banane", "Fruit oblong à pulpe farineuse.");
        FastDic.put("Avion", "Appareil capable de se déplacer en l'air.");
        FastDic.put("Ordinateur", "Qui ordonne, met en ordre.");
        FastDic.put("Trotoir", "Chemin surélevé réservé à la circulation des piétons.");
        FastDic.put("Pansement", "Linge, compresse servant à protéger une plaie.");
        FastDic.put("Lunette", "Instrument d'optique destiné à l'observation des objets éloignés.");

        // Test de la méthode affichage
        System.out.println("\t" + FastDic);

        // Test de la méthode size
        System.out.println("\tTaille : " + FastDic.size());

        // Test de la méthode get
        System.out.println("\tPomme : " + FastDic.get("Pomme"));
        System.out.println("\tLivre : " + FastDic.get("Livre"));

        System.out.println("## Modification d'un élément du dictionnaire et ajout d'un élément ##");
        // Test de la méthode put
        FastDic.put("Livre", "Pas un e-book.");
        FastDic.put("Maison", "Un bâtiment destiné à l'habitation humaine.");
        FastDic.put("maison", "Un bâtiment destiné à l'habitation humaine.");
        FastDic.put("Poil", "Production filiforme sur la peau de certains animaux.");
        FastDic.put("Orage", "Perturbation atmosphérique violente.");
        FastDic.put("OpenAIEngineer", "Un engenieur d'open AI");
        FastDic.put("SoftwareMaster", "Un master en Software");

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + FastDic.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + FastDic.size());

        // Test de la méthode affichage
        System.out.println("\t" + FastDic);

        // Test de la méthode get
        System.out.println("\tPomme : " + FastDic.get("Pomme"));
        System.out.println("\tLivre : " + FastDic.get("Livre"));
        System.out.println("\tMaison : " + FastDic.get("Maison"));
        System.out.println("\tPoire : " + FastDic.get("Poire"));

        System.out.println("## Test fonction containsKey ##");

        // Test de la méthode containsKey
        System.out.println("\tPomme ? " + FastDic.containsKey("Pomme"));
        System.out.println("\tLivre ? " + FastDic.containsKey("Livre"));
        System.out.println("\tPoire ? " + FastDic.containsKey("Poire"));

        // ################################################################################################################

        System.out.println("\n####################################\n");

        System.out.println("Test de la classe sortedDictionary : \n");

        System.out.println("## Création d'un dictionnaire vide ##");
        IDictionary sortedDictionary = new SortedDictionary();

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + sortedDictionary.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionary.size());

        System.out.println("## Ajout d'éléments dans le dictionnaire ##");
        // Test de la méthode put
        sortedDictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        sortedDictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        sortedDictionary.put("Voiture", "Un véhicule à moteur à quatre roues utilisé pour le transport.");
        sortedDictionary.put("Chien", "Un mammifère domestique de l'espèce Canis lupus familiaris.");
        sortedDictionary.put("Chat", "Petit mammifère familier à poil doux.");
        sortedDictionary.put("Banane", "Fruit oblong à pulpe farineuse.");
        sortedDictionary.put("Avion", "Appareil capable de se déplacer en l'air.");
        sortedDictionary.put("Ordinateur", "Qui ordonne, met en ordre.");
        sortedDictionary.put("Trotoir", "Chemin surélevé réservé à la circulation des piétons.");
        sortedDictionary.put("Pansement", "Linge, compresse servant à protéger une plaie.");
        sortedDictionary.put("Lunette", "Instrument d'optique destiné à l'observation des objets éloignés.");

        // Test de la méthode affichage
        System.out.println("\t" + sortedDictionary);

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionary.size());

        // Test de la méthode get
        System.out.println("\tPomme : " + sortedDictionary.get("Pomme"));
        System.out.println("\tLivre : " + sortedDictionary.get("Livre"));

        System.out.println("## Modification d'un élément du dictionnaire et ajout d'un élément ##");
        // Test de la méthode put
        sortedDictionary.put("Livre", "Pas un e-book.");
        sortedDictionary.put("Maison", "Un bâtiment destiné à l'habitation humaine.");
        sortedDictionary.put("maison", "Un bâtiment destiné à l'habitation humaine.");
        sortedDictionary.put("Poil", "Production filiforme sur la peau de certains animaux.");
        sortedDictionary.put("Orage", "Perturbation atmosphérique violente.");
        sortedDictionary.put("OpenAIEngineer", "Un engenieur d'open AI");
        sortedDictionary.put("SoftwareMaster", "Un master en Software");

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + sortedDictionary.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionary.size());

        // Test de la méthode affichage
        System.out.println("\t" + sortedDictionary);

        // Test de la méthode get
        System.out.println("\tPomme : " + sortedDictionary.get("Pomme"));
        System.out.println("\tLivre : " + sortedDictionary.get("Livre"));
        System.out.println("\tMaison : " + sortedDictionary.get("Maison"));
        System.out.println("\tPoire : " + sortedDictionary.get("Poire"));

        System.out.println("## Test fonction containsKey ##");

        // Test de la méthode containsKey
        System.out.println("\tPomme ? " + sortedDictionary.containsKey("Pomme"));
        System.out.println("\tLivre ? " + sortedDictionary.containsKey("Livre"));
        System.out.println("\tPoire ? " + sortedDictionary.containsKey("Poire"));

        // ################################################################################################################

        System.out.println("\n####################################\n");

        System.out.println("Test de la classe sortedDictionaryDichoto : \n");

        System.out.println("## Création d'un dictionnaire vide ##");
        IDictionary sortedDictionaryDichoto = new SortedDictionaryDichoto();

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + sortedDictionaryDichoto.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionaryDichoto.size());

        System.out.println("## Ajout d'éléments dans le dictionnaire ##");
        // Test de la méthode put
        sortedDictionaryDichoto.put("Pomme", "Un fruit généralement de couleur rouge.");
        sortedDictionaryDichoto.put("Livre", "Une oeuvre écrite ou imprimée.");
        sortedDictionaryDichoto.put("Voiture", "Un véhicule à moteur à quatre roues utilisé pour le transport.");
        sortedDictionaryDichoto.put("Chien", "Un mammifère domestique de l'espèce Canis lupus familiaris.");
        sortedDictionaryDichoto.put("Chat", "Petit mammifère familier à poil doux.");
        sortedDictionaryDichoto.put("Banane", "Fruit oblong à pulpe farineuse.");
        sortedDictionaryDichoto.put("Avion", "Appareil capable de se déplacer en l'air.");
        sortedDictionaryDichoto.put("Ordinateur", "Qui ordonne, met en ordre.");
        sortedDictionaryDichoto.put("Trotoir", "Chemin surélevé réservé à la circulation des piétons.");
        sortedDictionaryDichoto.put("Pansement", "Linge, compresse servant à protéger une plaie.");
        sortedDictionaryDichoto.put("Lunette", "Instrument d'optique destiné à l'observation des objets éloignés.");

        // Test de la méthode affichage
        System.out.println("\t" + sortedDictionaryDichoto);

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionaryDichoto.size());

        // Test de la méthode get
        System.out.println("\tPomme : " + sortedDictionaryDichoto.get("Pomme"));
        System.out.println("\tLivre : " + sortedDictionaryDichoto.get("Livre"));

        System.out.println("## Modification d'un élément du dictionnaire et ajout d'un élément ##");
        // Test de la méthode put
        sortedDictionaryDichoto.put("Livre", "Pas un e-book.");
        sortedDictionaryDichoto.put("Maison", "Un bâtiment destiné à l'habitation humaine.");
        sortedDictionaryDichoto.put("maison", "Un bâtiment destiné à l'habitation humaine.");
        sortedDictionaryDichoto.put("Poil", "Production filiforme sur la peau de certains animaux.");
        sortedDictionaryDichoto.put("Orage", "Perturbation atmosphérique violente.");
        sortedDictionaryDichoto.put("OpenAIEngineer", "Un engenieur d'open AI");
        sortedDictionaryDichoto.put("SoftwareMaster", "Un master en Software");

        // Test de la méthode isEmpty
        System.out.println("\tVide ? " + sortedDictionaryDichoto.isEmpty());

        // Test de la méthode size
        System.out.println("\tTaille : " + sortedDictionaryDichoto.size());

        // Test de la méthode affichage
        System.out.println("\t" + sortedDictionaryDichoto);

        // Test de la méthode get
        System.out.println("\tPomme : " + sortedDictionaryDichoto.get("Pomme"));
        System.out.println("\tLivre : " + sortedDictionaryDichoto.get("Livre"));
        System.out.println("\tMaison : " + sortedDictionaryDichoto.get("Maison"));
        System.out.println("\tTrotoir : " + sortedDictionaryDichoto.get("Trotoir"));
        System.out.println("\tPoire : " + sortedDictionaryDichoto.get("Poire"));

        System.out.println("## Test fonction containsKey ##");

        // Test de la méthode containsKey
        System.out.println("\tPomme ? " + sortedDictionaryDichoto.containsKey("Pomme"));
        System.out.println("\tLivre ? " + sortedDictionaryDichoto.containsKey("Livre"));
        System.out.println("\tPoire ? " + sortedDictionaryDichoto.containsKey("Poire"));
        System.out.println("\tTrotoir ? " + sortedDictionaryDichoto.containsKey("Trotoir"));

    }
}
