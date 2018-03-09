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

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "datosadicionales_microlocalizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosadicionalesMicrolocalizacion.findAll", query = "SELECT d FROM DatosadicionalesMicrolocalizacion d")
    , @NamedQuery(name = "DatosadicionalesMicrolocalizacion.findById", query = "SELECT d FROM DatosadicionalesMicrolocalizacion d WHERE d.id = :id")})
public class DatosadicionalesMicrolocalizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_datos_adicionales", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DatosAdicionalesPm idDatosAdicionales;
    @JoinColumn(name = "id_microlocalizacion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MicrolocalizacionPm idMicrolocalizacion;

    public DatosadicionalesMicrolocalizacion() {
    }

    public DatosadicionalesMicrolocalizacion(Integer id) {
        this.id = id;
    }

    public DatosadicionalesMicrolocalizacion(DatosAdicionalesPm idDatosAdicionales, MicrolocalizacionPm idMicrolocalizacion) {
        this.idDatosAdicionales = idDatosAdicionales;
        this.idMicrolocalizacion = idMicrolocalizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DatosAdicionalesPm getIdDatosAdicionales() {
        return idDatosAdicionales;
    }

    public void setIdDatosAdicionales(DatosAdicionalesPm idDatosAdicionales) {
        this.idDatosAdicionales = idDatosAdicionales;
    }

    public MicrolocalizacionPm getIdMicrolocalizacion() {
        return idMicrolocalizacion;
    }

    public void setIdMicrolocalizacion(MicrolocalizacionPm idMicrolocalizacion) {
        this.idMicrolocalizacion = idMicrolocalizacion;
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
        if (!(object instanceof DatosadicionalesMicrolocalizacion)) {
            return false;
        }
        DatosadicionalesMicrolocalizacion other = (DatosadicionalesMicrolocalizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.DatosadicionalesMicrolocalizacion[ id=" + id + " ]";
    }
    
}
