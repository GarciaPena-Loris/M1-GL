
package m1.archi.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the m1.archi.client package. 
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

    private final static QName _GetHotelResponse_QNAME = new QName("http://service.archi.m1/", "getHotelResponse");
    private final static QName _GetHotelsResponse_QNAME = new QName("http://service.archi.m1/", "getHotelsResponse");
    private final static QName _DeleteHotel_QNAME = new QName("http://service.archi.m1/", "deleteHotel");
    private final static QName _GetHotel_QNAME = new QName("http://service.archi.m1/", "getHotel");
    private final static QName _GetHotels_QNAME = new QName("http://service.archi.m1/", "getHotels");
    private final static QName _HotelNotFoundException_QNAME = new QName("http://service.archi.m1/", "HotelNotFoundException");
    private final static QName _HotelAlreadyExistsException_QNAME = new QName("http://service.archi.m1/", "HotelAlreadyExistsException");
    private final static QName _AddHotelResponse_QNAME = new QName("http://service.archi.m1/", "addHotelResponse");
    private final static QName _AddHotel_QNAME = new QName("http://service.archi.m1/", "addHotel");
    private final static QName _DeleteHotelResponse_QNAME = new QName("http://service.archi.m1/", "deleteHotelResponse");
    private final static QName _UpdateHotel_QNAME = new QName("http://service.archi.m1/", "updateHotel");
    private final static QName _UpdateHotelResponse_QNAME = new QName("http://service.archi.m1/", "updateHotelResponse");
    private final static QName _CountResponse_QNAME = new QName("http://service.archi.m1/", "countResponse");
    private final static QName _Count_QNAME = new QName("http://service.archi.m1/", "count");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: m1.archi.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteHotelResponse }
     * 
     */
    public DeleteHotelResponse createDeleteHotelResponse() {
        return new DeleteHotelResponse();
    }

    /**
     * Create an instance of {@link UpdateHotel }
     * 
     */
    public UpdateHotel createUpdateHotel() {
        return new UpdateHotel();
    }

    /**
     * Create an instance of {@link AddHotel }
     * 
     */
    public AddHotel createAddHotel() {
        return new AddHotel();
    }

    /**
     * Create an instance of {@link UpdateHotelResponse }
     * 
     */
    public UpdateHotelResponse createUpdateHotelResponse() {
        return new UpdateHotelResponse();
    }

    /**
     * Create an instance of {@link Count }
     * 
     */
    public Count createCount() {
        return new Count();
    }

    /**
     * Create an instance of {@link CountResponse }
     * 
     */
    public CountResponse createCountResponse() {
        return new CountResponse();
    }

    /**
     * Create an instance of {@link GetHotelsResponse }
     * 
     */
    public GetHotelsResponse createGetHotelsResponse() {
        return new GetHotelsResponse();
    }

    /**
     * Create an instance of {@link GetHotelResponse }
     * 
     */
    public GetHotelResponse createGetHotelResponse() {
        return new GetHotelResponse();
    }

    /**
     * Create an instance of {@link GetHotels }
     * 
     */
    public GetHotels createGetHotels() {
        return new GetHotels();
    }

    /**
     * Create an instance of {@link DeleteHotel }
     * 
     */
    public DeleteHotel createDeleteHotel() {
        return new DeleteHotel();
    }

    /**
     * Create an instance of {@link GetHotel }
     * 
     */
    public GetHotel createGetHotel() {
        return new GetHotel();
    }

    /**
     * Create an instance of {@link HotelAlreadyExistsException }
     * 
     */
    public HotelAlreadyExistsException createHotelAlreadyExistsException() {
        return new HotelAlreadyExistsException();
    }

    /**
     * Create an instance of {@link AddHotelResponse }
     * 
     */
    public AddHotelResponse createAddHotelResponse() {
        return new AddHotelResponse();
    }

    /**
     * Create an instance of {@link HotelNotFoundException }
     * 
     */
    public HotelNotFoundException createHotelNotFoundException() {
        return new HotelNotFoundException();
    }

    /**
     * Create an instance of {@link Carte }
     * 
     */
    public Carte createCarte() {
        return new Carte();
    }

    /**
     * Create an instance of {@link Chambre }
     * 
     */
    public Chambre createChambre() {
        return new Chambre();
    }

    /**
     * Create an instance of {@link Adresse }
     * 
     */
    public Adresse createAdresse() {
        return new Adresse();
    }

    /**
     * Create an instance of {@link Hotel }
     * 
     */
    public Hotel createHotel() {
        return new Hotel();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link Client }
     * 
     */
    public Client createClient() {
        return new Client();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "getHotelResponse")
    public JAXBElement<GetHotelResponse> createGetHotelResponse(GetHotelResponse value) {
        return new JAXBElement<GetHotelResponse>(_GetHotelResponse_QNAME, GetHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotelsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "getHotelsResponse")
    public JAXBElement<GetHotelsResponse> createGetHotelsResponse(GetHotelsResponse value) {
        return new JAXBElement<GetHotelsResponse>(_GetHotelsResponse_QNAME, GetHotelsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "deleteHotel")
    public JAXBElement<DeleteHotel> createDeleteHotel(DeleteHotel value) {
        return new JAXBElement<DeleteHotel>(_DeleteHotel_QNAME, DeleteHotel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "getHotel")
    public JAXBElement<GetHotel> createGetHotel(GetHotel value) {
        return new JAXBElement<GetHotel>(_GetHotel_QNAME, GetHotel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHotels }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "getHotels")
    public JAXBElement<GetHotels> createGetHotels(GetHotels value) {
        return new JAXBElement<GetHotels>(_GetHotels_QNAME, GetHotels.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "HotelNotFoundException")
    public JAXBElement<HotelNotFoundException> createHotelNotFoundException(HotelNotFoundException value) {
        return new JAXBElement<HotelNotFoundException>(_HotelNotFoundException_QNAME, HotelNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "HotelAlreadyExistsException")
    public JAXBElement<HotelAlreadyExistsException> createHotelAlreadyExistsException(HotelAlreadyExistsException value) {
        return new JAXBElement<HotelAlreadyExistsException>(_HotelAlreadyExistsException_QNAME, HotelAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "addHotelResponse")
    public JAXBElement<AddHotelResponse> createAddHotelResponse(AddHotelResponse value) {
        return new JAXBElement<AddHotelResponse>(_AddHotelResponse_QNAME, AddHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "addHotel")
    public JAXBElement<AddHotel> createAddHotel(AddHotel value) {
        return new JAXBElement<AddHotel>(_AddHotel_QNAME, AddHotel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "deleteHotelResponse")
    public JAXBElement<DeleteHotelResponse> createDeleteHotelResponse(DeleteHotelResponse value) {
        return new JAXBElement<DeleteHotelResponse>(_DeleteHotelResponse_QNAME, DeleteHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateHotel }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "updateHotel")
    public JAXBElement<UpdateHotel> createUpdateHotel(UpdateHotel value) {
        return new JAXBElement<UpdateHotel>(_UpdateHotel_QNAME, UpdateHotel.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateHotelResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "updateHotelResponse")
    public JAXBElement<UpdateHotelResponse> createUpdateHotelResponse(UpdateHotelResponse value) {
        return new JAXBElement<UpdateHotelResponse>(_UpdateHotelResponse_QNAME, UpdateHotelResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "countResponse")
    public JAXBElement<CountResponse> createCountResponse(CountResponse value) {
        return new JAXBElement<CountResponse>(_CountResponse_QNAME, CountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Count }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.archi.m1/", name = "count")
    public JAXBElement<Count> createCount(Count value) {
        return new JAXBElement<Count>(_Count_QNAME, Count.class, null, value);
    }

}
