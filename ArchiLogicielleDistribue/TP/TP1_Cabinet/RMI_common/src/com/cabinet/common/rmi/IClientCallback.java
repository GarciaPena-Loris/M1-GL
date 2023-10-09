package com.cabinet.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientCallback extends Remote {
    void notifierSeuilAtteint(int nombrePatients) throws RemoteException;
}
