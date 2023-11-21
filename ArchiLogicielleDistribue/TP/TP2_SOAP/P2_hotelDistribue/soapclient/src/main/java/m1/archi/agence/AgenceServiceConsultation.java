
package m1.archi.agence;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
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
@WebService(name = "AgenceServiceConsultation", targetNamespace = "http://service.archi.m1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AgenceServiceConsultation {


    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg5
     * @param arg4
     * @param arg1
     * @param arg0
     * @param arg7
     * @param arg6
     * @param arg8
     * @return
     *     returns java.util.List<m1.archi.agence.Offres>
     * @throws DateNonValideException_Exception
     * @throws UserNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listeChoisOffresHotelCriteres", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.ListeChoisOffresHotelCriteres")
    @ResponseWrapper(localName = "listeChoisOffresHotelCriteresResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.ListeChoisOffresHotelCriteresResponse")
    @Action(input = "http://service.archi.m1/AgenceServiceConsultation/listeChoisOffresHotelCriteresRequest", output = "http://service.archi.m1/AgenceServiceConsultation/listeChoisOffresHotelCriteresResponse", fault = {
        @FaultAction(className = DateNonValideException_Exception.class, value = "http://service.archi.m1/AgenceServiceConsultation/listeChoisOffresHotelCriteres/Fault/DateNonValideException"),
        @FaultAction(className = UserNotFoundException_Exception.class, value = "http://service.archi.m1/AgenceServiceConsultation/listeChoisOffresHotelCriteres/Fault/UserNotFoundException")
    })
    public List<Offres> listeChoisOffresHotelCriteres(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        XMLGregorianCalendar arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        XMLGregorianCalendar arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        int arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        int arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        int arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        int arg8)
        throws DateNonValideException_Exception, UserNotFoundException_Exception
    ;

}