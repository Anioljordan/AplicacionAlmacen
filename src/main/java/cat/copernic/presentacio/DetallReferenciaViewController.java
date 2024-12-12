package cat.copernic.presentacio;

import cat.copernic.dades.ObReferencia;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class DetallReferenciaViewController {

    @FXML
    private TextField textFieldIdReferencia;
    @FXML
    private TextField textFieldNom;
    @FXML
    private TextField textFieldUoM;
    @FXML
    private TextField textFieldQuantitatProducte;
    @FXML
    private TextField textFieldFamiliaProductes;
    @FXML
    private TextField textFieldProveidor;
    @FXML
    private TextField textFieldDataAlta;
    @FXML
    private TextField textFieldDataBaixa;
    @FXML
    private TextField textFieldPreuUnitari;
    @FXML
    private TextField textFieldMotiuBaixa;
    @FXML
    private Button btnSortir;
    

    /**
     * Aquest metode carregarà les dades a la base de dades
     */
    public void cargarDatosReferencia(ObReferencia referencia) {
        textFieldIdReferencia.setText(String.valueOf(referencia.getIdReferencia()));
        textFieldNom.setText(referencia.getNom());
        textFieldUoM.setText(referencia.getUoM());
        textFieldQuantitatProducte.setText(String.valueOf(referencia.getQuantitatProducte()));
        textFieldFamiliaProductes.setText(referencia.getFamiliaProductes());
        textFieldProveidor.setText(referencia.getProveidor());
        textFieldDataAlta.setText(referencia.getDataAlta().toString());
        textFieldDataBaixa.setText(referencia.getDataBaixa() != null ? referencia.getDataBaixa().toString() : "");
        textFieldPreuUnitari.setText(String.valueOf(referencia.getPreuUnitari()));
        textFieldMotiuBaixa.setText(referencia.getMotiuBaixa());
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
