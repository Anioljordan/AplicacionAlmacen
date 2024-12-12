/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.presentacio;

import cat.copernic.dades.ObMagatzem;
import cat.copernic.logica.MagatzemDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author marcv
 */
public class AltaMagController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDataE;

    @FXML
    private ComboBox<ObMagatzem.tipus> cmbTipus; // Per a triar entre ACTIU, INACTIU, SUSPÈS

    @FXML
    private TextField txtDataS;

    @FXML
    private TextField txtMotiu;

    @FXML
    private TextField txtValor;

    private MagatzemDTO magatzemDTO = new MagatzemDTO();

    /**
     * Establim els valors que tindrà el combobox
     */
    public void initialize() {
        cmbTipus.getItems().setAll(ObMagatzem.tipus.values()); // Carregar els tipus en el ComboBox
    }

    // Mètode per validar dates amb el format yyyy-MM-dd
    private boolean validarFecha(String data) {
        // Expressió regular per validar dates en format yyyy-MM-dd
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        return data.matches(regex);
    }

    @FXML
    private void altaEntrada() throws IOException {
        try {
            // Verificar que l'ID no estigui buit i sigui un número
            if (txtId.getText().isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "El camp ID no pot estar buit.");
                return;
            }

            int idMag;
            try {
                // Intentar convertir l'ID a número
                idMag = Integer.parseInt(txtId.getText());
            } catch (NumberFormatException e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "El camp ID ha de ser un número vàlid.");
                return;
            }

            // Recollir els valors dels camps del formulari
            String nom = txtNom.getText();
            String dataE = txtDataE.getText();
            String dataS = txtDataS.getText();
            ObMagatzem.tipus tipus = cmbTipus.getValue();
            String motiuS = txtMotiu.getText();
            float valorT;

            // Validar el camp de valor total
            try {
                valorT = Float.parseFloat(txtValor.getText());
            } catch (NumberFormatException e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "El valor total ha de ser un número vàlid.");
                return;
            }

            // Validar que no hi hagi camps obligatoris buits (excloent dataS i motiuS)
            if (nom.isEmpty() || dataE.isEmpty() || tipus == null) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "El nom, la data d'entrada i el tipus són obligatoris.");
                return;
            }

            // Validar les dates si no estan buides
            if (!dataE.isEmpty() && !validarFecha(dataE)) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en data d'entrada", "La data d'entrada no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            if (!dataS.isEmpty() && !validarFecha(dataS)) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error en data de sortida", "La data de sortida no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(dataE, formatter);
            if (dataS != null && !dataS.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(dataS, formatter);

                if (dataBaixa.isBefore(dataAlta)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La data de sortida no pot ser anterior a la data d'entrada.");
                    alert.showAndWait();
                    return;
                }
            }

            // Crear un nou objecte ObMagatzem amb els valors recollits
            ObMagatzem novaEntrada = new ObMagatzem(idMag, nom, dataE, dataS.isEmpty() ? null : dataS, motiuS.isEmpty() ? null : motiuS, valorT, tipus);

            // Cridar al mètode alta de MagatzemImpl per inserir la nova entrada
            magatzemDTO.alta(novaEntrada);

            // Mostrar missatge d'èxit
            App.mostrarAlerta(Alert.AlertType.INFORMATION, "Alta correcta", "L'entrada s'ha donat d'alta correctament.");

            App.setRoot("MagatzemView");

        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "Hi ha hagut un error a la base de dades: " + e.getMessage());
        }
    }

    @FXML
    private void goToMagatzem() throws IOException {
        App.setRoot("MagatzemView");
    }
}
