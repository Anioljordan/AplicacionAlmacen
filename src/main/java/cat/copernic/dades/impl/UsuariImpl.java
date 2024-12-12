/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades.impl;

import cat.copernic.dades.ConnexioDB;
import cat.copernic.dades.DAOUsuari;
import cat.copernic.dades.ObUsuari;
import cat.copernic.presentacio.App;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author marcv
 */
public class UsuariImpl implements DAOUsuari {

    @Override
    public void alta(ObUsuari usuari) throws SQLException {
        /**
         * String que contè la consulta sql, en aquest cas es un inserció a la
         * taula familia
         */
        String sql = "INSERT INTO usuari (nomUsuari, contrasenya, esManager) VALUES (?, ?, ?)";

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setString(1, usuari.getNom());
            stmt.setString(2, usuari.getPass());
            stmt.setString(3, String.valueOf(usuari.getMan()));

            /**
             * Executem la consulta d'inserció
             */
            stmt.executeUpdate();
            /**
             * Tanquem les connexions i statements
             */
            stmt.close();
            con.close();
        }
    }

    /**
     * Mètode per comprovar si el nom d'usuari ja existeix
     */
    @Override
    public boolean existeix(String username) {
        String query = "SELECT COUNT(*) FROM usuari WHERE nomUsuari = ?"; //Consulta on conta quantes vegades apareix el nom d'usuari
        boolean userExists = false;

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, username); //Introduïm el nom d'usuari que hem picat
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userExists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userExists;
    }

    /**
     * Mètode per a comprovar si l'usuari i contrasenya estan a la base de dades
     */
    @Override
    public ObUsuari autenticarUsuari(String username, String password) {
        ObUsuari usuari = null; // Variable per emmagatzemar l'usuari autenticat
        String query = "SELECT nomUsuari, contrasenya, esManager FROM usuari WHERE nomUsuari = ? AND contrasenya = ?"; // Suposant que tipus és on emmagatzemes MANAGER o VENEDOR

        try (Connection conn = ConnexioDB.getConnection(); // Obtens la connexió amb el teu mètode
                 PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username); // Estableix el nom d'usuari
            stmt.setString(2, password); // Estableix la contrasenya

            ResultSet rs = stmt.executeQuery(); // Executa la consulta

            if (rs.next()) {
                // Si trobem un registre
                String nom = rs.getString("nomUsuari");
                String pass = rs.getString("contrasenya");
                String tipusUsuariStr = rs.getString("esManager"); // Supon que tipus és un String

                // Converteix a l'enumeració
                ObUsuari.esManager tipusUsuari = ObUsuari.esManager.valueOf(tipusUsuariStr);

                // Crea l'objecte d'ObUsuari
                usuari = new ObUsuari(nom, pass, tipusUsuari);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Gestiona l'excepció segons les teves necessitats
        }
        return usuari; // Retorna l'usuari, o null si no s'ha trobat
    }
}
