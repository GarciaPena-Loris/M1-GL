package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.model.Offre;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

@WebService
public interface HotelServiceConsultation {

    @WebMethod
    ArrayList<Offre> getChambreDisponibleCriteres(String ville, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne)
            throws DateNonValideException;

}
