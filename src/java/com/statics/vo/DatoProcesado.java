/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "dato_procesado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatoProcesado.findAll", query = "SELECT d FROM DatoProcesado d")
    , @NamedQuery(name = "DatoProcesado.findById", query = "SELECT d FROM DatoProcesado d WHERE d.id = :id")
    , @NamedQuery(name = "DatoProcesado.findByValor", query = "SELECT d FROM DatoProcesado d WHERE d.valor = :valor")})
public class DatoProcesado implements Serializable {

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "fecha_conversion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConversion;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @JoinColumn(name = "id_punto_muestral", referencedColumnName = "pumu_id")
    @ManyToOne
    private PuntoMuestral idPuntoMuestral;
    @JoinColumn(name = "id_unidad_tiempo", referencedColumnName = "id")
    @ManyToOne
    private UnidadTiempo idUnidadTiempo;
    @JoinColumn(name = "id_parametro_factorconversion", referencedColumnName = "id")
    @ManyToOne
    private ParametroFactorconversion idParametroFactorconversion;

    public DatoProcesado() {
    }

    public DatoProcesado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public PuntoMuestral getIdPuntoMuestral() {
        return idPuntoMuestral;
    }

    public void setIdPuntoMuestral(PuntoMuestral idPuntoMuestral) {
        this.idPuntoMuestral = idPuntoMuestral;
    }

    public UnidadTiempo getIdUnidadTiempo() {
        return idUnidadTiempo;
    }

    public void setIdUnidadTiempo(UnidadTiempo idUnidadTiempo) {
        this.idUnidadTiempo = idUnidadTiempo;
    }

    public ParametroFactorconversion getIdParametroFactorconversion() {
        return idParametroFactorconversion;
    }

    public void setIdParametroFactorconversion(ParametroFactorconversion idParametroFactorconversion) {
        this.idParametroFactorconversion = idParametroFactorconversion;
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
        if (!(object instanceof DatoProcesado)) {
            return false;
        }
        DatoProcesado other = (DatoProcesado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.DatoProcesado[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaConversion() {
        return fechaConversion;
    }

    public void setFechaConversion(Date fechaConversion) {
        this.fechaConversion = fechaConversion;
    }
    
}
