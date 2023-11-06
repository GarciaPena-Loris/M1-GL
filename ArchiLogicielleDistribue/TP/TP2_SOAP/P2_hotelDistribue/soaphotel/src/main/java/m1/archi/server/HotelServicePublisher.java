package m1.archi.server;

import m1.archi.service.HotelServiceIdentificationImpl;

import javax.xml.ws.Endpoint;

public class HotelServicePublisher {

    public static void main(String[] args) {

        String adresse = "http://localhost:8080/hotelservice/identifiantHotels";
        Endpoint.publish(adresse, new HotelServiceIdentificationImpl());

        System.err.println("Server ready");
        System.out.println("Adresse du service des identifiants : " + adresse);

    }
}
