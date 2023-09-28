/**
 * IDictionary
 */
public interface IDictionary { // Normalement utiliser la généréricité pour les types de clés et de valeurs

    Object get(Object key); // rend la valeur associee a key dans le receveur.

    IDictionary put(Object key, Object value); // entre une nouveau couple cle-valeur dans le receveur, rend le
                                               // receveur.

    boolean isEmpty(); // rend vrai si le receveur est vide, faux sinon

    boolean containsKey(Object key); // rend vrai si la cle est connue dans le receveur, faux sinon.

    int size(); // rend le nombre d’elements (donc le nombre de couples cle-valeur) contenus
                // dans le receveur.

}