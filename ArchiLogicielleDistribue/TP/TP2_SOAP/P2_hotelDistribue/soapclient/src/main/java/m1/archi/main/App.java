package m1.archi.main;

import m1.archi.agence.*;
import m1.archi.main.swingInterface.Interface;

import javax.xml.datatype.DatatypeConfigurationException;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    public static void main(String[] args) throws MalformedURLException, AgenceNotFoundException_Exception, InterruptedException, UserAlreadyExistsException_Exception, HotelNotFoundExceptionException, DatatypeConfigurationException, DateNonValideException_Exception, UserNotFoundException_Exception, ReservationProblemeException_Exception {
        // Recupere la liste des agences
        URL url = new URL("http://localhost:8090/agencesservice/identifiantAgences?wsdl");
        AgenceServiceIdentificationImplService agenceServiceIdentification = new AgenceServiceIdentificationImplService(url);
        AgenceServiceIdentification proxyAgences = agenceServiceIdentification.getAgenceServiceIdentificationImplPort();

        new Interface(proxyAgences);
    }
}
