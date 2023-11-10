
package m1.archi.hotel;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "HotelServiceIdentification", targetNamespace = "http://service.archi.m1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HotelServiceIdentification {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws HotelNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "afficherReservationsHotel", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherReservationsHotel")
    @ResponseWrapper(localName = "afficherReservationsHotelResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherReservationsHotelResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/afficherReservationsHotelRequest", output = "http://service.archi.m1/HotelServiceIdentification/afficherReservationsHotelResponse", fault = {
        @FaultAction(className = HotelNotFoundException_Exception.class, value = "http://service.archi.m1/HotelServiceIdentification/afficherReservationsHotel/Fault/HotelNotFoundException")
    })
    public String afficherReservationsHotel(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws HotelNotFoundException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIdentifiantHotels", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.GetIdentifiantHotels")
    @ResponseWrapper(localName = "getIdentifiantHotelsResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.GetIdentifiantHotelsResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/getIdentifiantHotelsRequest", output = "http://service.archi.m1/HotelServiceIdentification/getIdentifiantHotelsResponse")
    public List<String> getIdentifiantHotels();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws HotelNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "afficherHotelSimple", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherHotelSimple")
    @ResponseWrapper(localName = "afficherHotelSimpleResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherHotelSimpleResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/afficherHotelSimpleRequest", output = "http://service.archi.m1/HotelServiceIdentification/afficherHotelSimpleResponse", fault = {
        @FaultAction(className = HotelNotFoundException_Exception.class, value = "http://service.archi.m1/HotelServiceIdentification/afficherHotelSimple/Fault/HotelNotFoundException")
    })
    public String afficherHotelSimple(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws HotelNotFoundException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws HotelNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "afficherHotel", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherHotel")
    @ResponseWrapper(localName = "afficherHotelResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AfficherHotelResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/afficherHotelRequest", output = "http://service.archi.m1/HotelServiceIdentification/afficherHotelResponse", fault = {
        @FaultAction(className = HotelNotFoundException_Exception.class, value = "http://service.archi.m1/HotelServiceIdentification/afficherHotel/Fault/HotelNotFoundException")
    })
    public String afficherHotel(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws HotelNotFoundException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     * @throws HotelNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteHotel", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.DeleteHotel")
    @ResponseWrapper(localName = "deleteHotelResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.DeleteHotelResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/deleteHotelRequest", output = "http://service.archi.m1/HotelServiceIdentification/deleteHotelResponse", fault = {
        @FaultAction(className = HotelNotFoundException_Exception.class, value = "http://service.archi.m1/HotelServiceIdentification/deleteHotel/Fault/HotelNotFoundException")
    })
    public boolean deleteHotel(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws HotelNotFoundException_Exception
    ;

    /**
     * 
     * @return
     *     returns boolean
     * @throws HotelAlreadyExistsException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addRandomHotel", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AddRandomHotel")
    @ResponseWrapper(localName = "addRandomHotelResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.hotel.AddRandomHotelResponse")
    @Action(input = "http://service.archi.m1/HotelServiceIdentification/addRandomHotelRequest", output = "http://service.archi.m1/HotelServiceIdentification/addRandomHotelResponse", fault = {
        @FaultAction(className = HotelAlreadyExistsException_Exception.class, value = "http://service.archi.m1/HotelServiceIdentification/addRandomHotel/Fault/HotelAlreadyExistsException")
    })
    public boolean addRandomHotel()
        throws HotelAlreadyExistsException_Exception
    ;

}
