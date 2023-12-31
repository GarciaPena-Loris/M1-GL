
package hai704i.tp2.demo.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "EmployeeAlreadyExistsException", targetNamespace = "http://service.demo.tp2.hai704i/")
public class EmployeeAlreadyExistsException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EmployeeAlreadyExistsException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public EmployeeAlreadyExistsException_Exception(String message, EmployeeAlreadyExistsException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public EmployeeAlreadyExistsException_Exception(String message, EmployeeAlreadyExistsException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: hai704i.tp2.demo.client.EmployeeAlreadyExistsException
     */
    public EmployeeAlreadyExistsException getFaultInfo() {
        return faultInfo;
    }

}
