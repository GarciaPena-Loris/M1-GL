package m1.archi.service;

import m1.archi.exception.UserAlreadyExistsException;
import m1.archi.exception.UserNotFoundException;
import m1.archi.model.User;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface UserServiceConnectionInscription {

    @WebMethod
    public User inscriptionUser(String login, String motDePasse) throws UserAlreadyExistsException;

    @WebMethod
    public User connectionUser(String login, String motDePasse) throws UserNotFoundException;
}
