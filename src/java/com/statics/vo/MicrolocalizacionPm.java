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
@Table(name = "microlocalizacion_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MicrolocalizacionPm.findAll", query = "SELECT m FROM MicrolocalizacionPm m")
    , @NamedQuery(name = "MicrolocalizacionPm.findById", query = "SELECT m FROM MicrolocalizacionPm m WHERE m.id = :id")
    , @NamedQuery(name = "MicrolocalizacionPm.findByFechaCreado", query = "SELECT m FROM MicrolocalizacionPm m WHERE m.fechaCreado = :fechaCreado")})
public class MicrolocalizacionPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaCreado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreado;
    @OneToMany(mappedBy = "idMicrolocalizacion")
    private List<PuntoMuestral> puntoMuestralList;
    @OneToMany(mappedBy = "idMicrolocalizacion")
    private List<CriterioMicrolocalizacion> criterioMicrolocalizacionList;
    @OneToMany(mappedBy = "idMicrolocalizacion")
    private List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList;

    public MicrolocalizacionPm() {
    }

    public MicrolocalizacionPm(Integer id) {
        this.id = id;
    }

    public MicrolocalizacionPm(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    @XmlTransient
    public List<CriterioMicrolocalizacion> getCriterioMicrolocalizacionList() {
        return criterioMicrolocalizacionList;
    }

    public void setCriterioMicrolocalizacionList(List<CriterioMicrolocalizacion> criterioMicrolocalizacionList) {
        this.criterioMicrolocalizacionList = criterioMicrolocalizacionList;
    }

    @XmlTransient
    public List<DatosadicionalesMicrolocalizacion> getDatosadicionalesMicrolocalizacionList() {
        return datosadicionalesMicrolocalizacionList;
    }

    public void setDatosadicionalesMicrolocalizacionList(List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList) {
        this.datosadicionalesMicrolocalizacionList = datosadicionalesMicrolocalizacionList;
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
        if (!(object instanceof MicrolocalizacionPm)) {
            return false;
        }
        MicrolocalizacionPm other = (MicrolocalizacionPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.MicrolocalizacionPm[ id=" + id + " ]";
    }
    
}
