/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import cat.copernic.dades.ObMagatzem;
import cat.copernic.dades.impl.MagatzemImpl;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author marcv
 */
public class MagatzemDTO {
    
    private MagatzemImpl magatzemImpl = new MagatzemImpl();
    
    public void alta(ObMagatzem magatzem) throws SQLException {
        magatzemImpl.alta(magatzem);
    }
    
    public void baixa(int idEnt) throws SQLException {
        magatzemImpl.baixa(idEnt);
    }
    
    public void modificar(ObMagatzem magatzem) throws SQLException {
        magatzemImpl.modificar(magatzem);
    }
    
    public ObMagatzem mostrar(int idEnt) throws SQLException {
        return magatzemImpl.mostrar(idEnt);
    }
    
    public List<ObMagatzem> mostrarTot() throws SQLException {
        return magatzemImpl.mostrarTot();
    }
}
