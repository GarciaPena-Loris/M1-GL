package m1.archi.dao;

import m1.archi.domaines.Reservation;

public class ReservationDao extends GenericDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }
}