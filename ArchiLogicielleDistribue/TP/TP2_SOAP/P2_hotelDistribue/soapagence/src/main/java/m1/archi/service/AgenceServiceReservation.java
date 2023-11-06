package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.exception.ReservationProblemeException;
import m1.archi.exception.UserNotFoundException;
import m1.archi.hotel.Carte;
import m1.archi.hotel.Offre;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface AgenceServiceReservation {
    
    @WebMethod
    String reserverChambresHotel(String login, String motDePasse, Offre offre, boolean petitDejeuner, String nomClient, String prenomClient, String email, String telephone, Carte carte) throws DateNonValideException, UserNotFoundException, ReservationProblemeException;
}
