package m1.archi.service;


import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.HotelNotFoundException_Exception;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface AgenceServiceIdentification {

    @WebMethod
    ArrayList<String> getListeAgence();

    @WebMethod
    boolean deleteAgence(String identifiant) throws AgenceNotFoundException;

    @WebMethod
    String afficherAgence(String identifiant) throws AgenceNotFoundException;

    @WebMethod
    String afficherHotel(String identifiant) throws HotelNotFoundException_Exception;
}
