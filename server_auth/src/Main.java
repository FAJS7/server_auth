import classes.User;
import interfaces.InterfaceUser;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {

    public static void main(String[] args) throws RemoteException {
        try {
            InterfaceUser serviceAuth = new User();
            System.out.println(serviceAuth.login("jsoo","jsoo"));
            LocateRegistry.createRegistry(1802);
            Naming.rebind("//127.0.0.1:1802/serviceAuth", serviceAuth);
            System.out.println("Corriendo...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}