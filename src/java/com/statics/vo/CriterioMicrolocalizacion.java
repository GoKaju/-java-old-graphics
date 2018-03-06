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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "criterio_microlocalizacion")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CriterioMicrolocalizacion.findAll", query = "SELECT c FROM CriterioMicrolocalizacion c")
    , @NamedQuery(name = "CriterioMicrolocalizacion.findById", query = "SELECT c FROM CriterioMicrolocalizacion c WHERE c.id = :id")
    , @NamedQuery(name = "CriterioMicrolocalizacion.findByCumpleCriterio", query = "SELECT c FROM CriterioMicrolocalizacion c WHERE c.cumpleCriterio = :cumpleCriterio")
    , @NamedQuery(name = "CriterioMicrolocalizacion.findByObservacionCriterio", query = "SELECT c FROM CriterioMicrolocalizacion c WHERE c.observacionCriterio = :observacionCriterio")})
public class CriterioMicrolocalizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cumple_criterio")
    private Boolean cumpleCriterio;
    @Column(name = "observacion_criterio")
    private String observacionCriterio;
    @JoinColumn(name = "id_microlocalizacion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MicrolocalizacionPm idMicrolocalizacion;
    @JoinColumn(name = "id_criterio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CriterioPm idCriterio;

    public CriterioMicrolocalizacion() {
    }

    public CriterioMicrolocalizacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCumpleCriterio() {
        return cumpleCriterio;
    }

    public void setCumpleCriterio(Boolean cumpleCriterio) {
        this.cumpleCriterio = cumpleCriterio;
    }

    public String getObservacionCriterio() {
        return observacionCriterio;
    }

    public void setObservacionCriterio(String observacionCriterio) {
        this.observacionCriterio = observacionCriterio;
    }

    public MicrolocalizacionPm getIdMicrolocalizacion() {
        return idMicrolocalizacion;
    }

    public void setIdMicrolocalizacion(MicrolocalizacionPm idMicrolocalizacion) {
        this.idMicrolocalizacion = idMicrolocalizacion;
    }

    public CriterioPm getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(CriterioPm idCriterio) {
        this.idCriterio = idCriterio;
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
        if (!(object instanceof CriterioMicrolocalizacion)) {
            return false;
        }
        CriterioMicrolocalizacion other = (CriterioMicrolocalizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.CriterioMicrolocalizacion[ id=" + id + " ]";
    }
    
}
