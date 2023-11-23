package m1.archi.restagence.data;

import java.util.*;

public class RandomDonneesAgence {
    private static final String[] listeNomAgenceSansDoublons = {
            "Agence du Voyage Étoilé", "Agence Horizon Sans Limite", "Aventure Sans Frontières", "Agence du Monde Magique",
            "Explorateurs Intrépides", "Agence Terres Lointaines", "Voyages en Émeraude", "Agence des Rêves Exotiques",
            "Oasis de Découvertes", "Agence de l'Aventure Cachée", "Voyages au Paradis Terrestre", "Agence des Merveilles Naturelles",
            "Découvertes au Clair de Lune", "Agence de l'Exploration Ensoleillée", "Voyages en Terres Inconnues",
            "Agence de l'Aventure Enchantée", "Légendes de Voyages", "Agence des Horizons Infinis", "Explorations Mystérieuses",
            "Agence du Monde Insolite", "Évasions Secrètes", "Agence des Voyages Surnaturels", "Rêves d'Ailleurs",
            "Agence des Voyages Fantastiques", "Odyssées Inoubliables", "Agence des Rêves Inexplorés",
            "Aventures Magiques", "Voyages de l'Imagination", "Agence des Trésors Cachés", "Lumières de l'Aventure",
            "Voyages au Royaume des Merveilles", "Agence de l'Exploration Insoupçonnée", "Rêves d'Évasion",
            "Agence de l'Aventure Infinie", "Mystères du Monde", "Voyages Vers l'Inconnu", "Agence des Expériences Uniques"
    };

    private static <T> T getElementListeAleatoire(T[] liste) {
        return liste[new Random().nextInt(liste.length)];
    }

    public static String randomNomAgence() {
        return getElementListeAleatoire(listeNomAgenceSansDoublons);
    }


    public static int randomNombreHotelPartenaire(int nombreHotel) {
        return new Random().nextInt(nombreHotel) + 1; // +1 pour éviter le 0 et avoir un nombre entre 1 et nombreHotel
    }

    public static List<Long> randomHotelPartenaire(List<Long> listeHotel, int nombreHotel) {
        // Recupere aleatoirement 'nombreHotelPartenaire' hotels de la liste 'listeHotel'
        List<Long> listeHotelPartenaire = new ArrayList<>();
        for (int i = 0; i < randomNombreHotelPartenaire(nombreHotel); i++) {
            Long identifiantHotel = getElementListeAleatoire(listeHotel.toArray(new Long[0]));
            if (!listeHotelPartenaire.contains(identifiantHotel)) {
                listeHotelPartenaire.add(identifiantHotel);
            }
        }
        return listeHotelPartenaire;
    }

    public static int randomReduction() {
        return new Random().nextInt(30) + 5;
    }

    public static Map<Long, Integer> randomReductionHotelPartenaire(List<Long> listeHotel, int nombreHotelPartenaire) {
        Map<Long, Integer> mapReductionHotelPartenaire = new HashMap<>();
        for (Long identifiantHotel : randomHotelPartenaire(listeHotel, nombreHotelPartenaire)) {
            mapReductionHotelPartenaire.put(identifiantHotel, randomReduction());
        }
        return mapReductionHotelPartenaire;
    }

}
