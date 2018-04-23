/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "parametrizacion_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroLabels.findAll", query = "SELECT p FROM ParametroLabels p")
    , @NamedQuery(name = "ParametroLabels.findByPalaId", query = "SELECT p FROM ParametroLabels p WHERE p.palaId = :palaId")
    , @NamedQuery(name = "ParametroLabels.findActivos", query = "SELECT p FROM ParametroLabels p inner join Parametros as pa on p.paraId = pa WHERE pa.paraEstado>0")
    , @NamedQuery(name = "ParametroLabels.findByPalaLabel", query = "SELECT p FROM ParametroLabels p WHERE p.palaLabel = :palaLabel")
    , @NamedQuery(name = "ParametroLabels.findByPalaRegistradopor", query = "SELECT p FROM ParametroLabels p WHERE p.palaRegistradopor = :palaRegistradopor")
    , @NamedQuery(name = "ParametroLabels.findByPalaFechacambio", query = "SELECT p FROM ParametroLabels p WHERE p.palaFechacambio = :palaFechacambio")})
public class ParametrizacionApp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "paap_id")
    private Integer paapId;
    @Column(name = "paap_tipo")
    private String paapTipo;
    @Column(name = "paap_descripcion")
    private String paapDescripcion;
    @Basic(optional = false)
    @Column(name = "paap_valor")
    private String paapValor;
    @Column(name = "paap_registradopor")
    private Integer paapRegistradopor;
    @Column(name = "paap_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paapFechacambio;

    public ParametrizacionApp() {
    }

    public ParametrizacionApp(Integer paapId) {
        this.paapId = paapId;
    }

    public ParametrizacionApp(Integer paapId, String paapValor) {
        this.paapId = paapId;
        this.paapValor = paapValor;
    }

    public Integer getPaapId() {
        return paapId;
    }

    public void setPaapId(Integer paapId) {
        this.paapId = paapId;
    }

    public String getPaapTipo() {
        return paapTipo;
    }

    public void setPaapTipo(String paapTipo) {
        this.paapTipo = paapTipo;
    }

    public String getPaapDescripcion() {
        return paapDescripcion;
    }

    public void setPaapDescripcion(String paapDescripcion) {
        this.paapDescripcion = paapDescripcion;
    }

    public String getPaapValor() {
        return paapValor;
    }

    public void setPaapValor(String paapValor) {
        this.paapValor = paapValor;
    }

    public Integer getPaapRegistradopor() {
        return paapRegistradopor;
    }

    public void setPaapRegistradopor(Integer paapRegistradopor) {
        this.paapRegistradopor = paapRegistradopor;
    }

    public Date getPaapFechacambio() {
        return paapFechacambio;
    }

    public void setPaapFechacambio(Date paapFechacambio) {
        this.paapFechacambio = paapFechacambio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paapId != null ? paapId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametrizacionApp)) {
            return false;
        }
        ParametrizacionApp other = (ParametrizacionApp) object;
        if ((this.paapId == null && other.paapId != null) || (this.paapId != null && !this.paapId.equals(other.paapId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ParametrizacionApp[ paapId=" + paapId + " ]";
    }
    
}
