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
@Table(name = "estados")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estados.findAll", query = "SELECT e FROM Estados e")
    , @NamedQuery(name = "Estados.findByEstaId", query = "SELECT e FROM Estados e WHERE e.estaId = :estaId")
    , @NamedQuery(name = "Estados.findByEstaDescripcion", query = "SELECT e FROM Estados e WHERE e.estaDescripcion = :estaDescripcion")})
public class Estados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "esta_id")
    private Integer estaId;
    @Basic(optional = false)
    @Column(name = "esta_descripcion")
    private String estaDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estaId")
    private List<Grupo> grupoList;
    @OneToMany(mappedBy = "estaIdestado")
    private List<Estaciones> estacionesList;
    @JoinColumn(name = "ties_id", referencedColumnName = "ties_id")
    @ManyToOne(optional = false)
    private TipoEstado tiesId;

    public Estados() {
    }

    public Estados(Integer estaId) {
        this.estaId = estaId;
    }

    public Estados(Integer estaId, String estaDescripcion) {
        this.estaId = estaId;
        this.estaDescripcion = estaDescripcion;
    }

    public Integer getEstaId() {
        return estaId;
    }

    public void setEstaId(Integer estaId) {
        this.estaId = estaId;
    }

    public String getEstaDescripcion() {
        return estaDescripcion;
    }

    public void setEstaDescripcion(String estaDescripcion) {
        this.estaDescripcion = estaDescripcion;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<Estaciones> getEstacionesList() {
        return estacionesList;
    }

    public void setEstacionesList(List<Estaciones> estacionesList) {
        this.estacionesList = estacionesList;
    }

    public TipoEstado getTiesId() {
        return tiesId;
    }

    public void setTiesId(TipoEstado tiesId) {
        this.tiesId = tiesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estaId != null ? estaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estados)) {
            return false;
        }
        Estados other = (Estados) object;
        if ((this.estaId == null && other.estaId != null) || (this.estaId != null && !this.estaId.equals(other.estaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Estados[ estaId=" + estaId + " ]";
    }
    
}
