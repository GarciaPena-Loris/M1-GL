package m1.archi.service;

import m1.archi.exception.UserAlreadyExistsException;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UserServiceInscription {

    @WebMethod
    public boolean inscription(String login, String motDePasse) throws UserAlreadyExistsException;
}
