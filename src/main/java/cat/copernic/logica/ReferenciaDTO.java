/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.ObReferencia;
import cat.copernic.dades.impl.ReferenciaImpl;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author marcv
 */
public class ReferenciaDTO {
    
    private ReferenciaImpl referenciaImpl = new ReferenciaImpl();
    
    public void alta(ObReferencia referencia) throws SQLException {
        referenciaImpl.alta(referencia);
    }
    
    public void baixa(int idRefe) throws SQLException {
        referenciaImpl.baixa(idRefe);
    }
    
    public void modificar(ObReferencia referencia) throws SQLException {
        referenciaImpl.modificar(referencia);
    }
    
    public ObReferencia mostrar(int idRefe) throws SQLException {
        return referenciaImpl.mostrar(idRefe);
    }
    
    public List<ObReferencia> mostrarTot() throws SQLException {
        return referenciaImpl.mostrarTot();
    }
    
    public List<ObProveidor> obtenirTotsElsProveidors() throws SQLException {
        return referenciaImpl.obtenirTotsElsProveidors();
    }
    
    public List<ObFamilia> obtenirTotesLesFamilias() throws SQLException {
        return referenciaImpl.obtenirTotesLesFamilias();
    }
    
}
