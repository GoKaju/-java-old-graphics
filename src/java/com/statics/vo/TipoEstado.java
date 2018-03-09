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
@Table(name = "tipo_estado")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEstado.findAll", query = "SELECT t FROM TipoEstado t")
    , @NamedQuery(name = "TipoEstado.findByTiesId", query = "SELECT t FROM TipoEstado t WHERE t.tiesId = :tiesId")
    , @NamedQuery(name = "TipoEstado.findByTiesNombre", query = "SELECT t FROM TipoEstado t WHERE t.tiesNombre = :tiesNombre")})
public class TipoEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ties_id")
    private Integer tiesId;
    @Basic(optional = false)
    @Column(name = "ties_nombre")
    private String tiesNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiesId")
    private List<Estados> estadosList;

    public TipoEstado() {
    }

    public TipoEstado(Integer tiesId) {
        this.tiesId = tiesId;
    }

    public TipoEstado(Integer tiesId, String tiesNombre) {
        this.tiesId = tiesId;
        this.tiesNombre = tiesNombre;
    }

    public Integer getTiesId() {
        return tiesId;
    }

    public void setTiesId(Integer tiesId) {
        this.tiesId = tiesId;
    }

    public String getTiesNombre() {
        return tiesNombre;
    }

    public void setTiesNombre(String tiesNombre) {
        this.tiesNombre = tiesNombre;
    }

    @XmlTransient
    public List<Estados> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<Estados> estadosList) {
        this.estadosList = estadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiesId != null ? tiesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEstado)) {
            return false;
        }
        TipoEstado other = (TipoEstado) object;
        if ((this.tiesId == null && other.tiesId != null) || (this.tiesId != null && !this.tiesId.equals(other.tiesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.TipoEstado[ tiesId=" + tiesId + " ]";
    }
    
}
