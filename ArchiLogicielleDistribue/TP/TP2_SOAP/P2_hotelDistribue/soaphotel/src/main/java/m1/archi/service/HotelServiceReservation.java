package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.model.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelServiceReservation {
    
    @WebMethod
    Reservation reserverChambres(Offre offre, boolean petitDejeuner, String nomClient, String prenomClient, String email, String telephone, String nomCarte, String numeroCarte, String expirationCarte, String CCVCarte) throws DateNonValideException;
}
