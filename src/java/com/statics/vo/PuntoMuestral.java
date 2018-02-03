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
@Table(name = "punto_muestral")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PuntoMuestral.findAll", query = "SELECT p FROM PuntoMuestral p")
    , @NamedQuery(name = "PuntoMuestral.findByPumuId", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuId = :pumuId")
    , @NamedQuery(name = "PuntoMuestral.findByPumuNombre", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuNombre = :pumuNombre")
    , @NamedQuery(name = "PuntoMuestral.findByPumuDescripcion", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuDescripcion = :pumuDescripcion")
    , @NamedQuery(name = "PuntoMuestral.findByPumuLong", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuLong = :pumuLong")
    , @NamedQuery(name = "PuntoMuestral.findByPumuLat", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuLat = :pumuLat")
    , @NamedQuery(name = "PuntoMuestral.findByPumuFechainicial", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuFechainicial = :pumuFechainicial")
    , @NamedQuery(name = "PuntoMuestral.findByPumuRegistradopor", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuRegistradopor = :pumuRegistradopor")
    , @NamedQuery(name = "PuntoMuestral.findByPumuFechacambio", query = "SELECT p FROM PuntoMuestral p WHERE p.pumuFechacambio = :pumuFechacambio")})
public class PuntoMuestral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pumu_id")
    private Integer pumuId;
    @Column(name = "pumu_nombre")
    private String pumuNombre;
    @Column(name = "pumu_descripcion")
    private String pumuDescripcion;
    @Column(name = "pumu_long")
    private String pumuLong;
    @Column(name = "pumu_lat")
    private String pumuLat;
    @Column(name = "pumu_fechainicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pumuFechainicial;
    @Column(name = "pumu_registradopor")
    private Integer pumuRegistradopor;
    @Column(name = "pumu_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pumuFechacambio;
    @JoinColumn(name = "esta_id", referencedColumnName = "esta_id")
    @ManyToOne
    private Estaciones estaId;
    @JoinColumn(name = "camp_id", referencedColumnName = "camp_id")
    @ManyToOne
    private Campanas campId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pumuId")
    private List<Cargas> cargasList;

    public PuntoMuestral() {
    }

    public PuntoMuestral(Integer pumuId) {
        this.pumuId = pumuId;
    }

    public Integer getPumuId() {
        return pumuId;
    }

    public void setPumuId(Integer pumuId) {
        this.pumuId = pumuId;
    }

    public String getPumuNombre() {
        return pumuNombre;
    }

    public void setPumuNombre(String pumuNombre) {
        this.pumuNombre = pumuNombre;
    }

    public String getPumuDescripcion() {
        return pumuDescripcion;
    }

    public void setPumuDescripcion(String pumuDescripcion) {
        this.pumuDescripcion = pumuDescripcion;
    }

    public String getPumuLong() {
        return pumuLong;
    }

    public void setPumuLong(String pumuLong) {
        this.pumuLong = pumuLong;
    }

    public String getPumuLat() {
        return pumuLat;
    }

    public void setPumuLat(String pumuLat) {
        this.pumuLat = pumuLat;
    }

    public Date getPumuFechainicial() {
        return pumuFechainicial;
    }

    public void setPumuFechainicial(Date pumuFechainicial) {
        this.pumuFechainicial = pumuFechainicial;
    }

    public Integer getPumuRegistradopor() {
        return pumuRegistradopor;
    }

    public void setPumuRegistradopor(Integer pumuRegistradopor) {
        this.pumuRegistradopor = pumuRegistradopor;
    }

    public Date getPumuFechacambio() {
        return pumuFechacambio;
    }

    public void setPumuFechacambio(Date pumuFechacambio) {
        this.pumuFechacambio = pumuFechacambio;
    }

    public Estaciones getEstaId() {
        return estaId;
    }

    public void setEstaId(Estaciones estaId) {
        this.estaId = estaId;
    }

    public Campanas getCampId() {
        return campId;
    }

    public void setCampId(Campanas campId) {
        this.campId = campId;
    }

    @XmlTransient
    public List<Cargas> getCargasList() {
        return cargasList;
    }

    public void setCargasList(List<Cargas> cargasList) {
        this.cargasList = cargasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pumuId != null ? pumuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoMuestral)) {
            return false;
        }
        PuntoMuestral other = (PuntoMuestral) object;
        if ((this.pumuId == null && other.pumuId != null) || (this.pumuId != null && !this.pumuId.equals(other.pumuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.PuntoMuestral[ pumuId=" + pumuId + " ]";
    }
    
}
