package com.cabinet.client.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.cabinet.common.rmi.IClientCallback;

public class ClientCallbackImpl extends UnicastRemoteObject implements IClientCallback {
    public ClientCallbackImpl() throws RemoteException {
    }

    @Override
    public void notifierSeuilAtteint(int nombrePatients) throws RemoteException {
        System.out.println("\033[31m \033[1mAlerte\033[0m \033[0m : Le nombre de patients est de \033[1m"
                + nombrePatients + "\033[0m !");
    }
}
