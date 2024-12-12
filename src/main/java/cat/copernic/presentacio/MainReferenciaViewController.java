package cat.copernic.presentacio;

import cat.copernic.dades.ConnexioDB;
import cat.copernic.dades.ObReferencia;
import cat.copernic.dades.ObUsuari;
import cat.copernic.logica.ReferenciaDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainReferenciaViewController implements ControllerInterface {

    @FXML
    private Button botoSortida;

    @FXML
    private TableView<ObReferencia> taulaReferencia;

    @FXML
    private TableColumn<ObReferencia, Integer> columnaID;

    @FXML
    private TableColumn<ObReferencia, String> columnaNom;

    @FXML
    private TableColumn<ObReferencia, String> columnaUoM;

    @FXML
    private TableColumn<ObReferencia, Integer> columnaQuantitatProducte;

    @FXML
    private TableColumn<ObReferencia, String> columnaFamiliaProductes;

    @FXML
    private TableColumn<ObReferencia, String> columnaProveidor;

    @FXML
    private TableColumn<ObReferencia, String> columnaDataAlta;

    @FXML
    private TableColumn<ObReferencia, String> columnaDataBaixa;

    @FXML
    private TableColumn<ObReferencia, Float> columnaPreuUnitari;

    @FXML
    private TableColumn<ObReferencia, String> columnaMotiuBaixa;

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnBaixa;

    @FXML
    private Button btnModificar;

    private TextField idTextField;

    private ObservableList<ObReferencia> referencias;

    private ReferenciaDTO ReferenciaDTO = new ReferenciaDTO();

    private ObUsuari usr;

    private ObUsuari.esManager tipusUsuari;

    @FXML
    public void initialize() {
        // Recuperar les dades de l'usuari i tipus d'usuari des de UserSession
        this.usr = UserSession.getInstance().getUsuari();
        this.tipusUsuari = UserSession.getInstance().getTipusUsuari();

        // Configurar les columnes
        columnaID.setCellValueFactory(new PropertyValueFactory<>("idReferencia"));
        columnaNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnaUoM.setCellValueFactory(new PropertyValueFactory<>("UoM"));
        columnaQuantitatProducte.setCellValueFactory(new PropertyValueFactory<>("quantitatProducte"));
        columnaFamiliaProductes.setCellValueFactory(new PropertyValueFactory<>("familiaProductes"));
        columnaProveidor.setCellValueFactory(new PropertyValueFactory<>("proveidor"));
        columnaDataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        columnaDataBaixa.setCellValueFactory(new PropertyValueFactory<>("dataBaixa"));
        columnaPreuUnitari.setCellValueFactory(new PropertyValueFactory<>("preuUnitari"));
        columnaMotiuBaixa.setCellValueFactory(new PropertyValueFactory<>("motiuBaixa"));

        // Configurar botons segons el tipus d'usuari
        configurarBotons();

        // Carregar dades
        carregarDades();
    }

    private void carregarDades() {
        referencias = FXCollections.observableArrayList();
        // Obtener la conexión a la base de datos
        try (Connection con = ConnexioDB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM referencia");
            while (rs.next()) {
                int idReferencia = rs.getInt("idReferencia");
                String nom = rs.getString("nom");
                String uom = rs.getString("uom");
                int quantitatProducte = rs.getInt("quantitatProducte");
                String familiaProductes = rs.getString("familiaProductes");
                String proveidor = rs.getString("proveidor");
                String dataAlta = rs.getString("dataAlta");
                String dataBaixa = rs.getString("dataBaixa");
                float preuUnitari = rs.getFloat("preuUnitari");
                String motiuBaixa = rs.getString("motiuBaixa");
                referencias.add(new ObReferencia(idReferencia, nom, uom, quantitatProducte, familiaProductes, proveidor, dataAlta, dataBaixa, preuUnitari, motiuBaixa));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error", "Les dades no s'han carregat correctament: " + e.getMessage());
        }
        taulaReferencia.setItems(referencias);
    }

    @FXML
    private void handleBotoMod() {
        // Obtener la referencia seleccionada
        ObReferencia referenciaSeleccionada = taulaReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModificarReferenciaView.fxml"));
                Parent root = loader.load();
                // Obtener el controlador de ModificarReferenciaViewController
                ModificarReferenciaViewController modificarController = loader.getController();
                // Pasar la referencia seleccionada y la lista observable al controlador de modificación
                modificarController.inizialitate(referenciaSeleccionada, referencias);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modificar Referència");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensaje("Error", "No s'ha pogut obrir la finestra de modificació: " + e.getMessage());
            }
        } else {
            // Mostrar un mensaje si no hay ninguna referencia seleccionada
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Sense selecció");
            noSelectionAlert.setHeaderText("Cap referència seleccionada");
            noSelectionAlert.setContentText("Si us plau, selecciona una referència de la taula.");
            noSelectionAlert.show();
        }
    }

    @FXML
    private void handleBotoAlta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AltaReferenciaView.fxml"));
            Parent root = loader.load();
            // Obtener el controlador de AltaReferenciaViewController
            AltaReferenciaViewController altaController = loader.getController();
            altaController.setReferencias(referencias);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Alta Referència");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error", "No s'ha pogut obrir la finestra d'alta: " + e.getMessage());
        }
    }

    public static class ReferenciaNotFoundException extends Exception {

        public ReferenciaNotFoundException(String message) {
            super(message);
        }
    }

 @FXML
private void BuscaId() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Buscar Referència per ID");
    dialog.setHeaderText("Introduïu el ID de la referència que voleu buscar");
    dialog.setContentText("ID:");

    dialog.showAndWait().ifPresent(idInput -> {
        try {
            // Comprovació per veure si l'input és buit
            if (idInput.trim().isEmpty()) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El camp no pot estar buit.");
                return; // Acaba l'execució si el camp està buit
            }

            int idRefe = Integer.parseInt(idInput);
            ObReferencia referencia = ReferenciaDTO.mostrar(idRefe);

            // Comprova si la referència és null i mostra un missatge d'error
            if (referencia == null) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'ha trobat cap referència amb aquest ID.");
                return; // Acaba l'execució si no es troba la referència
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuscarIdReferenciaView.fxml"));
            Parent root = loader.load();
            DetallReferenciaViewController controller = loader.getController();
            controller.cargarDatosReferencia(referencia);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (NumberFormatException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "L'ID ha de ser un número enter vàlid.");
        } catch (SQLException | IOException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Hi ha hagut un error en consultar la base de dades: " + e.getMessage());
        }
    });
}



    @FXML
    private void handleBotoBaixa(ActionEvent event) {
        // Obtener la referencia seleccionada
        ObReferencia referenciaSeleccionada = taulaReferencia.getSelectionModel().getSelectedItem();
        if (referenciaSeleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmació d'Eliminació");
            confirmacion.setHeaderText("¿Estás segur de que vols eliminar aquesta referencia?");
            confirmacion.setContentText("Aquesta acció no es pot desfer.");
            Optional<ButtonType> result = confirmacion.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Eliminar la referencia de la base de datos
                    ReferenciaDTO.baixa(referenciaSeleccionada.getIdReferencia());
                    // Eliminar la referencia de la tabla en la interfaz
                    taulaReferencia.getItems().remove(referenciaSeleccionada);
                    // Mostrar mensaje de éxito
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Eliminació Exitosa");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La referencia ha sigut eliminada exitosament.");
                    successAlert.show();
                } catch (SQLException ex) {
                    // Mostrar mensaje de error
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("Error en eliminar la referència");
                    errorAlert.setContentText("No s'ha pogut eliminar la referència. Si us plau, intenta-ho de nou.");
                    errorAlert.show();
                }
            }
        } else {
            // Mostrar un mensaje si no hay ninguna referencia seleccionada
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Sense selecció");
            noSelectionAlert.setHeaderText("Cap referència seleccionada");
            noSelectionAlert.setContentText("Si us plau, selecciona una referència de la taula.");
            noSelectionAlert.show();
        }
    }

    @FXML
    private void handleBotoSortida() {
        Stage stage = (Stage) botoSortida.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBotoMenu() throws IOException {
        App.setRoot("principal");
    }

    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @Override
    public void setUsuari(ObUsuari usuari) {
        this.usr = usuari;
    }

    @Override
    public void setTipusUsuari(ObUsuari.esManager tipusUsuari) {
        this.tipusUsuari = tipusUsuari;
        configurarBotons();
    }

    private void configurarBotons() {
        boolean esVenedor = (tipusUsuari == ObUsuari.esManager.VENEDOR);
        if (esVenedor) {
            btnAlta.setDisable(true);
            btnBaixa.setDisable(true);
            btnModificar.setDisable(true);
        } else {
            btnAlta.setDisable(false);
            btnBaixa.setDisable(false);
            btnModificar.setDisable(false);
        }
    }
}
