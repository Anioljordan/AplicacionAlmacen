package cat.copernic.presentacio; // Assegura't d'utilitzar el paquet correcte

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    private static Map<String, Object> controllers = new HashMap<>();
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        scene = new Scene(loadFXML("Login"), 1910, 1040);
        
        String css = getClass().getResource("/cat/copernic/presentacio/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Aplicació De Gestió de Magatzem"); // Títol de la finestra
        primaryStage.show(); // Mostrem la finestra
    }

    public static void setRoot(String fxml) throws IOException {
    Parent newRoot = loadFXML(fxml);
    scene.setRoot(newRoot);
    // Força un redimensionament de la finestra
    Stage stage = (Stage) scene.getWindow();
    stage.sizeToScene(); // Ajusta la mida de la finestra a la nova escena
}
    
    public static void setRootParent(Parent root) {
    scene.setRoot(root);
}


    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        
        // Almacenar el controlador en el mapa
        controllers.put(fxml, fxmlLoader.getController());
        
        return root;

    }

        public static void mostrarAlerta(Alert.AlertType alertType, String content, String missatge) {
        Alert alert = new Alert(alertType);
        alert.setTitle(content);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
    
    public static <T> T getController(String fxml) {
        // Devuelve el controlador correspondiente a la vista
        return (T) controllers.get(fxml);
    }

    public static void main(String[] args) {
        launch(args); // Iniciem l'aplicació
    }
}
