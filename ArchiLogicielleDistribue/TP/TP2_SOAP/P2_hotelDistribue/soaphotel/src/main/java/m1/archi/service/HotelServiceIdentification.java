package m1.archi.service;

import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;
import m1.archi.model.Hotel;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;

@WebService
public interface HotelServiceIdentification {

    @WebMethod
    ArrayList<String> getIdentifiantHotels();

    @WebMethod
    boolean addRandomHotel() throws HotelAlreadyExistsException, IOException;

    @WebMethod
    boolean deleteHotel(String identifiant) throws HotelNotFoundException;

    @WebMethod
    Hotel getHotel(String identifiant) throws HotelNotFoundException;
    @WebMethod
    String afficherHotelSimple(String identifiant) throws HotelNotFoundException;

    @WebMethod
    String afficherHotel(String identifiant) throws HotelNotFoundException;

    @WebMethod
    String afficherReservationsHotel(String identifiant) throws HotelNotFoundException;
}
