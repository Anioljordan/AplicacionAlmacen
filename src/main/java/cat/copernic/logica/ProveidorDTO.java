/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.impl.ProveidorImpl;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author marcv
 */
public class ProveidorDTO {

    private ProveidorImpl proveidorImpl = new ProveidorImpl();

    public void alta(ObProveidor proveidor) throws SQLException {
        proveidorImpl.alta(proveidor);
    }
    
    public void baixa(int idProv) throws SQLException {
        proveidorImpl.baixa(idProv);
    }
    
    public void modificar(ObProveidor proveidor) throws SQLException {
        proveidorImpl.modificar(proveidor);
    }
    
    public ObProveidor mostrar(int idProv) throws SQLException {
        return proveidorImpl.mostrar(idProv);
    }
    
    public List<ObProveidor> mostrarTot() throws SQLException {
        return proveidorImpl.mostrarTot();
    }
    
    public void importarcsv(String path) throws SQLException {
        proveidorImpl.importarcsv(path);
    }
    
    public void exportarcsv(String path) {
        proveidorImpl.exportarcsv(path);
    }
    
    public boolean estaEnUs(String nomProveidor) throws SQLException {
        return proveidorImpl.estaEnUs(nomProveidor);
    }

}
