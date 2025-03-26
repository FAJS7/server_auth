package model;
import org.mindrot.jbcrypt.BCrypt;
import config.ConfigDB;

import java.sql.*;

import static repository.jwt.generateJWT;

public class ModelUser {

    public static boolean isUserRegistered(String mail) {
        String sqlCheck = "SELECT COUNT(*) FROM user WHERE mail = ?";
        Connection conn = null;
        PreparedStatement stmtCheck = null;
        ResultSet rs = null;

        try {
            conn = ConfigDB.connDB();
            stmtCheck = conn.prepareStatement(sqlCheck);
            stmtCheck.setString(1, mail);
            rs = stmtCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtCheck != null) stmtCheck.close();
                if (conn != null) ConfigDB.disconnDB(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String registerDB(String mail, String pass) {
        if (isUserRegistered(mail)) {
            return "El usuario ya está registrado";
        }

        String hash = BCrypt.hashpw(pass, BCrypt.gensalt(12));
        String sqlInsert = "INSERT INTO user(mail, pass) VALUES(?, ?)";
        Connection conn = null;
        PreparedStatement stmtInsert = null;

        try {
            conn = ConfigDB.connDB();
            stmtInsert = conn.prepareStatement(sqlInsert);
            stmtInsert.setString(1, mail);
            stmtInsert.setString(2, hash);

            int rows = stmtInsert.executeUpdate();
            if (rows > 0) {
                return "Usuario registrado con éxito";
            } else {
                return "Error al registrar usuario";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error SQL: " + e.getMessage();
        } finally {
            try {
                if (stmtInsert != null) stmtInsert.close();
                if (conn != null) ConfigDB.disconnDB(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String verifyCred(String mail, String pass) {
        String sqlSelect = "SELECT pass FROM user WHERE mail = ?";
        Connection conn = null;
        PreparedStatement stmtSelect = null;
        ResultSet rs = null;

        try {
            conn = ConfigDB.connDB();
            stmtSelect = conn.prepareStatement(sqlSelect);
            stmtSelect.setString(1, mail);
            rs = stmtSelect.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("pass");

                if (BCrypt.checkpw(pass, hashedPassword)) {
                    System.out.println("Inicio de sesión exitoso");
                    return generateJWT(mail);
                } else {
                    return "Error: Contraseña incorrecta";
                }
            } else {
                return "Error: Usuario no encontrado";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error SQL: " + e.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtSelect != null) stmtSelect.close();
                if (conn != null) ConfigDB.disconnDB(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}