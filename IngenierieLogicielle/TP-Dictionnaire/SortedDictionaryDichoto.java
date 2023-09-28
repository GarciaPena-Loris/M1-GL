public class SortedDictionaryDichoto extends AbstractDictionary {
    /*
     * Recherche dichotomique
     */

    public SortedDictionaryDichoto() {
        keys = new Comparable[0];
        values = new Object[0];
        size = 0;
    }

    // IndexOf
    protected int indexOf(Object key) {
        int index = size / 2;
        int pos = ((Comparable<Object>) keys[index]).compareTo(key);
        if (pos == 0) {
            return index;
        }
        if (pos < 0) {
            for (int i = index; i < size; i++) {
                if (((Comparable<Object>) keys[i]).compareTo(key) == 0) {
                    return i;
                }
            }
        } else {
            for (int i = index; i >= 0; i--) {
                if (((Comparable<Object>) keys[i]).compareTo(key) == 0) {
                    return i;
                }
            }
        }
    }

    // NewIndexOf
    protected int newIndexOf(Object key) {
        if (indexOf(key) != -1) { // Verifier si key n'est pas dans le dictionnaire
            return -1;
        }
        Object[] newKeys = new Object[size + 1]; // On cree deux nouveaux tableaux de taille + 1
        Object[] newValues = new Object[size + 1];
        int index = size / 2;
        int pos = ((Comparable<Object>) keys[index]).compareTo(key);
        if (pos < 0) {
            for (int i = index; i < size; i++) {
                if (((Comparable<Object>) keys[i]).compareTo(key) == 0) {
                    return i;
                }
            }
        } else {
            for (int i = index; i >= 0; i--) {
                if (((Comparable<Object>) keys[i]).compareTo(key) == 0) {
                    return i;
                }
            }
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
