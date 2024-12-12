package cat.copernic.presentacio;

import cat.copernic.dades.ObUsuari;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class PrincipalViewController {

    private ObUsuari usr;
    private ObUsuari.esManager tipusUsuari;

    @FXML
    private ImageView imageLogo; //Aquest es l'imageview carregat en el FMXML

    @FXML
    public void initialize() {
        
        // Recuperar l'usuari i tipus d'usuari des de UserSession
        this.usr = UserSession.getInstance().getUsuari();
        this.tipusUsuari = UserSession.getInstance().getTipusUsuari();
    }

    @FXML
    private void goToProveidor() throws IOException {
        App.setRoot("Proveidor");
    }

    @FXML
    private void goToReferencia() throws IOException {
        App.setRoot("ReferenciaViewMain");
    }

    @FXML
    private void goToFamilia() throws IOException {
        carregarVista("FamiliaView.fxml");
    }

    @FXML
    private void handleObrirMagatzem() throws IOException {
        carregarVista("MagatzemView.fxml");
    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("Login");
    }

    private void carregarVista(String fxml) throws IOException {
        if (usr != null && tipusUsuari != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            // Obtenir el controlador
            ControllerInterface controller = loader.getController();
            // Passar l'usuari i tipus d'usuari al nou controlador
            controller.setUsuari(usr);
            controller.setTipusUsuari(tipusUsuari);
            // Canviar a la nova vista
            App.setRootParent(root);
        } else {
            System.out.println("L'usuari o el tipus d'usuari no estan inicialitzats");
        }
    }
}
