package m1.archi.service;

import m1.archi.exception.UserAlreadyExistsException;
import m1.archi.exception.UserNotFoundException;
import m1.archi.model.Agence;
import m1.archi.model.User;

import javax.jws.WebService;

@WebService(endpointInterface = "m1.archi.service.UserServiceConnectionInscription")
public class UserServiceConnectionInscriptionImpl implements UserServiceConnectionInscription {

    private final Agence agence;

    public UserServiceConnectionInscriptionImpl() {
        this.agence = new Agence();
    }

    public UserServiceConnectionInscriptionImpl(Agence agence) {
        this.agence = agence;
    }

    @Override
    public User inscriptionUser(String login, String motDePasse) throws UserAlreadyExistsException {
        return agence.inscriptionUser(login, motDePasse);
    }

    @Override
    public User connectionUser(String login, String motDePasse) throws UserNotFoundException {
        return agence.connectionUser(login, motDePasse);
    }
}
