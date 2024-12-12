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
import cat.copernic.dades.DAOProveidor;
import cat.copernic.dades.ObProveidor;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author marcv
 */
public class ProveidorImpl implements DAOProveidor {

    /**
     * Mètode per a donar d'alta.
     */
    @Override
    public void alta(ObProveidor proveidor) throws SQLException {

        /**
         * String que contè la consulta sql, en aquest cas es un inserció a la
         * taula proveidor
         */
        String sql = "INSERT INTO proveidor (idProveidor, nom, cif, estat, motiu, telefon) VALUES (?, ?, ?, ?, ?, ?)";

        /**
         * Try on s'estableix la connexio amb la base de dades i el prepared
         * statement per a poder realitzar la consulta sql, un insert
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setInt(1, proveidor.getIdProveidor());
            stmt.setString(2, proveidor.getNom());
            stmt.setString(3, proveidor.getCif());
            ObProveidor.estat estat = proveidor.getEstat();
            stmt.setString(4, estat.name());
            stmt.setString(5, proveidor.getMotiuInactivitat());
            stmt.setString(6, proveidor.getTelefon());

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
    public void baixa(int idProv) throws SQLException {
        /**
         * Creem la consutlta SQL
         */
        String sql = "DELETE FROM proveidor WHERE idProveidor = ?";

        /**
         * Primer iniciem el connection i el preparedStatement en el try per a
         * que es carregui la info de la consulta SQL i del id de referència
         */
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idProv)
             */
            stmt.setInt(1, idProv);

            /**
             * Creem un int que emmagatzemarà en nombre de files que s'han
             * eliminat
             */
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminació correcta", "El proveidor s'ha eliminat correctament.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error d'eliminació", "No s'ha pogut trobar el proveidor amb aquest ID.");
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
    public void modificar(ObProveidor proveidor) throws SQLException {

        /**
         * Consulta SQL per a fer un update
         */
        String sql = "UPDATE proveidor SET nom = ?, cif = ?, estat = ?, motiu = ?, telefon = ? WHERE idProveidor = ?";
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            /**
             * Una vegada establerta la connexió i iniciat el PreparedStatement
             * introduïm les dades a la base de dades
             */
            stmt.setString(1, proveidor.getNom());
            stmt.setString(2, proveidor.getCif());
            ObProveidor.estat estat = proveidor.getEstat();
            stmt.setObject(3, estat.name());
            stmt.setString(4, proveidor.getMotiuInactivitat());
            stmt.setString(5, proveidor.getTelefon());
            stmt.setInt(6, proveidor.getIdProveidor());

            /**
             * Executem la consulta d'inserció
             */
            /**
             * Mostrem una alerta en el cas que s'hagin modificat algunes files
             */
            int filesAfectades = stmt.executeUpdate();

            if (filesAfectades > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Modificació realitzada", "El proveidor s'ha modificat correctament.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al modificar", "No s'ha pogut trobar el proveidor amb aquest ID.");
            }
            /**
             * Tanquem les connexions i statements
             */
            stmt.close();
            con.close();

        }
    }

    @Override
    public ObProveidor mostrar(int idProv) throws SQLException {
        /**
         * Creem la consutlta SQL
         */
        String sql = "SELECT * FROM proveidor WHERE  idProveidor = ?";

        /**
         * Iniciem l'objecte proveidor
         */
        ObProveidor proveidor = null;
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

            /**
             * A continuació amb el prepared statment fem un set del valor que
             * coincideixi amb la consulta SQL i el int introduit per l'usuari
             * al javafx (idProv)
             */
            stmt.setInt(1, idProv);

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
                    int idProveidor = rs.getInt("idProveidor");
                    String nom = rs.getString("nom");
                    String cif = rs.getString("cif");

                    /**
                     * Obtenim el valor de l'enum tipus en format objecte tipus
                     */
                    ObProveidor.estat estat = ObProveidor.estat.valueOf(rs.getString("estat"));
                    String motiu = rs.getString("motiu");
                    String telefon = rs.getString("telefon");

                    /**
                     * Intorduïm els valors a l'objecte referència
                     */
                    proveidor = new ObProveidor(idProveidor, nom, cif, estat, motiu, telefon);
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
        return proveidor;
    }

    @Override
    public List<ObProveidor> mostrarTot() throws SQLException {
        /**
         * Creem l'arraylist de familia
         */
        List<ObProveidor> proveidor = new ArrayList<>();

        /**
         * Creem un String que contrindra la consulta SQL
         */
        String sql = "SELECT * FROM proveidor";

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
                 * Creem un objecte preoveidor i establim els seus atributs
                 */
                ObProveidor prov = new ObProveidor();

                /**
                 * A continuacio amb l'objecte "prov" fem un set on el seu
                 * contingut serà el resultat de la query SQL
                 */
                prov.setIdProveidor(rs.getInt("idProveidor"));
                prov.setNom(rs.getString("nom"));
                prov.setCif(rs.getString("cif"));

                /**
                 * Obtenim el valor de l'enum tipus en format objecte tipus
                 */
                prov.setEstat(ObProveidor.estat.valueOf(rs.getString("estat")));
                prov.setMotiuInactivitat(rs.getString("motiu"));
                prov.setTelefon(rs.getString("telefon"));

                /**
                 * Finalment afegim la info que hem obtingut al array proveidor
                 */
                proveidor.add(prov);
            }
            /**
             * Tanquem tot fluxe de dades per evitar que es sobrecarregui el
             * buffer i puguin ocorrer errors a llarg termini
             */
            rs.close();
            stmt.close();
            con.close();
        }
        return proveidor;
    }

    /**
     * Mètode per a importar un fitxer CSV a la base de dades.
     */
    @Override
    public void importarcsv(String path) throws SQLException {
        int filesImportades = 0; // Comptador de files correctament importades o actualitzades
        int filesProcessades = 0; // Comptador de files totals processades

        try (Reader reader = new FileReader(path)) {
            // Analitzar el fitxer CSV sense capçaleres
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            // Obtenir connexió
            try (Connection con = ConnexioDB.getConnection(); PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO proveidor (idProveidor, nom, cif, estat, motiu, telefon) VALUES (?, ?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE nom = VALUES(nom), cif = VALUES(cif), estat = VALUES(estat), motiu = VALUES(motiu), telefon = VALUES(telefon)")) {

                for (CSVRecord record : records) {
                    try {
                        // Assegurar-se que la fila té la quantitat correcta de columnes
                        if (record.size() < 6) {
                            System.out.println("Fila ignorada, menys de 6 columnes: " + record);
                            continue; // Ignorar files que no tinguin prou columnes
                        }

                        // Accedir a les columnes per índex
                        ObProveidor proveidor = new ObProveidor();
                        proveidor.setIdProveidor(Integer.parseInt(record.get(0))); // primera columna
                        proveidor.setNom(record.get(1)); // segona columna
                        proveidor.setCif(record.get(2)); // tercera columna
                        proveidor.setEstat(ObProveidor.estat.valueOf(record.get(3).toUpperCase())); // quarta columna
                        proveidor.setMotiuInactivitat(record.get(4)); // cinquena columna
                        proveidor.setTelefon(record.get(5)); // sisena columna

                        // Assignar paràmetres a la sentència
                        preparedStatement.setInt(1, proveidor.getIdProveidor());
                        preparedStatement.setString(2, proveidor.getNom());
                        preparedStatement.setString(3, proveidor.getCif());
                        preparedStatement.setString(4, proveidor.getEstat().name());
                        preparedStatement.setString(5, proveidor.getMotiuInactivitat());
                        preparedStatement.setString(6, proveidor.getTelefon());

                        preparedStatement.addBatch(); // Afegir al batch
                        filesProcessades++; // Incrementar el comptador de files processades

                    } catch (Exception filaExcepcion) {
                        // Log per indicar que aquesta fila va fallar
                        System.out.println("Error en processar la fila: " + record + ". Error: " + filaExcepcion.getMessage());
                        continue; // Continuar amb la següent fila
                    }
                }

                // Executar el batch només si hi ha files per processar
                if (filesProcessades > 0) {
                    int[] resultatBatch = preparedStatement.executeBatch(); // Executar el batch
                    for (int resultat : resultatBatch) {
                        if (resultat >= 0) { // Només comptar files inserides o actualitzades (resultat positiu)
                            filesImportades++;
                        }
                    }

                    if (filesImportades > 0) {
                        System.out.println("Dades de proveïdors importades correctament.");
                        mostrarAlerta(AlertType.INFORMATION, "Importació Exitosa", "Dades de proveïdors importades correctament.");
                    } else {
                        // Si no es va modificar cap fila, llançar excepció
                        throw new SQLException("No es va modificar cap fila. Operació fallida.");
                    }

                } else {
                    System.out.println("No es van processar files.");
                    mostrarAlerta(AlertType.WARNING, "Importació Incompleta", "No es van processar dades de proveïdors.");
                }
            }

        } catch (SQLException e) {
            // Mostrar alerta d'error en cas de SQLException
            mostrarAlerta(AlertType.ERROR, "Error en la Importació", "S'ha produït un error en la base de dades: " + e.getMessage());
            e.printStackTrace(); // Per a més detalls de l'error en la consola
        } catch (Exception e) {
            // Mostrar alerta d'error per a altres tipus d'excepció
            mostrarAlerta(AlertType.ERROR, "Error en la Importació", "S'ha produït un error inesperat.");
            e.printStackTrace(); // Per a més detalls de l'error en la consola
        }
    }

    @Override
    public void exportarcsv(String path) {
        // Connexió a la base de dades
        try (Connection con = ConnexioDB.getConnection(); Statement statement = con.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM proveidor")) {

            try (FileWriter writer = new FileWriter(path)) {

                // Escriure dades
                while (resultSet.next()) {
                    writer.append(resultSet.getString("idProveidor")).append(",")
                            .append(resultSet.getString("nom")).append(",")
                            .append(resultSet.getString("cif")).append(",")
                            .append(resultSet.getString("estat")).append(",")
                            .append(resultSet.getString("motiu")).append(",")
                            .append(resultSet.getString("telefon")).append("\n");
                }

                // Mostrar alerta d'èxit
                mostrarAlerta(AlertType.INFORMATION, "Exportació Exitosa", "Dades de proveïdors exportades correctament.");

            } catch (IOException e) {
                // Gestionar errors d'escriptura
                mostrarAlerta(AlertType.ERROR, "Error d'Exportació", "Error en exportar les dades: " + e.getMessage());
            }

        } catch (SQLException e) {
            // Gestionar errors de la base de dades
            mostrarAlerta(AlertType.ERROR, "Error de Base de Dades", "Error en connectar amb la base de dades: " + e.getMessage());
        }
    }

    public void mostrarAlerta(Alert.AlertType tipus, String titol, String missatge) {
        Alert alerta = new Alert(tipus);
        alerta.setTitle(titol);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }

    public boolean estaEnUs(String nomProveidor) throws SQLException {
        // Comprovar si el proveïdor està en ús en famílies
        String queryFam = "SELECT COUNT(*) FROM familia WHERE proveidorDefault = ?";
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement ps = con.prepareStatement(queryFam)) {
            ps.setString(1, nomProveidor);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }

        // Comprovar si el proveïdor està en ús en referències
        String queryRef = "SELECT COUNT(*) FROM referencia WHERE proveidor = ?";
        try (Connection con = ConnexioDB.getConnection(); PreparedStatement ps = con.prepareStatement(queryRef)) {
            ps.setString(1, nomProveidor);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }

        return false;
    }

}
