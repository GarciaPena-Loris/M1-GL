package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.model.Hotel;
import m1.archi.model.Offre;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

@WebService(endpointInterface = "m1.archi.service.HotelServiceConsultation")
public class HotelServiceConsultationImpl implements HotelServiceConsultation {

    private Hotel hotel;

    public HotelServiceConsultationImpl() {
        this.hotel = new Hotel();
    }

    public HotelServiceConsultationImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public ArrayList<Offre> getChambreDisponibleCriteres(String ville, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne)
            throws DateNonValideException {
        return hotel.getChambreDisponibleCriteres(ville, dateArrivee, dateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
    }

}
