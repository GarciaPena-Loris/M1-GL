package m1gl.ig;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSortedDichotoDictionary {

    private SortedDictionaryDichoto dictionary;

    @Before
    public void setUp() {
        dictionary = new SortedDictionaryDichoto();
    }

    // Test SortedDictionaryDichoto
    @Test
    public void testSortedDictionaryDichotoPut() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Banane", dictionary.keys[0]);
        assertEquals("Livre", dictionary.keys[1]);
        assertEquals("Pomme", dictionary.keys[2]);
    }

    @Test
    public void TestSortedDictionaryDichotoIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(0, dictionary.indexOf("Banane"));
        assertEquals(1, dictionary.indexOf("Livre"));
        assertEquals(2, dictionary.indexOf("Pomme"));
        assertEquals(-1, dictionary.indexOf("serpent"));
    }

    @Test
    public void TestSortedDictionaryDichotoNewIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(0, dictionary.newIndexOf("Abricot"));
        assertEquals(3, dictionary.newIndexOf("Serpent"));
    }

}