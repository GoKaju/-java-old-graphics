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
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "separadores")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Separadores.findAll", query = "SELECT s FROM Separadores s")
    , @NamedQuery(name = "Separadores.findBySepaId", query = "SELECT s FROM Separadores s WHERE s.sepaId = :sepaId")
    , @NamedQuery(name = "Separadores.findBySepaDescripcion", query = "SELECT s FROM Separadores s WHERE s.sepaDescripcion = :sepaDescripcion")
    , @NamedQuery(name = "Separadores.findBySepaSeparador", query = "SELECT s FROM Separadores s WHERE s.sepaSeparador = :sepaSeparador")
    , @NamedQuery(name = "Separadores.findBySepaRegistradopor", query = "SELECT s FROM Separadores s WHERE s.sepaRegistradopor = :sepaRegistradopor")
    , @NamedQuery(name = "Separadores.findBySepaFechacambio", query = "SELECT s FROM Separadores s WHERE s.sepaFechacambio = :sepaFechacambio")})
public class Separadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sepa_id")
    private Integer sepaId;
    @Basic(optional = false)
    @Column(name = "sepa_descripcion")
    private String sepaDescripcion;
    @Basic(optional = false)
    @Column(name = "sepa_separador")
    private String sepaSeparador;
    @Basic(optional = false)
    @Column(name = "sepa_registradopor")
    private int sepaRegistradopor;
    @Basic(optional = false)
    @Column(name = "sepa_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sepaFechacambio;
    @OneToMany(mappedBy = "sepaId")
    private List<Estaciones> estacionesList;

    public Separadores() {
    }

    public Separadores(Integer sepaId) {
        this.sepaId = sepaId;
    }

    public Separadores(Integer sepaId, String sepaDescripcion, String sepaSeparador, int sepaRegistradopor, Date sepaFechacambio) {
        this.sepaId = sepaId;
        this.sepaDescripcion = sepaDescripcion;
        this.sepaSeparador = sepaSeparador;
        this.sepaRegistradopor = sepaRegistradopor;
        this.sepaFechacambio = sepaFechacambio;
    }

    public Integer getSepaId() {
        return sepaId;
    }

    public void setSepaId(Integer sepaId) {
        this.sepaId = sepaId;
    }

    public String getSepaDescripcion() {
        return sepaDescripcion;
    }

    public void setSepaDescripcion(String sepaDescripcion) {
        this.sepaDescripcion = sepaDescripcion;
    }

    public String getSepaSeparador() {
        return sepaSeparador;
    }

    public void setSepaSeparador(String sepaSeparador) {
        this.sepaSeparador = sepaSeparador;
    }

    public int getSepaRegistradopor() {
        return sepaRegistradopor;
    }

    public void setSepaRegistradopor(int sepaRegistradopor) {
        this.sepaRegistradopor = sepaRegistradopor;
    }

    public Date getSepaFechacambio() {
        return sepaFechacambio;
    }

    public void setSepaFechacambio(Date sepaFechacambio) {
        this.sepaFechacambio = sepaFechacambio;
    }

    @XmlTransient
    public List<Estaciones> getEstacionesList() {
        return estacionesList;
    }

    public void setEstacionesList(List<Estaciones> estacionesList) {
        this.estacionesList = estacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sepaId != null ? sepaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Separadores)) {
            return false;
        }
        Separadores other = (Separadores) object;
        if ((this.sepaId == null && other.sepaId != null) || (this.sepaId != null && !this.sepaId.equals(other.sepaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Separadores[ sepaId=" + sepaId + " ]";
    }
    
}
