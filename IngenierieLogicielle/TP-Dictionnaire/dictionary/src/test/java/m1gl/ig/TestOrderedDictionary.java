package m1gl.ig;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestOrderedDictionary {

    private OrderedDictionary dictionary;

    @Before
    public void setUp() {
        dictionary = new OrderedDictionary();
    }

    // Test OrderedDictionary
    @Test
    public void testOrderedDictionaryPut() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Pomme", dictionary.keys[0]);
        assertEquals("Banane", dictionary.keys[1]);
        assertEquals("Livre", dictionary.keys[2]);
    }

    @Test
    public void TestOrderedDictionaryIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(0, dictionary.indexOf("Pomme"));
        assertEquals(1, dictionary.indexOf("Banane"));
        assertEquals(2, dictionary.indexOf("Livre"));
        assertEquals(-1, dictionary.indexOf("serpent"));
    }

    @Test
    public void TestOrderedDictionaryNewIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(3, dictionary.newIndexOf("Abricot"));
        assertEquals(3, dictionary.newIndexOf("Serpent"));
    }

}