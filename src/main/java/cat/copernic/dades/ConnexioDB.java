/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe que permet la connexió del projecte amb la base de dades "magatzemdb"
 */
public class ConnexioDB {

    /**
     * Constants que configuren la connexió de la base de dades on,
     * respectivament, especifiquen la direcció de la base de dades, el usuari i
     * la contrasenya que fa servir l'usuari.
     */
    private static final String url = "jdbc:mysql://localhost:3306/magatzemdb";
    private static final String usuari = "root";
    private static final String pass = "123456";

    /**
     * Metode que permet la connexió del projecte amb la base de dades amb les
     * credencials proporcionades prèviament; També es llança una excepció.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuari, pass);
    }
}
