/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "carga_errores")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargaErrores.findAll", query = "SELECT c FROM CargaErrores c")
    , @NamedQuery(name = "CargaErrores.findByCaerId", query = "SELECT c FROM CargaErrores c WHERE c.caerId = :caerId")
    , @NamedQuery(name = "CargaErrores.findByCaerNumfila", query = "SELECT c FROM CargaErrores c WHERE c.caerNumfila = :caerNumfila")
    , @NamedQuery(name = "CargaErrores.findByCaerError", query = "SELECT c FROM CargaErrores c WHERE c.caerError = :caerError")})
public class CargaErrores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "caer_id")
    private Integer caerId;
    @Column(name = "caer_numfila")
    private Integer caerNumfila;
    @Column(name = "caer_error")
    private String caerError;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caerId")
    private List<CaerCampos> caerCamposList;
    @JoinColumn(name = "carg_id", referencedColumnName = "carg_id")
    @ManyToOne(optional = false)
    private Cargas cargId;

    public CargaErrores() {
    }

    public CargaErrores(Integer caerId) {
        this.caerId = caerId;
    }

    public Integer getCaerId() {
        return caerId;
    }

    public void setCaerId(Integer caerId) {
        this.caerId = caerId;
    }

    public Integer getCaerNumfila() {
        return caerNumfila;
    }

    public void setCaerNumfila(Integer caerNumfila) {
        this.caerNumfila = caerNumfila;
    }

    public String getCaerError() {
        return caerError;
    }

    public void setCaerError(String caerError) {
        this.caerError = caerError;
    }

    @XmlTransient
    public List<CaerCampos> getCaerCamposList() {
        return caerCamposList;
    }

    public void setCaerCamposList(List<CaerCampos> caerCamposList) {
        this.caerCamposList = caerCamposList;
    }

    public Cargas getCargId() {
        return cargId;
    }

    public void setCargId(Cargas cargId) {
        this.cargId = cargId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caerId != null ? caerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargaErrores)) {
            return false;
        }
        CargaErrores other = (CargaErrores) object;
        if ((this.caerId == null && other.caerId != null) || (this.caerId != null && !this.caerId.equals(other.caerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.CargaErrores[ caerId=" + caerId + " ]";
    }
    
}
