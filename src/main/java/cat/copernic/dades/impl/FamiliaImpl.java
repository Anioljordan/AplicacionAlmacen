/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades.impl;

/**
 * Collections que es necessiten per a fer la implementació de
 */
import cat.copernic.dades.ConnexioDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cat.copernic.dades.DAOFamilia;
import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author marcv
 */
public class FamiliaImpl implements DAOFamilia {

    /**
     * Mètode per a donar d'alta una família a la base de dades.
     */
    @Override
    public void alta(ObFamilia familia) throws SQLException {

        /**
         * String que contè la consulta sql, en aquest cas es un inserció a la
         * taula familia
         */
        String sql = "INSERT INTO familia (idFamilia, nom, descripcio, dataAlta, dataBaixa, proveidorDefault, observacions, descompteGeneral) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        /**
         * Try on s'estableix la connexio amb la base de dades i el prepared
         * statement per a poder realitzar la consulta sql, un insert
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setInt(1, familia.getIdFamilia());
            stmt.setString(2, familia.getNom());
            stmt.setString(3, familia.getDescripcio());
            stmt.setString(4, familia.getDataAlta());
            stmt.setString(5, familia.getDataBaixa());
            stmt.setString(6, familia.getProveidorDefault());
            stmt.setString(7, familia.getObservacions());
            stmt.setFloat(8, familia.getDescompteGeneral());

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
     * Mètode per a donar de baixa.
     */
    @Override
    public void baixa(int idFam) throws SQLException {

        /**
         * Creem la consutlta SQL
         */
        String sql = "DELETE FROM familia WHERE idFamilia = ?";

        /**
         * Primer iniciem el connection i el preparedStatement en el try per a
         * que es carregui la info de la consulta SQL i del id de familia.
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idFam)
             */
            stmt.setInt(1, idFam);

            /**
             * Creem un int que emmagatzemarà en nombre de files que s'han
             * eliminat
             */
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminació correcta", "L'entrada s'ha eliminat correctament.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error d'eliminació", "No s'ha pogut trobar l'entrada amb aquest ID.");
            }

            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            stmt.close();
            con.close();
        }

    }

    /**
     * Mètode per a modificar.
     */
    @Override
    public void modificar(ObFamilia familia) throws SQLException {

        /**
         * Consulta SQL per a fer un update
         */
        String sql = "UPDATE familia SET nom = ?, descripcio = ?, dataAlta = ?, dataBaixa = ?, proveidorDefault = ?, observacions = ?, descompteGeneral = ? WHERE idFamilia = ?";
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setString(1, familia.getNom());
            stmt.setString(2, familia.getDescripcio());
            stmt.setString(3, familia.getDataAlta());
            stmt.setString(4, familia.getDataBaixa());
            stmt.setString(5, familia.getProveidorDefault());
            stmt.setString(6, familia.getObservacions());
            stmt.setFloat(7, familia.getDescompteGeneral());
            stmt.setInt(8, familia.getIdFamilia());

            /**
             * Executem la consulta d'inserció
             */
            stmt.executeUpdate();

            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Modificació realitzada", "L'entrada s'ha modificat correctament.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No s'ha pogut trobar l'entrada amb aquest ID.");
            }
            /**
             * Tanquem les connexions i statements
             */
            stmt.close();
            con.close();

        }
    }

    /**
     * Mètode per a mostrar per ID.
     */
    @Override
    public ObFamilia mostrar(int idFam) throws SQLException {

        /**
         * Creem la consutlta SQL
         */
        String sql = "SELECT * FROM familia WHERE  idFamilia = ?";

        /**
         * Iniciem l'objecte familia
         */
        ObFamilia familia = null;
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idFam)
             */
            stmt.setInt(1, idFam);

            /**
             * Fem un altre try on iniciem el resultset amb la info que te
             * actualment l'statement.
             */
            try (ResultSet rs = stmt.executeQuery()) {

                /**
                 * Seguidament fem un if on la seva condició és que si el
                 * resultset troba alguna coincidencia s'entrarà a dins, sino no
                 * ho farà
                 */
                if (rs.next()) {

                    /**
                     * Iniciem statments on s'emmagatzemaràn els valors
                     * pertinents que fan referencia a la familia demandada per
                     * l'usuari
                     */
                    int idFamilia = rs.getInt("idFamilia");
                    String nom = rs.getString("nom");
                    String descripcio = rs.getString("descripcio");
                    String dataAlta = rs.getString("dataAlta");
                    String dataBaixa = rs.getString("dataBaixa");
                    String proveidorDefault = rs.getString("proveidorDefault");
                    String observacions = rs.getString("observacions");
                    float descompteGeneral = rs.getFloat("descompteGeneral");

                    /**
                     * Intorduïm els valors a l'objecte familia
                     */
                    familia = new ObFamilia(idFamilia, nom, descripcio, dataAlta, dataBaixa, proveidorDefault, observacions, descompteGeneral);
                }

                /**
                 * Tanquem el ResultSet
                 */
                rs.close();
            }

            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            stmt.close();
            con.close();
        }
        return familia;
    }

    /**
     * Mètode per a mostrar tots els elements de la base de dades.
     */
    @Override
    public List<ObFamilia> mostrarTot() throws SQLException {

        /**
         * Creem l'arraylist de familia
         */
        List<ObFamilia> familia = new ArrayList<>();

        /**
         * Creem un String que contrindra la consulta SQL
         */
        String sql = "SELECT * FROM familia";

        /**
         * Fem un try per a assegurar-nos que es tanquen les conexions i no es
         * prodeuix cap error.
         */
        try (Connection con = ConnexioDB.getConnection(); //Iniciem la col·lecció connection per a connectar amb la base de dades cridant la funcio getConnection que esta a la clase ConnexioDB
                 PreparedStatement stmt = con.prepareStatement(sql); //Iniciem la col·lecció preparedStatement per a enviar mitjançant "con", la consulta SQL a la base de dades
                 ResultSet rs = stmt.executeQuery();) { //Iniciem ResultSet per a poder obtenir el resultat de la consulta que s'ha fet a la base de dades

            /**
             * Creem un while que iterarà mentres s'obtinguin resultats en el
             * resulset, quan aquest sigui fals sortirà del while.
             */
            while (rs.next()) {
                /**
                 * Creem un objecte familia i establim els seus atributs
                 */
                ObFamilia fam = new ObFamilia();

                /**
                 * A continuacio amb l'objecte "fam" fem un set on el seu
                 * contingut serà el resultat de la query SQL
                 */
                fam.setIdFamilia(rs.getInt("idFamilia"));
                fam.setNom(rs.getString("nom"));
                fam.setDescripcio(rs.getString("descripcio"));
                fam.setDataAlta(rs.getString("dataAlta"));
                fam.setDataBaixa(rs.getString("dataBaixa"));
                fam.setProveidorDefault(rs.getString("proveidorDefault"));
                fam.setObservacions(rs.getString("observacions"));
                fam.setDescompteGeneral(rs.getFloat("descompteGeneral"));

                /**
                 * Finalment afegim la info que hem obtingut al array familia
                 */
                familia.add(fam);
            }
            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            rs.close();
            stmt.close();
            con.close();
        }
        return familia;
    }

    /**
     * Mètode per obtenir la llista de proveïdors
     */
    public List<ObProveidor> obtenirTotsElsProveidors() throws SQLException {
        List<ObProveidor> proveidors = new ArrayList<>();
        String sql = "SELECT nom FROM proveidor";  // Canvia aquesta consulta segons la teva estructura de taula

        // Obre la connexió amb la base de dades
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            // Llegeix els resultats de la consulta
            while (rs.next()) {
                // Crear un objecte ObProveidor amb les dades obtingudes
                ObProveidor proveidor = new ObProveidor();
                proveidor.setNom(rs.getString("nom"));  // Estableix el nom del proveïdor
                proveidors.add(proveidor);  // Afegeix el proveïdor a la llista
            }
        }
        return proveidors;  // Torna la llista de proveïdors
    }

    /**
     * Alerta per a avisar de l'activitat realitzada.
     */
    private void mostrarAlerta(Alert.AlertType tipus, String titol, String missatge) {
        Alert alerta = new Alert(tipus);
        alerta.setTitle(titol);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }
}
