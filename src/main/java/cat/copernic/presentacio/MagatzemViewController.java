package cat.copernic.presentacio;

import cat.copernic.dades.ObMagatzem;
import cat.copernic.dades.ConnexioDB;
import cat.copernic.dades.ObUsuari;
import cat.copernic.logica.MagatzemDTO;
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

public class MagatzemViewController implements ControllerInterface {

    @FXML
    private TableView<ObMagatzem> taulaMagatzem;
    @FXML
    private TableColumn<ObMagatzem, Integer> columnaID;
    @FXML
    private TableColumn<ObMagatzem, String> columnaNom;
    @FXML
    private TableColumn<ObMagatzem, String> columnaDataEntrada;
    @FXML
    private TableColumn<ObMagatzem, String> columnaDataSortida;
    @FXML
    private TableColumn<ObMagatzem, String> columnaMotiuSortida;
    @FXML
    private TableColumn<ObMagatzem, Float> columnaValorTotal;
    @FXML
    private TableColumn<ObMagatzem, String> columnaTipus;
    @FXML
    private Button btnAlta;
    @FXML
    private Button btnBaixa;
    @FXML
    private Button btnModificar;

    private ObservableList<ObMagatzem> productes;
    private MagatzemDTO magatzemDTO = new MagatzemDTO();
    private ObUsuari usr;
    private ObUsuari.esManager tipusUsuari;

    @FXML
    public void initialize() {
        // Recuperar les dades de l'usuari i el tipus d'usuari des de UserSession
        this.usr = UserSession.getInstance().getUsuari();
        this.tipusUsuari = UserSession.getInstance().getTipusUsuari();

        // Configurar les columnes
        columnaID.setCellValueFactory(new PropertyValueFactory<>("idEntrada"));
        columnaNom.setCellValueFactory(new PropertyValueFactory<>("nomEntrada"));
        columnaDataEntrada.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
        columnaDataSortida.setCellValueFactory(new PropertyValueFactory<>("dataSortida"));
        columnaMotiuSortida.setCellValueFactory(new PropertyValueFactory<>("motiuSortida"));
        columnaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        columnaTipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));

        // Configurar botons segons el tipus d'usuari
        configurarBotons();

        // Carregar dades
        carregarDades();
    }

    void carregarDades() {
        productes = FXCollections.observableArrayList();
        // Obtenir la connexió a la base de dades
        try (Connection con = ConnexioDB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM magatzem");
            while (rs.next()) {
                int id = rs.getInt("idEntrada");
                String nom = rs.getString("nomEntrada");
                String dataEntrada = rs.getString("dataEntrada");
                String dataSortida = rs.getString("dataSortida");
                String motiuSortida = rs.getString("motiuSortida");
                float valorTotal = rs.getFloat("valorTotal");
                ObMagatzem.tipus tipus = ObMagatzem.tipus.valueOf(rs.getString("tipus"));
                productes.add(new ObMagatzem(id, nom, dataEntrada, dataSortida, motiuSortida, valorTotal, tipus));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        taulaMagatzem.setItems(productes);
    }

    @FXML
    private void goToPrincipal() throws IOException {
        App.setRoot("principal");
    }

    @FXML
    private void goToAltaMag() throws IOException {
        App.setRoot("AltaMag");
    }

    @FXML
    private void goToBuscarMag() throws IOException {
        App.setRoot("BuscarMag");
    }

    @FXML
    private void eliminarFila() {
        // Obtenir la fila seleccionada
        ObMagatzem seleccionat = taulaMagatzem.getSelectionModel().getSelectedItem();
        // Comprovar si hi ha una fila seleccionada
        if (seleccionat != null) {
            // Obtenir l'ID de l'entrada seleccionada
            int idEntrada = seleccionat.getIdEntrada();
            // Confirmar l'acció amb l'usuari
            Alert confirmacio = new Alert(Alert.AlertType.CONFIRMATION, "Estàs segur que vols eliminar l'entrada amb ID: " + idEntrada + "?");
            confirmacio.setTitle("Confirmació d'eliminació");
            confirmacio.setHeaderText(null);
            confirmacio.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        magatzemDTO.baixa(idEntrada);
                    } catch (SQLException ex) {
                        Logger.getLogger(MagatzemViewController.class.getName()).log(Level.SEVERE, null, ex);
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
        ObMagatzem seleccionat = taulaMagatzem.getSelectionModel().getSelectedItem();
        if (seleccionat != null) {
            try {
                // Carregar l'FXML de la finestra de modificació
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cat/copernic/presentacio/ModificarMag.fxml"));
                Parent root = loader.load();
                // Obtenir el controlador de la finestra de modificació
                ModificarMagController modificarController = loader.getController();
                // Passar les dades de la fila seleccionada al controlador de la finestra de modificació
                modificarController.carregarDades(seleccionat);
                // Crear un nou Stage per a la finestra de modificació
                Stage stage = new Stage();
                stage.setTitle("Modificar Entrada de Magatzem");
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

    @FXML
    private void buscarMagatzem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Magatzem");
        dialog.setHeaderText("Introdueix l'ID de l'entrada a buscar:");
        dialog.showAndWait().ifPresent(idInput -> {
            try {
                int id = Integer.parseInt(idInput);
                ObMagatzem magatzem = magatzemDTO.mostrar(id);
                if (magatzem != null) {
                    // Carregar el fitxer FXML per mostrar els detalls
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cat/copernic/presentacio/BuscarMag.fxml"));
                    Parent root = loader.load();
                    // Obtenir el controlador de la finestra de detalls
                    BuscarMagController detallsController = loader.getController();
                    detallsController.carregarDades(magatzem); // Passar les dades de l'entrada
                    // Crear un nou Stage per mostrar els detalls
                    Stage stage = new Stage();
                    stage.setTitle("Detalls de l'Entrada de Magatzem");
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    throw new MagatzemNotFoundException("No s'ha trobat cap entrada amb ID: " + id);
                }
            } catch (NumberFormatException e) {
                App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "L'ID introduït no és vàlid. Si us plau, introdueix un número enter.");
            } catch (MagatzemNotFoundException e) {
                App.mostrarAlerta(Alert.AlertType.WARNING, "No trobat", e.getMessage());
            } catch (SQLException e) {
    App.mostrarAlerta(Alert.AlertType.ERROR, "Error de connexió", "S'ha produït un error en connectar a la base de dades.");
} catch (IOException e) {
    e.printStackTrace();
}
    });
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

@Override
public void setUsuari(ObUsuari usuari) {
    this.usr = usuari;
}

@Override
public void setTipusUsuari(ObUsuari.esManager tipusUsuari) {
    this.tipusUsuari = tipusUsuari;
    configurarBotons();
}

public static class MagatzemNotFoundException extends Exception {
    public MagatzemNotFoundException(String message) {
        super(message);
    }
}
}
