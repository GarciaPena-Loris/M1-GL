public class FastDictionary extends AbstractDictionary {
    /*
     * Pour les instances de FaslDictionary, on utilise une technique de hachage
     * pour retrouver les index. La technique est la suivante : la méthode Java
     * hashCode appliquée à un objet retourne un nombre (potentielle-ment négatif),
     * par exemple "toto".hashCode() rend 6032110. Ce nombre étant potentiellement
     * supérieur à la taille du dictionnaire, on le ramène à une valeur d'index
     * utilisable pour le dictionnaire grâce à un modulo (opérateur : % en Java).
     * Ce modulo est la source des conflits de hachage. Un conflit survient à
     * l'insertion d'un couple, lorsque l'index calculé référence un emplacement
     * déjà occupé. En cas de conflit, on recherche à partir de l'index calculé la
     * première place libre, en incrémentant autant de fois que nécessaire l'index,
     * modulo la taille de la collection. Le fait que la collection soit au 3/4
     * pleine au maximum assure qu'on trouvera rapidement une place libre. En
     * résumé, le hachage permet de déterminer très rapidement la zone dans laquelle
     * se trouve la clé recherchée, en cas de conflit, on effectue une petite
     * recherche séquentielle locale.
     */

    public FastDictionary() {
        keys = new Object[1];
        values = new Object[1];
        size = 0;
    }

    // Ecrivez la methode size rendant le nombre d’elements contenus dans un
    // fastDictionary.
    public int size() {
        return size;
    }

    // Ecrivez une methode booleenne mustGrow disant si les tableaux sont au 3/4
    // pleins.
    private boolean mustGrow() {
        return size >= (keys.length * 3) / 4;
    }

    // Ecrivez la methode grow() de la classe FastDictionary qui recule les index
    protected void grow() {
        Object[] oldKeys = keys;
        Object[] oldValues = values;
        keys = new Object[oldKeys.length * 2];
        values = new Object[oldValues.length * 2];
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                int index = newIndexOf(oldKeys[i]);
                keys[index] = oldKeys[i];
                values[index] = oldValues[i];
            }
        }
    }

    protected int indexOf(Object key) {
        int index = key.hashCode() % keys.length; // On calcule l'index de la cle
        if (index < 0) { // Si l'index est negatif, on le rend positif
            index += keys.length;
        }
        while (keys[index] != null && !keys[index].equals(key)) { // On cherche la cle dans le dictionnaire
            index = (index + 1) % keys.length; // On incremente l'index modulo la taille du tableau
        }
        if (keys[index] == null) { // Si la cle n'est pas dans le dictionnaire, on retourne -1
            return -1;
        }
        return index; // Sinon on retourne l'index de la cle
    }

    protected int newIndexOf(Object key) {
        if (indexOf(key) != -1) { // Verifier si key n'est pas dans le dictionnaire
            return -1;
        }
        if (mustGrow()) { // Verifier si les tableaux sont au 3/4 pleins
            grow(); // Si oui, on les agrandit
        }
        int index = key.hashCode() % keys.length; // On calcule l'index de la cle
        if (index < 0) {
            index += keys.length;
        }
        while (keys[index] != null) { // On cherche la premiere place libre
            index = (index + 1) % keys.length;
        }
        return index; // On retourne l'index de la premiere place libre
    }

}
