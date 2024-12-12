package cat.copernic.presentacio;

import cat.copernic.dades.ObUsuari;
import cat.copernic.logica.UsuariDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    private UsuariDTO UsuariDTO;

    @FXML
    private TextField txtUsuari;

    @FXML
    private PasswordField txtContrasenya;

    @FXML
    private TextField txtContrasenyaVisible; // Camp de contrasenya visible (quan es mostra)

    @FXML
    private ImageView eyeIcon; // Icona per mostrar/amagar contrasenya

    @FXML
    private ImageView imageLogo; // L'ImageView que vas declarar en l'FXML

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
        // Carregar la imatge del logotip des de l'arxiu de recursos
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        imageLogo.setImage(logoImage);

        // Carregar la imatge inicial de la icona de l'ull (per mostrar la contrasenya)
        Image eyeOpen = new Image(getClass().getResourceAsStream("/images/contra.jpeg")); // Canvia la ruta si cal
        eyeIcon.setImage(eyeOpen);

        // Inicialment, el camp de contrasenya visible està ocult
        txtContrasenyaVisible.setVisible(false);
        txtContrasenyaVisible.setManaged(false);
    }

    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Amagar contrasenya
            txtContrasenya.setText(txtContrasenyaVisible.getText());
            txtContrasenyaVisible.setVisible(false);
            txtContrasenyaVisible.setManaged(false);
            txtContrasenya.setVisible(true);
            txtContrasenya.setManaged(true);

            // Canviar la icona a "ull tancat"
            Image eyeClosed = new Image(getClass().getResourceAsStream("/images/contra.jpeg"));
            eyeIcon.setImage(eyeClosed);
        } else {
            // Mostrar contrasenya
            txtContrasenyaVisible.setText(txtContrasenya.getText());
            txtContrasenya.setVisible(false);
            txtContrasenya.setManaged(false);
            txtContrasenyaVisible.setVisible(true);
            txtContrasenyaVisible.setManaged(true);

            // Canviar la icona a "ull obert"
            Image eyeOpen = new Image(getClass().getResourceAsStream("/images/contravisible.jpeg"));
            eyeIcon.setImage(eyeOpen);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    private void handleLogin() throws IOException, SQLException {
        String username = txtUsuari.getText();
        String pwd = txtContrasenya.getText();
        ObUsuari usuari = UsuariDTO.autenticarUsuari(username, pwd); // Retorna l'usuari complet
        if (usuari != null) {
            // Guardar les credencials a UserSession
            UserSession.getInstance().setUsuari(usuari);
            UserSession.getInstance().setTipusUsuari(usuari.getMan());

            // Carregar i establir la vista principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("principal.fxml"));
            Parent root = loader.load();
            App.setRootParent(root); // Passar el Parent carregat aquí
        } else {
            App.mostrarAlerta(Alert.AlertType.ERROR, "Inici de sessió incorrecte", "Usuari o contrasenya incorrectes.");
        }
    }

    @FXML
    private void goToSignUp() throws IOException {
        // Canviar a la vista de registre
        App.setRoot("SignUp");
    }

    @FXML
    private void close(ActionEvent event) {
        // Obtenir l'escenari a partir de l'event i tancar l'aplicació
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public LoginController() {
        this.UsuariDTO = new UsuariDTO(); // Inicialitza l'objecte
    }
}
