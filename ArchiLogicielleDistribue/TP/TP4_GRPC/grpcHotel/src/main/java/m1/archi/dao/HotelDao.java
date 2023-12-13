package m1.archi.dao;

import m1.archi.domaines.Hotel;

public class HotelDao extends GenericDao<Hotel> {
    public HotelDao() {
        super(Hotel.class);
    }
}