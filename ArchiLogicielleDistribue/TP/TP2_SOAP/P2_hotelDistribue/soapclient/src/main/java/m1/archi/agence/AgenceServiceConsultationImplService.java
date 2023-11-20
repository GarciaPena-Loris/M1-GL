
package m1.archi.agence;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AgenceServiceConsultationImplService", targetNamespace = "http://service.archi.m1/", wsdlLocation = "http://localhost:8090/agencesservice/A1700315914081/consultation?wsdl")
public class AgenceServiceConsultationImplService
    extends Service
{

    private final static URL AGENCESERVICECONSULTATIONIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException AGENCESERVICECONSULTATIONIMPLSERVICE_EXCEPTION;
    private final static QName AGENCESERVICECONSULTATIONIMPLSERVICE_QNAME = new QName("http://service.archi.m1/", "AgenceServiceConsultationImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8090/agencesservice/A1700315914081/consultation?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AGENCESERVICECONSULTATIONIMPLSERVICE_WSDL_LOCATION = url;
        AGENCESERVICECONSULTATIONIMPLSERVICE_EXCEPTION = e;
    }

    public AgenceServiceConsultationImplService() {
        super(__getWsdlLocation(), AGENCESERVICECONSULTATIONIMPLSERVICE_QNAME);
    }

    public AgenceServiceConsultationImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AGENCESERVICECONSULTATIONIMPLSERVICE_QNAME, features);
    }

    public AgenceServiceConsultationImplService(URL wsdlLocation) {
        super(wsdlLocation, AGENCESERVICECONSULTATIONIMPLSERVICE_QNAME);
    }

    public AgenceServiceConsultationImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AGENCESERVICECONSULTATIONIMPLSERVICE_QNAME, features);
    }

    public AgenceServiceConsultationImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AgenceServiceConsultationImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AgenceServiceConsultation
     */
    @WebEndpoint(name = "AgenceServiceConsultationImplPort")
    public AgenceServiceConsultation getAgenceServiceConsultationImplPort() {
        return super.getPort(new QName("http://service.archi.m1/", "AgenceServiceConsultationImplPort"), AgenceServiceConsultation.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AgenceServiceConsultation
     */
    @WebEndpoint(name = "AgenceServiceConsultationImplPort")
    public AgenceServiceConsultation getAgenceServiceConsultationImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.archi.m1/", "AgenceServiceConsultationImplPort"), AgenceServiceConsultation.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AGENCESERVICECONSULTATIONIMPLSERVICE_EXCEPTION!= null) {
            throw AGENCESERVICECONSULTATIONIMPLSERVICE_EXCEPTION;
        }
        return AGENCESERVICECONSULTATIONIMPLSERVICE_WSDL_LOCATION;
    }

}
