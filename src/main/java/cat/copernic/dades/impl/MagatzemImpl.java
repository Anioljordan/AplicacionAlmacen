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
import cat.copernic.dades.DAOMagatzem;
import cat.copernic.dades.ObMagatzem;
import cat.copernic.presentacio.App;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author marcv
 */
public class MagatzemImpl implements DAOMagatzem {

    @Override
    public void alta(ObMagatzem magatzem) throws SQLException {
        /**
         * String que contè la consulta sql, en aquest cas es un inserció a la
         * taula familia
         */
        String sql = "INSERT INTO magatzem (idEntrada, dataEntrada, dataSortida, motiuSortida, valorTotal, tipus, nomEntrada) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setInt(1, magatzem.getIdEntrada());
            stmt.setString(2, magatzem.getDataEntrada());
            stmt.setString(3, magatzem.getDataSortida());
            stmt.setString(4, magatzem.getMotiuSortida());
            stmt.setFloat(5, magatzem.getValorTotal());
            
            /**
             * Creem un objecte tipus on tindra com a valor el enum pertinent;
             * posteriorment amb .name(), fem que tipus sigui de tipu String
             */
            ObMagatzem.tipus tipus = magatzem.getTipus();
            stmt.setString(6, tipus.name());
            stmt.setString(7, magatzem.getNomEntrada());

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

    @Override
    public void baixa(int idEnt) throws SQLException {
        String query = "DELETE FROM magatzem WHERE idEntrada = ?";

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, idEnt);
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                App.mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminació correcta", "L'entrada s'ha eliminat correctament.");
            } else {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error d'eliminació", "No s'ha pogut trobar l'entrada amb aquest ID.");
            }

        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error de base de dades", "S'ha produït un error en eliminar l'entrada: " + e.getMessage());
        }
    }

    @Override
    public void modificar(ObMagatzem magatzem) throws SQLException {
        /**
         * String que contè la consulta sql, en aquest cas es un update a la
         * taula magatzem
         */
        String sql = "UPDATE magatzem SET nomEntrada = ?, dataEntrada = ?, dataSortida = ?, motiuSortida = ?, valorTotal = ?, tipus = ? WHERE idEntrada = ?";

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades. Assegurant que l'ordre 
             * és el del UPDATE
             */
            
            stmt.setString(1, magatzem.getNomEntrada());
            stmt.setString(2, magatzem.getDataEntrada());
            stmt.setString(3, magatzem.getDataSortida());
            stmt.setString(4, magatzem.getMotiuSortida());
            stmt.setFloat(5, magatzem.getValorTotal());
            ObMagatzem.tipus tipus = magatzem.getTipus();
            stmt.setObject(6, tipus.name());
            stmt.setInt(7, magatzem.getIdEntrada());
            

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

    @Override
    public ObMagatzem mostrar(int idEnt) throws SQLException {
        // Creem la consutlta SQL
        String sql = "SELECT * FROM magatzem WHERE idEntrada = ?";

        // Iniciem l'objecte magatzem
        ObMagatzem magatzem = null;

        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            // Assignem el valor idEnt a la consulta SQL
            stmt.setInt(1, idEnt);

            try (ResultSet rs = stmt.executeQuery()) {
                // Si hi ha un resultat, inicialitzem l'objecte
                if (rs.next()) {
                    int idEntrada = rs.getInt("idEntrada");
                    String dataEntrada = rs.getString("dataEntrada");
                    String dataSortida = rs.getString("dataSortida");
                    String motiuSortida = rs.getString("motiuSortida");
                    float valorTotal = rs.getFloat("valorTotal");
                    /**
                     * Obtenim el valor de l'enum tipus en format objecte tipus
                     */
                    ObMagatzem.tipus tipus = ObMagatzem.tipus.valueOf(rs.getString("tipus"));

                    String nomEntrada = rs.getString("nomEntrada");

                    // Assignem els valors a l'objecte familia
                    magatzem = new ObMagatzem(idEntrada, nomEntrada, dataEntrada, dataSortida, motiuSortida, valorTotal, tipus);
                }
                rs.close();
            }
            stmt.close();
            con.close();
        }
        return magatzem;
    }

    @Override
    public List<ObMagatzem> mostrarTot() throws SQLException {

        /**
         * Creem l'arraylist de magatzem
         */
        List<ObMagatzem> magatzem = new ArrayList<>();

        /**
         * Creem un String que contrindra la consulta SQL
         */
        String sql = "SELECT * FROM magatzem";

        /**
         * Fem un try per a assegurar-nos que es tanquen les conexions i no es
         * prodeuix cap error
         */
        try (Connection con = ConnexioDB.getConnection(); //Iniciem la col·lecció connection per a connectar amb la base de dades cridant la funcio getConnection que esta a la clase ConnexioDB
                 PreparedStatement stmt = con.prepareStatement(sql); //Iniciem la col·lecció preparedStatement per a enviar mitjançant "con", la consulta SQL a la base de dades
                 ResultSet rs = stmt.executeQuery();) { //Iniciem ResultSet per a poder obtenir el resultat de la consulta que s'ha fet a la base de dades

            /**
             * Creem un while que iterarà mentres s'obtinguin resultats en el
             * resulset, quan aquest sigui fals sortirà del while
             */
            while (rs.next()) {
                /**
                 * Creem un objecte magatzem i establim els seus atributs
                 */
                ObMagatzem mag = new ObMagatzem();

                /**
                 * A continuacio amb l'objecte "mag" fem un set on el seu
                 * contingut serà el resultat de la query SQL
                 */
                mag.setIdEntrada(rs.getInt("idMagatzem"));
                mag.setDataEntrada(rs.getString("dataEntrada"));
                mag.setDataSortida(rs.getString("dataSortida"));
                mag.setMotiuSortida(rs.getString("motiuSortida"));
                mag.setValorTotal(rs.getFloat("valorTotal"));
                mag.setTipus(ObMagatzem.tipus.valueOf(rs.getString("tipus")));
                mag.setNomEntrada(rs.getString("nomEntrada"));

                /**
                 * Finalment afegim la info que hem obtingut al array magatzem
                 */
                magatzem.add(mag);
            }
            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            rs.close();
            stmt.close();
            con.close();
        }
        return magatzem;
    }   
    
}
