package cat.copernic.presentacio;

import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.logica.FamiliaDTO;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModificarFamController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDescr;

    @FXML
    private TextField txtDataA;

    @FXML
    private TextField txtDataB;

    @FXML
    private TextField txtObs;

    @FXML
    private ComboBox<ObProveidor> cmbProv;

    @FXML
    private TextField txtDesc;

    private ObFamilia entradaActual;
    private FamiliaDTO familiaDTO = new FamiliaDTO();

    public void omplirCamps(ObFamilia familia) {
        this.entradaActual = familia;
        txtId.setText(String.valueOf(familia.getIdFamilia()));
        txtNom.setText(familia.getNom());
        txtDescr.setText(familia.getDescripcio());
        txtDataA.setText(familia.getDataAlta());
        txtDataB.setText(familia.getDataBaixa());
        cmbProv.setValue(familia.getProveidor());
        txtDesc.setText(String.valueOf(familia.getDescompteGeneral()));
        txtObs.setText(familia.getObservacions());
    }

    public void initialize() {
        // Afegir els proveïdors al ComboBox
        try {
            // Obtenim els proveïdors de la base de dades
            List<ObProveidor> proveidors = familiaDTO.obtenirTotsElsProveidors();

            // Carregar la llista de proveïdors al ComboBox
            cmbProv.getItems().setAll(proveidors);

        } catch (SQLException e) {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'ha pogut carregar els proveïdors: " + e.getMessage());
        }
    }

    private boolean validarFecha(String fecha) {
        // Expresión regular para validar fechas en formato yyyy/MM/dd o permitir vacío
        String regex = "^$|^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        return fecha.matches(regex);
    }

    @FXML
    private void guardarModificacioFam() {
        try {
            // Comprovacions per assegurar que els camps no són buits o nuls
            if (txtId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp ID no pot estar buit.");
                alert.showAndWait();
                return;
            }

            int idFamilia = Integer.parseInt(txtId.getText());
            String nouNom = txtNom.getText();
            String novaDescripcio = txtDescr.getText();
            String novaDataA = txtDataA.getText();
            String novaDataB = txtDataB.getText();
            ObProveidor proveidorSeleccionat = cmbProv.getValue();

            // Comprovar si el proveïdor seleccionat és nul
            if (proveidorSeleccionat == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selecciona un proveïdor.");
                alert.showAndWait();
                return;
            }

            // Comprovar si el camp 'Data d'Alta' és nul·la o buida
            if (novaDataA.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp 'Data d'Alta' no pot estar buit.");
                alert.showAndWait();
                return;
            }

            // Validar si la data d'alta té el format correcte yyyy-MM-dd
            if (!validarFecha(novaDataA)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp 'Data d'Alta' ha de tenir el format yyyy-MM-dd.");
                alert.showAndWait();
                return;
            }

            // Validar si la data de baixa té el format correcte yyyy-MM-dd, si no és buida
            if (novaDataB != null && !novaDataB.isEmpty() && !validarFecha(novaDataB)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp 'Data de Baixa' ha de tenir el format yyyy-MM-dd.");
                alert.showAndWait();
                return;
            }

            // Comparar les dates d'alta i baixa
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(novaDataA, formatter);
            if (novaDataB != null && !novaDataB.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(novaDataB, formatter);

                if (dataBaixa.isBefore(dataAlta)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no pot ser anterior a la data d'alta.");
                    alert.showAndWait();
                    return;
                }
            }

            // Comprovar si el nom és nul o buit
            if (nouNom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp 'Nom' no pot estar buit.");
                alert.showAndWait();
                return;
            }

            // Comprovar si les observacions són nul·les o buides
            String observacions = txtObs.getText();
            if (observacions.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El camp 'Observacions' no pot estar buit.");
                alert.showAndWait();
                return;
            }

            // Obtenim el descompte, si està buit, assignem null
            float descompte;
            if (txtDesc.getText().isEmpty()) {
                try {
                    descompte = 0;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Si us plau, introdueix un valor numèric correcte per al camp 'Descompte General'.");
                    alert.showAndWait();
                    return;
                }
            } else {
                try {
                    descompte = Float.parseFloat(txtDesc.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Si us plau, introdueix un valor numèric correcte per al camp 'Descompte General'.");
                    alert.showAndWait();
                    return;
                }
            }

            // Actualització de l'objecte entradaActual amb els nous valors
            entradaActual.setNom(nouNom);
            entradaActual.setDescripcio(novaDescripcio == null || novaDescripcio.isEmpty() ? null : novaDescripcio);
            entradaActual.setDataAlta(novaDataA);
            entradaActual.setDataBaixa(novaDataB != null && !novaDataB.isEmpty() ? novaDataB : null);
            entradaActual.setProveidorDefault(proveidorSeleccionat.toString());
            entradaActual.setObservacions(observacions);
            entradaActual.setDescompteGeneral(descompte);

            // Modificar l'entrada actual a la base de dades
            familiaDTO.modificar(entradaActual);

            // Tancar la finestra actual
            Stage stage = (Stage) txtId.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en actualitzar les dades.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Format numèric incorrecte en un dels camps.");
            alert.showAndWait();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en el format de la data.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error inesperat: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelarModificacioFam() {
        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();
    }

}
