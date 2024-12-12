/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
public class ObUsuari {

    public enum esManager {
        MANAGER,
        VENEDOR
    }
    protected String nom;
    protected String pass;
    public esManager man;

    public ObUsuari() {
        nom = "";
        pass = "";
        man = esManager.MANAGER;
    }

    public ObUsuari(String nouNom, String novaPass, esManager nouMan) {
        nom = nouNom;
        pass = novaPass;
        man = nouMan;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the man
     */
    public esManager getMan() {
        return this.man;
    }

    /**
     * @param man the man to set
     */
    public void setMan(esManager man) {
        this.man = man;
    }
}
