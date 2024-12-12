package cat.copernic.presentacio;

import cat.copernic.dades.ConnexioDB;
import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObUsuari;
import cat.copernic.logica.FamiliaDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FamiliaViewController implements ControllerInterface {

    @FXML
    private TableView<ObFamilia> taulaFamilia;
    @FXML
    private TableColumn<ObFamilia, Integer> clmnID;
    @FXML
    private TableColumn<ObFamilia, String> clmnNom;
    @FXML
    private TableColumn<ObFamilia, String> clmnDescri;
    @FXML
    private TableColumn<ObFamilia, String> clmnDataA;
    @FXML
    private TableColumn<ObFamilia, String> clmnDataB;
    @FXML
    private TableColumn<ObFamilia, String> clmnProv;
    @FXML
    private TableColumn<ObFamilia, String> clmnObs;
    @FXML
    private TableColumn<ObFamilia, Float> clmnDesc;
    @FXML
    private Button btnAlta;
    @FXML
    private Button btnBaixa;
    @FXML
    private Button btnModificar;

    private ObservableList<ObFamilia> families;
    private ObservableList<ObFamilia> llistaFamilies = FXCollections.observableArrayList();
    private FamiliaDTO familiaDTO = new FamiliaDTO();
    private ObUsuari usr;
    private ObUsuari.esManager tipusUsuari;

    @FXML
    public void initialize() {
        // Recuperar les dades de l'usuari i tipus d'usuari des de UserSession
        this.usr = UserSession.getInstance().getUsuari();
        this.tipusUsuari = UserSession.getInstance().getTipusUsuari();

        // Configurar les columnes
        clmnID.setCellValueFactory(new PropertyValueFactory<>("idFamilia"));
        clmnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        clmnDescri.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        clmnDataA.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        clmnDataB.setCellValueFactory(new PropertyValueFactory<>("dataBaixa"));
        clmnProv.setCellValueFactory(new PropertyValueFactory<>("proveidorDefault"));
        clmnObs.setCellValueFactory(new PropertyValueFactory<>("observacions"));
        clmnDesc.setCellValueFactory(new PropertyValueFactory<>("descompteGeneral"));

        // Configurar botons segons el tipus d'usuari
        configurarBotons();

        // Carregar dades
        carregarDades();
    }

    private void carregarDades() {
        families = FXCollections.observableArrayList();
        // Obtenir la connexió a la base de dades
        try (Connection con = ConnexioDB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM familia");
            while (rs.next()) {
                int id = rs.getInt("idFamilia");
                String nom = rs.getString("nom");
                String descripcio = rs.getString("descripcio");
                String dataAlta = rs.getString("dataAlta");
                String dataBaixa = rs.getString("dataBaixa");
                String proveidor = rs.getString("proveidorDefault");
                String observacions = rs.getString("observacions");
                float descompte = rs.getFloat("descompteGeneral");
                families.add(new ObFamilia(id, nom, descripcio, dataAlta, dataBaixa, proveidor, observacions, descompte));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        taulaFamilia.setItems(families);
    }

    @FXML
    private void goToPrincipal() throws IOException {
        App.setRoot("principal");
    }

    @FXML
    private void goToAltaFam() throws IOException {
        App.setRoot("AltaFam");
    }

    @FXML
    private void eliminarFila() {
        // Obtenir la fila seleccionada
        ObFamilia seleccionat = taulaFamilia.getSelectionModel().getSelectedItem();
        // Comprovar si hi ha una fila seleccionada
        if (seleccionat != null) {
            // Obtenir l'ID de l'entrada seleccionada
            int idEntrada = seleccionat.getIdFamilia();
            // Confirmar l'acció amb l'usuari
            Alert confirmacio = new Alert(Alert.AlertType.CONFIRMATION, "Estàs segur que vols eliminar l'entrada amb ID: " + idEntrada + "?");
            confirmacio.setTitle("Confirmació d'eliminació");
            confirmacio.setHeaderText(null);
            confirmacio.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        familiaDTO.baixa(idEntrada);
                    } catch (SQLException ex) {
                        Logger.getLogger(FamiliaViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    carregarDades(); // Torna a carregar les dades després de l'eliminació
                }
            });
        } else {
            // Informar l'usuari que no hi ha cap fila seleccionada
            Alert alert = new Alert(Alert.AlertType.WARNING, "Si us plau, selecciona una entrada a eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void modificarFila() {
        // Obtenir la fila seleccionada
        ObFamilia seleccionat = taulaFamilia.getSelectionModel().getSelectedItem();
        if (seleccionat != null) {
            try {
                // Carregar l'FXML de la finestra de modificació
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cat/copernic/presentacio/ModificarFam.fxml"));
                Parent root = loader.load();
                // Obtenir el controlador de la finestra de modificació
                ModificarFamController modificarController = loader.getController();
                // Passar les dades de la fila seleccionada al controlador de la finestra de modificació
                modificarController.omplirCamps(seleccionat);
                // Crear un nou Stage per a la finestra de modificació
                Stage stage = new Stage();
                stage.setTitle("Modificar Entrada de Família");
                stage.setScene(new Scene(root));
                stage.showAndWait(); // Espera que la finestra es tanqui
                // Actualitzar la taula després de la modificació
                carregarDades();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar una alerta si no hi ha cap fila seleccionada
            Alert alert = new Alert(Alert.AlertType.WARNING, "Si us plau, selecciona una entrada a modificar.");
            alert.showAndWait();
        }
    }

    public static class FamiliaNotFoundException extends Exception {

        public FamiliaNotFoundException(String message) {
            super(message);
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

    @FXML
    private void buscarFamilia() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Familia");
        dialog.setHeaderText("Introdueix l'ID de la família:");
        dialog.setContentText("ID:");
        dialog.showAndWait().ifPresent(idInput -> {
            try {
                if (idInput == null || idInput.trim().isEmpty()) {
                    throw new FamiliaNotFoundException("L'ID de la família no pot ser nul o buit.");
                }
                int idFam = Integer.parseInt(idInput);
                ObFamilia familia = familiaDTO.mostrar(idFam);
                if (familia == null) {
                    throw new FamiliaNotFoundException("No s'ha trobat cap família amb aquest ID.");
                }
                // Primer canviem a la vista, després busquem el controlador
                App.setRoot("BuscarFam");
                // Obtenim el controlador de la vista 'mostrarFamilia'
                BuscarFamController controller = App.getController("BuscarFam");
                controller.mostrarFamilia(idFam);
            } catch (NumberFormatException e) {
                // Utilitzar el mètode mostrarAlerta de FamiliaViewController
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "L'ID ha de ser un número vàlid.");
            } catch (FamiliaNotFoundException e) {
                App.mostrarAlerta(Alert.AlertType.WARNING, "Família no trobada", e.getMessage());
            } catch (Exception e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No es va poder carregar la vista: " + e.getMessage());
            }
        });
    }
}
