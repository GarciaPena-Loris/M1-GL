public abstract class AbstractDictionary implements IDictionary {
    protected Object[] keys;
    protected Object[] values;
    protected int size;

    public Object get(Object key) {
        int index = indexOf(key);
        if (index == -1) { // key n'est pas dans le dictionnaire
            return null;
        }
        return values[index];
    }

    public IDictionary put(Object key, Object value) {
        int index = indexOf(key);
        if (index == -1) { // key n'est pas dans le dictionnaire
            index = newIndexOf(key);
            size++;
        }
        keys[index] = key;
        values[index] = value;
        return this;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        return indexOf(key) != -1;
    }

    public int size() {
        return size;
    }

    // Affichage du dictionnaire
    public String toString() {
        if (keys.length == 0) {
            return "{}";
        }
        String s = "{\n";
        for (int i = 0; i < keys.length - 1 ; i++) {
            s += "\t " + keys[i] + " : " + values[i] + ",  \n";
        }
        s += "\t " + keys[keys.length-1] + " : " + values[keys.length-1] + "\n\t}";
        return s;
    }

    // rend l’index auquel est rangé le nom key dans le dictionnaire receveur, si
    // key n’est pas dans le receveur, rend -1.
    protected abstract int indexOf(Object key);

    // Cette methode est appelee uniquement si key N’EST PAS dans le dictionnaire.
    // Cette methode prepare l’insertion et rend l’index auquel la nouvelle cle, et
    // la valeur correspondante, pourront etre inserees; elle n’effectue ces
    // insertions. S’il n’y a pas assez de place pour l’insertion dans le
    // dictionnaire concerne, cette methode devra se charger d’en faire, en
    // remplacant les tableaux par d’autres plus grands, afin de rendre
    // l’insertion possible.
    protected abstract int newIndexOf(Object key);

}
