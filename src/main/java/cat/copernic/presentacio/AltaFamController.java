package cat.copernic.presentacio;

import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.logica.FamiliaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author marcv
 */
public class AltaFamController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDescrip;

    @FXML
    private ComboBox<ObProveidor> cmbProv; // Per triar entre ACTIU, INACTIU, SUSPES

    @FXML
    private TextField txtDataA;

    @FXML
    private TextField txtDataB;

    @FXML
    private TextField txtObs;

    @FXML
    private TextField txtDescom;

    private FamiliaDTO familiaDTO = new FamiliaDTO();

    /**
     * Establim els valors que tindrà el combobox
     */
    public void initialize() {
        try {
            // Obtenim els proveïdors de la base de dades
            List<ObProveidor> proveidors = familiaDTO.obtenirTotsElsProveidors();

            // Carregar la llista de proveïdors al ComboBox
            cmbProv.getItems().setAll(proveidors);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar els proveïdors: " + e.getMessage());
        }
    }

    private boolean validarFecha(String fecha) {
        // Expressió regular per validar dates en format yyyy/MM/dd o permetre buits
        String regex = "^$|^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$"; // Format yyyy-MM-dd
        return fecha.matches(regex);
    }

    @FXML
    private void altaFamilia() throws IOException {
        try {
            // Verificar que l'ID no estigui buit i sigui un número
            if (txtId.getText().isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en IdFamilia", "El camp ID no pot estar buit.");
                return;
            }

            int idFam;
            try {
                // Intentar convertir l'ID a número
                idFam = Integer.parseInt(txtId.getText());
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en IdFamilia", "El camp ID ha de ser un número vàlid.");
                return;
            }

            // Recollir els valors dels camps del formulari
            String nom = txtNom.getText();
            String descripcio = txtDescrip.getText();

            if (descripcio.isEmpty()) {
                descripcio = null;
                mostrarAlerta(Alert.AlertType.INFORMATION, "Entitat buida", "La descripció s'ha desat buida.");
            }

            String dataA = txtDataA.getText();
            String dataB = txtDataB.getText();

            // Validar que la data d'alta no estigui buida
            if (dataA.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en data d'alta", "El camp de data d'alta no pot estar buit.");
                return;
            }

            // Validar dataA i dataB
            if (!validarFecha(dataA)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en data d'alta", "La data d'alta no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            if (!dataB.isEmpty() && !validarFecha(dataB)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en data de baixa", "La data de baixa no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(dataA, formatter);
            if (dataB != null && !dataB.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(dataB, formatter);

                if (dataBaixa.isBefore(dataAlta)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no pot ser anterior a la data d'alta.");
                    alert.showAndWait();
                    return;
                }
            }

            ObProveidor proveidor = cmbProv.getValue();
            String observacions = txtObs.getText();

            float descompte;

            // Validar el camp de descompte
            try {
                if (txtDescom.getText().isEmpty()) {
                    descompte = 0;
                } else {
                    descompte = Float.parseFloat(txtDescom.getText());
                }
            } catch (NumberFormatException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en descompte", "El descompte ha de ser un número decimal.");
                return;
            }

            // Validar que no hi hagi camps obligatoris buits
            if (nom.isEmpty() || proveidor == null || observacions.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "Tots els camps són obligatoris.");
                return;
            }

            // Convertir l'objecte proveïdor a String amb el nom seleccionat
            String proveidorNom = proveidor.getNom();

            // Crear un nou objecte ObFamilia amb els valors recollits
            ObFamilia novaEntrada = new ObFamilia(idFam, nom, descripcio, dataA, dataB.isEmpty() ? null : dataB, proveidorNom, observacions, descompte);

            // Cridar al mètode alta de MagatzemImpl per inserir la nova família
            familiaDTO.alta(novaEntrada);

            // Mostrar missatge d'èxit
            mostrarAlerta(Alert.AlertType.INFORMATION, "Alta correcta", "La família s'ha donat d'alta correctament.");

            App.setRoot("FamiliaView");

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error en alta", "Hi ha hagut un error en la base de dades: " + e.getMessage());
        }
    }

    public void mostrarAlerta(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void goToFamilia() throws IOException {
        App.setRoot("FamiliaView");
    }
}
