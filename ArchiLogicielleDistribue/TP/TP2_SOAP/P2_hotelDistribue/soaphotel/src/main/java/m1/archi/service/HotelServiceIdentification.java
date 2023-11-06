package m1.archi.service;

import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface HotelServiceIdentification {

    @WebMethod
    ArrayList<String> getIdentifiantHotels();

    @WebMethod
    boolean addRandomHotel() throws HotelAlreadyExistsException;

    @WebMethod
    boolean deleteHotel(String identifiant) throws HotelNotFoundException;

    @WebMethod
    String afficherHotel(String identifiant) throws HotelNotFoundException;
}
