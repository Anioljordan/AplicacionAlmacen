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
public interface DAOProveidor {
    
     /**
      * Metode que dona d'alta un proveidor i que necessita per a ser iniciat 
      * l'objecte ObProveidor, llança si o si una excepcio de SQL.
     */
    void alta(ObProveidor proveidor) throws SQLException;
    
    /**
     * Metode que dona de baixa un proveidor i que necessita per a ser iniciat 
     * un identificador unic id, llança si o si una excepcio de SQL.
     */
    void baixa(int idProveidor) throws SQLException; 
     
    /**
     * Metode que permet modificar un proveidor i que necessita per a ser iniciat 
     * l'objecte ObProveidor, que llança si o si una excepcio de SQL.
     */
    void modificar(ObProveidor proveidor) throws SQLException;
     
    /**
     * Metode que mostra un proveidor i retorna un objecte ObProveidor, necessita 
     * per a ser iniciat un identificador unic id, llança si o si una excepcio de SQL.
     */
    ObProveidor mostrar(int idProveidor) throws SQLException;
    
     
    /**
     * Metode que mostra totes els proveidors de la base de dades i retorna una 
     * llista d'objectes ObProveidor, també llança si o si una excepcio de SQL.
     */
    List<ObProveidor> mostrarTot() throws SQLException;
    
    /**
     * Metode que importa el contingut d'un CSV a la base de dades, també llança 
     * si o si una excepcio de SQL.
     */
    void importarcsv(String path) throws SQLException;
    
    /**
     * Metode que exporta el contingut de la base de dades a un CSV, també llança 
     * si o si una excepcio de SQL.
     */
    void exportarcsv(String path) throws SQLException;
}
