package m1.archi.service;

import m1.archi.exception.HotelAlreadyExistsException;
import m1.archi.exception.HotelNotFoundException;
import m1.archi.model.Hotel;
import m1.archi.repository.HotelRepository;
import m1.archi.repository.HotelRepositoryImpl;

import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;

@WebService(endpointInterface = "m1.archi.service.HotelServiceIdentification")
public class HotelServiceIdentificationImpl implements HotelServiceIdentification {
    private HotelRepository hotelRepository = new HotelRepositoryImpl();

    public HotelServiceIdentificationImpl() throws IOException {
    }

    @Override
    public ArrayList<String> getIdentifiantHotels() {
        return this.hotelRepository.getIdentifiantHotels();
    }
    
    @Override
    public boolean addRandomHotel() throws HotelAlreadyExistsException, IOException {
        return this.hotelRepository.addRandomHotel();
    }
    
    @Override
    public boolean deleteHotel(String identifiant) throws HotelNotFoundException {
        return this.hotelRepository.deleteHotel(identifiant);
    }

    @Override
    public Hotel getHotel(String identifiant) throws HotelNotFoundException {
        return this.hotelRepository.getHotel(identifiant);
    }

    @Override
    public String afficherHotelSimple(String identifiant) throws HotelNotFoundException {
        return this.hotelRepository.afficherHotelSimple(identifiant);
    }

    @Override
    public String afficherHotel(String identifiant) throws HotelNotFoundException {
        return this.hotelRepository.afficherHotel(identifiant);
    }

    @Override
    public String afficherReservationsHotel(String identifiant) throws HotelNotFoundException {
        return this.hotelRepository.afficherReservationsHotel(identifiant);
    }
}
