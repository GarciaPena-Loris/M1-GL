package m1.archi.service;


import m1.archi.exception.DateNonValideException;
import m1.archi.model.*;

import javax.jws.WebService;

@WebService(endpointInterface = "m1.archi.service.HotelServiceReservation")
public class HotelServiceReservationImpl implements HotelServiceReservation {

    private Hotel hotel;

    public HotelServiceReservationImpl() {
        this.hotel = new Hotel();
    }

    public HotelServiceReservationImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public Reservation reserverChambres(Offre offre, boolean petitDejeuner, String nomClient, String prenomClient, String email, String telephone, Carte carte)
    throws DateNonValideException {
        return hotel.reserverChambres(offre, nomClient, prenomClient, email, telephone, carte, petitDejeuner);
    }
}
