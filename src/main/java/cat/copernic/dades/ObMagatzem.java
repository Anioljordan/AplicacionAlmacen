/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.copernic.dades;

/**
 *
 * @author marcv
 */
public class ObMagatzem {
    /**
     * Enum per a tipus
     */
    public enum tipus{
        FAMILIA,
        REFERENCIA
    }
    protected int idEntrada;
    protected String nomEntrada;
    protected String dataEntrada;
    protected String dataSortida;
    protected String motiuSortida; 
    protected float valorTotal;
    protected tipus tipus ;
    
    
    /**
     * Donem un valor per defecte als atributs
     */
    public ObMagatzem(){
        idEntrada = 0;
        nomEntrada = "";
        dataEntrada = "";
        dataSortida = "";
        motiuSortida = "";
        valorTotal = 0; 
        tipus = tipus.FAMILIA;
        
    }
    
    //Iniciem els atributs sobrecarregats
    public ObMagatzem(int nouId, String nouNomEntrada, String novaDataE, String novaDataS, String nouMotiu, float nouValor, tipus nouTipus){
        idEntrada = nouId;
        nomEntrada = nouNomEntrada;
        dataEntrada = novaDataE;
        dataSortida = novaDataS;
        motiuSortida = nouMotiu;
        valorTotal = nouValor; 
        tipus = nouTipus;
        
    }


    /**
     * @return the idEntrada
     */
    public int getIdEntrada() {
        return idEntrada;
    }

    /**
     * @param idEntrada the idEntrada to set
     */
    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }
    
     /**
     * @return the nomEntrada
     */
    public String getNomEntrada() {
        return nomEntrada;
    }

    /**
     * @param nomEntrada the nomEntrada to set
     */
    public void setNomEntrada(String nomEntrada) {
        this.nomEntrada = nomEntrada;
    }
    
    /**
     * @return the dataEntrada
     */
    public String getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSortida
     */
    public String getDataSortida() {
        return dataSortida;
    }

    /**
     * @param dataSortida the dataSortida to set
     */
    public void setDataSortida(String dataSortida) {
        this.dataSortida = dataSortida;
    }

    /**
     * @return the motiuSortida
     */
    public String getMotiuSortida() {
        return motiuSortida;
    }

    /**
     * @param motiuSortida the motiuSortida to set
     */
    public void setMotiuSortida(String motiuSortida) {
        this.motiuSortida = motiuSortida;
    }

    /**
     * @return the valorTotal
     */
    public float getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * @return the tipus
     */
    public tipus getTipus() {
        return tipus;
    }

    /**
     * @param tipus the tipus to set
     */
    public void setTipus(tipus nouTipus) {
        tipus = nouTipus;
    }


}
