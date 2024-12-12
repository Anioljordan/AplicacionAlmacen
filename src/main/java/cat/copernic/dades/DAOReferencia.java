/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cat.copernic.dades;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author marcv
 */
public interface DAOReferencia {

    /**
     * Metode que dona d'alta una referencia i que necessita per a ser iniciat
     * l'objecte referencia, llança si o si una excepcio de SQL.
     */
    void alta(ObReferencia referencia) throws SQLException;

    /**
     * Metode que dona de baixa una referencia i que necessita per a ser iniciat
     * un identificador unic id, llança si o si una excepcio de SQL.
     */
    void baixa(int idReferencia) throws SQLException;

    /**
     * Metode que permet modificar una referencia i que necessita per a ser
     * iniciat l'objecte referencia, que llança si o si una excepcio de SQL.
     */
    void modificar(ObReferencia referencia) throws SQLException;

    /**
     * Metode que mostra una referencia i retorna un objecte ObReferencia,
     * necessita per a ser iniciat un identificador unic id, llança si o si una
     * excepcio de SQL.
     */
    ObReferencia mostrar(int idReferencia) throws SQLException;

    /**
     * Metode que mostra totes les referencias de la base de dades i retorna una
     * llista d'objectes ObReferencia, també llança si o si una excepcio de SQL.
     */
    List<ObReferencia> mostrarTot() throws SQLException;

}
