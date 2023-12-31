
package m1.archi.hotel;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "HotelNotFoundException", targetNamespace = "http://service.archi.m1/")
public class HotelNotFoundException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private HotelNotFoundException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public HotelNotFoundException_Exception(String message, HotelNotFoundException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public HotelNotFoundException_Exception(String message, HotelNotFoundException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: m1.archi.hotel.HotelNotFoundException
     */
    public HotelNotFoundException getFaultInfo() {
        return faultInfo;
    }

}
