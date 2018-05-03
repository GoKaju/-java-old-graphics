/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    , @NamedQuery(name = "Parametros.findByParaId", query = "SELECT p FROM Parametros p WHERE p.paraId = :paraId")
    , @NamedQuery(name = "Parametros.findByPareNombre", query = "SELECT p FROM Parametros p WHERE p.pareNombre = :pareNombre")
    , @NamedQuery(name = "Parametros.findByPareCodigo", query = "SELECT p FROM Parametros p WHERE p.paraCodigo = :pareCode")
    , @NamedQuery(name = "Parametros.findByPareDescripcion", query = "SELECT p FROM Parametros p WHERE p.pareDescripcion = :pareDescripcion")
    , @NamedQuery(name = "Parametros.findByPareRegistradopor", query = "SELECT p FROM Parametros p WHERE p.pareRegistradopor = :pareRegistradopor")
    , @NamedQuery(name = "Parametros.findByPareFechacambio", query = "SELECT p FROM Parametros p WHERE p.pareFechacambio = :pareFechacambio")
    , @NamedQuery(name = "Parametros.findByCargaParametro", query = "SELECT DISTINCT(c.paraId) FROM CargaParametro c WHERE c.cargId.cargId IN :Cargas and c.paraId.paraTipografica = :tipo")
    , @NamedQuery(name = "Parametros.findByTipoGraf", query = "SELECT p FROM Parametros p WHERE p.paraTipografica = :tipo")
})
public class Parametros implements Serializable {
    @OneToMany(mappedBy = "idParametro")
    private List<UnidadmedidaParametro> unidadmedidaParametroList;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "masa_molar")
    private Double masaMolar;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "para_id")
    private Integer paraId;
    @Column(name = "pare_nombre")
    private String pareNombre;
    @Column(name = "pare_descripcion")
    private String pareDescripcion;
    @Column(name = "pare_registradopor")
    private Integer pareRegistradopor;
    @Column(name = "pare_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pareFechacambio;
    @Column(name = "para_estado")
    private Integer paraEstado;
    @Basic(optional = false)
    @Column(name = "para_codigo")
    private int paraCodigo;
    @Column(name = "para_tipografica")
    private String paraTipografica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paraId")
    private List<ParametroLabels> parametroLabelsList;
    @OneToMany(mappedBy = "idParametro")
    private List<ParametroFactorconversion> parametroFactorconversionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paraId")
    private List<CargaParametro> cargaParametroList;

    public Parametros() {
    }

    public Parametros(Integer paraId) {
        this.paraId = paraId;
    }

    public Parametros(Integer paraId, int paraCodigo) {
        this.paraId = paraId;
        this.paraCodigo = paraCodigo;
    }

    public Integer getParaId() {
        return paraId;
    }

    public void setParaId(Integer paraId) {
        this.paraId = paraId;
    }

    public String getPareNombre() {
        return pareNombre;
    }

    public void setPareNombre(String pareNombre) {
        this.pareNombre = pareNombre;
    }

    public String getPareDescripcion() {
        return pareDescripcion;
    }

    public void setPareDescripcion(String pareDescripcion) {
        this.pareDescripcion = pareDescripcion;
    }

    public Integer getPareRegistradopor() {
        return pareRegistradopor;
    }

    public void setPareRegistradopor(Integer pareRegistradopor) {
        this.pareRegistradopor = pareRegistradopor;
    }

    public Date getPareFechacambio() {
        return pareFechacambio;
    }

    public void setPareFechacambio(Date pareFechacambio) {
        this.pareFechacambio = pareFechacambio;
    }

    public Integer getParaEstado() {
        return paraEstado;
    }

    public void setParaEstado(Integer paraEstado) {
        this.paraEstado = paraEstado;
    }

    public int getParaCodigo() {
        return paraCodigo;
    }

    public void setParaCodigo(int paraCodigo) {
        this.paraCodigo = paraCodigo;
    }

    public String getParaTipografica() {
        return paraTipografica;
    }

    public void setParaTipografica(String paraTipografica) {
        this.paraTipografica = paraTipografica;
    }

    @XmlTransient
    public List<ParametroLabels> getParametroLabelsList() {
        return parametroLabelsList;
    }

    public void setParametroLabelsList(List<ParametroLabels> parametroLabelsList) {
        this.parametroLabelsList = parametroLabelsList;
    }

    @XmlTransient
    public List<ParametroFactorconversion> getParametroFactorconversionList() {
        return parametroFactorconversionList;
    }

    public void setParametroFactorconversionList(List<ParametroFactorconversion> parametroFactorconversionList) {
        this.parametroFactorconversionList = parametroFactorconversionList;
    }

    @XmlTransient
    public List<CargaParametro> getCargaParametroList() {
        return cargaParametroList;
    }

    public void setCargaParametroList(List<CargaParametro> cargaParametroList) {
        this.cargaParametroList = cargaParametroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paraId != null ? paraId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.paraId == null && other.paraId != null) || (this.paraId != null && !this.paraId.equals(other.paraId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Parametros[ paraId=" + paraId + " ]";
    }

    public Double getMasaMolar() {
        return masaMolar;
    }

    public void setMasaMolar(Double masaMolar) {
        this.masaMolar = masaMolar;
    }

    @XmlTransient
    public List<UnidadmedidaParametro> getUnidadmedidaParametroList() {
        return unidadmedidaParametroList;
    }

    public void setUnidadmedidaParametroList(List<UnidadmedidaParametro> unidadmedidaParametroList) {
        this.unidadmedidaParametroList = unidadmedidaParametroList;
    }
    
}
