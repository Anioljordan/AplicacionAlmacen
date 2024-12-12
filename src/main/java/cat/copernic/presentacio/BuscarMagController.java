package cat.copernic.presentacio;

import cat.copernic.dades.ObMagatzem;
import cat.copernic.dades.ObUsuari;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuscarMagController {

    @FXML
    private TextField labelID;
    
    @FXML
    private TextField labelNom;
    
    @FXML
    private TextField labelDataEntrada;
    
    @FXML
    private TextField labelDataSortida;
    
    @FXML
    private TextField labelMotiuSortida;
    
    @FXML
    private TextField labelValorTotal;
    
    @FXML
    private TextField labelTipus;
    
    private ObUsuari usr;

    private ObUsuari.esManager tipusUsuari; 

    public void carregarDades(ObMagatzem magatzem) {
        labelID.setText(String.valueOf(magatzem.getIdEntrada()));
        labelNom.setText(magatzem.getNomEntrada());
        labelDataEntrada.setText(magatzem.getDataEntrada());
        labelDataSortida.setText(magatzem.getDataSortida());
        labelMotiuSortida.setText(magatzem.getMotiuSortida());
        labelValorTotal.setText(String.valueOf(magatzem.getValorTotal()));
        labelTipus.setText(magatzem.getTipus().toString());
    }


    
    @FXML
    private void tancar() {
        Stage stage = (Stage) labelID.getScene().getWindow();
        stage.close();
    }
}
