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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IMAGINAMOS
 */
@Entity
@Table(name = "unidadmedida_parametro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadmedidaParametro.findAll", query = "SELECT u FROM UnidadmedidaParametro u"),
    @NamedQuery(name = "UnidadmedidaParametro.findById", query = "SELECT u FROM UnidadmedidaParametro u WHERE u.id = :id")})
public class UnidadmedidaParametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_unidad_medida", referencedColumnName = "id")
    @ManyToOne
    private UnidadMedida idUnidadMedida;
    @JoinColumn(name = "id_parametro", referencedColumnName = "para_id")
    @ManyToOne
    private Parametros idParametro;

    public UnidadmedidaParametro() {
    }

    public UnidadmedidaParametro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnidadMedida getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(UnidadMedida idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public Parametros getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Parametros idParametro) {
        this.idParametro = idParametro;
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
        if (!(object instanceof UnidadmedidaParametro)) {
            return false;
        }
        UnidadmedidaParametro other = (UnidadmedidaParametro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.UnidadmedidaParametro[ id=" + id + " ]";
    }
    
}
