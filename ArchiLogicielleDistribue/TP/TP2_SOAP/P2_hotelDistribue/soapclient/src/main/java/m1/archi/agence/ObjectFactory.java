
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

    private final static QName _UserAlreadyExistsException_QNAME = new QName("http://service.archi.m1/", "UserAlreadyExistsException");
    private final static QName _Inscription_QNAME = new QName("http://service.archi.m1/", "inscription");
    private final static QName _InscriptionResponse_QNAME = new QName("http://service.archi.m1/", "inscriptionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: m1.archi.agence
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Inscription }
     * 
     */
    public Inscription createInscription() {
        return new Inscription();
    }

    /**
     * Create an instance of {@link UserAlreadyExistsException }
     * 
     */
    public UserAlreadyExistsException createUserAlreadyExistsException() {
        return new UserAlreadyExistsException();
    }

    /**
     * Create an instance of {@link InscriptionResponse }
     * 
     */
    public InscriptionResponse createInscriptionResponse() {
        return new InscriptionResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "UserAlreadyExistsException")
    public JAXBElement<UserAlreadyExistsException> createUserAlreadyExistsException(UserAlreadyExistsException value) {
        return new JAXBElement<UserAlreadyExistsException>(_UserAlreadyExistsException_QNAME, UserAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Inscription }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "inscription")
    public JAXBElement<Inscription> createInscription(Inscription value) {
        return new JAXBElement<Inscription>(_Inscription_QNAME, Inscription.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InscriptionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "inscriptionResponse")
    public JAXBElement<InscriptionResponse> createInscriptionResponse(InscriptionResponse value) {
        return new JAXBElement<InscriptionResponse>(_InscriptionResponse_QNAME, InscriptionResponse.class, null, value);
    }

}
