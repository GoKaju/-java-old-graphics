/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "caer_campos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaerCampos.findAll", query = "SELECT c FROM CaerCampos c")
    , @NamedQuery(name = "CaerCampos.findByCacaId", query = "SELECT c FROM CaerCampos c WHERE c.cacaId = :cacaId")
    , @NamedQuery(name = "CaerCampos.findByCacaCampo", query = "SELECT c FROM CaerCampos c WHERE c.cacaCampo = :cacaCampo")
    , @NamedQuery(name = "CaerCampos.findByCacaValor", query = "SELECT c FROM CaerCampos c WHERE c.cacaValor = :cacaValor")
    , @NamedQuery(name = "CaerCampos.findByCacaObservacion", query = "SELECT c FROM CaerCampos c WHERE c.cacaObservacion = :cacaObservacion")})
public class CaerCampos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "caca_id")
    private Integer cacaId;
    @Column(name = "caca_campo")
    private String cacaCampo;
    @Column(name = "caca_valor")
    private String cacaValor;
    @Column(name = "caca_observacion")
    private String cacaObservacion;
    @JoinColumn(name = "caer_id", referencedColumnName = "caer_id")
    @ManyToOne(optional = false)
    private CargaErrores caerId;

    public CaerCampos() {
    }

    public CaerCampos(Integer cacaId) {
        this.cacaId = cacaId;
    }

    public Integer getCacaId() {
        return cacaId;
    }

    public void setCacaId(Integer cacaId) {
        this.cacaId = cacaId;
    }

    public String getCacaCampo() {
        return cacaCampo;
    }

    public void setCacaCampo(String cacaCampo) {
        this.cacaCampo = cacaCampo;
    }

    public String getCacaValor() {
        return cacaValor;
    }

    public void setCacaValor(String cacaValor) {
        this.cacaValor = cacaValor;
    }

    public String getCacaObservacion() {
        return cacaObservacion;
    }

    public void setCacaObservacion(String cacaObservacion) {
        this.cacaObservacion = cacaObservacion;
    }

    public CargaErrores getCaerId() {
        return caerId;
    }

    public void setCaerId(CargaErrores caerId) {
        this.caerId = caerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cacaId != null ? cacaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaerCampos)) {
            return false;
        }
        CaerCampos other = (CaerCampos) object;
        if ((this.cacaId == null && other.cacaId != null) || (this.cacaId != null && !this.cacaId.equals(other.cacaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.CaerCampos[ cacaId=" + cacaId + " ]";
    }
    
}
