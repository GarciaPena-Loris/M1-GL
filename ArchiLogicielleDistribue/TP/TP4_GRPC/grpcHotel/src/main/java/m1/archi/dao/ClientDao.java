package m1.archi.dao;

import m1.archi.domaines.Client;

public class ClientDao extends GenericDao<Client> {
    public ClientDao() {
        super(Client.class);
    }
}