/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cat.copernic.dades;

import java.sql.SQLException;

/**
 *
 * @author marcv
 */
public interface DAOUsuari {
    void alta(ObUsuari usuari) throws SQLException;
    
    boolean existeix(String username);
    
    ObUsuari autenticarUsuari(String usuari, String contrasenya) throws SQLException;
}
