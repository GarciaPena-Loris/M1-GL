
package hai704i.tp2.demo.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MathService", targetNamespace = "http://server.demo.tp2.hai704i/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MathService {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "subtract", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.Subtract")
    @ResponseWrapper(localName = "subtractResponse", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.SubtractResponse")
    @Action(input = "http://server.demo.tp2.hai704i/MathService/subtractRequest", output = "http://server.demo.tp2.hai704i/MathService/subtractResponse")
    public int subtract(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.AddResponse")
    @Action(input = "http://server.demo.tp2.hai704i/MathService/addRequest", output = "http://server.demo.tp2.hai704i/MathService/addResponse")
    public int add(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "divide", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.Divide")
    @ResponseWrapper(localName = "divideResponse", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.DivideResponse")
    @Action(input = "http://server.demo.tp2.hai704i/MathService/divideRequest", output = "http://server.demo.tp2.hai704i/MathService/divideResponse")
    public int divide(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "multiply", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.Multiply")
    @ResponseWrapper(localName = "multiplyResponse", targetNamespace = "http://server.demo.tp2.hai704i/", className = "hai704i.tp2.demo.client.MultiplyResponse")
    @Action(input = "http://server.demo.tp2.hai704i/MathService/multiplyRequest", output = "http://server.demo.tp2.hai704i/MathService/multiplyResponse")
    public int multiply(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        int arg1);

}
