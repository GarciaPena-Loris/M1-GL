package m1.archi.service;

import m1.archi.exception.AgenceNotFoundException;
import m1.archi.hotel.Hotel;
import m1.archi.hotel.HotelNotFoundException_Exception;
import m1.archi.model.Agence;
import m1.archi.repository.AgenceRepository;
import m1.archi.repository.AgenceRepositoryImpl;

import javax.jws.WebService;
import java.net.MalformedURLException;
import java.util.ArrayList;
@WebService(endpointInterface = "m1.archi.service.AgenceServiceIdentification")
public class AgenceServiceIdentificationImpl implements AgenceServiceIdentification {
    private AgenceRepository agenceRepository = new AgenceRepositoryImpl();

    public AgenceServiceIdentificationImpl() throws MalformedURLException {
    }

    @Override
    public ArrayList<String> getListeAgence() {
        return this.agenceRepository.getListeAgence();
    }

    
    @Override
    public boolean deleteAgence(String identifiant) throws AgenceNotFoundException {
        return this.agenceRepository.deleteAgence(identifiant);
    }

    @Override
    public Agence getAgence(String identifiant) throws AgenceNotFoundException {
        return this.agenceRepository.getAgence(identifiant);
    }

    @Override
    public String afficherAgence(String identifiant) throws AgenceNotFoundException {
        return this.agenceRepository.afficherAgence(identifiant);
    }

    @Override
    public ArrayList<String> getListeIdentifiantHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException {
        return this.agenceRepository.getListeIdentifiantHotelsPartenaire(identifiantAgence);
    }

    @Override
    public ArrayList<Hotel> getListeHotelsPartenaire(String identifiantAgence) throws AgenceNotFoundException {
        return this.agenceRepository.getListeHotelsPartenaire(identifiantAgence);
    }

    @Override
    public Hotel getHotel(String identifiant) throws HotelNotFoundException_Exception {
        return this.agenceRepository.getHotel(identifiant);
    }

    @Override
    public String afficherHotelSimple(String identifiant) throws HotelNotFoundException_Exception {
        return this.agenceRepository.afficherHotelSimple(identifiant);
    }

    @Override
    public String afficherHotel(String identifiant) throws HotelNotFoundException_Exception {
        return this.agenceRepository.afficherHotel(identifiant);
    }

    @Override
    public String afficherReservationsHotel(String identifiantHotel) throws HotelNotFoundException_Exception {
        return this.agenceRepository.afficherReservationsHotel(identifiantHotel);
    }
}
