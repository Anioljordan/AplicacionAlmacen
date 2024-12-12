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
public interface DAOMagatzem {
    /**
 * Metode que dona d'alta un producete (familia o referencia) al magatzem i que necessita per a ser iniciat 
 * l'objecte ObMagatzem, llança si o si una excepcio de SQL.
 */
void alta(ObMagatzem magatzem) throws SQLException;

/**
 * Metode que dona de baixa un producete (familia o referencia) del magatzem i que necessita per a ser iniciat 
 * un identificador unic id, llança si o si una excepcio de SQL.
 */
void baixa(int idEntrada) throws SQLException;

/**
 * Metode que permet modificar una entrada o sortida del magatzem i que necessita per a ser iniciat 
 * l'objecte ObMagatzem, que llança si o si una excepcio de SQL.
 */
void modificar(ObMagatzem magatzem) throws SQLException;

/**
 * Metode que mostra una entrada o sortida magatzem i retorna un objecte ObMagatzem, necessita 
 * per a ser iniciat un identificador unic id, llança si o si una excepcio de SQL.
 */
ObMagatzem mostrar(int idEntrada) throws SQLException;

/**
 * Metode que mostra totes les entrades i sortides del magatzem que hi ha a la base de dades i retorna una 
 * llista d'objectes ObMagatzem, també llança si o si una excepcio de SQL.
 */
List<ObMagatzem> mostrarTot() throws SQLException;

}
