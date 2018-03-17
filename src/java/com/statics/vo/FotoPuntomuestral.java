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
@Table(name = "foto_puntomuestral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoPuntomuestral.findAll", query = "SELECT f FROM FotoPuntomuestral f")
    , @NamedQuery(name = "FotoPuntomuestral.findById", query = "SELECT f FROM FotoPuntomuestral f WHERE f.id = :id")
    , @NamedQuery(name = "FotoPuntomuestral.findByNombre", query = "SELECT f FROM FotoPuntomuestral f WHERE f.nombre = :nombre")})
public class FotoPuntomuestral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "id_punto", referencedColumnName = "pumu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PuntoMuestral idPunto;

    public FotoPuntomuestral() {
    }

    public FotoPuntomuestral(String nombre, PuntoMuestral idPunto) {
        this.nombre = nombre;
        this.idPunto = idPunto;
    }

    public FotoPuntomuestral(Integer id) {
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

    public PuntoMuestral getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(PuntoMuestral idPunto) {
        this.idPunto = idPunto;
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
        if (!(object instanceof FotoPuntomuestral)) {
            return false;
        }
        FotoPuntomuestral other = (FotoPuntomuestral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.FotoPuntomuestral[ id=" + id + " ]";
    }
    
}
