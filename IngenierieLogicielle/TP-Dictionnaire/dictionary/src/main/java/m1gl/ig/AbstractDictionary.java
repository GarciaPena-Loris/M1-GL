package m1gl.ig;

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

    public IDictionary flush() {
        keys = new Object[0];
        values = new Object[0];
        size = 0;
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
        for (int i = 0; i < keys.length; i++) {
            int colorCode = (int) (Math.random() * 6) + 31; // 31-36 are the color codes for foreground colors
            s += "\t \033[" + colorCode + "m" + keys[i] + "\033[0m : \033[1m" + values[i] + "\033[0m \n";
        }
        s += "\t}";
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
