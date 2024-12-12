/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.presentacio;

/**
 *
 * @author marcv
 */

import cat.copernic.dades.ObUsuari;

public class UserSession {
    private static UserSession instance;
    private ObUsuari usr;
    private ObUsuari.esManager tipusUsuari;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUsuari(ObUsuari usuari) {
        this.usr = usuari;
    }

    public ObUsuari getUsuari() {
        return usr;
    }

    public void setTipusUsuari(ObUsuari.esManager tipusUsuari) {
        this.tipusUsuari = tipusUsuari;
    }

    public ObUsuari.esManager getTipusUsuari() {
        return tipusUsuari;
    }
}

