package m1.archi.service;

import m1.archi.exception.DateNonValideException;
import m1.archi.exception.UserNotFoundException;
import m1.archi.hotel.Offre;
import m1.archi.model.Agence;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

@WebService(endpointInterface = "m1.archi.service.AgenceServiceConsultation")
public class AgenceServiceConsultationImpl implements AgenceServiceConsultation {

    private Agence agence;

    public AgenceServiceConsultationImpl() {
        this.agence = new Agence();
    }

    public AgenceServiceConsultationImpl(Agence agence) {
        this.agence = agence;
    }

    @Override
    public ArrayList<ArrayList<Offre>> listeChoisOffresHotelCriteres(String login, String motDePasse, String ville, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne)
            throws DateNonValideException, UserNotFoundException {
        return agence.listeChoisOffresHotelCriteres(login, motDePasse, ville, dateArrivee, dateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
    }

}
