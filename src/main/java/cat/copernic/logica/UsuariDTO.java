/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import cat.copernic.dades.ObUsuari;
import cat.copernic.dades.impl.UsuariImpl;
import java.sql.SQLException;

/**
 *
 * @author marcv
 */
public class UsuariDTO {
    private UsuariImpl usuariImpl = new UsuariImpl();
    
    public void alta(ObUsuari usuari) throws SQLException {
        usuariImpl.alta(usuari);
    }
    
    public boolean existeix(String username) {
        return usuariImpl.existeix(username);
    }
    
    public ObUsuari autenticarUsuari(String nomUsuari, String contrasenya) throws SQLException {
        return usuariImpl.autenticarUsuari(nomUsuari, contrasenya);
    }
}
