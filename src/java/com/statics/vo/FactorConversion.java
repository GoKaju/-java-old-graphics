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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "factor_conversion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FactorConversion.findAll", query = "SELECT f FROM FactorConversion f")
    , @NamedQuery(name = "FactorConversion.findById", query = "SELECT f FROM FactorConversion f WHERE f.id = :id")})
public class FactorConversion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "idFactorConversion")
    private List<ParametroFactorconversion> parametroFactorconversionList;
    @JoinColumn(name = "id_unidad_destino", referencedColumnName = "id")
    @ManyToOne
    private UnidadMedida idUnidadDestino;
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    @ManyToOne
    private Pais idPais;

    public FactorConversion() {
    }

    public FactorConversion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<ParametroFactorconversion> getParametroFactorconversionList() {
        return parametroFactorconversionList;
    }

    public void setParametroFactorconversionList(List<ParametroFactorconversion> parametroFactorconversionList) {
        this.parametroFactorconversionList = parametroFactorconversionList;
    }

    public UnidadMedida getIdUnidadDestino() {
        return idUnidadDestino;
    }

    public void setIdUnidadDestino(UnidadMedida idUnidadDestino) {
        this.idUnidadDestino = idUnidadDestino;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
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
        if (!(object instanceof FactorConversion)) {
            return false;
        }
        FactorConversion other = (FactorConversion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.FactorConversion[ id=" + id + " ]";
    }
    
}
