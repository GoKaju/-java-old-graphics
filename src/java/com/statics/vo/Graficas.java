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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "graficas")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Graficas.findAll", query = "SELECT g FROM Graficas g")
    , @NamedQuery(name = "Graficas.findByGrafId", query = "SELECT g FROM Graficas g WHERE g.grafId = :grafId")
    , @NamedQuery(name = "Graficas.findByGrafNombre", query = "SELECT g FROM Graficas g WHERE g.grafNombre = :grafNombre")
    , @NamedQuery(name = "Graficas.findByGrafUrl", query = "SELECT g FROM Graficas g WHERE g.grafUrl = :grafUrl")
    , @NamedQuery(name = "Graficas.findByGrafPath", query = "SELECT g FROM Graficas g WHERE g.grafPath = :grafPath")
    , @NamedQuery(name = "Graficas.findByGrafTipo", query = "SELECT g FROM Graficas g WHERE g.grafTipo = :grafTipo")
    , @NamedQuery(name = "Graficas.findByGrafFuncion", query = "SELECT g FROM Graficas g WHERE g.grafFuncion = :grafFuncion")})
public class Graficas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "graf_id")
    private Integer grafId;
    @Column(name = "graf_nombre")
    private String grafNombre;
    @Column(name = "graf_url")
    private String grafUrl;
    @Column(name = "graf_path")
    private String grafPath;
    @Column(name = "graf_tipo")
    private String grafTipo;
    @Column(name = "graf_funcion")
    private String grafFuncion;

    public Graficas() {
    }

    public Graficas(Integer grafId) {
        this.grafId = grafId;
    }

    public Integer getGrafId() {
        return grafId;
    }

    public void setGrafId(Integer grafId) {
        this.grafId = grafId;
    }

    public String getGrafNombre() {
        return grafNombre;
    }

    public void setGrafNombre(String grafNombre) {
        this.grafNombre = grafNombre;
    }

    public String getGrafUrl() {
        return grafUrl;
    }

    public void setGrafUrl(String grafUrl) {
        this.grafUrl = grafUrl;
    }

    public String getGrafPath() {
        return grafPath;
    }

    public void setGrafPath(String grafPath) {
        this.grafPath = grafPath;
    }

    public String getGrafTipo() {
        return grafTipo;
    }

    public void setGrafTipo(String grafTipo) {
        this.grafTipo = grafTipo;
    }

    public String getGrafFuncion() {
        return grafFuncion;
    }

    public void setGrafFuncion(String grafFuncion) {
        this.grafFuncion = grafFuncion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grafId != null ? grafId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Graficas)) {
            return false;
        }
        Graficas other = (Graficas) object;
        if ((this.grafId == null && other.grafId != null) || (this.grafId != null && !this.grafId.equals(other.grafId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Graficas[ grafId=" + grafId + " ]";
    }
    
}
