package m1.archi.repository;


import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;

import java.util.ArrayList;

public interface HotelRepository {
    ArrayList<String> getIdentifiantHotels();

    boolean addRandomHotel() throws HotelAlreadyExistsException;

    boolean deleteHotel(String identifiant) throws HotelNotFoundException;

    String afficherHotel(String identifiant) throws HotelNotFoundException;

}
