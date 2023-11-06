package m1.archi.repository;

import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.HotelNotFoundException_Exception;

import java.util.ArrayList;

public interface AgenceRepository {

    ArrayList<String> getListeAgence();

    boolean deleteAgence(String identifiant) throws AgenceNotFoundException;

    String afficherAgence(String identifiant) throws AgenceNotFoundException;

    String afficherHotel(String identifiant) throws HotelNotFoundException_Exception;
}
