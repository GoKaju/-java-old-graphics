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
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "datos")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datos.findAll", query = "SELECT d FROM Datos d")
    , @NamedQuery(name = "Datos.findByDatoId", query = "SELECT d FROM Datos d WHERE d.datoId = :datoId")
    , @NamedQuery(name = "Datos.findByDatoData", query = "SELECT d FROM Datos d WHERE d.datoData = :datoData")
    , @NamedQuery(name = "Datos.findByDatoFecha", query = "SELECT d FROM Datos d WHERE d.datoFecha = :datoFecha")})
public class Datos implements Serializable {

    @Basic(optional = false)
    @Column(name = "dato_data")
    private String datoData;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dato_id")
    private Integer datoId;
    @Basic(optional = false)
    @Column(name = "dato_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datoFecha;
    @JoinColumn(name = "papu_id", referencedColumnName = "capa_id")
    @ManyToOne(optional = false)
    private CargaParametro papuId;

    public Datos() {
    }

    public Datos(Integer datoId) {
        this.datoId = datoId;
    }

    public Datos(Integer datoId, String datoData, Date datoFecha) {
        this.datoId = datoId;
        this.datoData = datoData;
        this.datoFecha = datoFecha;
    }

    public Integer getDatoId() {
        return datoId;
    }

    public void setDatoId(Integer datoId) {
        this.datoId = datoId;
    }


    public Date getDatoFecha() {
        return datoFecha;
    }

    public void setDatoFecha(Date datoFecha) {
        this.datoFecha = datoFecha;
    }

    public CargaParametro getPapuId() {
        return papuId;
    }

    public void setPapuId(CargaParametro papuId) {
        this.papuId = papuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datoId != null ? datoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datos)) {
            return false;
        }
        Datos other = (Datos) object;
        if ((this.datoId == null && other.datoId != null) || (this.datoId != null && !this.datoId.equals(other.datoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Datos[ datoId=" + datoId + " ]";
    }

    public String getDatoData() {
        return datoData;
    }

    public void setDatoData(String datoData) {
        this.datoData = datoData;
    }
    
}
