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
import javax.persistence.FetchType;
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
@Table(name = "item_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPm.findAll", query = "SELECT i FROM ItemPm i")
    , @NamedQuery(name = "ItemPm.findById", query = "SELECT i FROM ItemPm i WHERE i.id = :id")
    , @NamedQuery(name = "ItemPm.findByNombre", query = "SELECT i FROM ItemPm i WHERE i.nombre = :nombre")
    , @NamedQuery(name = "ItemPm.findByDescripcion", query = "SELECT i FROM ItemPm i WHERE i.descripcion = :descripcion")})
public class ItemPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "idItem", fetch = FetchType.LAZY)
    private List<ItemLogistica> itemLogisticaList;

    public ItemPm() {
    }

    public ItemPm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof ItemPm)) {
            return false;
        }
        ItemPm other = (ItemPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ItemPm[ id=" + id + " ]";
    }
    
}
