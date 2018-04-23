/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "carga_parametro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargaParametro.findAll", query = "SELECT c FROM CargaParametro c")
    , @NamedQuery(name = "CargaParametro.findByCapaId", query = "SELECT c FROM CargaParametro c WHERE c.capaId = :capaId")
    , @NamedQuery(name = "CargaParametroList", query = "SELECT c FROM CargaParametro c WHERE c.paraId = :Parametro AND c.cargId = :Carga")
    , @NamedQuery(name = "CargaParametroGrafica1", query = "SELECT c FROM CargaParametro c WHERE c.cargId.cargId IN :cargas and c.paraId = :para  ")
})
public class CargaParametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "capa_id")
    private Integer capaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "papuId")
    private List<Datos> datosList;
    @JoinColumn(name = "para_id", referencedColumnName = "para_id")
    @ManyToOne(optional = false)
    private Parametros paraId;
    @JoinColumn(name = "carg_id", referencedColumnName = "carg_id")
    @ManyToOne(optional = false)
    private Cargas cargId;

    public CargaParametro() {
    }

    public CargaParametro(Integer capaId) {
        this.capaId = capaId;
    }

    public Integer getCapaId() {
        return capaId;
    }

    public void setCapaId(Integer capaId) {
        this.capaId = capaId;
    }

    @XmlTransient
    public List<Datos> getDatosList() {
        return datosList;
    }

    public void setDatosList(List<Datos> datosList) {
        this.datosList = datosList;
    }

    public Parametros getParaId() {
        return paraId;
    }

    public void setParaId(Parametros paraId) {
        this.paraId = paraId;
    }

    public Cargas getCargId() {
        return cargId;
    }

    public void setCargId(Cargas cargId) {
        this.cargId = cargId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (capaId != null ? capaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargaParametro)) {
            return false;
        }
        CargaParametro other = (CargaParametro) object;
        if ((this.capaId == null && other.capaId != null) || (this.capaId != null && !this.capaId.equals(other.capaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.CargaParametro[ capaId=" + capaId + " ]";
    }
    
}
