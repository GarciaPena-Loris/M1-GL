package m1gl.ig;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSortedDictionary {

    private SortedDictionary dictionary;

    @Before
    public void setUp() {
        dictionary = new SortedDictionary();
    }

    // Test SortedDictionary
    @Test
    public void testSortedDictionaryPut() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Banane", dictionary.keys[0]);
        assertEquals("Livre", dictionary.keys[1]);
        assertEquals("Pomme", dictionary.keys[2]);
    }

    @Test
    public void TestSortedDictionaryIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(0, dictionary.indexOf("Banane"));
        assertEquals(1, dictionary.indexOf("Livre"));
        assertEquals(2, dictionary.indexOf("Pomme"));
        assertEquals(-1, dictionary.indexOf("serpent"));
    }

    @Test
    public void TestSortedDictionaryNewIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(0, dictionary.newIndexOf("Abricot"));
        assertEquals(3, dictionary.newIndexOf("Serpent"));
    }

}