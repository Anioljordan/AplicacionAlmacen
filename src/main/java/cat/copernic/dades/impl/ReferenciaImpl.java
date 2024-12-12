/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades.impl;

/**
 * Paquets que es necessiten per a fer la implementació de
 */
import cat.copernic.dades.ConnexioDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cat.copernic.dades.DAOReferencia;
import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.ObReferencia;
import cat.copernic.logica.AlertaBaixEstoc;
import cat.copernic.logica.EstocSubject;
import cat.copernic.presentacio.App;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author marcv
 */
public class ReferenciaImpl implements DAOReferencia {

    /**
     * Mètode per a donar d'alta.
     */
    @Override
    public void alta(ObReferencia referencia) throws SQLException {

        /**
         * String que contè la consulta sql, en aquest cas es un inserció a la
         * taula referencia
         */
        String sql = "INSERT INTO referencia (idReferencia, nom, UoM, quantitatProducte, familiaProductes, proveidor, dataAlta, dataBaixa, preuUnitari, motiuBaixa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        /**
         * Try on s'estableix la connexio amb la base de dades i el prepared
         * statement per a poder realitzar la consulta sql, un insert
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setInt(1, referencia.getIdReferencia());
            stmt.setString(2, referencia.getNom());
            stmt.setString(3, referencia.getUoM());
            stmt.setInt(4, referencia.getQuantitatProducte());
            stmt.setString(5, referencia.getFamiliaProductes());
            stmt.setString(6, referencia.getProveidor());
            stmt.setString(7, referencia.getDataAlta());
            stmt.setString(8, referencia.getDataBaixa());
            stmt.setFloat(9, referencia.getPreuUnitari());
            stmt.setString(10, referencia.getMotiuBaixa());

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
    public void baixa(int idRefe) throws SQLException {

        /**
         * Creem la consutlta SQL
         */
        String sql = "DELETE FROM referencia WHERE idReferencia = ?";

        /**
         * Primer iniciem el connection i el preparedStatement en el try per a
         * que es carregui la info de la consulta SQL i del id de referència
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idRefe)
             */
            stmt.setInt(1, idRefe);

            /**
             * Creem un int que emmagatzemarà en nombre de files que s'han
             * eliminat
             */
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                App.mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminació correcta", "L'entrada s'ha eliminat correctament.");
            } else {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error d'eliminació", "No s'ha pogut trobar l'entrada amb aquest ID.");
            }

            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            stmt.close();
            con.close();
        }
    }

    @Override
    public void modificar(ObReferencia referencia) throws SQLException {

        /**
         * Consulta SQL per a fer un update
         */
        String sql = "UPDATE referencia SET nom = ?, UoM = ?, quantitatProducte = ?, familiaProductes = ?, proveidor = ?, dataAlta = ?, dataBaixa = ?, preuUnitari = ?, motiuBaixa = ? WHERE idReferencia = ?";
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setString(1, referencia.getNom());
            stmt.setString(2, referencia.getUoM());
            stmt.setInt(3, referencia.getQuantitatProducte());
            stmt.setString(4, referencia.getFamiliaProductes());
            stmt.setString(5, referencia.getProveidor());
            stmt.setString(6, referencia.getDataAlta());
            stmt.setString(7, referencia.getDataBaixa());
            stmt.setFloat(8, referencia.getPreuUnitari());
            stmt.setString(9, referencia.getMotiuBaixa());
            stmt.setInt(10, referencia.getIdReferencia());

            /**
             * Executem la consulta d'inserció
             */
            stmt.executeUpdate();

            /**
             * Mostrem una alerta en el cas que s'hagin modificat algunes files
             */
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                App.mostrarAlerta(Alert.AlertType.INFORMATION, "Modificació realitzada", "L'entrada s'ha modificat correctament.");

                /**
                 * Notifiquem al observer la quantitat d'estoc
                 */
                estocSubject.actualitzarObserver(referencia.getQuantitatProducte());
            } else {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No s'ha pogut trobar l'entrada amb aquest ID.");
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
    public ObReferencia mostrar(int idRefe) throws SQLException {
        /**
         * Creem la consutlta SQL
         */
        String sql = "SELECT * FROM referencia WHERE  idReferencia = ?";

        /**
         * Iniciem l'objecte referencia
         */
        ObReferencia referencia = null;
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idRefe)
             */
            stmt.setInt(1, idRefe);

            /**
             * Fem un altre try on iniciem el resultset amb la info que te
             * actualment l'statement
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
                     * pertinents que fan referencia a la referència demandada
                     * per l'usuari
                     */
                    int idReferencia = rs.getInt("idReferencia");
                    String nom = rs.getString("nom");
                    String uom = rs.getString("UoM");
                    int quantitatProducte = rs.getInt("quantitatProducte");
                    String familiaProductes = rs.getString("familiaProductes");
                    String proveidorRefe = rs.getString("proveidor");
                    String dataAlta = rs.getString("dataAlta");
                    String dataBaixa = rs.getString("dataBaixa");
                    String motiuBaixa = rs.getString("motiuBaixa");
                    float preUnitari = rs.getFloat("preuUnitari");

                    /**
                     * Intorduïm els valors a l'objecte referència
                     */
                    referencia = new ObReferencia(idReferencia, nom, uom, quantitatProducte, familiaProductes, proveidorRefe, dataAlta, dataBaixa, preUnitari, motiuBaixa);
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
        return referencia;
    }

    @Override
    public List<ObReferencia> mostrarTot() throws SQLException {

        /**
         * Creem l'arraylist de referencia
         */
        List<ObReferencia> referencia = new ArrayList<>();

        /**
         * Creem un String que contrindra la consulta SQL
         */
        String sql = "SELECT * FROM referencia";

        /**
         * Fem un try per a assegurar-nos que es tanquen les conexions i no es
         * prodeuix cap error
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
                ObReferencia ref = new ObReferencia();

                /**
                 * A continuacio amb l'objecte "ref" fem un set on el seu
                 * contingut serà el resultat de la query SQL
                 */
                ref.setIdReferencia(rs.getInt("idReferencia"));
                ref.setNom(rs.getString("nom"));
                ref.setUoM(rs.getString("UoM"));
                ref.setQuantitatProducte(rs.getInt("quantitatProducte"));
                ref.setFamiliaProductes(rs.getString("familiaProductes"));
                ref.setProveidor(rs.getString("proveidor"));
                ref.setDataAlta(rs.getString("dataAlta"));
                ref.setDataBaixa(rs.getString("dataBaixa"));
                ref.setMotiuBaixa(rs.getString("motiuBaixa"));
                ref.setPreuUnitari(rs.getFloat("preuUnitari"));

                /**
                 * Finalment afegim la info que hem obtingut al array referencia
                 */
                referencia.add(ref);
            }
            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            rs.close();
            stmt.close();
            con.close();
        }
        return referencia;
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
     * Mètode per obtenir la llista de famílies
     */
    public List<ObFamilia> obtenirTotesLesFamilias() throws SQLException {
        List<ObFamilia> families = new ArrayList<>();
        String sql = "SELECT nom FROM familia";  // Canvia aquesta consulta segons la teva estructura de taula

        // Obre la connexió amb la base de dades
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            // Llegeix els resultats de la consulta
            while (rs.next()) {
                // Crear un objecte ObFamilia amb les dades obtingudes
                ObFamilia familia = new ObFamilia();
                familia.setNom(rs.getString("nom"));  // Estableix el nom de la família
                families.add(familia);  // Afegeix la família a la llista
            }
        }
        return families;  // Torna la llista de famílies
    }

    /**
     * Iniciem el EstocSubject per a poder utilitzar l'alerta de baix estoc
     */
    private EstocSubject estocSubject = new EstocSubject();

    public ReferenciaImpl() {
        estocSubject.afegirObserver(new AlertaBaixEstoc());
    }
}
