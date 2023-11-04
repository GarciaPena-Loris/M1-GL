package m1.archi.main;

import m1.archi.client.Hotel;
import m1.archi.client.HotelService;
import m1.archi.client.HotelServiceImplService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class client {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/hotelservice?wsdl");
            HotelServiceImplService hotelService = new HotelServiceImplService(url);
            HotelService proxy = hotelService.getHotelServiceImplPort();

            System.out.println(proxy.count());
            ArrayList<Hotel> hotels = (ArrayList<Hotel>) proxy.getHotels();
            for (Hotel hotel : hotels) {
                System.out.println(hotel.toString());
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
