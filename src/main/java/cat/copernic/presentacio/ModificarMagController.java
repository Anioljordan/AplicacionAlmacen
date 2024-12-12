package cat.copernic.presentacio;

import cat.copernic.dades.ObMagatzem;
import cat.copernic.logica.MagatzemDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarMagController {

    @FXML
    private TextField txtIdEntrada;

    @FXML
    private TextField txtNomEntrada;

    @FXML
    private TextField txtDataEntrada;

    @FXML
    private TextField txtDataSortida;

    @FXML
    private TextField txtMotiuSortida;

    @FXML
    private TextField txtValorTotal;

    @FXML
    private ComboBox<ObMagatzem.tipus> comboTipus;

    private MagatzemDTO magatzemDTO = new MagatzemDTO();

    public void carregarDades(ObMagatzem magatzem) {
        txtIdEntrada.setText(String.valueOf(magatzem.getIdEntrada()));
        txtNomEntrada.setText(magatzem.getNomEntrada());
        txtDataEntrada.setText(magatzem.getDataEntrada());
        txtDataSortida.setText(magatzem.getDataSortida());
        txtMotiuSortida.setText(magatzem.getMotiuSortida());
        txtValorTotal.setText(String.valueOf(magatzem.getValorTotal()));
        comboTipus.getItems().setAll(ObMagatzem.tipus.values()); // Carreguem els tipus disponibles al ComboBox
        comboTipus.setValue(magatzem.getTipus()); // Seleccionem el tipus actual
    }

    // Método para validar fechas con el formato yyyy-MM-dd
    private boolean validarFecha(String fecha) {
        // Expresión regular para validar fechas en formato yyyy-MM-dd
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        return fecha.matches(regex);
    }

    /**
     * Mètode per guardar les modificacions realitzades.
     */
    @FXML
    private void guardarModificacioMag() {
        try {
            // Comprovacions per assegurar que els camps no són buits o nuls
            if (txtIdEntrada.getText().isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp ID no pot estar buit.");
                return;
            }

            int idEntrada = Integer.parseInt(txtIdEntrada.getText());
            String nom = txtNomEntrada.getText();

            // Comprovar si el nom és nul o buit
            if (nom.isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp NOM no pot estar buit.");
                return;
            }

            String dataE = txtDataEntrada.getText();

            // Comprovar si la data d'entrada és nul·la o buida
            if (dataE.isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp DATA D'ENTRADA no pot estar buit.");
                return;
            }

            // Validar la fecha de entrada
            if (!validarFecha(dataE)) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "La data d'entrada no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            // Obtenemos la data de sortida
            String dataS = txtDataSortida.getText(); // Obtener el texto del campo de salida

            // Validar la fecha de salida solo si no está vacía
            if (dataS != null && !dataS.isEmpty() && !validarFecha(dataS)) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "La data de sortida no és vàlida. Format: yyyy-MM-dd.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(dataE, formatter);
            if (dataS != null && !dataS.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(dataS, formatter);

                if (dataBaixa.isBefore(dataAlta)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no pot ser anterior a la data d'alta.");
                    alert.showAndWait();
                    return;
                }
            }
            // Obtener el motivo de salida, permitiendo que esté vacío
            String motiu = txtMotiuSortida.getText(); // Obtener el texto del motivo

            // Asegúrate de que 'motiu' no sea nulo ni vacío
            if (motiu != null && motiu.isEmpty()) {
                motiu = null; // Asignar null si está vacío
            }

            // Comprovar si el valor total és nul·la o buida
            String valorTotalStr = txtValorTotal.getText();
            if (valorTotalStr.isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp VALOR TOTAL no pot estar buit.");
                return;
            }

            // Intentar parsear el valor total, asegurándote de que es un número válido
            float valor;
            try {
                valor = Float.parseFloat(valorTotalStr);
            } catch (NumberFormatException e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El valor total no és vàlid. Assegureu-vos que és un número.");
                return;
            }

            ObMagatzem.tipus tipus = comboTipus.getValue();

            // Comprovar si el tipus és nul
            if (tipus == null) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp TIPUS no pot estar buit.");
                return;
            }

            // Creem un nou objecte ObMagatzem amb les dades actualitzades
            ObMagatzem magatzemActualitzat = new ObMagatzem(idEntrada, nom, dataE,
                    (dataS != null && !dataS.isEmpty()) ? dataS : null, motiu, valor, tipus);

            // Actualitzem les dades a la base de dades mitjançant el DAO
            magatzemDTO.modificar(magatzemActualitzat);

            // Tancar la finestra després de guardar
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Referència modificada exitosament.");
            successAlert.showAndWait();
            Stage stage = (Stage) txtIdEntrada.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error en els valors numèrics. Assegureu-vos que els valors introduïts són correctes.");
        } catch (Exception e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "S'ha produït un error al guardar les modificacions: " + e.getMessage());
        }
    }

    /**
     * Mètode per cancel·lar les modificacions i tancar la finestra.
     */
    @FXML
    private void cancelarModificacioMag() {
        Stage stage = (Stage) txtIdEntrada.getScene().getWindow();
        stage.close();
    }
}
