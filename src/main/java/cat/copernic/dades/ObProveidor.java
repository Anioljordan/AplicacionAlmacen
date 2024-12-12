/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
public class ObProveidor {

    /**
     * Enum per a estat
     */
    public enum estat {
        ACTIU,
        INACTIU,
        SUSPES
    }
    protected int idProveidor;
    protected String nom;
    protected String cif;
    protected estat estat;
    protected String motiuInactivitat;
    protected String telefon;

    /**
     * S'inicien els atributs amb un valor per defecte per a que no siguin null
     */
    public ObProveidor() {
        idProveidor = 0;
        nom = "";
        cif = "";
        estat = estat.INACTIU;
        motiuInactivitat = "";
        telefon = "";
    }

    /**
     * S'inicien els atributs sobrecarregats
     */
    public ObProveidor(int nouId, String nouNom, String nouCif, estat nouEstat, String nouMotiu, String nouTelefon) {
        idProveidor = nouId;
        nom = nouNom;
        cif = nouCif;
        estat = nouEstat;
        motiuInactivitat = nouMotiu;
        telefon = nouTelefon;
    }

    /**
     * Setters i getters per a ObProveidor
     */
    /**
     * @return the idProveidor
     */
    public int getIdProveidor() {
        return idProveidor;
    }

    /**
     * @param idProveidor the idProveidor to set
     */
    public void setIdProveidor(int idProveidor) {
        this.idProveidor = idProveidor;
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
     * @return the cif
     */
    public String getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * @return the estat
     */
    public estat getEstat() {
        return estat;
    }

    /**
     * @param estat the estat to set
     */
    public void setEstat(estat nouEstat) {
        estat = nouEstat;
    }

    /**
     * @return the motiuInactivitat
     */
    public String getMotiuInactivitat() {
        return motiuInactivitat;
    }

    /**
     * @param motiuInactivitat the motiuInactivitat to set
     */
    public void setMotiuInactivitat(String motiuInactivitat) {
        this.motiuInactivitat = motiuInactivitat;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon the telefon to set
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Sobreescriptura del mètode toString per mostrar el nom al ComboBox
     */
    @Override
    public String toString() {
        return this.nom;  // Retorna el nom del proveïdor
    }
}
