package cat.copernic.presentacio;

import cat.copernic.dades.ConnexioDB;
import cat.copernic.dades.ObFamilia;
import cat.copernic.dades.ObProveidor;
import cat.copernic.dades.ObReferencia;
import cat.copernic.logica.ReferenciaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AltaReferenciaViewController {

    @FXML
    private TextField idReferenciaTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField UomTextField;
    @FXML
    private TextField QuantitatTextField;
    @FXML
    private ComboBox<ObFamilia> familiaComboBox; // Canviat a ComboBox
    @FXML
    private ComboBox<ObProveidor> proveidorComboBox; // Canviat a ComboBox
    @FXML
    private TextField DataAltaTextField;
    @FXML
    private TextField DataBaixaTextField;
    @FXML
    private TextField PreuUnitariTextField;
    @FXML
    private TextField MotiuTextField;

    @FXML
    private TableView<ObReferencia> taulaReferencias; // Assegura't de tenir una taula

    private ObReferencia referencia;

    private ReferenciaDTO ReferenciaDTO = new ReferenciaDTO();

    private ObservableList<ObReferencia> referencias;

    private MainReferenciaViewController main;

    @FXML
    public void setReferencias(ObservableList<ObReferencia> referencias) {
        this.referencias = referencias;
        taulaReferencias = new TableView<>();
        // Aquí podries actualitzar la teva taula, si és necessari
        taulaReferencias.setItems(referencias);
    }

    @FXML
    public void initialize() {
        inicialitzarComponents();
    }

    private void inicialitzarComponents() {
        carregarFamilies();
        carregarProveidors();
    }

    private void carregarProveidors() {
        try {
            List<ObProveidor> proveidors = ReferenciaDTO.obtenirTotsElsProveidors(); // Assegura't que 'refe' és la instància del teu DAO o servei
            proveidorComboBox.getItems().clear();
            proveidorComboBox.getItems().addAll(proveidors); // Afegeix els proveïdors al ComboBox
        } catch (SQLException e) {
            e.printStackTrace();
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar els proveïdors.");
        }
    }

    private void carregarFamilies() {
        try {
            List<ObFamilia> families = ReferenciaDTO.obtenirTotesLesFamilias(); // Assegura't que 'refe' és la instància del teu DAO o servei
            familiaComboBox.getItems().clear();
            familiaComboBox.getItems().addAll(families); // Afegeix les famílies al ComboBox
        } catch (SQLException e) {
            e.printStackTrace();
            App.mostrarAlerta(Alert.AlertType.ERROR, "Error", "No s'han pogut carregar les famílies.");
        }
    }

    /**
     * Iniciem el carregar dades perquè no sigui null
     */
    public void setMain(MainReferenciaViewController main) {
        this.main = main;
    }

    public void altaReferencia(int idReferencia, String nom, String UoM, int quantitatProducte,
            ObFamilia familiaProductes, ObProveidor proveidor, String dataAlta,
            String dataBaixa, float preuUnitari, String motiuBaixa) throws SQLException {
        // Verificar que els camps obligatoris no estiguin buits
        if (nom == null || nom.isEmpty() || UoM == null || UoM.isEmpty() || quantitatProducte <= 0
                || familiaProductes == null || proveidor == null || dataAlta == null || dataAlta.isEmpty()) {
            throw new IllegalArgumentException("Tots els camps obligatoris han de ser omplerts, excepte la data de baixa i motiu.");
        }

        // Crear la nova instància d'ObReferencia
        ObReferencia novaReferencia = new ObReferencia();
        novaReferencia.setIdReferencia(idReferencia);
        novaReferencia.setNom(nom);
        novaReferencia.setUoM(UoM);
        novaReferencia.setQuantitatProducte(quantitatProducte);
        novaReferencia.setFamiliaProductes(familiaProductes.getNom());
        novaReferencia.setProveidor(proveidor.getNom());
        novaReferencia.setDataAlta(dataAlta);
        novaReferencia.setDataBaixa(dataBaixa != null ? dataBaixa : null); // Manejar el valor null
        novaReferencia.setPreuUnitari(preuUnitari);
        novaReferencia.setMotiuBaixa(motiuBaixa);

        // Inserir la nova referència a la base de dades
        try (Connection con = ConnexioDB.getConnection()) {
            String sql = "INSERT INTO referencia (idReferencia, nom, UoM, quantitatProducte, familiaProductes, proveidor, dataAlta, dataBaixa, preuUnitari, motiuBaixa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, novaReferencia.getIdReferencia());
                stmt.setString(2, novaReferencia.getNom());
                stmt.setString(3, novaReferencia.getUoM());
                stmt.setInt(4, novaReferencia.getQuantitatProducte());
                stmt.setString(5, novaReferencia.getFamiliaProductes());
                stmt.setString(6, novaReferencia.getProveidor());
                stmt.setString(7, novaReferencia.getDataAlta());
                stmt.setString(8, novaReferencia.getDataBaixa()); // Aquest pot ser null
                stmt.setFloat(9, novaReferencia.getPreuUnitari());
                stmt.setString(10, novaReferencia.getMotiuBaixa());

                stmt.executeUpdate(); // Executar la inserció
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en inserir la nova referència: " + e.getMessage());
        }
    }

    private boolean validarData(String data) {
        // Expressió regular per validar dates en format yyyy/MM/dd o permetre buit
        String regex = "^$|^\\d{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])$";
        return data.matches(regex);
    }

    @FXML
    private void handleguardar(ActionEvent event) {
        // Verificar si els camps estan buits
        if (nomTextField.getText().isEmpty() || UomTextField.getText().isEmpty() || QuantitatTextField.getText().isEmpty()
                || familiaComboBox.getSelectionModel().isEmpty() || proveidorComboBox.getSelectionModel().isEmpty()
                || DataAltaTextField.getText().isEmpty()) {

            // Si algun camp està buit, mostrar una alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Camps buits");
            alert.setHeaderText(null);
            alert.setContentText("Si us plau, omple tots els camps obligatoris abans de desar.");
            alert.showAndWait();
            return; // Sortir del mètode sense desar
        }

        // Validar format de dates en yyyy/MM/dd
        if (!validarData(DataAltaTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La data d'alta no té el format correcte (yyyy/MM/dd).");
            alert.showAndWait();
            return;
        }

        // Validar data de baixa només si no està buida
        if (!DataBaixaTextField.getText().isEmpty() && !validarData(DataBaixaTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La data de baixa no té el format correcte (yyyy/MM/dd).");
            alert.showAndWait();
            return;
        }

        try {
            // Assignar els valors dels TextField i ComboBox a la nova referència
            int nouId = Integer.parseInt(idReferenciaTextField.getText());
            String nouNom = nomTextField.getText();
            String nouUOM = UomTextField.getText();
            int nouQuantitat = Integer.parseInt(QuantitatTextField.getText());
            ObFamilia familia = familiaComboBox.getValue();
            ObProveidor proveidor = proveidorComboBox.getValue();

            String nouDataAlta = DataAltaTextField.getText();
            String nouDataBaixa = DataBaixaTextField.getText(); // Pot estar buit

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataAlta = LocalDate.parse(nouDataAlta, formatter);
            if (nouDataBaixa != null && !nouDataBaixa.isEmpty()) {
                LocalDate dataBaixa = LocalDate.parse(nouDataBaixa, formatter);
                if (dataBaixa.isBefore(dataAlta)) {
                    throw new IllegalArgumentException("La data de baixa no pot ser anterior a la data d'alta.");
                }
            }

            float nouPreuUnitari = Float.parseFloat(PreuUnitariTextField.getText());
            String motiu = MotiuTextField.getText(); // Pot ser buit

            // Executar la funció d'alta de referència
            altaReferencia(nouId, nouNom, nouUOM, nouQuantitat, familia, proveidor, nouDataAlta, nouDataBaixa, nouPreuUnitari, motiu);

            // Mostrar una alerta d'èxit
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Referència desada");
            alert.setHeaderText(null);
            alert.setContentText("La referència s'ha desat correctament.");
            alert.showAndWait();

            // Tancar la finestra actual
            Stage stage = (Stage) idReferenciaTextField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            // Mostrar una alerta si algun camp numèric no és vàlid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en els camps numèrics");
            alert.setHeaderText(null);
            alert.setContentText("Si us plau, assegura't que els valors numèrics són vàlids.");
            alert.showAndWait();
        } catch (IllegalArgumentException | SQLException e) {
            // Mostrar una alerta si hi ha algun altre error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hi ha hagut un error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSortir(ActionEvent event) {
        Stage stage = (Stage) idReferenciaTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAnarMenuPrincipal(ActionEvent event) {
        try {
            App.setRoot("PrincipalView");
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No s'ha pogut carregar el menú principal.");
            alert.showAndWait();
        }
    }
}
