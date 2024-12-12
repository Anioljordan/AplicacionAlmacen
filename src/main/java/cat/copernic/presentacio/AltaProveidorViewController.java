/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.presentacio;

import cat.copernic.dades.ObProveidor;
import cat.copernic.logica.ProveidorDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Aniol
 */
public class AltaProveidorViewController {

    public void initialize() {
        cmbEstat.getItems().setAll(ObProveidor.estat.values()); // Carregar els estats en el ComboBox
    }

    @FXML
    private TextField txtIdProveidor;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtCif;

    @FXML
    private ComboBox<ObProveidor.estat> cmbEstat; // Per triar entre ACTIU, INACTIU, SUSPES

    @FXML
    private TextField txtMotiuInactivitat;

    @FXML
    private TextField txtTelefon;

    private ProveidorDTO ProveidorDTO = new ProveidorDTO();

    // Mètode per validar que el telèfon tingui exactament 9 números
    private boolean validarTelefon(String telefon) {
        return telefon.matches("\\+?\\d{9,11}");
    }

    // Mètode per validar que el CIF tingui exactament 8 números i una lletra
    private boolean validarCif(String cif) {
        // Expressió regular per CIF: 8 números seguits d'una lletra
        return cif.matches("\\d{8}[A-Za-z]");
    }

    @FXML
    private void altaProveidor() throws IOException {
        try {
            // Recollir els valors dels camps del formulari
            int idProveidor = Integer.parseInt(txtIdProveidor.getText());
            String nom = txtNom.getText();
            String cif = txtCif.getText();
            ObProveidor.estat estat = cmbEstat.getValue();
            String motiuInactivitat = txtMotiuInactivitat.getText();
            String telefon = txtTelefon.getText();

            // Validar les dades
            if (nom.isEmpty() || cif.isEmpty() || telefon.isEmpty() || estat == null) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "Tots els camps són obligatoris.");
                return;
            }

            // Validar el número de telèfon
            if (!validarTelefon(telefon)) {
                // Mostrar alerta si la validació del telèfon falla
                App.mostrarAlerta(Alert.AlertType.ERROR, "Telèfon no vàlid", "El número de telèfon ha de tenir exactament 9 dígits.");
                return;
            }

            // Validar el CIF
            if (!validarCif(cif)) {
                // Mostrar alerta si la validació del CIF falla
                App.mostrarAlerta(Alert.AlertType.ERROR, "CIF no vàlid", "El CIF ha de tenir 8 números seguits d'una lletra.");
                return;
            }

            // Crear un nou objecte ObProveidor amb els valors recollits
            ObProveidor nouProveidor = new ObProveidor(idProveidor, nom, cif, estat, motiuInactivitat, telefon);

            // Cridar el mètode alta de ProveidorImpl per inserir el nou proveïdor
            ProveidorDTO.alta(nouProveidor);

            // Mostrar missatge d'èxit
            App.mostrarAlerta(Alert.AlertType.INFORMATION, "Alta correcta", "El proveïdor s'ha donat d'alta correctament.");
            goToProveidor();
        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "Hi ha hagut un error en la base de dades: " + e.getMessage());
        } catch (NumberFormatException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "El ID ha de ser un número vàlid.");
        }
    }

    @FXML
    private void goToProveidor() throws IOException {
        App.setRoot("Proveidor");
    }
}
