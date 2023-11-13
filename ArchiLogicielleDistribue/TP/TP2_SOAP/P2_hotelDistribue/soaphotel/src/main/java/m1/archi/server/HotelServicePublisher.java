package m1.archi.server;

import m1.archi.service.HotelServiceIdentificationImpl;

import javax.xml.ws.Endpoint;
import java.io.IOException;

public class HotelServicePublisher {

    public static void main(String[] args) throws IOException {

        String adresse = "http://localhost:8080/hotelservice/identifiantHotels";
        Endpoint.publish(adresse, new HotelServiceIdentificationImpl());

        System.err.println("Server ready");
        System.out.println("Adresse du service des identifiants : " + adresse);

    }
}
