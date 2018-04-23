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
@Table(name = "parametro_factorconversion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroFactorconversion.findAll", query = "SELECT p FROM ParametroFactorconversion p")
    , @NamedQuery(name = "ParametroFactorconversion.findById", query = "SELECT p FROM ParametroFactorconversion p WHERE p.id = :id")})
public class ParametroFactorconversion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "idParametroFactorconversion")
    private List<DatoProcesado> datoProcesadoList;
    @OneToMany(mappedBy = "idParametroFactorconversion")
    private List<NivelMaximo> nivelMaximoList;
    @JoinColumn(name = "id_unidad_medida", referencedColumnName = "id")
    @ManyToOne
    private UnidadMedida idUnidadMedida;
    @JoinColumn(name = "id_parametro", referencedColumnName = "para_id")
    @ManyToOne
    private Parametros idParametro;
    @JoinColumn(name = "id_factor_conversion", referencedColumnName = "id")
    @ManyToOne
    private FactorConversion idFactorConversion;

    public ParametroFactorconversion() {
    }

    public ParametroFactorconversion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public FactorConversion getIdFactorConversion() {
        return idFactorConversion;
    }

    public void setIdFactorConversion(FactorConversion idFactorConversion) {
        this.idFactorConversion = idFactorConversion;
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
        if (!(object instanceof ParametroFactorconversion)) {
            return false;
        }
        ParametroFactorconversion other = (ParametroFactorconversion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ParametroFactorconversion[ id=" + id + " ]";
    }
    
}
