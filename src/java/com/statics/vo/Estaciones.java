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
@Table(name = "estaciones")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estaciones.findAll", query = "SELECT e FROM Estaciones e")
    , @NamedQuery(name = "Estaciones.findByEstaId", query = "SELECT e FROM Estaciones e WHERE e.estaId = :estaId")
    , @NamedQuery(name = "Estaciones.findByEstaNombre", query = "SELECT e FROM Estaciones e WHERE e.estaNombre = :estaNombre")
    , @NamedQuery(name = "Estaciones.findByEstaCarpetacarga", query = "SELECT e FROM Estaciones e WHERE e.estaCarpetacarga = :estaCarpetacarga")
    , @NamedQuery(name = "Estaciones.findByEstaRutacargaservidor", query = "SELECT e FROM Estaciones e WHERE e.estaRutacargaservidor = :estaRutacargaservidor")
    , @NamedQuery(name = "Estaciones.findByEstaFechacambio", query = "SELECT e FROM Estaciones e WHERE e.estaFechacambio = :estaFechacambio")
    , @NamedQuery(name = "Estaciones.findByEstaRegistradopor", query = "SELECT e FROM Estaciones e WHERE e.estaRegistradopor = :estaRegistradopor")})
public class Estaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "esta_id")
    private Integer estaId;
    @Column(name = "esta_nombre")
    private String estaNombre;
    @Column(name = "esta_carpetacarga")
    private String estaCarpetacarga;
    @Column(name = "esta_rutacargaservidor")
    private String estaRutacargaservidor;
    @Column(name = "esta_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estaFechacambio;
    @Column(name = "esta_registradopor")
    private Integer estaRegistradopor;
    @OneToMany(mappedBy = "estaId")
    private List<PuntoMuestral> puntoMuestralList;
    @JoinColumn(name = "esta_idestado", referencedColumnName = "esta_id")
    @ManyToOne
    private Estados estaIdestado;
    @JoinColumn(name = "grup_id", referencedColumnName = "grup_id")
    @ManyToOne
    private Grupo grupId;
    @JoinColumn(name = "sepa_id", referencedColumnName = "sepa_id")
    @ManyToOne
    private Separadores sepaId;
    @JoinColumn(name = "fofe_id", referencedColumnName = "fofe_id")
    @ManyToOne(optional = false)
    private Formatofechas fofeId;

    public Estaciones() {
    }

    public Estaciones(Integer estaId) {
        this.estaId = estaId;
    }

    public Integer getEstaId() {
        return estaId;
    }

    public void setEstaId(Integer estaId) {
        this.estaId = estaId;
    }

    public String getEstaNombre() {
        return estaNombre;
    }

    public void setEstaNombre(String estaNombre) {
        this.estaNombre = estaNombre;
    }

    public String getEstaCarpetacarga() {
        return estaCarpetacarga;
    }

    public void setEstaCarpetacarga(String estaCarpetacarga) {
        this.estaCarpetacarga = estaCarpetacarga;
    }

    public String getEstaRutacargaservidor() {
        return estaRutacargaservidor;
    }

    public void setEstaRutacargaservidor(String estaRutacargaservidor) {
        this.estaRutacargaservidor = estaRutacargaservidor;
    }

    public Date getEstaFechacambio() {
        return estaFechacambio;
    }

    public void setEstaFechacambio(Date estaFechacambio) {
        this.estaFechacambio = estaFechacambio;
    }

    public Integer getEstaRegistradopor() {
        return estaRegistradopor;
    }

    public void setEstaRegistradopor(Integer estaRegistradopor) {
        this.estaRegistradopor = estaRegistradopor;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    public Estados getEstaIdestado() {
        return estaIdestado;
    }

    public void setEstaIdestado(Estados estaIdestado) {
        this.estaIdestado = estaIdestado;
    }

    public Grupo getGrupId() {
        return grupId;
    }

    public void setGrupId(Grupo grupId) {
        this.grupId = grupId;
    }

    public Separadores getSepaId() {
        return sepaId;
    }

    public void setSepaId(Separadores sepaId) {
        this.sepaId = sepaId;
    }

    public Formatofechas getFofeId() {
        return fofeId;
    }

    public void setFofeId(Formatofechas fofeId) {
        this.fofeId = fofeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estaId != null ? estaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estaciones)) {
            return false;
        }
        Estaciones other = (Estaciones) object;
        if ((this.estaId == null && other.estaId != null) || (this.estaId != null && !this.estaId.equals(other.estaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Estaciones[ estaId=" + estaId + " ]";
    }
    
}
