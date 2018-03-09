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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "formatofechas")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formatofechas.findAll", query = "SELECT f FROM Formatofechas f")
    , @NamedQuery(name = "Formatofechas.findByFofeId", query = "SELECT f FROM Formatofechas f WHERE f.fofeId = :fofeId")
    , @NamedQuery(name = "Formatofechas.findByFofeValue", query = "SELECT f FROM Formatofechas f WHERE f.fofeValue = :fofeValue")
    , @NamedQuery(name = "Formatofechas.findByFofeLabel", query = "SELECT f FROM Formatofechas f WHERE f.fofeLabel = :fofeLabel")})
public class Formatofechas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fofe_id")
    private Integer fofeId;
    @Basic(optional = false)
    @Column(name = "fofe_value")
    private String fofeValue;
    @Basic(optional = false)
    @Column(name = "fofe_label")
    private String fofeLabel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fofeId")
    private List<Estaciones> estacionesList;

    public Formatofechas() {
    }

    public Formatofechas(Integer fofeId) {
        this.fofeId = fofeId;
    }

    public Formatofechas(Integer fofeId, String fofeValue, String fofeLabel) {
        this.fofeId = fofeId;
        this.fofeValue = fofeValue;
        this.fofeLabel = fofeLabel;
    }

    public Integer getFofeId() {
        return fofeId;
    }

    public void setFofeId(Integer fofeId) {
        this.fofeId = fofeId;
    }

    public String getFofeValue() {
        return fofeValue;
    }

    public void setFofeValue(String fofeValue) {
        this.fofeValue = fofeValue;
    }

    public String getFofeLabel() {
        return fofeLabel;
    }

    public void setFofeLabel(String fofeLabel) {
        this.fofeLabel = fofeLabel;
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
        hash += (fofeId != null ? fofeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formatofechas)) {
            return false;
        }
        Formatofechas other = (Formatofechas) object;
        if ((this.fofeId == null && other.fofeId != null) || (this.fofeId != null && !this.fofeId.equals(other.fofeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Formatofechas[ fofeId=" + fofeId + " ]";
    }
    
}
