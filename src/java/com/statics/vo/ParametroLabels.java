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
@Table(name = "parametro_labels")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroLabels.findAll", query = "SELECT p FROM ParametroLabels p")
    , @NamedQuery(name = "ParametroLabels.findByPalaId", query = "SELECT p FROM ParametroLabels p WHERE p.palaId = :palaId")
    , @NamedQuery(name = "ParametroLabels.findActivos", query = "SELECT p FROM ParametroLabels p inner join Parametros as pa on p.paraId = pa WHERE pa.paraEstado>0")
    , @NamedQuery(name = "ParametroLabels.findByPalaLabel", query = "SELECT p FROM ParametroLabels p WHERE p.palaLabel = :palaLabel")
    , @NamedQuery(name = "ParametroLabels.findByPalaRegistradopor", query = "SELECT p FROM ParametroLabels p WHERE p.palaRegistradopor = :palaRegistradopor")
    , @NamedQuery(name = "ParametroLabels.findByPalaFechacambio", query = "SELECT p FROM ParametroLabels p WHERE p.palaFechacambio = :palaFechacambio")})
public class ParametroLabels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pala_id")
    private Integer palaId;
    @Basic(optional = false)
    @Column(name = "pala_label")
    private String palaLabel;
    @Basic(optional = false)
    @Column(name = "pala_registradopor")
    private int palaRegistradopor;
    @Basic(optional = false)
    @Column(name = "pala_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date palaFechacambio;
    @JoinColumn(name = "para_id", referencedColumnName = "para_id")
    @ManyToOne(optional = false)
    private Parametros paraId;

    public ParametroLabels() {
    }

    public ParametroLabels(Integer palaId) {
        this.palaId = palaId;
    }

    public ParametroLabels(Integer palaId, String palaLabel, int palaRegistradopor, Date palaFechacambio) {
        this.palaId = palaId;
        this.palaLabel = palaLabel;
        this.palaRegistradopor = palaRegistradopor;
        this.palaFechacambio = palaFechacambio;
    }

    public Integer getPalaId() {
        return palaId;
    }

    public void setPalaId(Integer palaId) {
        this.palaId = palaId;
    }

    public String getPalaLabel() {
        return palaLabel;
    }

    public void setPalaLabel(String palaLabel) {
        this.palaLabel = palaLabel;
    }

    public int getPalaRegistradopor() {
        return palaRegistradopor;
    }

    public void setPalaRegistradopor(int palaRegistradopor) {
        this.palaRegistradopor = palaRegistradopor;
    }

    public Date getPalaFechacambio() {
        return palaFechacambio;
    }

    public void setPalaFechacambio(Date palaFechacambio) {
        this.palaFechacambio = palaFechacambio;
    }

    public Parametros getParaId() {
        return paraId;
    }

    public void setParaId(Parametros paraId) {
        this.paraId = paraId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (palaId != null ? palaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroLabels)) {
            return false;
        }
        ParametroLabels other = (ParametroLabels) object;
        if ((this.palaId == null && other.palaId != null) || (this.palaId != null && !this.palaId.equals(other.palaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ParametroLabels[ palaId=" + palaId + " ]";
    }
    
}
