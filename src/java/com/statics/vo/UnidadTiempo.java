/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "unidad_tiempo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadTiempo.findAll", query = "SELECT u FROM UnidadTiempo u")
    , @NamedQuery(name = "UnidadTiempo.findById", query = "SELECT u FROM UnidadTiempo u WHERE u.id = :id")
    , @NamedQuery(name = "UnidadTiempo.findByDescripcion", query = "SELECT u FROM UnidadTiempo u WHERE u.descripcion = :descripcion")
    , @NamedQuery(name = "UnidadTiempo.findByUnidad", query = "SELECT u FROM UnidadTiempo u WHERE u.unidad = :unidad")
    , @NamedQuery(name = "UnidadTiempo.findByNumeroHoras", query = "SELECT u FROM UnidadTiempo u WHERE u.numeroHoras = :numeroHoras")})
public class UnidadTiempo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "unidad")
    private String unidad;
    @Column(name = "numero_horas")
    private Integer numeroHoras;
    @OneToMany(mappedBy = "idUnidadTiempo")
    private List<DatoProcesado> datoProcesadoList;
    @OneToMany(mappedBy = "idUnidadTiempo")
    private List<NivelMaximo> nivelMaximoList;

    public UnidadTiempo() {
    }

    public UnidadTiempo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getNumeroHoras() {
        return numeroHoras;
    }

    public void setNumeroHoras(Integer numeroHoras) {
        this.numeroHoras = numeroHoras;
    }

    @XmlTransient
    public List<DatoProcesado> getDatoProcesadoList() {
        return datoProcesadoList;
    }

    public void setDatoProcesadoList(List<DatoProcesado> datoProcesadoList) {
        this.datoProcesadoList = datoProcesadoList;
    }

    @XmlTransient
    public List<NivelMaximo> getNivelMaximoList() {
        return nivelMaximoList;
    }

    public void setNivelMaximoList(List<NivelMaximo> nivelMaximoList) {
        this.nivelMaximoList = nivelMaximoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadTiempo)) {
            return false;
        }
        UnidadTiempo other = (UnidadTiempo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.UnidadTiempo[ id=" + id + " ]";
    }
    
}
