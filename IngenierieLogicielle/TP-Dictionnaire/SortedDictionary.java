public class SortedDictionary extends AbstractDictionary {
    /*
     * Les couples sont ici stockes par ordre alphabetique sur les cles. Ces
     * dictionnaires etant tries, on peut y chercher des elements sequentiellement.
     */

    /*
     * Il est nécéssaire que les clés dans le tableau des
     * clés soient comparables entre elles. Le type des clés tel qu'il est déclaré
     * dans la classe AbstractDictionary est trop général (Obj ect). Une partie de
     * la solution va consister à utiliser le type Comparable de Java. Un
     * objet de type Comparable est un objet auquel on peut envoyer un message
     * correspondant à la signature suivante : int compareTo (Object o) dont la
     * description est :
     * Compares this Object wilh the specificd Object for order. Returns a négative
     * integer, zero, or a positive integer as this object is less than, equal to,
     * or greater than the specifted Object.
     */

    public SortedDictionary() {
        keys = new Comparable[0];
        values = new Object[0];
        size = 0;
    }

    protected int indexOf(Object key) {
        int index = 0;
        while (index < size && ((Comparable<Object>) keys[index]).compareTo(key) < 0) { // On parcourt le tableau
                                                                                        // jusqu'a trouver la cle
            index++;
        }
        if (index < size && ((Comparable<Object>) keys[index]).compareTo(key) == 0) { // Si on a trouve la cle
            return index;
        }
        return -1;
    }

    protected int newIndexOf(Object key) {
        if (indexOf(key) != -1) { // Verifier si key n'est pas dans le dictionnaire
            return -1;
        }
        Object[] newKeys = new Object[size + 1]; // On cree deux nouveaux tableaux de taille + 1
        Object[] newValues = new Object[size + 1];
        int index = 0;
        if (size > 0) {
        }
        while (index < size && ((Comparable<Object>) keys[index]).compareTo(key) < 0) {
            index++;
        }
        // On copie les élements avant l'index
        for (int i = 0; i < index; i++) {
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        // On ajoute l'élément à l'index
        newKeys[index] = key;
        // On copie les élements après l'index
        for (int i = index; i < size; i++) {
            newKeys[i + 1] = keys[i];
            newValues[i + 1] = values[i];
        }
        keys = newKeys; // On remplace les anciens tableaux par les nouveaux
        values = newValues;
        return index; // On retourne la taille du nouveau tableau
    }
}
