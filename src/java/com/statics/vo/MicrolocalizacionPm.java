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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "microlocalizacion_pm")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MicrolocalizacionPm.findAll", query = "SELECT m FROM MicrolocalizacionPm m")
    , @NamedQuery(name = "MicrolocalizacionPm.findById", query = "SELECT m FROM MicrolocalizacionPm m WHERE m.id = :id")})
public class MicrolocalizacionPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "idMicrolocalizacion", fetch = FetchType.LAZY)
    private List<PuntoMuestral> puntoMuestralList;
    @OneToMany(mappedBy = "idMicrolocalizacion", fetch = FetchType.LAZY)
    private List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList;
    @OneToMany(mappedBy = "idMicrolocalizacion", fetch = FetchType.LAZY)
    private List<CriterioMicrolocalizacion> criterioMicrolocalizacionList;

    public MicrolocalizacionPm() {
    }

    public MicrolocalizacionPm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    @XmlTransient
    public List<DatosadicionalesMicrolocalizacion> getDatosadicionalesMicrolocalizacionList() {
        return datosadicionalesMicrolocalizacionList;
    }

    public void setDatosadicionalesMicrolocalizacionList(List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList) {
        this.datosadicionalesMicrolocalizacionList = datosadicionalesMicrolocalizacionList;
    }

    @XmlTransient
    public List<CriterioMicrolocalizacion> getCriterioMicrolocalizacionList() {
        return criterioMicrolocalizacionList;
    }

    public void setCriterioMicrolocalizacionList(List<CriterioMicrolocalizacion> criterioMicrolocalizacionList) {
        this.criterioMicrolocalizacionList = criterioMicrolocalizacionList;
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
