package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceUser extends Remote {
    String register(String mail, String pass) throws RemoteException;
    String login(String mail, String pass) throws RemoteException;
}
