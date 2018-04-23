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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "grupo_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoUsuarios.findAll", query = "SELECT g FROM GrupoUsuarios g")
    , @NamedQuery(name = "GrupoUsuarios.findByGrusId", query = "SELECT g FROM GrupoUsuarios g WHERE g.grusId = :grusId")
    , @NamedQuery(name = "GrupoUsuarios.findByGrusRegistradopor", query = "SELECT g FROM GrupoUsuarios g WHERE g.grusRegistradopor = :grusRegistradopor")
    , @NamedQuery(name = "GrupoUsuarios.findByGrusFechacambio", query = "SELECT g FROM GrupoUsuarios g WHERE g.grusFechacambio = :grusFechacambio")})
public class GrupoUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grus_id")
    private Integer grusId;
    @Basic(optional = false)
    @Column(name = "grus_registradopor")
    private int grusRegistradopor;
    @Basic(optional = false)
    @Column(name = "grus_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grusFechacambio;
    @JoinColumn(name = "grup_id", referencedColumnName = "grup_id")
    @ManyToOne(optional = false)
    private Grupo grupId;
    @JoinColumn(name = "usua_id", referencedColumnName = "usua_id")
    @ManyToOne(optional = false)
    private Usuarios usuaId;

    public GrupoUsuarios() {
    }

    public GrupoUsuarios(Integer grusId) {
        this.grusId = grusId;
    }

    public GrupoUsuarios(Integer grusId, int grusRegistradopor, Date grusFechacambio) {
        this.grusId = grusId;
        this.grusRegistradopor = grusRegistradopor;
        this.grusFechacambio = grusFechacambio;
    }

    public Integer getGrusId() {
        return grusId;
    }

    public void setGrusId(Integer grusId) {
        this.grusId = grusId;
    }

    public int getGrusRegistradopor() {
        return grusRegistradopor;
    }

    public void setGrusRegistradopor(int grusRegistradopor) {
        this.grusRegistradopor = grusRegistradopor;
    }

    public Date getGrusFechacambio() {
        return grusFechacambio;
    }

    public void setGrusFechacambio(Date grusFechacambio) {
        this.grusFechacambio = grusFechacambio;
    }

    public Grupo getGrupId() {
        return grupId;
    }

    public void setGrupId(Grupo grupId) {
        this.grupId = grupId;
    }

    public Usuarios getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Usuarios usuaId) {
        this.usuaId = usuaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grusId != null ? grusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuarios)) {
            return false;
        }
        GrupoUsuarios other = (GrupoUsuarios) object;
        if ((this.grusId == null && other.grusId != null) || (this.grusId != null && !this.grusId.equals(other.grusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.GrupoUsuarios[ grusId=" + grusId + " ]";
    }
    
}
