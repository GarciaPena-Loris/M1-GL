package m1.archi.server;

import m1.archi.service.AgenceServiceIdentificationImpl;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;

public class AgenceServicePublisher {
    public static void main(String[] args) throws MalformedURLException {

        String adresse = "http://localhost:8090/agencesservice/identifiantAgences";
        Endpoint.publish(adresse, new AgenceServiceIdentificationImpl());

        System.err.println("Server ready");
        System.out.println("Adresse du service des identifiant des agences : " + adresse);
    }
}
