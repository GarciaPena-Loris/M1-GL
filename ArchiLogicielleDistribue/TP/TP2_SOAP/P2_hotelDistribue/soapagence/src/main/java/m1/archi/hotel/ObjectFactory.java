
package m1.archi.hotel;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the m1.archi.hotel package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReserverChambresResponse_QNAME = new QName("http://service.archi.m1/", "reserverChambresResponse");
    private final static QName _DateNonValideException_QNAME = new QName("http://service.archi.m1/", "DateNonValideException");
    private final static QName _ReserverChambres_QNAME = new QName("http://service.archi.m1/", "reserverChambres");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: m1.archi.hotel
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DateNonValideException }
     * 
     */
    public DateNonValideException createDateNonValideException() {
        return new DateNonValideException();
    }

    /**
     * Create an instance of {@link ReserverChambresResponse }
     * 
     */
    public ReserverChambresResponse createReserverChambresResponse() {
        return new ReserverChambresResponse();
    }

    /**
     * Create an instance of {@link ReserverChambres }
     * 
     */
    public ReserverChambres createReserverChambres() {
        return new ReserverChambres();
    }

    /**
     * Create an instance of {@link Carte }
     * 
     */
    public Carte createCarte() {
        return new Carte();
    }

    /**
     * Create an instance of {@link Adresse }
     * 
     */
    public Adresse createAdresse() {
        return new Adresse();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link Hotel }
     * 
     */
    public Hotel createHotel() {
        return new Hotel();
    }

    /**
     * Create an instance of {@link Client }
     * 
     */
    public Client createClient() {
        return new Client();
    }

    /**
     * Create an instance of {@link Offre }
     * 
     */
    public Offre createOffre() {
        return new Offre();
    }

    /**
     * Create an instance of {@link Chambre }
     * 
     */
    public Chambre createChambre() {
        return new Chambre();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserverChambresResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "reserverChambresResponse")
    public JAXBElement<ReserverChambresResponse> createReserverChambresResponse(ReserverChambresResponse value) {
        return new JAXBElement<ReserverChambresResponse>(_ReserverChambresResponse_QNAME, ReserverChambresResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateNonValideException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "DateNonValideException")
    public JAXBElement<DateNonValideException> createDateNonValideException(DateNonValideException value) {
        return new JAXBElement<DateNonValideException>(_DateNonValideException_QNAME, DateNonValideException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserverChambres }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "reserverChambres")
    public JAXBElement<ReserverChambres> createReserverChambres(ReserverChambres value) {
        return new JAXBElement<ReserverChambres>(_ReserverChambres_QNAME, ReserverChambres.class, null, value);
    }

}
