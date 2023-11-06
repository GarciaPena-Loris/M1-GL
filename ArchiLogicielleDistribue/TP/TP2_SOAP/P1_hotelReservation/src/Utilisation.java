import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Utilisation {
    private ArrayList<Hotel> hotels;

    public Utilisation() {
        this.hotels = new ArrayList<Hotel>();
    }

    public Utilisation ajouterHotel(Hotel hotel) {
        this.hotels.add(hotel);
        return this;
    }

    public HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> rechercherChambre(String ville, LocalDate dateArrivee,
            LocalDate dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne) {
        HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> resultatRecherche = new HashMap<>();
        for (Hotel hotel : hotels) {
            HashMap<Integer, ArrayList<Chambre>> chambreDisponibleHotel = hotel.getChambreDisponibleCriteres(ville,
                    dateArrivee, dateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
            if (chambreDisponibleHotel.size() > 0) {
                resultatRecherche.put(hotel, chambreDisponibleHotel);
            }
        }
        return resultatRecherche;
    }

    public static void main(String[] args) {
        // Creation d'adresse
        Adresse paris = new Adresse("France", "Paris", "Rue de la Paix", 10, "", "GPS 48.8701, 2.3316");
        Adresse bordeaux = new Adresse("France", "Bordeau", "Rue Sainte-Catherine", 15, "",
                "GPS 44.8378, -0.5792");
        Adresse newYork = new Adresse("USA", "New York", "Broadway", 123, "Times Square",
                "GPS 40.7589, -73.9851");
        Adresse toronto = new Adresse("Canada", "Toronto", "Yonge Street", 456, "", "GPS 43.6511, -79.3763");

        // Creation d'hotel
        Hotel hotelParisien1 = new Hotel("Le Parisien Luxe", paris, 4);
        Hotel hotelParisien2 = new Hotel("Le Parisien Comfort", paris, 3);
        Hotel hotelParisien3 = new Hotel("Le Parisien Deluxe", paris, 4);

        Hotel hotelBordeaux1 = new Hotel("Bordeaux Luxury Hotel", bordeaux, 5);
        Hotel hotelBordeaux2 = new Hotel("Bordeaux OneNight Hotel", bordeaux, 2);

        Hotel hotelTimesSquare1 = new Hotel("Times Square Luxury Hotel", newYork, 5);
        Hotel hotelTimesSquare2 = new Hotel("Times Square Boutique Hotel", newYork, 4);

        Hotel hotelToronto1 = new Hotel("Toronto Inn & Suites", toronto, 5);

        // Chambres
        Chambre chambre1 = new Chambre("1", hotelParisien1, 100.0, 1);
        Chambre chambre2 = new Chambre("2", hotelParisien1, 120.0, 2);
        Chambre chambre3 = new Chambre("3", hotelParisien1, 150.0, 1);
        Chambre chambre4 = new Chambre("4", hotelParisien2, 80.0, 1);
        Chambre chambre5 = new Chambre("5", hotelParisien2, 100.0, 2);
        Chambre chambre6 = new Chambre("6", hotelParisien3, 90.0, 1);
        Chambre chambre7 = new Chambre("7", hotelParisien3, 110.0, 2);
        Chambre chambre8 = new Chambre("8", hotelBordeaux1, 470.0, 1);
        Chambre chambre9 = new Chambre("9", hotelBordeaux1, 390.0, 2);
        Chambre chambre10 = new Chambre("10", hotelBordeaux1, 1020.0, 5);
        Chambre chambre11 = new Chambre("11", hotelBordeaux2, 130.0, 2);
        Chambre chambre12 = new Chambre("12", hotelBordeaux2, 150.0, 4);
        Chambre chambre13 = new Chambre("13", hotelTimesSquare1, 110.0, 2);
        Chambre chambre14 = new Chambre("14", hotelTimesSquare1, 120.0, 2);
        Chambre chambre15 = new Chambre("15", hotelTimesSquare1, 140.0, 4);
        Chambre chambre16 = new Chambre("16", hotelTimesSquare2, 95.0, 3);
        Chambre chambre17 = new Chambre("17", hotelTimesSquare2, 120.0, 2);
        Chambre chambre18 = new Chambre("18", hotelTimesSquare2, 110.0, 2);
        Chambre chambre19 = new Chambre("19", hotelToronto1, 380.0, 5);
        Chambre chambre20 = new Chambre("20", hotelToronto1, 400.0, 2);
        Chambre chambre21 = new Chambre("21", hotelToronto1, 620.0, 1);
        Chambre chambre22 = new Chambre("22", hotelToronto1, 2500.0, 1);

        // Ajout des chambres aux hotels
        hotelParisien1.setChambres(new ArrayList<>(Arrays.asList(chambre1, chambre2, chambre3)));
        hotelParisien2.setChambres(new ArrayList<>(Arrays.asList(chambre4, chambre5)));
        hotelParisien3.setChambres(new ArrayList<>(Arrays.asList(chambre6, chambre7)));
        hotelBordeaux1.setChambres(new ArrayList<>(Arrays.asList(chambre8, chambre9, chambre10)));
        hotelBordeaux2.setChambres(new ArrayList<>(Arrays.asList(chambre11, chambre12)));
        hotelTimesSquare1.setChambres(new ArrayList<>(Arrays.asList(chambre13, chambre14, chambre15)));
        hotelTimesSquare2.setChambres(new ArrayList<>(Arrays.asList(chambre16, chambre17, chambre18)));
        hotelToronto1.setChambres(new ArrayList<>(Arrays.asList(chambre19, chambre20, chambre21, chambre22)));

        // Création d'une carte de crédit
        Carte carteFactice = new Carte("Jean Jean", "1234 5678 9012 3456", "12/24", "123");

        // Création de clients
        Client alice = new Client("Dupont", "Alice", "alice.dupont@example.com", "123-456-7890", carteFactice);
        Client bob = new Client("Martin", "Bob", "bob.martin@example.com", "234-567-8901", carteFactice);
        Client charlie = new Client("Smith", "Charlie", "charlie.smith@example.com", "345-678-9012",
                carteFactice);

        // Création de réservations
        Reservation reservation1 = new Reservation("524415", hotelParisien1,
                new ArrayList<>(Arrays.asList(chambre1, chambre3)), alice,
                LocalDate.of(2023, 10, 15), LocalDate.of(2023, 10, 17), 2, true);
        hotelParisien1.addReservation(reservation1);
        Reservation reservation2 = new Reservation("123456", hotelTimesSquare1,
                new ArrayList<>(Arrays.asList(chambre13)), bob,
                LocalDate.of(2023, 10, 18), LocalDate.of(2023, 10, 20), 2, false);
        hotelTimesSquare1.addReservation(reservation2);
        Reservation reservation3 = new Reservation("789012", hotelToronto1,
                new ArrayList<>(Arrays.asList(chambre19, chambre20, chambre21)), charlie,
                LocalDate.of(2023, 10, 19), LocalDate.of(2023, 10, 21), 8, true);
        hotelToronto1.addReservation(reservation3);

        Utilisation utilisation = new Utilisation();
        utilisation.ajouterHotel(hotelParisien1).ajouterHotel(hotelParisien2).ajouterHotel(hotelParisien3)
                .ajouterHotel(hotelBordeaux1).ajouterHotel(hotelBordeaux2).ajouterHotel(hotelTimesSquare1)
                .ajouterHotel(hotelTimesSquare2).ajouterHotel(hotelToronto1);

        // Test de la fonction rechercherChambre
        System.out.println("\n----- Recherche de chambre ----");
        HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> resultatRecherche = utilisation.rechercherChambre("Paris",
                LocalDate.of(2023, 10, 15), LocalDate.of(2023, 10, 21), 0, 1000, 4, 2);
        resultatRecherche.forEach((hotel, chambres) -> {
            System.out.println("Hotel: " + hotel);
            System.out.println("Chambre disponible : ");
            chambres.forEach((prix, chambresDisponibles) -> {
                System.out.println("---");
                System.out.println("  Pour le prix de " + prix + " euros : ");
                chambresDisponibles.forEach(chambre -> {
                    System.out.println("\t-" + chambre);
                });
            });
        });

        // Test de la fonction reserverChambre sans date prise
        System.out.println("\n----- Date disponible ----");
        HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> resultatRecherche3 = utilisation.rechercherChambre(
                "New York",
                LocalDate.of(2023, 11, 18),
                LocalDate.of(2023, 11, 25), 100, 5000, 5, 2);
        resultatRecherche3.forEach((hotel, chambres) -> {
            System.out.println("Hotel: " + hotel);
            System.out.println("Chambre disponible : ");
            chambres.forEach((prix, chambresDisponibles) -> {
                System.out.println("---");
                System.out.println("  Pour le prix de " + prix + " euros : ");
                chambresDisponibles.forEach(chambre -> {
                    System.out.println("\t-" + chambre);
                });
            });
        });

        // Test de la fonction reserverChambre avec date prise
        System.out.println("\n----- Date occupée ----");
        HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> resultatRecherche2 = utilisation.rechercherChambre(
                "New York",
                LocalDate.of(2023, 10, 18),
                LocalDate.of(2023, 10, 22), 100, 5000, 5, 2);
        resultatRecherche2.forEach((hotel, chambres) -> {
            System.out.println("Hotel: " + hotel);
            System.out.println("Chambre disponible : ");
            chambres.forEach((prix, chambresDisponibles) -> {
                System.out.println("---");
                System.out.println("  Pour le prix de " + prix + " euros : ");
                chambresDisponibles.forEach(chambre -> {
                    System.out.println("\t-" + chambre);
                });
            });
        });

        // Test de la fonction reserverChambre avec combinaison de chambre
        System.out.println("\n----- Combinaison de chambre ----");
        HashMap<Hotel, HashMap<Integer, ArrayList<Chambre>>> resultatRecherche4 = utilisation.rechercherChambre(
                "Toronto",
                LocalDate.of(2023, 10, 2),
                LocalDate.of(2023, 10, 8), 100, 5000, 5, 7);
        resultatRecherche4.forEach((hotel, chambres) -> {
            System.out.println("Hotel: " + hotel);
            System.out.println("Chambre disponible : ");
            chambres.forEach((prix, chambresDisponibles) -> {
                System.out.println("---");
                System.out.println("  Pour le prix de " + prix + " euros : ");
                chambresDisponibles.forEach(chambre -> {
                    System.out.println("\t-" + chambre);
                });
            });
        });
    }

}
