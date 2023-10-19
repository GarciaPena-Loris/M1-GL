import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;

public class OldUtilisation {
        public static void main(String[] args) {
                // Creation d'adresse
                Adresse paris = new Adresse("France", "Paris", "Rue de la Paix", 10, "", "GPS 48.8701, 2.3316");
                Adresse bordeaux = new Adresse("France", "Bordeau", "Rue Sainte-Catherine", 15, "",
                                "GPS 44.8378, -0.5792");
                Adresse newYork = new Adresse("USA", "New York", "Broadway", 123, "Times Square",
                                "GPS 40.7589, -73.9851");
                Adresse toronto = new Adresse("Canada", "Toronto", "Yonge Street", 456, "", "GPS 43.6511, -79.3763");
                Adresse berlin = new Adresse("Germany", "Berlin", "Friedrichstrasse", 34, "Unter den Linden",
                                "GPS 52.5200, 13.4050");
                Adresse rome = new Adresse("Italy", "Rome", "Via del Corso", 77, "", "GPS 41.9028, 12.4964");
                Adresse barcelone = new Adresse("Spain", "Barcelona", "La Rambla", 88, "", "GPS 41.3797, 2.1712");

                // Creation d'hotel
                Hotel hotelParisien1 = new Hotel("Le Parisien Luxe", paris, 4);
                Hotel hotelParisien2 = new Hotel("Le Parisien Comfort", paris, 3);
                Hotel hotelParisien3 = new Hotel("Le Parisien Deluxe", paris, 4);

                Hotel hotelBordeaux1 = new Hotel("Bordeaux Luxury Hotel", bordeaux, 5);
                Hotel hotelBordeaux2 = new Hotel("Bordeaux OneNight Hotel", bordeaux, 2);

                Hotel hotelTimesSquare1 = new Hotel("Times Square Luxury Hotel", newYork, 5);
                Hotel hotelTimesSquare2 = new Hotel("Times Square Boutique Hotel", newYork, 4);

                Hotel hotelToronto1 = new Hotel("Toronto Inn & Suites", toronto, 5);

                Hotel hotelBerlin1 = new Hotel("Berlin City View Hotel", berlin, 4);
                Hotel hotelBerlin2 = new Hotel("Berlin Central Hotel", berlin, 5);
                Hotel hotelBerlin3 = new Hotel("Berlin Riverside Inn", berlin, 4);

                Hotel hotelRome1 = new Hotel("Rome Urban Retreat", rome, 4);
                Hotel hotelRome2 = new Hotel("Rome Historical Hotel", rome, 3);

                Hotel hotelBarcelona1 = new Hotel("Barcelona Beachfront Resort", barcelone, 3);
                Hotel hotelBarcelona2 = new Hotel("Barcelona City View Resort", barcelone, 4);
                Hotel hotelBarcelona3 = new Hotel("Barcelona Coastal Retreat", barcelone, 5);

                // Chambres --> A changer
                Chambre chambre1 = new Chambre("1", hotelParisien1, 100.0, 1);
                Chambre chambre2 = new Chambre("2", hotelParisien1, 120.0, 2);
                Chambre chambre3 = new Chambre("3", hotelParisien1, 150.0, 1);
                Chambre chambre4 = new Chambre("1", hotelParisien2, 80.0, 1);
                Chambre chambre5 = new Chambre("2", hotelParisien2, 100.0, 2);
                Chambre chambre6 = new Chambre("3", hotelParisien3, 90.0, 1);
                Chambre chambre7 = new Chambre("1", hotelParisien3, 110.0, 2);
                Chambre chambre8 = new Chambre("2", hotelParisien3, 470.0, 1);
                Chambre chambre9 = new Chambre("3", hotelParisien1, 390.0, 2);
                Chambre chambre10 = new Chambre("4", hotelParisien1, 1020.0, 5);
                Chambre chambre11 = new Chambre("1", hotelParisien1, 130.0, 2);
                Chambre chambre12 = new Chambre("2", hotelParisien1, 150.0, 4);
                Chambre chambre13 = new Chambre("3", hotelParisien1, 110.0, 2);
                Chambre chambre14 = new Chambre("6", hotelParisien1, 120.0, 2);
                Chambre chambre15 = new Chambre("7", hotelParisien1, 140.0, 4);
                Chambre chambre16 = new Chambre("1", hotelParisien1, 95.0, 3);
                Chambre chambre17 = new Chambre("2", hotelParisien1, 120.0, 2);
                Chambre chambre18 = new Chambre("3", hotelParisien1, 110.0, 2);
                Chambre chambre19 = new Chambre("4", hotelParisien1, 380.0, 5);
                Chambre chambre20 = new Chambre("7", hotelParisien1, 400.0, 2);
                Chambre chambre21 = new Chambre("9", hotelParisien1, 620.0, 1);

                // Ajout des chambres aux hotels
                hotelParisien1.setChambres(new ArrayList<>(Arrays.asList(chambre1, chambre2, chambre3)));
                hotelParisien2.setChambres(new ArrayList<>(Arrays.asList(chambre4, chambre5)));
                hotelParisien3.setChambres(new ArrayList<>(Arrays.asList(chambre6, chambre7)));
                hotelBordeaux1.setChambres(new ArrayList<>(Arrays.asList(chambre8, chambre9, chambre10)));
                hotelBordeaux2.setChambres(new ArrayList<>(Arrays.asList(chambre11, chambre12)));
                hotelTimesSquare1.setChambres(new ArrayList<>(Arrays.asList(chambre13, chambre14, chambre15)));
                hotelTimesSquare2.setChambres(new ArrayList<>(Arrays.asList(chambre16, chambre17, chambre18)));
                hotelToronto1.setChambres(new ArrayList<>(Arrays.asList(chambre19, chambre20, chambre21)));
                hotelBerlin1.setChambres(new ArrayList<>(Arrays.asList(chambre1, chambre2, chambre3)));
                hotelBerlin2.setChambres(new ArrayList<>(Arrays.asList(chambre4, chambre5)));
                hotelBerlin3.setChambres(new ArrayList<>(Arrays.asList(chambre6, chambre7)));
                hotelRome1.setChambres(new ArrayList<>(Arrays.asList(chambre8, chambre9, chambre10)));
                hotelRome2.setChambres(new ArrayList<>(Arrays.asList(chambre11, chambre12)));
                hotelBarcelona1.setChambres(new ArrayList<>(Arrays.asList(chambre13, chambre14, chambre15)));
                hotelBarcelona2.setChambres(new ArrayList<>(Arrays.asList(chambre16, chambre17, chambre18)));
                hotelBarcelona3.setChambres(new ArrayList<>(Arrays.asList(chambre19, chambre20, chambre21)));

                // Création d'une carte de crédit
                Carte carteFactice = new Carte("Jean Jean", "1234 5678 9012 3456", "12/24", "123");

                // Création de clients
                Client alice = new Client("Dupont", "Alice", "alice.dupont@example.com", "123-456-7890", carteFactice);
                Client bob = new Client("Martin", "Bob", "bob.martin@example.com", "234-567-8901", carteFactice);
                Client charlie = new Client("Smith", "Charlie", "charlie.smith@example.com", "345-678-9012",
                                carteFactice);
                Client david = new Client("Johnson", "David", "david.johnson@example.com", "456-789-0123",
                                carteFactice);
                Client emma = new Client("Wilson", "Emma", "emma.wilson@example.com", "567-890-1234", carteFactice);
                Client frank = new Client("Brown", "Frank", "frank.brown@example.com", "678-901-2345", carteFactice);
                Client grace = new Client("Taylor", "Grace", "grace.taylor@example.com", "789-012-3456", carteFactice);
                Client helen = new Client("Clark", "Helen", "helen.clark@example.com", "890-123-4567", carteFactice);
                Client ian = new Client("Adams", "Ian", "ian.adams@example.com", "901-234-5678", carteFactice);
                Client jane = new Client("White", "Jane", "jane.white@example.com", "012-345-6789", carteFactice);

                // Création de réservations
                Reservation reservation1 = new Reservation("524415", hotelParisien1, chambre1, alice,
                                LocalDate.of(2023, 10, 15), LocalDate.of(2023, 10, 17), 2, true);
                Reservation reservation2 = new Reservation("123456", hotelTimesSquare1, chambre13, bob,
                                LocalDate.of(2023, 10, 18), LocalDate.of(2023, 10, 20), 3, false);
                Reservation reservation3 = new Reservation("789012", hotelToronto1, chambre19, charlie,
                                LocalDate.of(2023, 10, 19), LocalDate.of(2023, 10, 21), 1, true);

        }

}
