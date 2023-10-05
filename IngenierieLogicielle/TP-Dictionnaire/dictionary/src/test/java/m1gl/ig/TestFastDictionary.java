package m1gl.ig;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestFastDictionary {

    private FastDictionary dictionary;

    @Before
    public void setUp() {
        dictionary = new FastDictionary();
    }

    // Test FastDictionary
    @Test
    public void testFastDictionaryInit() {
        assertEquals(0, dictionary.size());
        assertEquals(1, dictionary.keys.length);
        assertEquals(1, dictionary.values.length);
    }

    // TestGrow
    @Test
    public void testFastDictionaryGrow() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(3, dictionary.size());
        assertEquals(4, dictionary.keys.length);
        assertEquals(4, dictionary.values.length);
    }
    
    
    @Test
    public void testFastDictionaryPut() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        int ashCodePomme = "Pomme".hashCode()%dictionary.keys.length;
        int ashCodeBanane = "Banane".hashCode()%dictionary.keys.length;
        int ashCodeLivre = "Livre".hashCode()%dictionary.keys.length;

        assertEquals("Banane", dictionary.keys[ashCodeBanane]);
        assertEquals("Livre", dictionary.keys[ashCodeLivre]);
        assertEquals("Pomme", dictionary.keys[ashCodePomme]);
    }

    @Test
    public void TestFastDictionaryIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        dictionary.put("Serpent", "Un animal qui rampe.");
        dictionary.put("Abricot", "Un fruit généralement de couleur orange.");
        int ashCodePomme = "Pomme".hashCode()%dictionary.keys.length;
        int ashCodeBanane = "Banane".hashCode()%dictionary.keys.length;
        int ashCodeLivre = "Livre".hashCode()%dictionary.keys.length;
        int ashCodeSerpent = "Serpent".hashCode()%dictionary.keys.length;
        int ashCodeAbricot = "Abricot".hashCode()%dictionary.keys.length;

        assertEquals(ashCodeBanane, dictionary.indexOf("Banane"));
        assertEquals(ashCodeLivre, dictionary.indexOf("Livre"));
        assertEquals(ashCodePomme, dictionary.indexOf("Pomme"));
        // ashcode serpent < 0
        assertEquals(ashCodeSerpent + dictionary.keys.length, dictionary.indexOf("Serpent"));
        assertEquals(ashCodeAbricot, dictionary.indexOf("Abricot"));
        assertEquals(-1, dictionary.indexOf("Meuble"));
    }

    @Test
    public void TestFastDictionaryNewIndexOf() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Abricot".hashCode()%dictionary.keys.length, dictionary.newIndexOf("Abricot"));
        assertEquals("Bougie".hashCode()%dictionary.keys.length, dictionary.newIndexOf("Bougie"));

        dictionary.put("Serpent", "Un animal qui rampe.");
        assertEquals(5, dictionary.newIndexOf("Serpent"));
    }

}