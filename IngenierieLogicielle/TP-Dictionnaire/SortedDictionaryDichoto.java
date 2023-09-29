public class SortedDictionaryDichoto extends AbstractDictionary {
    /*
     * Recherche dichotomique
     */

    public SortedDictionaryDichoto() {
        keys = new Comparable[0];
        values = new Object[0];
        size = 0;
    }

    // IndexOf dichotomique
    protected int indexOf(Object key) {
        int min = 0;
        int max = size - 1;
        int index = 0;
        int pos = 0;
        while (min <= max) {
            index = (min + max) / 2;
            pos = ((Comparable<Object>) keys[index]).compareTo(key);
            if (pos < 0) { // Si l'élément est plus petit que la clé (dans l'ordre alphabétique a < b)
                min = index + 1; // On place le min à l'index + 1
            } else if (pos > 0) { // Si l'élément est plus grand que la clé (dans l'ordre alphabétique b > a)
                max = index - 1; // On place le max à l'index - 1
            } else {
                return index;
            }
        }
        return -1;
    }

    // NewIndexOf
    protected int newIndexOf(Object key) {
        if (indexOf(key) != -1) { // Verifier si key n'est pas dans le dictionnaire
            return -1;
        }
        Object[] newKeys = new Object[size + 1]; // On cree deux nouveaux tableaux de taille + 1
        Object[] newValues = new Object[size + 1];

        // Utilisation de min et max
        int min = 0;
        int max = size - 1;
        int index = 0;
        int pos = 0;
        while (min <= max) {
            index = (min + max) / 2;
            pos = ((Comparable<Object>) keys[index]).compareTo(key);
            if (pos < 0) { // Si l'élément est plus petit que la clé (dans l'ordre alphabétique a < b)
                min = index + 1; // On place le min à l'index + 1
            } else if (pos > 0) { // Si l'élément est plus grand que la clé (dans l'ordre alphabétique b > a)
                max = index - 1; // On place le max à l'index - 1
            } else {
                break;
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