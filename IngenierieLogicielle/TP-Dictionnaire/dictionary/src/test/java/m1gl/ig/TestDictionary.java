package m1gl.ig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestDictionary {

    private IDictionary dictionary;

    @Before
    public void setUp() {
        dictionary = new OrderedDictionary();
    }

    @Test
    public void testDictionaryPut() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertNotNull(dictionary);
    }

    @Test
    public void testDictionaryGet() {
        dictionary.put("Livre", "Une oeuvre écrite ou imprimée.");
        assertEquals("Une oeuvre écrite ou imprimée.", dictionary.get("Livre"));
        assertEquals(null, dictionary.get("Ordinateur"));
    }

    @Test
    public void testDictionaryFlush() {
        dictionary.flush();
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testDictionaryIsEmpty() {
        assertTrue(dictionary.isEmpty());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testDictionaryContainsKey() {
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertTrue(dictionary.containsKey("Pomme"));
        assertFalse(dictionary.containsKey("Banane"));
    }

    @Test
    public void testDictionarySize() {
        dictionary.flush();
        assertEquals(0, dictionary.size());
        dictionary.put("Pomme", "Un fruit généralement de couleur rouge.");
        assertEquals(1, dictionary.size());
        dictionary.put("Banane", "Un fruit généralement de couleur jaune.");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void testFastDictionary() {
        dictionary = new FastDictionary();
        testDictionaryPut();
        testDictionaryGet();
        testDictionaryFlush();
        testDictionaryIsEmpty();
        testDictionaryContainsKey();
        testDictionarySize();
    }

    @Test
    public void testSortedDictionary() {
        dictionary = new SortedDictionary();
        testDictionaryPut();
        testDictionaryGet();
        testDictionaryFlush();
        testDictionaryIsEmpty();
        testDictionaryContainsKey();
        testDictionarySize();
    }

    @Test
    public void testSortedDictionaryDichoto() {
        dictionary = new SortedDictionaryDichoto();
        testDictionaryPut();
        testDictionaryGet();
        testDictionaryFlush();
        testDictionaryIsEmpty();
        testDictionaryContainsKey();
        testDictionarySize();
    }

    // Test du dictionnaire le plus rapide

    // @Test
    public void testFastestDictionaryPutSpeed() {
        System.out.println("\nTest de vitesse entre chaque dictionnaire : ");
        // OrderedDictionary
        IDictionary orderedDictionary = new OrderedDictionary();
        long startTime = System.nanoTime();
        System.out.println(
                "- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mOrderedDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            orderedDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        long endTime = System.nanoTime();
        long durationOrdered = (endTime - startTime) / 1000000;
        System.out.println("\tOrderedDictionary : \033[31m" + durationOrdered + " ms.\033[0m");

        // FastDictionary
        IDictionary fastDictionary = new FastDictionary();
        startTime = System.nanoTime();
        System.out.println(
                "- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mFastDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            fastDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        endTime = System.nanoTime();
        long durationFast = (endTime - startTime) / 1000000;
        System.out.println("\tFastDictionary : \033[31m" + durationFast + " ms.\033[0m");

        // SortedDictionary
        IDictionary sortedDictionary = new SortedDictionary();
        startTime = System.nanoTime();
        System.out.println(
                "- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionary\033[0m...");
        for (int i = 0; i < 50000; i++) {
            sortedDictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        endTime = System.nanoTime();
        long durationSorted = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionary : \033[31m" + durationSorted + " ms.\033[0m");

        // SortedDictionaryDichoto
        IDictionary sortedDichotoDictionary = new SortedDictionaryDichoto();
        startTime = System.nanoTime();
        System.out.println(
                "- \033[34mAjout\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionaryDichoto\033[0m...");
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

    // @Test
    public void testSortedDictionaryDichotoGetSpeed() {
        System.out.println("\nTest de vitesse get entre SortedDictionary et SortedDictionaryDichoto : ");
        IDictionary dictionary = new SortedDictionary();
        for (int i = 0; i < 50000; i++) {
            dictionary.put("Pomme" + i, "Un fruit generalement de couleur rouge.");
        }
        long startTime = System.nanoTime();
        System.out.println(
                "- \033[32mRecherche\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionary\033[0m...");
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
        System.out.println(
                "- \033[32mRecherche\033[0m de \033[3m50000 elements\033[0m dans le \033[1mSortedDictionaryDichoto\033[0m...");
        for (int i = 0; i < 50000; i++) {
            dictionary.get("Pomme" + i);
        }
        endTime = System.nanoTime();
        long durationDichoto = (endTime - startTime) / 1000000;
        System.out.println("\tSortedDictionaryDichoto : \033[31m" + durationDichoto + " ms.\033[0m");

        assertTrue(durationDichoto <= duration);
    }

}