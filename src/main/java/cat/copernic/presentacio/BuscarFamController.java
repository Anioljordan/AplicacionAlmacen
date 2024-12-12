package cat.copernic.presentacio;

import cat.copernic.dades.ObFamilia;
import cat.copernic.logica.FamiliaDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class BuscarFamController {

    @FXML
    private TextField lblIdFam;

    @FXML
    private TextField lblNom;

    @FXML
    private TextField lblDescrip;

    @FXML
    private TextField lblDataA;

    @FXML
    private TextField lblDataB;

    @FXML
    private TextField lblProv;

    @FXML
    private TextField lblObs;

    @FXML
    private TextField lblDesc;

    private FamiliaDTO familiaDTO = new FamiliaDTO();

    @FXML
    public void mostrarFamilia(int idFamilia) {
        try {
            // Aquí pots fer una consulta a la base de dades per obtenir les dades de la família
            ObFamilia familia = familiaDTO.mostrar(idFamilia); // Assegura't que aquest mètode existeix

            // Comprovar si la família existeix
            if (familia != null) {
                // Mostrar la informació de la família en els camps de la vista
                lblIdFam.setText(String.valueOf(familia.getIdFamilia()));
                lblNom.setText(familia.getNom());
                lblDescrip.setText(familia.getDescripcio());
                lblDataA.setText(familia.getDataAlta());
                lblDataB.setText(familia.getDataBaixa());
                lblProv.setText(familia.getProveidorDefault());
                lblObs.setText(familia.getObservacions());
                lblDesc.setText(String.valueOf(familia.getDescompteGeneral()));
            } else {
                App.mostrarAlerta(Alert.AlertType.WARNING, "Familia No Troba", "No s'ha trobat cap família amb l'ID: " + idFamilia);
            }
        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error de BD", "No es poden carregar les dades: " + e.getMessage());
        }
    }

    @FXML
    private void goToFamilia() throws IOException {
        App.setRoot("FamiliaView");
    }
}
