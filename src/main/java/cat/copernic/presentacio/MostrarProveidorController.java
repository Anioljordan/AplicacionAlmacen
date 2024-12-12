/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.presentacio;

/**
 *
 * @author Aniol
 */
import cat.copernic.dades.ObProveidor;
import cat.copernic.logica.ProveidorDTO;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MostrarProveidorController {

    @FXML
    private TextField lblIdProveidor;

    @FXML
    private TextField lblNom;

    @FXML
    private TextField lblCif;

    @FXML
    private TextField lblEstat;

    @FXML
    private TextField lblMotiuInactivitat;

    @FXML
    private TextField lblTelefon;

    private ProveidorDTO ProveidorDTO = new ProveidorDTO();

    public void mostrarProveidor(int idProv) {
        try {
            ObProveidor proveidor = ProveidorDTO.mostrar(idProv);
            if (proveidor != null) {
                lblIdProveidor.setText(String.valueOf(proveidor.getIdProveidor()));
                lblNom.setText(proveidor.getNom());
                lblCif.setText(proveidor.getCif());
                lblEstat.setText(proveidor.getEstat().name());
                lblMotiuInactivitat.setText(proveidor.getMotiuInactivitat());
                lblTelefon.setText(proveidor.getTelefon());
            } else {
                // Manejo si no se encuentra el proveedor
                lblIdProveidor.setText("No trobat");
            }
        } catch (Exception e) {
            // Manejar errores
            e.printStackTrace();
        }
    }

    @FXML
    private void goToProveidor() throws IOException {
        App.setRoot("Proveidor");
    }
}
