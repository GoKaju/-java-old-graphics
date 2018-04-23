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
 * @author FoxHG
 */
@Entity
@Table(name = "nivel_maximo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelMaximo.findAll", query = "SELECT n FROM NivelMaximo n")
    , @NamedQuery(name = "NivelMaximo.findById", query = "SELECT n FROM NivelMaximo n WHERE n.id = :id")
    , @NamedQuery(name = "NivelMaximo.findByNivelMinimo", query = "SELECT n FROM NivelMaximo n WHERE n.nivelMinimo = :nivelMinimo")
    , @NamedQuery(name = "NivelMaximo.findByNivelMaximo", query = "SELECT n FROM NivelMaximo n WHERE n.nivelMaximo = :nivelMaximo")})
public class NivelMaximo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nivel_minimo")
    private Double nivelMinimo;
    @Column(name = "nivel_maximo")
    private Double nivelMaximo;
    @JoinColumn(name = "id_parametro_factorconversion", referencedColumnName = "id")
    @ManyToOne
    private ParametroFactorconversion idParametroFactorconversion;
    @JoinColumn(name = "id_unidad_tiempo", referencedColumnName = "id")
    @ManyToOne
    private UnidadTiempo idUnidadTiempo;

    public NivelMaximo() {
    }

    public NivelMaximo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNivelMinimo() {
        return nivelMinimo;
    }

    public void setNivelMinimo(Double nivelMinimo) {
        this.nivelMinimo = nivelMinimo;
    }

    public Double getNivelMaximo() {
        return nivelMaximo;
    }

    public void setNivelMaximo(Double nivelMaximo) {
        this.nivelMaximo = nivelMaximo;
    }

    public ParametroFactorconversion getIdParametroFactorconversion() {
        return idParametroFactorconversion;
    }

    public void setIdParametroFactorconversion(ParametroFactorconversion idParametroFactorconversion) {
        this.idParametroFactorconversion = idParametroFactorconversion;
    }

    public UnidadTiempo getIdUnidadTiempo() {
        return idUnidadTiempo;
    }

    public void setIdUnidadTiempo(UnidadTiempo idUnidadTiempo) {
        this.idUnidadTiempo = idUnidadTiempo;
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
        if (!(object instanceof NivelMaximo)) {
            return false;
        }
        NivelMaximo other = (NivelMaximo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.NivelMaximo[ id=" + id + " ]";
    }
    
}
