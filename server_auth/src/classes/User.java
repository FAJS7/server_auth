package classes;

import interfaces.InterfaceUser;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static model.ModelUser.registerDB;
import static model.ModelUser.verifyCred;

public class User extends UnicastRemoteObject implements InterfaceUser {

    public User() throws RemoteException {
    }

    @Override
    public String register(String mail, String pass) throws RemoteException {
        try{
            return registerDB(mail,pass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String login(String mail, String pass) throws RemoteException {
        try{
            return verifyCred(mail, pass);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
