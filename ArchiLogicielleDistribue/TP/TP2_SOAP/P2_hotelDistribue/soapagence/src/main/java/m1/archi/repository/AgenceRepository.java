package m1.archi.repository;

import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.Hotel;
import m1.archi.hotel.HotelNotFoundException_Exception;
import m1.archi.model.Agence;

import java.util.ArrayList;

public interface AgenceRepository {

    ArrayList<String> getListeAgence();

    boolean deleteAgence(String identifiant) throws AgenceNotFoundException;

    Agence getAgence(String identifiant) throws AgenceNotFoundException;

    String afficherAgence(String identifiant) throws AgenceNotFoundException;

    ArrayList<String> getListeIdentifiantHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException;

    int getReduction(String identifiantAgence, String identifiantHotel) throws AgenceNotFoundException;

    ArrayList<Hotel> getListeHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException;

    Hotel getHotel(String identifiant) throws HotelNotFoundException_Exception;

    String afficherHotelSimple(String identifiant) throws HotelNotFoundException_Exception;

    String afficherHotel(String identifiant) throws HotelNotFoundException_Exception;

    String afficherReservationsHotel(String identifiantHotel) throws HotelNotFoundException_Exception;
}
