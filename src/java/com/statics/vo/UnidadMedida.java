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
@Table(name = "unidad_medida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadMedida.findAll", query = "SELECT u FROM UnidadMedida u")
    , @NamedQuery(name = "UnidadMedida.findById", query = "SELECT u FROM UnidadMedida u WHERE u.id = :id")
    , @NamedQuery(name = "UnidadMedida.findByDescripcion", query = "SELECT u FROM UnidadMedida u WHERE u.descripcion = :descripcion")
    , @NamedQuery(name = "UnidadMedida.findByFactor", query = "SELECT u FROM UnidadMedida u WHERE u.factor = :factor")})
public class UnidadMedida implements Serializable {
    @OneToMany(mappedBy = "idUnidadMedida")
    private List<UnidadmedidaParametro> unidadmedidaParametroList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "factor")
    private Double factor;
    @OneToMany(mappedBy = "idUnidadMedida")
    private List<ParametroFactorconversion> parametroFactorconversionList;
    @OneToMany(mappedBy = "idUnidadDestino")
    private List<FactorConversion> factorConversionList;

    public UnidadMedida() {
    }

    public UnidadMedida(Integer id) {
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

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    @XmlTransient
    public List<ParametroFactorconversion> getParametroFactorconversionList() {
        return parametroFactorconversionList;
    }

    public void setParametroFactorconversionList(List<ParametroFactorconversion> parametroFactorconversionList) {
        this.parametroFactorconversionList = parametroFactorconversionList;
    }

    @XmlTransient
    public List<FactorConversion> getFactorConversionList() {
        return factorConversionList;
    }

    public void setFactorConversionList(List<FactorConversion> factorConversionList) {
        this.factorConversionList = factorConversionList;
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
        if (!(object instanceof UnidadMedida)) {
            return false;
        }
        UnidadMedida other = (UnidadMedida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.UnidadMedida[ id=" + id + " ]";
    }

    @XmlTransient
    public List<UnidadmedidaParametro> getUnidadmedidaParametroList() {
        return unidadmedidaParametroList;
    }

    public void setUnidadmedidaParametroList(List<UnidadmedidaParametro> unidadmedidaParametroList) {
        this.unidadmedidaParametroList = unidadmedidaParametroList;
    }
    
}
