/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.logica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcv
 */
public class EstocSubject {
    /**
     * Creem una list per al EstocObserver on s'emmagatzemaràn les dades
     */
    private List<EstocObserver> observer = new ArrayList();
    private int estoc;
    
    /**
     * Mètode per afegir un observador 
     */
    public void afegirObserver(EstocObserver observador){
        observer.add(observador);
    }
    
    /**
     * Mètode per a eliminar l'observador
     */
    public void eliminarObserver(EstocObserver observador){
        observer.remove(observador);
    }
    
    /**
     * Mètode per a actualitzar els observadors i notificar
     */
    public void actualitzarObserver(int estoc){
        this.estoc = estoc;
        notificarObservers();
    }
    
    /**
     * Mètode per a notificar als observadors
     */
    public void notificarObservers(){
        for (EstocObserver observers : observer) {
            observers.actualitzar(estoc); //
        }
    }
    
    
}
