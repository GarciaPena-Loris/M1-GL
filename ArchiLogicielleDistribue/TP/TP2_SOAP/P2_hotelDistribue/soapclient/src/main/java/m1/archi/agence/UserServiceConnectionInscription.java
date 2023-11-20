
package m1.archi.agence;

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
@WebService(name = "UserServiceConnectionInscription", targetNamespace = "http://service.archi.m1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserServiceConnectionInscription {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns m1.archi.agence.User
     * @throws UserNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "connectionUser", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.ConnectionUser")
    @ResponseWrapper(localName = "connectionUserResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.ConnectionUserResponse")
    @Action(input = "http://service.archi.m1/UserServiceConnectionInscription/connectionUserRequest", output = "http://service.archi.m1/UserServiceConnectionInscription/connectionUserResponse", fault = {
        @FaultAction(className = UserNotFoundException_Exception.class, value = "http://service.archi.m1/UserServiceConnectionInscription/connectionUser/Fault/UserNotFoundException")
    })
    public User connectionUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws UserNotFoundException_Exception
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns m1.archi.agence.User
     * @throws UserAlreadyExistsException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "inscriptionUser", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.InscriptionUser")
    @ResponseWrapper(localName = "inscriptionUserResponse", targetNamespace = "http://service.archi.m1/", className = "m1.archi.agence.InscriptionUserResponse")
    @Action(input = "http://service.archi.m1/UserServiceConnectionInscription/inscriptionUserRequest", output = "http://service.archi.m1/UserServiceConnectionInscription/inscriptionUserResponse", fault = {
        @FaultAction(className = UserAlreadyExistsException_Exception.class, value = "http://service.archi.m1/UserServiceConnectionInscription/inscriptionUser/Fault/UserAlreadyExistsException")
    })
    public User inscriptionUser(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1)
        throws UserAlreadyExistsException_Exception
    ;

}
