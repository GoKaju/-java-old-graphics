/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "grupo")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByGrupId", query = "SELECT g FROM Grupo g WHERE g.grupId = :grupId")
    , @NamedQuery(name = "Grupo.findByGrupNombre", query = "SELECT g FROM Grupo g WHERE g.grupNombre = :grupNombre")
    , @NamedQuery(name = "Grupo.findByGrupDescripcion", query = "SELECT g FROM Grupo g WHERE g.grupDescripcion = :grupDescripcion")
    , @NamedQuery(name = "Grupo.findByGrupRegistradopor", query = "SELECT g FROM Grupo g WHERE g.grupRegistradopor = :grupRegistradopor")
    , @NamedQuery(name = "Grupo.findByGrupFechacambio", query = "SELECT g FROM Grupo g WHERE g.grupFechacambio = :grupFechacambio")
    , @NamedQuery(name = "Grupo.findByGrupFechacreacion", query = "SELECT g FROM Grupo g WHERE g.grupFechacreacion = :grupFechacreacion")})
public class Grupo implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupId")
    private List<GrupoUsuarios> grupoUsuariosList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grup_id")
    private Integer grupId;
    @Basic(optional = false)
    @Column(name = "grup_nombre")
    private String grupNombre;
    @Column(name = "grup_descripcion")
    private String grupDescripcion;
    @Basic(optional = false)
    @Column(name = "grup_registradopor")
    private int grupRegistradopor;
    @Basic(optional = false)
    @Column(name = "grup_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grupFechacambio;
    @Basic(optional = false)
    @Column(name = "grup_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grupFechacreacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "grupo")
    private GrupoUsuarios grupoUsuarios;
    @JoinColumn(name = "esta_id", referencedColumnName = "esta_id")
    @ManyToOne(optional = false)
    private Estados estaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupId")
    private List<Campanas> campanasList;
    @OneToMany(mappedBy = "grupId")
    private List<Estaciones> estacionesList;

    public Grupo() {
    }

    public Grupo(Integer grupId) {
        this.grupId = grupId;
    }

    public Grupo(Integer grupId, String grupNombre, int grupRegistradopor, Date grupFechacambio, Date grupFechacreacion) {
        this.grupId = grupId;
        this.grupNombre = grupNombre;
        this.grupRegistradopor = grupRegistradopor;
        this.grupFechacambio = grupFechacambio;
        this.grupFechacreacion = grupFechacreacion;
    }

    public Integer getGrupId() {
        return grupId;
    }

    public void setGrupId(Integer grupId) {
        this.grupId = grupId;
    }

    public String getGrupNombre() {
        return grupNombre;
    }

    public void setGrupNombre(String grupNombre) {
        this.grupNombre = grupNombre;
    }

    public String getGrupDescripcion() {
        return grupDescripcion;
    }

    public void setGrupDescripcion(String grupDescripcion) {
        this.grupDescripcion = grupDescripcion;
    }

    public int getGrupRegistradopor() {
        return grupRegistradopor;
    }

    public void setGrupRegistradopor(int grupRegistradopor) {
        this.grupRegistradopor = grupRegistradopor;
    }

    public Date getGrupFechacambio() {
        return grupFechacambio;
    }

    public void setGrupFechacambio(Date grupFechacambio) {
        this.grupFechacambio = grupFechacambio;
    }

    public Date getGrupFechacreacion() {
        return grupFechacreacion;
    }

    public void setGrupFechacreacion(Date grupFechacreacion) {
        this.grupFechacreacion = grupFechacreacion;
    }

    public GrupoUsuarios getGrupoUsuarios() {
        return grupoUsuarios;
    }

    public void setGrupoUsuarios(GrupoUsuarios grupoUsuarios) {
        this.grupoUsuarios = grupoUsuarios;
    }

    public Estados getEstaId() {
        return estaId;
    }

    public void setEstaId(Estados estaId) {
        this.estaId = estaId;
    }

    @XmlTransient
    public List<Campanas> getCampanasList() {
        return campanasList;
    }

    public void setCampanasList(List<Campanas> campanasList) {
        this.campanasList = campanasList;
    }

    @XmlTransient
    public List<Estaciones> getEstacionesList() {
        return estacionesList;
    }

    public void setEstacionesList(List<Estaciones> estacionesList) {
        this.estacionesList = estacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupId != null ? grupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupId == null && other.grupId != null) || (this.grupId != null && !this.grupId.equals(other.grupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Grupo[ grupId=" + grupId + " ]";
    }

    @XmlTransient
    public List<GrupoUsuarios> getGrupoUsuariosList() {
        return grupoUsuariosList;
    }

    public void setGrupoUsuariosList(List<GrupoUsuarios> grupoUsuariosList) {
        this.grupoUsuariosList = grupoUsuariosList;
    }
    
}
