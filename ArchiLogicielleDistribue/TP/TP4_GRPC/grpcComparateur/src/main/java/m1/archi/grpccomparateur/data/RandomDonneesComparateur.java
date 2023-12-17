package m1.archi.grpccomparateur.data;

import m1.archi.grpccomparateur.models.Comparateur;

import java.util.ArrayList;
import java.util.Random;

public class RandomDonneesComparateur {
    private static final String[] listeNomAgenceSansDoublons = {
            "Trivago", "Booking.com", "Expedia", "Kayak", "Hotels.com", "TripAdvisor", "Airbnb"
    };

    private static <T> T getElementListeAleatoire(T[] liste) {
        return liste[new Random().nextInt(liste.length)];
    }

    public static String randomNomComparateur() {
        return getElementListeAleatoire(listeNomAgenceSansDoublons);
    }

    public static Comparateur randomComparateur() {
        return new Comparateur(null, randomNomComparateur(), new ArrayList<>());
    }

}
