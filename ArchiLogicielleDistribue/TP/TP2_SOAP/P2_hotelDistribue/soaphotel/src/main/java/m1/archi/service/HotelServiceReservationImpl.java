package m1.archi.service;


import m1.archi.exception.DateNonValideException;
import m1.archi.model.Chambre;
import m1.archi.model.Client;
import m1.archi.model.Hotel;
import m1.archi.model.Reservation;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;

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
    public Reservation reserverChambres(Hotel hotel, ArrayList<Chambre> chambres, Client clientPrincipal, Date dateArrivee, Date dateDepart, int nombrePersonne, boolean petitDejeuner)
    throws DateNonValideException {
        return hotel.reserverChambres(chambres, clientPrincipal, dateArrivee, dateDepart, nombrePersonne, petitDejeuner);
    }
}
