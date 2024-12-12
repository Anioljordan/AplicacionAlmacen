package cat.copernic.presentacio;

import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.ObReferencia;
import cat.copernic.logica.ReferenciaDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModificarReferenciaViewController {

    @FXML
    private TextField idReferenciaTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField UomTextField;
    @FXML
    private TextField QuantitatTextField;
    @FXML
    private ComboBox<ObFamilia> FamiliaComboBox; 
    @FXML
    private ComboBox<ObProveidor> ProveidorComboBox;
    @FXML
    private TextField DataAltaTextField;
    @FXML
    private TextField DataBaixaTextField;
    @FXML
    private TextField PreuUnitariTextField;
    @FXML
    private TextField MotiuTextField;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private ObFamilia familia;

    private ObReferencia referencia;

    private ObProveidor proveidor;

    private ReferenciaDTO ReferenciaDTO = new ReferenciaDTO();

    private ObservableList<ObReferencia> referencias;

    public void inizialitate(ObReferencia referencia, ObservableList<ObReferencia> referencias) {
        this.referencia = referencia;
        this.referencias = referencias; // Guardamos la lista observable de referencias

        idReferenciaTextField.setText(String.valueOf(referencia.getIdReferencia()));
        nomTextField.setText(referencia.getNom());
        UomTextField.setText(referencia.getUoM());
        QuantitatTextField.setText(String.valueOf(referencia.getQuantitatProducte()));

        // Cargar datos en los ComboBox
        cargarDatosComboBox();

        // Buscar la familia y el proveedor correspondientes a los Strings
        ObFamilia familiaSeleccionada = buscarFamiliaPerNom(referencia.getFamiliaProductes());
        ObProveidor proveidorSeleccionat = buscarProveidorPerNom(referencia.getProveidor());

        // Establecer valores en los ComboBox
        FamiliaComboBox.setValue(familiaSeleccionada);
        ProveidorComboBox.setValue(proveidorSeleccionat);

        DataAltaTextField.setText(referencia.getDataAlta());
        DataBaixaTextField.setText(referencia.getDataBaixa());
        PreuUnitariTextField.setText(String.valueOf(referencia.getPreuUnitari()));
        MotiuTextField.setText(referencia.getMotiuBaixa());
        
    }

    private ObFamilia buscarFamiliaPerNom(String nomFamilia) {
        for (ObFamilia familia : FamiliaComboBox.getItems()) {
            if (familia.getNom().equals(nomFamilia)) {
                return familia; // Retorna la família que coincideix amb el nom
            }
        }
        return null; // Retorna null si no es troba cap coincidència
    }

    private ObProveidor buscarProveidorPerNom(String nomProveidor) {
        for (ObProveidor proveidor : ProveidorComboBox.getItems()) {
            if (proveidor.getNom().equals(nomProveidor)) {
                return proveidor; // Retorna el proveïdor que coincideix amb el nom
            }
        }
        return null; // Retorna null si no es troba cap coincidència
    }

    @FXML
    public void cargarDatosComboBox() {
        try {
            // Limpiar los items actuales antes de añadir los nuevos
            FamiliaComboBox.getItems().clear();
            ProveidorComboBox.getItems().clear();

            // Obtener la lista de familias de la base de datos
            List<ObFamilia> families = ReferenciaDTO.obtenirTotesLesFamilias();
            if (!families.isEmpty()) {
                ObservableList<ObFamilia> listaFamilias = FXCollections.observableArrayList(families);
                FamiliaComboBox.setItems(listaFamilias);
            } else {
                System.out.println("No s'han trobat famílies.");
            }

            // Obtener la lista de proveedores de la base de datos
            List<ObProveidor> proveidors = ReferenciaDTO.obtenirTotsElsProveidors();
            if (!proveidors.isEmpty()) {
                ObservableList<ObProveidor> listaProveidors = FXCollections.observableArrayList(proveidors);
                ProveidorComboBox.setItems(listaProveidors);
            } else {
                System.out.println("No s'han trobat proveïdors.");
            }

        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar les dades: " + e.getMessage());
        }
    }

    private void carregarProveidors() {
        try {
            List<ObProveidor> proveidors = ReferenciaDTO.obtenirTotsElsProveidors(); // Mètode per obtenir proveïdors de la base de dades
            ProveidorComboBox.getItems().clear();
            ProveidorComboBox.getItems().addAll(proveidors); // Afegim els proveïdors al ComboBox
        } catch (SQLException e) {
            e.printStackTrace();
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar els proveïdors.");
        }
    }

    private void carregarFamilies() {
        try {
            List<ObFamilia> families = ReferenciaDTO.obtenirTotesLesFamilias(); // Mètode per obtenir famílies de la base de dades
            FamiliaComboBox.getItems().clear();
            FamiliaComboBox.getItems().addAll(families); // Afegim les famílies al ComboBox
        } catch (SQLException e) {
            e.printStackTrace();
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar les famílies.");
        }
    }

    private boolean validarFecha(String fecha) {
        // Expresión regular para validar fechas en formato yyyy/MM/dd o permitir vacío
        String regex = "^$|^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        return fecha.matches(regex);
    }

    @FXML
    private void handleguardar(ActionEvent event) {
        // Verificar si los campos están vacíos
        if (nomTextField.getText().isEmpty() || UomTextField.getText().isEmpty() || QuantitatTextField.getText().isEmpty()
                || FamiliaComboBox.getSelectionModel().isEmpty() || ProveidorComboBox.getSelectionModel().isEmpty()
                || DataAltaTextField.getText().isEmpty() || PreuUnitariTextField.getText().isEmpty()) {
            // Si algún campo está vacío, mostrar una alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Camps buits");
            alert.setHeaderText(null);
            alert.setContentText("Si us plau, completa tots els camps abans de guardar.");
            alert.showAndWait();
            return; // Salir del método sin guardar
        }

        // Validar formato de fechas en yyyy/MM/dd
        if (!validarFecha(DataAltaTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La data d'alta no té el format correcte (yyyy/MM/dd).");
            alert.showAndWait();
            return;
        }

        String dataBaixaText = DataBaixaTextField.getText(); // Comprovar si està buit o no
        if (dataBaixaText != null && !dataBaixaText.isEmpty() && !validarFecha(dataBaixaText)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no té el format correcte (yyyy/MM/dd).");
            alert.showAndWait();
            return;
        }

        try {
            String nouNomb = nomTextField.getText();
            String nouUOM = UomTextField.getText();
            int nouQuan = Integer.parseInt(QuantitatTextField.getText());
            ObFamilia familia = FamiliaComboBox.getValue();
            ObProveidor proveidor = ProveidorComboBox.getValue();
            String nouDAlt = DataAltaTextField.getText();
            String nouDBai = dataBaixaText; // Això ja no serà nul
            String nouMotiu = MotiuTextField.getText(); // També comprovem el motiu

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(nouDAlt, formatter);
            if (nouDBai != null && !nouDBai.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(nouDBai, formatter);
                if (dataBaixa.isBefore(dataAlta)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no pot ser anterior a la data d'alta.");
                    alert.showAndWait();
                    return;
                }
            }

            Float nouPreu = Float.valueOf(PreuUnitariTextField.getText());
            // Actualitzar la referència
            referencia.setNom(nouNomb);
            referencia.setUoM(nouUOM);
            referencia.setQuantitatProducte(nouQuan);
            referencia.setFamiliaProductes(String.valueOf(familia));
            referencia.setProveidor(String.valueOf(proveidor));
            referencia.setDataAlta(nouDAlt);
            referencia.setDataBaixa(nouDBai == null || nouDBai.isEmpty() ? null : nouDBai); // Comprovar si està buit
            referencia.setPreuUnitari(nouPreu);
            referencia.setMotiuBaixa(nouMotiu == null || nouMotiu.isEmpty() ? null : nouMotiu); // Comprovar si està buit

            // Guardar a la base de dades
            ReferenciaDTO.modificar(referencia);

            // Actualitzar la taula
            int index = referencias.indexOf(referencia);
            if (index >= 0) {
                referencias.set(index, referencia);
            }

            // Missatge d'èxit
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Referència modificada exitosament.");
            successAlert.showAndWait();
            Stage stage = (Stage) idReferenciaTextField.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en actualitzar les dades: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleSalir(ActionEvent event) {
        Stage stage = (Stage) nomTextField.getScene().getWindow();
        stage.close(); // Cierra la ventana actual
    }
}
