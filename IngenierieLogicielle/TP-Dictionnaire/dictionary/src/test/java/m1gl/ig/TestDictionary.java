package m1gl.ig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDictionary {

    // Test OrderedDictionary

    @Test
    public void testOrderedDictionaryPut() {
        IDictionary dictionary = new OrderedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertNotNull(dictionary);
    }

    @Test
    public void testOrderedDictionaryGet() {
        IDictionary dictionary = new OrderedDictionary();
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Une oeuvre écrite ou imprimée.", dictionary.get("Livre"));
    }

    @Test
    public void testOrderedDictionaryContainsKey() {
        IDictionary dictionary = new OrderedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertTrue(dictionary.containsKey("Pomme"));
        assertFalse(dictionary.containsKey("Banane"));
    }

    @Test
    public void testOrderedDictionarySize() {
        IDictionary dictionary = new OrderedDictionary();
        assertEquals(0, dictionary.size());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertEquals(1, dictionary.size());
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testOrderedDictionaryIndexOf() {
        OrderedDictionary dictionary = new OrderedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(0, dictionary.indexOf("Pomme"));
        assertEquals(1, dictionary.indexOf("Banane"));
        assertEquals(-1, dictionary.indexOf("Livre"));
    }

    // TestFastDictionary

    @Test
    public void testFastDictionaryPut() {
        IDictionary dictionary = new FastDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertNotNull(dictionary);
    }

    @Test
    public void testFastDictionaryGet() {
        IDictionary dictionary = new FastDictionary();
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Une oeuvre écrite ou imprimée.", dictionary.get("Livre"));
    }

    @Test
    public void testFastDictionaryContainsKey() {
        IDictionary dictionary = new FastDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertTrue(dictionary.containsKey("Pomme"));
        assertFalse(dictionary.containsKey("Banane"));
    }

    @Test
    public void testFastDictionarySize() {
        IDictionary dictionary = new FastDictionary();
        assertEquals(0, dictionary.size());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertEquals(1, dictionary.size());
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testFastDictionaryIndexOf() {
        FastDictionary dictionary = new FastDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.indexOf("Pomme"));
        assertEquals(1, dictionary.indexOf("Banane"));
        assertEquals(-1, dictionary.indexOf("Livre"));
    }

    // TestSortedDictionary

    @Test
    public void testSortedDictionaryPut() {
        IDictionary dictionary = new SortedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertNotNull(dictionary);
    }

    @Test
    public void testSortedDictionaryGet() {
        IDictionary dictionary = new SortedDictionary();
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Une oeuvre écrite ou imprimée.", dictionary.get("Livre"));
    }

    @Test
    public void testSortedDictionaryContainsKey() {
        IDictionary dictionary = new SortedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertTrue(dictionary.containsKey("Pomme"));
        assertFalse(dictionary.containsKey("Banane"));
    }

    @Test
    public void testSortedDictionarySize() {
        IDictionary dictionary = new SortedDictionary();
        assertEquals(0, dictionary.size());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertEquals(1, dictionary.size());
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testSortedDictionaryIndexOf() {
        SortedDictionary dictionary = new SortedDictionary();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(2, dictionary.indexOf("Pomme"));
        assertEquals(0, dictionary.indexOf("Banane"));
        assertEquals(1, dictionary.indexOf("Livre"));
        assertEquals(-1, dictionary.indexOf("Chat"));
    }

    // TestSortedDictionaryDichoto

    @Test
    public void testSortedDictionaryDichotoPut() {
        IDictionary dictionary = new SortedDictionaryDichoto();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertNotNull(dictionary);
    }

    @Test
    public void testSortedDictionaryDichotoGet() {
        IDictionary dictionary = new SortedDictionaryDichoto();
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Une oeuvre écrite ou imprimée.", dictionary.get("Livre"));
    }

    @Test
    public void testSortedDictionaryDichotoContainsKey() {
        IDictionary dictionary = new SortedDictionaryDichoto();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertTrue(dictionary.containsKey("Pomme"));
        assertFalse(dictionary.containsKey("Banane"));
    }

    @Test
    public void testSortedDictionaryDichotoSize() {
        IDictionary dictionary = new SortedDictionaryDichoto();
        assertEquals(0, dictionary.size());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertEquals(1, dictionary.size());
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testSortedDictionaryDichotoIndexOf() {
        SortedDictionaryDichoto dictionary = new SortedDictionaryDichoto();
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals(2, dictionary.indexOf("Pomme"));
        assertEquals(0, dictionary.indexOf("Banane"));
        assertEquals(1, dictionary.indexOf("Livre"));
        assertEquals(-1, dictionary.indexOf("Chat"));
    }

    // Test du dictionnaire le plus rapide

    @Test
    public void testFastestDictionaryPutSpeed() {
        System.out.println("\nTest de vitesse entre chaque dictionnaire : ");
        // OrderedDictionary
        IDictionary orderedDictionary = new OrderedDictionary();
        long startTime = System.nanoTime();
        System.out.println("- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mOrderedDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            orderedDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        long endTime = System.nanoTime();
        long durationOrdered = (endTime - startTime) / 1000000;
        System.out.println("\tOrderedDictionary : \033[31m" + durationOrdered + " ms.\033[0m");

        // FastDictionary
        IDictionary fastDictionary = new FastDictionary();
        startTime = System.nanoTime();
        System.out.println("- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mFastDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            fastDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        endTime = System.nanoTime();
        long durationFast = (endTime - startTime) / 1000000;
        System.out.println("\tFastDictionary : \033[31m" + durationFast + " ms.\033[0m");

        // SortedDictionary
        IDictionary sortedDictionary = new SortedDictionary();
        startTime = System.nanoTime();
        System.out.println("- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            sortedDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        endTime = System.nanoTime();
        long durationSorted = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionary : \033[31m" + durationSorted + " ms.\033[0m");

        // SortedDictionaryDichoto
        IDictionary sortedDichotoDictionary = new SortedDictionaryDichoto();
        startTime = System.nanoTime();
        System.out.println("- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionaryDichoto\033[0m...");
        for (int i = 0; i < 50000; i++) {
            sortedDichotoDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        endTime = System.nanoTime();
        long durationDichoto = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionaryDichoto : \033[31m" + durationDichoto + " ms.\033[0m");

        assertTrue(durationFast <= durationDichoto);
        assertTrue(durationDichoto <= durationOrdered);
        assertTrue(durationOrdered <= durationSorted);
    }

    @Test
    public void testSortedDictionaryDichotoGetSpeed() {
        System.out.println("\nTest de vitesse get entre SortedDictionary et SortedDictionaryDichoto : ");
        IDictionary dictionary = new SortedDictionary();
        for (int i = 0; i < 50000; i++) {
            dictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        long startTime = System.nanoTime();
        System.out.println("- \033[32mRecherche\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            dictionary.get("Pomme" + i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionary : \033[31m" + duration + " ms.\033[0m");

        dictionary = new SortedDictionaryDichoto();
        for (int i = 0; i < 50000; i++) {
            dictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        startTime = System.nanoTime();
        System.out.println("- \033[32mRecherche\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionaryDichoto\033[0m...");
        for (int i = 0; i < 50000; i++) {
            dictionary.get("Pomme" + i);
        }
        endTime = System.nanoTime();
        long durationDichoto = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionaryDichoto : \033[31m" + durationDichoto + " ms.\033[0m");

        assertTrue(durationDichoto <= duration);
    }

}