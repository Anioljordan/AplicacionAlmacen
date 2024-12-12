/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
public class ObReferencia {
    protected int idReferencia;
    protected String nom;
    public String UoM;   // Unitat de mesura
    protected int quantitatProducte;
    protected String familiaProductes;
    public String proveidor;
    protected String dataAlta;
    protected String dataBaixa;
    public float preuUnitari;
    protected String motiuBaixa;

    /**
     * Constructor per a establir atributs per defecte
     */
    public ObReferencia() {
        idReferencia = 0;
        nom = "";
        UoM = "";
        quantitatProducte = 0;
        familiaProductes = "";
        proveidor = "";
        dataAlta = "";
        dataBaixa = "";
        preuUnitari = 0.0f;
        motiuBaixa = "";
    }

    /**
     * Constructor sobrecarregat per a inicialitzar els atributs amb valors específics
     */
    public ObReferencia(int nouIdReferencia, String nouNom, String nouUom, int novaQuantitatProducte, String novaFamiliaProductes, String nouProveidorRefe, String novaDataAlta, String novaDataBaixa, float nouPreUnitari, String nouMotiuBaixa) {
        idReferencia = nouIdReferencia;
        nom = nouNom;
        UoM = nouUom;
        quantitatProducte = novaQuantitatProducte;
        familiaProductes = novaFamiliaProductes;
        proveidor = nouProveidorRefe;
        dataAlta = novaDataAlta;
        dataBaixa = novaDataBaixa;
        preuUnitari = nouPreUnitari;
        motiuBaixa = nouMotiuBaixa;
    }

    /**
     * @return the idReferencia
     */
    public int getIdReferencia() {
        return idReferencia;
    }

    /**
     * @param idReferencia the idReferencia to set
     */
    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
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
     * @return the quantitatProducte
     */
    public int getQuantitatProducte() {
        return quantitatProducte;
    }

    /**
     * @param quantitatProducte the quantitatProducte to set
     */
    public void setQuantitatProducte(int quantitatProducte) {
        this.quantitatProducte = quantitatProducte;
    }

    /**
     * @return the familiaProductes
     */
    public String getFamiliaProductes() {
        return familiaProductes;
    }

    /**
     * @param familiaProductes the familiaProductes to set
     */
    public void setFamiliaProductes(String familiaProductes) {
        this.familiaProductes = familiaProductes;
    }

    /**
     * @return the dataAlta
     */
    public String getDataAlta() {
        return dataAlta;
    }

    /**
     * @param dataAlta the dataAlta to set
     */
    public void setDataAlta(String dataAlta) {
        this.dataAlta = dataAlta;
    }

    /**
     * @return the dataBaixa
     */
    public String getDataBaixa() {
        return dataBaixa;
    }

    /**
     * @param dataBaixa the dataBaixa to set
     */
    public void setDataBaixa(String dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    /**
     * @return the motiuBaixa
     */
    public String getMotiuBaixa() {
        return motiuBaixa;
    }

    /**
     * @param motiuBaixa the motiuBaixa to set
     */
    public void setMotiuBaixa(String motiuBaixa) {
        this.motiuBaixa = motiuBaixa;
    }

    /**
     * @return the UoM
     */
    public String getUoM() {
        return UoM;
    }

    /**
     * @param UoM the UoM to set
     */
    public void setUoM(String UoM) {
        this.UoM = UoM;
    }

    /**
     * @return the proveidor
     */
    public String getProveidor() {
        return proveidor;
    }

    /**
     * @param proveidor the proveidor to set
     */
    public void setProveidor(String proveidor) {
        this.proveidor = proveidor;
    }

    /**
     * @return the preuUnitari
     */
    public float getPreuUnitari() {
        return preuUnitari;
    }

    /**
     * @param preuUnitari the preuUnitari to set
     */
    public void setPreuUnitari(float preuUnitari) {
        this.preuUnitari = preuUnitari;
    }

    
}
