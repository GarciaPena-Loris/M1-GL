package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.model.Chambre;
import m1.archi.model.Client;
import m1.archi.model.Hotel;
import m1.archi.model.Reservation;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

@WebService
public interface HotelServiceReservation {
    
    @WebMethod
    Reservation reserverChambres(Hotel hotel, ArrayList<Chambre> chambres, Client clientPrincipal, Date dateArrivee, Date dateDepart, int nombrePersonne, boolean petitDejeuner) throws DateNonValideException;
}
