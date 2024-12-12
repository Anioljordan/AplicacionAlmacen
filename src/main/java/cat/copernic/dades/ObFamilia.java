/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
public class ObFamilia {

    protected int idFamilia;
    protected String nom;
    protected String descripcio;
    protected String dataAlta;
    protected String dataBaixa;
    protected String proveidorDefault;
    protected String observacions;
    protected float descompteGeneral;
    private ObProveidor proveidor;
    
    /**
     * S'inicien els atributs sense sobrecarregar per a més seguretat
     */
    public ObFamilia(){
        idFamilia = 0;
        nom = "";
        descripcio = "";    
        dataAlta = "";        
        dataBaixa = "";        
        proveidorDefault = "";        
        observacions = "";        
        descompteGeneral = 0;        
    }
    
    /**
     * S'inicien els atributs sobrecarregats
     */
    public ObFamilia(int nouId, String nouNom, String novaDesc, String novaDataA, String novaDataB, String nouProv, String novaObs, float nouDescom){
        idFamilia = nouId;
        nom = nouNom;
        descripcio = novaDesc;    
        dataAlta = novaDataA;
        dataBaixa = novaDataB;        
        proveidorDefault = nouProv;        
        observacions = novaObs;        
        descompteGeneral = nouDescom;      
    }

    /**
     * @return the idFamilia
     */
    public int getIdFamilia() {
        return idFamilia;
    }

    /**
     * @param idFamilia the idFamilia to set
     */
    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
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
     * @return the descripcio
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * @param descripcio the descripcio to set
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
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
     * @return the proveidorDefault
     */
    public String getProveidorDefault() {
        return proveidorDefault;
    }

    /**
     * @param proveidorDefault the proveidorDefault to set
     */
    public void setProveidorDefault(String proveidorDefault) {
        this.proveidorDefault = proveidorDefault;
    }

    /**
     * @return the observacions
     */
    public String getObservacions() {
        return observacions;
    }

    /**
     * @param observacions the observacions to set
     */
    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    /**
     * @return the descompteGeneral
     */
    public float getDescompteGeneral() {
        return descompteGeneral;
    }

    /**
     * @param descompteGeneral the descompteGeneral to set
     */
    public void setDescompteGeneral(float descompteGeneral) {
        this.descompteGeneral = descompteGeneral;
    }

    public ObProveidor getProveidor() {
        return proveidor;
    }

    public void setProveidor(ObProveidor proveidor) {
        this.proveidor = proveidor;
    }
    
    @Override
    public String toString() {
        return nom;  // Retorna el nom de la família per mostrar al ComboBox
    }
  
}
