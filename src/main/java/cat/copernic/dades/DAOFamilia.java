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
public interface DAOFamilia {
    
     /**
      * Metode que dona d'alta una familia i que necessita per a ser iniciat 
      * l'objecte familia, llança si o si una excepcio de SQL.
     */
    void alta(ObFamilia familia) throws SQLException;
    
    /**
     * Metode que dona de baixa una familia i que necessita per a ser iniciat 
     * un identificador unic id,llança si o si una excepcio de SQL.
     */
    void baixa(int idFamilia) throws SQLException; 
     
    /**
     * Metode que permet modificar una familia i que necessita per a ser iniciat 
     * l'objecte familia, que llança si o si una excepcio de SQL.
     */
    void modificar(ObFamilia familia) throws SQLException;
     
    /**
     * Metode que mostra una familia i retorna un objecte ObFamilia, necessita 
     * per a ser iniciat un identificador unic id, llança si o si una excepcio de SQL.
     */
    ObFamilia mostrar(int idFamilia) throws SQLException;
    
     
    /**
     * Metode que mostra totes les families de la base de dades i retorna una 
     * llista d'objectes ObFamilia, també llança si o si una excepcio de SQL.
     */
    List<ObFamilia> mostrarTot() throws SQLException;
}
