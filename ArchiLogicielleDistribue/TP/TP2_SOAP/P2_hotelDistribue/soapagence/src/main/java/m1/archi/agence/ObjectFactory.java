
package m1.archi.agence;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the m1.archi.agence package. 
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

    private final static QName _UserNotFoundException_QNAME = new QName("http://service.archi.m1/", "UserNotFoundException");
    private final static QName _DateNonValideException_QNAME = new QName("http://service.archi.m1/", "DateNonValideException");
    private final static QName _ListeChoisOffresHotelCriteresResponse_QNAME = new QName("http://service.archi.m1/", "listeChoisOffresHotelCriteresResponse");
    private final static QName _ListeChoisOffresHotelCriteres_QNAME = new QName("http://service.archi.m1/", "listeChoisOffresHotelCriteres");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: m1.archi.agence
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserNotFoundException }
     * 
     */
    public UserNotFoundException createUserNotFoundException() {
        return new UserNotFoundException();
    }

    /**
     * Create an instance of {@link DateNonValideException }
     * 
     */
    public DateNonValideException createDateNonValideException() {
        return new DateNonValideException();
    }

    /**
     * Create an instance of {@link ListeChoisOffresHotelCriteres }
     * 
     */
    public ListeChoisOffresHotelCriteres createListeChoisOffresHotelCriteres() {
        return new ListeChoisOffresHotelCriteres();
    }

    /**
     * Create an instance of {@link ListeChoisOffresHotelCriteresResponse }
     * 
     */
    public ListeChoisOffresHotelCriteresResponse createListeChoisOffresHotelCriteresResponse() {
        return new ListeChoisOffresHotelCriteresResponse();
    }

    /**
     * Create an instance of {@link ArrayList }
     * 
     */
    public ArrayList createArrayList() {
        return new ArrayList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "UserNotFoundException")
    public JAXBElement<UserNotFoundException> createUserNotFoundException(UserNotFoundException value) {
        return new JAXBElement<UserNotFoundException>(_UserNotFoundException_QNAME, UserNotFoundException.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ListeChoisOffresHotelCriteresResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "listeChoisOffresHotelCriteresResponse")
    public JAXBElement<ListeChoisOffresHotelCriteresResponse> createListeChoisOffresHotelCriteresResponse(ListeChoisOffresHotelCriteresResponse value) {
        return new JAXBElement<ListeChoisOffresHotelCriteresResponse>(_ListeChoisOffresHotelCriteresResponse_QNAME, ListeChoisOffresHotelCriteresResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListeChoisOffresHotelCriteres }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "listeChoisOffresHotelCriteres")
    public JAXBElement<ListeChoisOffresHotelCriteres> createListeChoisOffresHotelCriteres(ListeChoisOffresHotelCriteres value) {
        return new JAXBElement<ListeChoisOffresHotelCriteres>(_ListeChoisOffresHotelCriteres_QNAME, ListeChoisOffresHotelCriteres.class, null, value);
    }

}
