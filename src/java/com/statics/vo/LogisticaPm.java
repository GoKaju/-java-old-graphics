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
@Table(name = "logistica_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogisticaPm.findAll", query = "SELECT l FROM LogisticaPm l")
    , @NamedQuery(name = "LogisticaPm.findById", query = "SELECT l FROM LogisticaPm l WHERE l.id = :id")
    , @NamedQuery(name = "LogisticaPm.findByDescripcionRuta", query = "SELECT l FROM LogisticaPm l WHERE l.descripcionRuta = :descripcionRuta")})
public class LogisticaPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion_ruta")
    private String descripcionRuta;
    @OneToMany(mappedBy = "idLogistica")
    private List<PuntoMuestral> puntoMuestralList;
    @OneToMany(mappedBy = "idLogistica")
    private List<ItemLogistica> itemLogisticaList;

    public LogisticaPm() {
    }

    public LogisticaPm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LogisticaPm(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcionRuta() {
        return descripcionRuta;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    @XmlTransient
    public List<ItemLogistica> getItemLogisticaList() {
        return itemLogisticaList;
    }

    public void setItemLogisticaList(List<ItemLogistica> itemLogisticaList) {
        this.itemLogisticaList = itemLogisticaList;
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
        if (!(object instanceof LogisticaPm)) {
            return false;
        }
        LogisticaPm other = (LogisticaPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.LogisticaPm[ id=" + id + " ]";
    }
    
}
