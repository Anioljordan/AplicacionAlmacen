/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import javafx.scene.control.Alert;

/**
 *
 * @author marcv
 */
public class AlertaBaixEstoc implements EstocObserver{

    /**
     * Alerta per a avisar de l'activitat realitzada.
     */
    @Override
    public void actualitzar(int estoc) {
        if(estoc < 50){
            mostrarAlerta(Alert.AlertType.INFORMATION, "Estoc baix","Alerta: L'estoc és inferior a 50! Estoc actual: " + estoc);
        }
    }
    
    /**
     * Alerta per a avisar de l'activitat realitzada.
     */
    private void mostrarAlerta(Alert.AlertType tipus, String titol, String missatge) {
        Alert alerta = new Alert(tipus);
        alerta.setTitle(titol);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }
    
}
