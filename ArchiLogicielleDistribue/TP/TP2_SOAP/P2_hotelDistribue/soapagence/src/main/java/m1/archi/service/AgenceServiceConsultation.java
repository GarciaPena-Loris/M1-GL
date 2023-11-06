package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.exception.UserNotFoundException;
import m1.archi.hotel.Offre;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

@WebService
public interface AgenceServiceConsultation {

    @WebMethod
    ArrayList<ArrayList<Offre>> listeChoisOffresHotelCriteres(String login, String motDePasse, String ville, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne)
            throws DateNonValideException, UserNotFoundException;

}
