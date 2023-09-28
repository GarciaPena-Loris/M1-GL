public class OrderedDictionary extends AbstractDictionary {

    public OrderedDictionary() {
        keys = new Object[0];
        values = new Object[0];
        size = 0;
    }

    protected int indexOf(Object key) {
        for (int i = 0; i < size(); i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    protected int newIndexOf(Object key) {
        if (indexOf(key) != -1) { // Verifier si key n'est pas dans le dictionnaire
            return -1;
        }
        Object[] newKeys = new Object[size + 1]; // On cree deux nouveaux tableaux de taille + 1
        Object[] newValues = new Object[size + 1];
        for (int i = 0; i < size; i++) { // On copie les valeurs du tableau dans le nouveau tableau
            newKeys[i] = keys[i];
            newValues[i] = values[i];
        }
        keys = newKeys; // On remplace les anciens tableaux par les nouveaux
        values = newValues;
        return size; // On retourne la taille du nouveau tableau
    }
}
