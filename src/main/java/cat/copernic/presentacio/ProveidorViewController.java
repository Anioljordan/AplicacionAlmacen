package cat.copernic.presentacio;

import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.ObUsuari;
import cat.copernic.logica.ProveidorDTO;
import java.io.File;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProveidorViewController implements ControllerInterface {

    @FXML
    private void goToActualitzarProveidorController() throws IOException {
        App.setRoot("ActualitzarProveidorController");
    }

    @FXML
    private void goToPrincipal() throws IOException {
        App.setRoot("principal");
    }

    @FXML
    private void goToAltaProveidorView() throws IOException {
        App.setRoot("AltaProveidor");
    }

    @FXML
    private TableView<ObProveidor> tableProveidor;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnAlta;
    @FXML
    private Button btnBaixa;
    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<ObProveidor, Integer> colId;
    @FXML
    private TableColumn<ObProveidor, String> colNom;
    @FXML
    private TableColumn<ObProveidor, String> colCif;
    @FXML
    private TableColumn<ObProveidor, ObProveidor.estat> colEstat;
    @FXML
    private TableColumn<ObProveidor, String> colMotiu;
    @FXML
    private TableColumn<ObProveidor, String> colTelefon;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField cifField;
    @FXML
    private TextField telefonField;
    private ObservableList<ObProveidor> llistaProveidors = FXCollections.observableArrayList();
    private ObUsuari usr;
    private ObUsuari.esManager tipusUsuari;
    private ProveidorDTO ProveidorDTO = new ProveidorDTO();

    @FXML
    public void initialize() throws SQLException {
        // Recuperar les dades de l'usuari i tipus d'usuari des de UserSession
        this.usr = UserSession.getInstance().getUsuari();
        this.tipusUsuari = UserSession.getInstance().getTipusUsuari();

        // Configura les columnes
        colId.setCellValueFactory(new PropertyValueFactory<>("idProveidor"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colCif.setCellValueFactory(new PropertyValueFactory<>("cif"));
        colEstat.setCellValueFactory(new PropertyValueFactory<>("estat"));
        colMotiu.setCellValueFactory(new PropertyValueFactory<>("motiuInactivitat"));
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        mostrarProveidors();

        // Configurar botons segons el tipus d'usuari
        configurarBotons();

        btnImport.setOnAction(event -> importarcsv());
        btnExport.setOnAction(event -> exportarcsv());
    }

    @FXML
    private void eliminarProveidor() {
        ObProveidor proveidorSeleccionat = tableProveidor.getSelectionModel().getSelectedItem();
        if (proveidorSeleccionat == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecciona un proveïdor per eliminar.");
            alerta.showAndWait();
            return;
        }

        try {
            // Comprovar si el proveïdor està en ús en famílies o referències
            boolean enUs = ProveidorDTO.estaEnUs(proveidorSeleccionat.getNom());
            if (enUs) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No es pot eliminar el proveïdor perquè està en ús en alguna família o referència.");
                alerta.showAndWait();
                return;
            }

            // Diàleg de confirmació
            Alert confirmacio = new Alert(Alert.AlertType.CONFIRMATION, "Estàs segur que vols eliminar el proveïdor " + proveidorSeleccionat.getNom() + "?", ButtonType.YES, ButtonType.NO);
            confirmacio.setTitle("Confirmació d'Eliminació");
            confirmacio.setHeaderText(null);
            confirmacio.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.YES) {
                    try {
                        ProveidorDTO.baixa(proveidorSeleccionat.getIdProveidor());
                        llistaProveidors.remove(proveidorSeleccionat); // Actualitzar la llista
                        tableProveidor.refresh(); // Refrescar la taula
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error en eliminar el proveïdor: " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en comprovar si el proveïdor està en ús: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void mostrarProveidors() {
        try {
            // Obtener la lista de proveedores desde la base de datos
            llistaProveidors.addAll(ProveidorDTO.mostrarTot());
            tableProveidor.setItems(llistaProveidors); // Asignar los datos a la tabla
        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error de BD", "No es poden carregar les dades: " + e.getMessage());
        }
    }

    @FXML
    private void editarProveidor() {
        // Obtener el proveedor seleccionado
        ObProveidor proveidorSeleccionat = tableProveidor.getSelectionModel().getSelectedItem();
        if (proveidorSeleccionat == null) {
            // Mostrar alerta si no se ha seleccionado ningún proveedor
            Alert alerta = new Alert(Alert.AlertType.WARNING, "Selecciona un proveïdor per editar.");
            alerta.showAndWait();
            return;
        }
        try {
            // Cargar la ventana de edición
            Parent root = App.loadFXML("ActualitzarProveidor");
            // Obtener el controlador de la ventana de edición
            ActualitzarProveidorController controladorEdicion = App.getController("ActualitzarProveidor");
            // Pasar el proveedor seleccionado al controlador
            controladorEdicion.setProveidor(proveidorSeleccionat);
            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Editar Proveïdor");
            stage.initModality(Modality.APPLICATION_MODAL); // Hace la ventana modal
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Espera a que la ventana se cierre
            // Refrescar la tabla para reflejar los cambios
            tableProveidor.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscarProveidor() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Proveïdor");
        dialog.setHeaderText("Ingresa l'ID del proveïdor:");
        dialog.setContentText("ID:");

        dialog.showAndWait().ifPresent(idInput -> {
            try {
                if (idInput == null || idInput.trim().isEmpty()) {
                    throw new ProveidorNotFoundException("L'ID del proveïdor no pot estar buit.");
                }
                int idProv = Integer.parseInt(idInput);

                ObProveidor proveidor = ProveidorDTO.mostrar(idProv);
                if (proveidor == null) {
                    throw new ProveidorNotFoundException("No s'ha trobat cap proveïdor amb aquest ID.");
                }

                // Primero cambiamos a la vista, luego buscamos el controlador
                App.setRoot("mostrarProveidor");

                // Obtener el controlador de la vista 'mostrarProveidor'
                MostrarProveidorController controller = App.getController("mostrarProveidor");
                controller.mostrarProveidor(idProv);

            } catch (NumberFormatException e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "L'ID ha de ser un nmero vàlid.");
            } catch (ProveidorNotFoundException e) {
                App.mostrarAlerta(Alert.AlertType.WARNING, "No s'ha trobat el proveïdor", e.getMessage());
            } catch (Exception e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'ha pogut carregar la vista: " + e.getMessage());
            }
        });
    }

    @FXML
    public void importarcsv() {
        // Crear un objeto FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        // Mostrar el diálogo de selección de archivo
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            try {
                ProveidorDTO.importarcsv(path);
                llistaProveidors.clear();
                llistaProveidors.addAll(ProveidorDTO.mostrarTot());
                tableProveidor.setItems(llistaProveidors);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No s'ha seleccionat cap arxiu.");
        }
    }

    @FXML
    public void exportarcsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        var file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ProveidorDTO.exportarcsv(file.getPath());
                App.mostrarAlerta(AlertType.INFORMATION, "Èxit en exportar", "Dades de proveïdors exportades amb èxit.");
            } catch (Exception e) {
                App.mostrarAlerta(AlertType.ERROR, "Error", "Hi ha hagut un error: " + e.getMessage());
            }
        }
    }

    private void configurarBotons() {
        boolean esVenedor = (tipusUsuari == ObUsuari.esManager.VENEDOR);
        if (esVenedor) {
            btnAlta.setDisable(true);
            btnBaixa.setDisable(true);
            btnModificar.setDisable(true);
            btnImport.setDisable(true);
            btnExport.setDisable(true);
        } else {
            btnAlta.setDisable(false);
            btnBaixa.setDisable(false);
            btnModificar.setDisable(false);
            btnImport.setDisable(false);
            btnExport.setDisable(false);
        }
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

    public static class ProveidorNotFoundException extends Exception {

        public ProveidorNotFoundException(String message) {
            super(message);
        }
    }
}
