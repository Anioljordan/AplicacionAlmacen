/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import java.sql.SQLException;
import cat.copernic.dades.impl.FamiliaImpl;
import java.util.List;

/**
 *
 * @author marcv
 */
public class FamiliaDTO {
    private FamiliaImpl familiaImpl = new FamiliaImpl();
    public void alta(ObFamilia familia) throws SQLException {
        familiaImpl.alta(familia);
    }
    
    public void baixa(int idFam) throws SQLException {
        familiaImpl.baixa(idFam);
    }
    
    public void modificar(ObFamilia familia) throws SQLException {
        familiaImpl.modificar(familia);
    }
    
    public ObFamilia mostrar(int idFam) throws SQLException {
            return familiaImpl.mostrar(idFam);
    }
    
    public List<ObFamilia> mostrarTot() throws SQLException {
        return familiaImpl.mostrarTot();
    }
    
    public List<ObProveidor> obtenirTotsElsProveidors() throws SQLException {
        return familiaImpl.obtenirTotsElsProveidors();
    }
    
}
