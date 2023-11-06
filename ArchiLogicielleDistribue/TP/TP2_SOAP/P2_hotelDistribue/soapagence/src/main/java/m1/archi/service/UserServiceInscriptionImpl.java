package m1.archi.service;

import m1.archi.exception.UserAlreadyExistsException;
import m1.archi.model.Agence;

import javax.jws.WebService;

@WebService(endpointInterface = "m1.archi.service.UserServiceInscription")
public class UserServiceInscriptionImpl implements UserServiceInscription {

    private Agence agence;

    public UserServiceInscriptionImpl() {
        this.agence = new Agence();
    }

    public UserServiceInscriptionImpl(Agence agence) {
        this.agence = agence;
    }

    @Override
    public boolean inscription(String login, String motDePasse) throws UserAlreadyExistsException {
        return agence.inscriptionUser(login, motDePasse);
    }
}
