package m1.archi.repository;


import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;
import m1.archi.model.Hotel;

import java.io.IOException;
import java.util.ArrayList;

public interface HotelRepository {
    ArrayList<String> getIdentifiantHotels();

    boolean addRandomHotel() throws HotelAlreadyExistsException, IOException;

    boolean deleteHotel(String identifiant) throws HotelNotFoundException;

    Hotel getHotel(String identifiant) throws HotelNotFoundException;

    String afficherHotelSimple(String identifiant) throws HotelNotFoundException;

    String afficherHotel(String identifiant) throws HotelNotFoundException;

    String afficherReservationsHotel(String identifiant) throws HotelNotFoundException;

}
