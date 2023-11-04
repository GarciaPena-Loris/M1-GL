package m1.archi.server;

import m1.archi.model.Adresse;
import m1.archi.model.Chambre;
import m1.archi.model.Hotel;
import m1.archi.service.HotelServiceConsultationImpl;
import m1.archi.service.HotelServiceReservationImpl;
import m1.archi.service.RandomDonneStockage;

import javax.xml.ws.Endpoint;

public class HotelServicePublisher {

    public static void main(String[] args) {
        // Créez une instance d'Hotel
        // -- Génération d'une adresse aléatoire
        String pays = RandomDonneStockage.randomPays();
        String ville = RandomDonneStockage.randomVille(pays);
        String rue = RandomDonneStockage.randomRue();
        String numero = RandomDonneStockage.randomNumero();
        String position = RandomDonneStockage.randomPositionGPS();
        Adresse adresseHotel = new Adresse(pays, ville, rue, numero, position);

        // -- Génération d'un hôtel aléatoire
        String identifiantHotel = RandomDonneStockage.randomIdentifiantHotel();
        String nomHotel = RandomDonneStockage.randomNomHotel();
        int nombreEtoiles = RandomDonneStockage.randomNombreEtoiles();
        Hotel hotel = new Hotel(identifiantHotel, nomHotel, adresseHotel, nombreEtoiles);

        // -- Génération de chambres aléatoires
        int nombreChambres = RandomDonneStockage.randomNombreChambres();
        for (int i = 0; i < nombreChambres + 1; i++) {
            if (i != 12) {
                int numeroChambre = i + 1;
                int nombreLits = RandomDonneStockage.randomNombreLits();
                int prix = RandomDonneStockage.randomPrix(nombreEtoiles, nombreLits);
                Chambre chambre = new Chambre(numeroChambre, prix, nombreLits);
                hotel.addChambre(chambre);
            }
        }

        System.out.println("Hotel généré aléatoirement : \n" + hotel.toString());

        // Créez et publiez le service de consultation
        HotelServiceConsultationImpl consultationService = new HotelServiceConsultationImpl(hotel);
        String adresse = "http://localhost:8080/hotelservice/" + identifiantHotel + "/consultation";
        System.out.println("Adresse du service de consultation : " + adresse);
        Endpoint.publish(adresse, consultationService);

        // Créez et publiez le service de réservation
        HotelServiceReservationImpl reservationService = new HotelServiceReservationImpl(hotel);
        adresse = "http://localhost:8080/hotelservice/" + identifiantHotel + "/reservation";
        System.out.println("Adresse du service de réservation : " + adresse);
        Endpoint.publish(adresse, reservationService);

        System.out.println("Server ready");
    }
}
