package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {

    public static Connection connDB(){
        Connection conn;
        String host = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pass = "1234";
        String bd = "serverauth";

        System.out.println("Conectando...");

        try {
            conn = DriverManager.getConnection(host+bd, user, pass);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return conn;
    }

    public static void disconnDB(Connection db){
        try {
            db.close();
            System.out.println("Desconectado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
