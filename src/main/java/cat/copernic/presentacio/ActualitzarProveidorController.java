package cat.copernic.presentacio;

import cat.copernic.dades.ObProveidor;
import cat.copernic.logica.ProveidorDTO;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActualitzarProveidorController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtCIF;
    @FXML
    private TextField txtTelefon;
    @FXML
    private TextField txtMotiu;

    @FXML
    private ComboBox<String> comboEstat; // Afegir ComboBox per a l'estat

    private ProveidorDTO proveidorDTO = new ProveidorDTO();

    private ObProveidor proveidor;

    public void setProveidor(ObProveidor proveidorSeleccionat) {
        this.proveidor = proveidorSeleccionat;
        txtId.setText(String.valueOf(proveidorSeleccionat.getIdProveidor()));
        txtNom.setText(proveidorSeleccionat.getNom());
        txtCIF.setText(proveidorSeleccionat.getCif());
        txtTelefon.setText(proveidorSeleccionat.getTelefon());
        txtMotiu.setText(proveidorSeleccionat.getMotiuInactivitat());

        comboEstat.getItems().addAll("ACTIU", "INACTIU", "SUSPES");
        comboEstat.setValue(proveidorSeleccionat.getEstat().toString());
    }

    // Mètode per validar que el CIF tingui exactament 8 números seguits d'una lletra
    private boolean validarCIF(String cif) {
        return cif.matches("\\d{8}[A-Za-z]");
    }

    // Mètode per validar que el telèfon tingui exactament 9 números
    private boolean validarTelefon(String telefon) {
        return telefon.matches("\\+?\\d{9,11}");
    }

    // Mètode per validar que els camps no estiguin buits
    private boolean campsNoBuits() {
        return !txtNom.getText().isEmpty()
                && !txtCIF.getText().isEmpty()
                && !txtTelefon.getText().isEmpty()
                && !txtMotiu.getText().isEmpty()
                && comboEstat.getValue() != null;
    }

    @FXML
    private void guardarProveidor() {
        // Verificar si tots els camps estan complets
        if (!campsNoBuits()) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Camps incomplets", "Tots els camps són obligatoris.");
            return; // Detenir el procés si algun camp està buit
        }

        // Convertir el String del ComboBox al enum
        String estatSeleccionat = comboEstat.getValue();

        // Actualitzar els valors del proveïdor només si l'operació és exitosa
        try {
            if (proveidor != null) {
                // Validar el CIF abans de procedir
                if (!validarCIF(txtCIF.getText())) {
                    App.mostrarAlerta(Alert.AlertType.ERROR, "CIF no vàlid", "El CIF ha de tenir 8 números seguits d'una lletra.");
                    return; // Detenir el procés si el CIF no és vàlid
                }

                // Validar el telèfon abans de procedir
                if (!validarTelefon(txtTelefon.getText())) {
                    App.mostrarAlerta(Alert.AlertType.ERROR, "Telèfon no vàlid", "El número de telèfon ha de tenir entre 9-12 dígits.");
                    return; // Detenir el procés si el telèfon no és vàlid
                }

                // Actualitzar les dades del proveïdor
                proveidor.setNom(txtNom.getText());
                proveidor.setCif(txtCIF.getText());
                proveidor.setTelefon(txtTelefon.getText());
                proveidor.setMotiuInactivitat(txtMotiu.getText());

                if (estatSeleccionat != null) {
                    proveidor.setEstat(ObProveidor.estat.valueOf(estatSeleccionat.toUpperCase()));
                }

                // Cridar al mètode de modificació
                proveidorDTO.modificar(proveidor);

                // Tancar la finestra d'edició només si l'operació és exitosa
                Stage stage = (Stage) txtNom.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error en modificar", e.getMessage());
        } catch (IllegalArgumentException e) {
            App.mostrarAlerta(Alert.AlertType.WARNING, "Estat no vàlid", "L'estat seleccionat no és vàlid.");
        }
    }
}
