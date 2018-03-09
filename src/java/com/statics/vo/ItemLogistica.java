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
import javax.persistence.FetchType;
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
@Table(name = "item_logistica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemLogistica.findAll", query = "SELECT i FROM ItemLogistica i")
    , @NamedQuery(name = "ItemLogistica.findById", query = "SELECT i FROM ItemLogistica i WHERE i.id = :id")
    , @NamedQuery(name = "ItemLogistica.findByRespuesta", query = "SELECT i FROM ItemLogistica i WHERE i.respuesta = :respuesta")
    , @NamedQuery(name = "ItemLogistica.findByObservacion", query = "SELECT i FROM ItemLogistica i WHERE i.observacion = :observacion")})
public class ItemLogistica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "respuesta")
    private String respuesta;
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemPm idItem;
    @JoinColumn(name = "id_logistica", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LogisticaPm idLogistica;

    public ItemLogistica() {
    }

    public ItemLogistica(Integer id) {
        this.id = id;
    }

    public ItemLogistica(String respuesta, String observacion, ItemPm idItem, LogisticaPm idLogistica) {
        this.respuesta = respuesta;
        this.observacion = observacion;
        this.idItem = idItem;
        this.idLogistica = idLogistica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public ItemPm getIdItem() {
        return idItem;
    }

    public void setIdItem(ItemPm idItem) {
        this.idItem = idItem;
    }

    public LogisticaPm getIdLogistica() {
        return idLogistica;
    }

    public void setIdLogistica(LogisticaPm idLogistica) {
        this.idLogistica = idLogistica;
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
        if (!(object instanceof ItemLogistica)) {
            return false;
        }
        ItemLogistica other = (ItemLogistica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ItemLogistica[ id=" + id + " ]";
    }
    
}
