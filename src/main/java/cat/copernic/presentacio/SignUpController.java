package cat.copernic.presentacio;

import cat.copernic.dades.ObUsuari;
import cat.copernic.dades.ObUsuari.esManager;
import cat.copernic.logica.UsuariDTO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SignUpController {

    private UsuariDTO UsuariDTO;

    @FXML
    private TextField txtNouUsuari;

    @FXML
    private PasswordField txtNovaContrasenya;

    @FXML
    private PasswordField txtConfirmaContrasenya;

    @FXML
    private TextField txtNovaContrasenyaVisible; // Campo para mostrar contraseña visible

    @FXML
    private TextField txtConfirmaContrasenyaVisible; // Campo para confirmar contraseña visible

    @FXML
    private ImageView imageMostrarPwd; // ImageView para mostrar/ocultar contraseñas

    @FXML
    private RadioButton radioButtonManager; // RadioButton para Manager
    @FXML
    private RadioButton radioButtonVenedor; // RadioButton para Venedor

    @FXML
    private ImageView imageLogo; // ImageView del logo

    private boolean mostrarContrasenya = false; // Estado de visibilidad de la contraseña
    private Image ojoAbierto; // Imagen del ojo abierto
    private Image ojoCerrado; // Imagen del ojo cerrado

    @FXML
    private void initialize() {
        // Cargar la imagen del logo
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
        imageLogo.setImage(logoImage);

        // Cargar las imágenes del ojo
        ojoAbierto = new Image(getClass().getResourceAsStream("/images/contravisible.jpeg")); // Imagen del ojo abierto
        ojoCerrado = new Image(getClass().getResourceAsStream("/images/contra.jpeg")); // Imagen del ojo cerrado

        // Inicializar la imagen del ojo como "cerrado" (contraseñas ocultas por defecto)
        imageMostrarPwd.setImage(ojoCerrado);

        // Inicializar campos de contraseñas visibles como no visibles
        txtNovaContrasenyaVisible.setVisible(false);
        txtConfirmaContrasenyaVisible.setVisible(false);
        txtNovaContrasenyaVisible.setManaged(false);
        txtConfirmaContrasenyaVisible.setManaged(false);

        // Evento para alternar visibilidad de contraseñas al hacer clic en la imagen
        imageMostrarPwd.setOnMouseClicked(event -> {
            mostrarContrasenya = !mostrarContrasenya; // Alternar estado

            if (mostrarContrasenya) {
                // Mostrar las contraseñas
                txtNovaContrasenyaVisible.setText(txtNovaContrasenya.getText());
                txtConfirmaContrasenyaVisible.setText(txtConfirmaContrasenya.getText());
                txtNovaContrasenya.setVisible(false);
                txtConfirmaContrasenya.setVisible(false);
                txtNovaContrasenyaVisible.setVisible(true);
                txtConfirmaContrasenyaVisible.setVisible(true);
                txtNovaContrasenya.setManaged(false);
                txtConfirmaContrasenya.setManaged(false);
                txtNovaContrasenyaVisible.setManaged(true);
                txtConfirmaContrasenyaVisible.setManaged(true);

                // Cambiar a la imagen del ojo abierto
                imageMostrarPwd.setImage(ojoAbierto);
            } else {
                // Ocultar las contraseñas
                txtNovaContrasenya.setText(txtNovaContrasenyaVisible.getText());
                txtConfirmaContrasenya.setText(txtConfirmaContrasenyaVisible.getText());
                txtNovaContrasenyaVisible.setVisible(false);
                txtConfirmaContrasenyaVisible.setVisible(false);
                txtNovaContrasenya.setVisible(true);
                txtConfirmaContrasenya.setVisible(true);
                txtNovaContrasenyaVisible.setManaged(false);
                txtConfirmaContrasenyaVisible.setManaged(false);
                txtNovaContrasenya.setManaged(true);
                txtConfirmaContrasenya.setManaged(true);

                // Cambiar a la imagen del ojo cerrado
                imageMostrarPwd.setImage(ojoCerrado);
            }
        });

        // Inicialización de los RadioButtons
        radioButtonManager.setSelected(true); // Por defecto seleccionado
        radioButtonVenedor.setSelected(false);

        // Listeners para RadioButtons
        radioButtonManager.setOnAction(event -> {
            if (radioButtonManager.isSelected()) {
                radioButtonVenedor.setSelected(false);
            }
        });

        radioButtonVenedor.setOnAction(event -> {
            if (radioButtonVenedor.isSelected()) {
                radioButtonManager.setSelected(false);
            }
        });
    }

    @FXML
    private void handleSignUp() throws IOException {
        String nouUsuari = txtNouUsuari.getText();
        String novaContrasenya = txtNovaContrasenya.getText();
        String confirmaContrasenya = txtConfirmaContrasenya.getText();

        // Comprobar si se ha seleccionado un rol
        esManager rolSeleccionat = radioButtonManager.isSelected() ? esManager.MANAGER : esManager.VENEDOR;

        if (!novaContrasenya.equals(confirmaContrasenya)) {
            mostrarError("Les contrasenyes no coincideixen.");
            return;
        }

        // Aquí puedes agregar la lógica para registrar el usuario con rolSeleccionat
        try {
            ObUsuari ObUsuari = new ObUsuari(nouUsuari, novaContrasenya, rolSeleccionat);
            UsuariDTO usu = new UsuariDTO();
            usu.alta(ObUsuari); // Suponiendo que esta es la implementación de alta de usuario

            // Mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuari registrat amb èxit.");
            alert.showAndWait();
            goToLogin(); // Volver a la vista de login
        } catch (SQLException e) {
            mostrarError("Error en registrar l'usuari: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje);
        alert.showAndWait();
    }

    @FXML
    private void goToLogin() throws IOException {
        // Implementar la lógica para volver a la vista de login
        App.setRoot("Login"); // Ajusta el nombre de la vista de login si es necesario
    }
}
