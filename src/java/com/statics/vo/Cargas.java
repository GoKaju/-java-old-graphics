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
import javax.persistence.Cacheable;
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

/**
 *
 * @author FoxHG
 */
@Entity
@Cacheable(false)
@Table(name = "cargas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargas.findAll", query = "SELECT c FROM Cargas c")
    , @NamedQuery(name = "Cargas.findByCargId", query = "SELECT c FROM Cargas c WHERE c.cargId = :cargId")
    , @NamedQuery(name = "Cargas.findByCargDescripcion", query = "SELECT c FROM Cargas c WHERE c.cargDescripcion = :cargDescripcion")
    , @NamedQuery(name = "Cargas.findByCargObservaciones", query = "SELECT c FROM Cargas c WHERE c.cargObservaciones = :cargObservaciones")
    , @NamedQuery(name = "Cargas.findByCargCantidadtotal", query = "SELECT c FROM Cargas c WHERE c.cargCantidadtotal = :cargCantidadtotal")
    , @NamedQuery(name = "Cargas.findByCargExitosos", query = "SELECT c FROM Cargas c WHERE c.cargExitosos = :cargExitosos")
    , @NamedQuery(name = "Cargas.findByCargErrores", query = "SELECT c FROM Cargas c WHERE c.cargErrores = :cargErrores")
    , @NamedQuery(name = "Cargas.findByCargRuta", query = "SELECT c FROM Cargas c WHERE c.cargRuta = :cargRuta")
    , @NamedQuery(name = "Cargas.findByCargArchivo", query = "SELECT c FROM Cargas c WHERE c.cargArchivo = :cargArchivo")
    , @NamedQuery(name = "Cargas.findByCargArchivoPuntoMuestral", query = "SELECT c FROM Cargas c INNER JOIN PuntoMuestral p ON c.pumuId = p WHERE p = :pumu and c.cargArchivo = :cargArchivo ")
    , @NamedQuery(name = "Cargas.findByCargMetadata", query = "SELECT c FROM Cargas c WHERE c.cargMetadata = :cargMetadata")
    , @NamedQuery(name = "Cargas.findByCargFechainicial", query = "SELECT c FROM Cargas c WHERE c.cargFechainicial = :cargFechainicial")
    , @NamedQuery(name = "Cargas.findByCargFechacambio", query = "SELECT c FROM Cargas c WHERE c.cargFechacambio = :cargFechacambio")
    , @NamedQuery(name = "Cargas.findByCargRegistradopor", query = "SELECT c FROM Cargas c WHERE c.cargRegistradopor = :cargRegistradopor")})
public class Cargas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "carg_id")
    private Integer cargId;
    @Column(name = "carg_descripcion")
    private String cargDescripcion;
    @Column(name = "carg_observaciones")
    private String cargObservaciones;
    @Column(name = "carg_cantidadtotal")
    private Integer cargCantidadtotal;
    @Column(name = "carg_exitosos")
    private Integer cargExitosos;
    @Basic(optional = false)
    @Column(name = "carg_ultimaposicion")
    private int cargUltimaposicion;
    @Column(name = "ultima_fechacargada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaFechacargada;
    @Column(name = "carg_errores")
    private Integer cargErrores;
    @Column(name = "carg_ruta")
    private String cargRuta;
    @Column(name = "carg_archivo")
    private String cargArchivo;
    @Column(name = "carg_metadata")
    private String cargMetadata;
    @Column(name = "carg_fechainicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cargFechainicial;
    @Basic(optional = false)
    @Column(name = "carg_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cargFechacambio;
    @Basic(optional = false)
    @Column(name = "carg_registradopor")
    private int cargRegistradopor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargId")
    private List<CargaErrores> cargaErroresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargId")
    private List<CargaParametro> cargaParametroList;
    @JoinColumn(name = "pumu_id", referencedColumnName = "pumu_id")
    @ManyToOne(optional = false)
    private PuntoMuestral pumuId;

    public Cargas() {
    }

    public Cargas(Integer cargId) {
        this.cargId = cargId;
    }

    public Cargas(Integer cargId, int cargUltimaposicion, Date cargFechacambio, int cargRegistradopor) {
        this.cargId = cargId;
        this.cargUltimaposicion = cargUltimaposicion;
        this.cargFechacambio = cargFechacambio;
        this.cargRegistradopor = cargRegistradopor;
    }

    public Integer getCargId() {
        return cargId;
    }

    public void setCargId(Integer cargId) {
        this.cargId = cargId;
    }

    public String getCargDescripcion() {
        return cargDescripcion;
    }

    public void setCargDescripcion(String cargDescripcion) {
        this.cargDescripcion = cargDescripcion;
    }

    public String getCargObservaciones() {
        return cargObservaciones;
    }

    public void setCargObservaciones(String cargObservaciones) {
        this.cargObservaciones = cargObservaciones;
    }

    public Integer getCargCantidadtotal() {
        return cargCantidadtotal;
    }

    public void setCargCantidadtotal(Integer cargCantidadtotal) {
        this.cargCantidadtotal = cargCantidadtotal;
    }

    public Integer getCargExitosos() {
        return cargExitosos;
    }

    public void setCargExitosos(Integer cargExitosos) {
        this.cargExitosos = cargExitosos;
    }

    public int getCargUltimaposicion() {
        return cargUltimaposicion;
    }

    public void setCargUltimaposicion(int cargUltimaposicion) {
        this.cargUltimaposicion = cargUltimaposicion;
    }

    public Date getUltimaFechacargada() {
        return ultimaFechacargada;
    }

    public void setUltimaFechacargada(Date ultimaFechacargada) {
        this.ultimaFechacargada = ultimaFechacargada;
    }

    public Integer getCargErrores() {
        return cargErrores;
    }

    public void setCargErrores(Integer cargErrores) {
        this.cargErrores = cargErrores;
    }

    public String getCargRuta() {
        return cargRuta;
    }

    public void setCargRuta(String cargRuta) {
        this.cargRuta = cargRuta;
    }

    public String getCargArchivo() {
        return cargArchivo;
    }

    public void setCargArchivo(String cargArchivo) {
        this.cargArchivo = cargArchivo;
    }

    public String getCargMetadata() {
        return cargMetadata;
    }

    public void setCargMetadata(String cargMetadata) {
        this.cargMetadata = cargMetadata;
    }

    public Date getCargFechainicial() {
        return cargFechainicial;
    }

    public void setCargFechainicial(Date cargFechainicial) {
        this.cargFechainicial = cargFechainicial;
    }

    public Date getCargFechacambio() {
        return cargFechacambio;
    }

    public void setCargFechacambio(Date cargFechacambio) {
        this.cargFechacambio = cargFechacambio;
    }

    public int getCargRegistradopor() {
        return cargRegistradopor;
    }

    public void setCargRegistradopor(int cargRegistradopor) {
        this.cargRegistradopor = cargRegistradopor;
    }

    @XmlTransient
    public List<CargaErrores> getCargaErroresList() {
        return cargaErroresList;
    }

    public void setCargaErroresList(List<CargaErrores> cargaErroresList) {
        this.cargaErroresList = cargaErroresList;
    }

    @XmlTransient
    public List<CargaParametro> getCargaParametroList() {
        return cargaParametroList;
    }

    public void setCargaParametroList(List<CargaParametro> cargaParametroList) {
        this.cargaParametroList = cargaParametroList;
    }

    public PuntoMuestral getPumuId() {
        return pumuId;
    }

    public void setPumuId(PuntoMuestral pumuId) {
        this.pumuId = pumuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargId != null ? cargId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargas)) {
            return false;
        }
        Cargas other = (Cargas) object;
        if ((this.cargId == null && other.cargId != null) || (this.cargId != null && !this.cargId.equals(other.cargId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Cargas[ cargId=" + cargId + " ]";
    }
    
}
