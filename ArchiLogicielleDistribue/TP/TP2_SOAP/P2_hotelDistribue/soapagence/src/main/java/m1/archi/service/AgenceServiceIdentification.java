package m1.archi.service;


import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.Hotel;
import m1.archi.hotel.HotelNotFoundException_Exception;
import m1.archi.model.Agence;

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
    Agence getAgence(String identifiant) throws AgenceNotFoundException;

    @WebMethod
    String afficherAgence(String identifiant) throws AgenceNotFoundException;

    @WebMethod
    ArrayList<String> getListeIdentifiantHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException;

    @WebMethod
    ArrayList<Hotel> getListeHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException;

    @WebMethod
    Hotel getHotel(String identifiant) throws HotelNotFoundException_Exception;

    @WebMethod
    String afficherHotelSimple(String identifiant) throws HotelNotFoundException_Exception;

    @WebMethod
    String afficherHotel(String identifiant) throws HotelNotFoundException_Exception;

    @WebMethod
    String afficherReservationsHotel(String identifiant) throws HotelNotFoundException_Exception;
}
