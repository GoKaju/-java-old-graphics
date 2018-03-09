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
@Table(name = "macrolocalizacion_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MacrolocalizacionPm.findAll", query = "SELECT m FROM MacrolocalizacionPm m")
    , @NamedQuery(name = "MacrolocalizacionPm.findById", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.id = :id")
    , @NamedQuery(name = "MacrolocalizacionPm.findByObservacionEmisionDominante", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.observacionEmisionDominante = :observacionEmisionDominante")
    , @NamedQuery(name = "MacrolocalizacionPm.findByDistanciaAlBorde", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.distanciaAlBorde = :distanciaAlBorde")
    , @NamedQuery(name = "MacrolocalizacionPm.findByAnchoVia", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.anchoVia = :anchoVia")
    , @NamedQuery(name = "MacrolocalizacionPm.findByTraficoDiario1", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.traficoDiario1 = :traficoDiario1")
    , @NamedQuery(name = "MacrolocalizacionPm.findByTraficoDiario2", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.traficoDiario2 = :traficoDiario2")
    , @NamedQuery(name = "MacrolocalizacionPm.findByVelocidadPromedio", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.velocidadPromedio = :velocidadPromedio")
    , @NamedQuery(name = "MacrolocalizacionPm.findByPorcentajeVehiculosPesados", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.porcentajeVehiculosPesados = :porcentajeVehiculosPesados")
    , @NamedQuery(name = "MacrolocalizacionPm.findByEstadoVia", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.estadoVia = :estadoVia")
    , @NamedQuery(name = "MacrolocalizacionPm.findByTiempoMuestreo", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.tiempoMuestreo = :tiempoMuestreo")
    , @NamedQuery(name = "MacrolocalizacionPm.findByTipo", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.tipo = :tipo")
    , @NamedQuery(name = "MacrolocalizacionPm.findByDistanciaFuente", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.distanciaFuente = :distanciaFuente")
    , @NamedQuery(name = "MacrolocalizacionPm.findByDireccionGrados", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.direccionGrados = :direccionGrados")
    , @NamedQuery(name = "MacrolocalizacionPm.findByFuenteEvaluada", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.fuenteEvaluada = :fuenteEvaluada")
    , @NamedQuery(name = "MacrolocalizacionPm.findByEncajonada", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.encajonada = :encajonada")
    , @NamedQuery(name = "MacrolocalizacionPm.findByLibre", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.libre = :libre")
    , @NamedQuery(name = "MacrolocalizacionPm.findByObservacionPuntoCritico", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.observacionPuntoCritico = :observacionPuntoCritico")
    , @NamedQuery(name = "MacrolocalizacionPm.findByCiudadesCercanas", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.ciudadesCercanas = :ciudadesCercanas")
    , @NamedQuery(name = "MacrolocalizacionPm.findByRegionales", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.regionales = :regionales")
    , @NamedQuery(name = "MacrolocalizacionPm.findByObservacionesRuralesFondo", query = "SELECT m FROM MacrolocalizacionPm m WHERE m.observacionesRuralesFondo = :observacionesRuralesFondo")})
public class MacrolocalizacionPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "observacion_emision_dominante")
    private String observacionEmisionDominante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "distancia_al_borde")
    private Double distanciaAlBorde;
    @Column(name = "ancho_via")
    private Double anchoVia;
    @Column(name = "trafico_diario_1")
    private Boolean traficoDiario1;
    @Column(name = "trafico_diario_2")
    private Boolean traficoDiario2;
    @Column(name = "velocidad_promedio")
    private Double velocidadPromedio;
    @Column(name = "porcentaje_vehiculos_pesados")
    private Integer porcentajeVehiculosPesados;
    @Column(name = "estado_via")
    private String estadoVia;
    @Column(name = "tiempo_muestreo")
    private Integer tiempoMuestreo;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "distancia_fuente")
    private Double distanciaFuente;
    @Column(name = "direccion_grados")
    private String direccionGrados;
    @Column(name = "fuente_evaluada")
    private String fuenteEvaluada;
    @Column(name = "encajonada")
    private Boolean encajonada;
    @Column(name = "libre")
    private Boolean libre;
    @Column(name = "observacion_punto_critico")
    private String observacionPuntoCritico;
    @Column(name = "ciudades_cercanas")
    private String ciudadesCercanas;
    @Column(name = "regionales")
    private String regionales;
    @Column(name = "observaciones_rurales_fondo")
    private String observacionesRuralesFondo;
    @OneToMany(mappedBy = "idMacrolocalizacion", fetch = FetchType.LAZY)
    private List<PuntoMuestral> puntoMuestralList;
    @JoinColumn(name = "id_tipo_area", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoAreaPm idTipoArea;
    @JoinColumn(name = "id_tiempo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TiempoPm idTiempo;
    @JoinColumn(name = "id_emision_dominante", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmisionDominantePm idEmisionDominante;
    @JoinColumn(name = "id_clima", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ClimaPm idClima;

    public MacrolocalizacionPm() {
    }

    public MacrolocalizacionPm(Integer id) {
        this.id = id;
    }

    public MacrolocalizacionPm(String observacionEmisionDominante, Double distanciaAlBorde, Double anchoVia, Boolean traficoDiario1, Boolean traficoDiario2, Double velocidadPromedio, Integer porcentajeVehiculosPesados, String estadoVia, Integer tiempoMuestreo, String tipo, Double distanciaFuente, String direccionGrados, String fuenteEvaluada, Boolean encajonada, Boolean libre, String observacionPuntoCritico, String ciudadesCercanas, String regionales, String observacionesRuralesFondo, TipoAreaPm idTipoArea, TiempoPm idTiempo, EmisionDominantePm idEmisionDominante, ClimaPm idClima) {
        this.observacionEmisionDominante = observacionEmisionDominante;
        this.distanciaAlBorde = distanciaAlBorde;
        this.anchoVia = anchoVia;
        this.traficoDiario1 = traficoDiario1;
        this.traficoDiario2 = traficoDiario2;
        this.velocidadPromedio = velocidadPromedio;
        this.porcentajeVehiculosPesados = porcentajeVehiculosPesados;
        this.estadoVia = estadoVia;
        this.tiempoMuestreo = tiempoMuestreo;
        this.tipo = tipo;
        this.distanciaFuente = distanciaFuente;
        this.direccionGrados = direccionGrados;
        this.fuenteEvaluada = fuenteEvaluada;
        this.encajonada = encajonada;
        this.libre = libre;
        this.observacionPuntoCritico = observacionPuntoCritico;
        this.ciudadesCercanas = ciudadesCercanas;
        this.regionales = regionales;
        this.observacionesRuralesFondo = observacionesRuralesFondo;
        this.idTipoArea = idTipoArea;
        this.idTiempo = idTiempo;
        this.idEmisionDominante = idEmisionDominante;
        this.idClima = idClima;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacionEmisionDominante() {
        return observacionEmisionDominante;
    }

    public void setObservacionEmisionDominante(String observacionEmisionDominante) {
        this.observacionEmisionDominante = observacionEmisionDominante;
    }

    public Double getDistanciaAlBorde() {
        return distanciaAlBorde;
    }

    public void setDistanciaAlBorde(Double distanciaAlBorde) {
        this.distanciaAlBorde = distanciaAlBorde;
    }

    public Double getAnchoVia() {
        return anchoVia;
    }

    public void setAnchoVia(Double anchoVia) {
        this.anchoVia = anchoVia;
    }

    public Boolean getTraficoDiario1() {
        return traficoDiario1;
    }

    public void setTraficoDiario1(Boolean traficoDiario1) {
        this.traficoDiario1 = traficoDiario1;
    }

    public Boolean getTraficoDiario2() {
        return traficoDiario2;
    }

    public void setTraficoDiario2(Boolean traficoDiario2) {
        this.traficoDiario2 = traficoDiario2;
    }

    public Double getVelocidadPromedio() {
        return velocidadPromedio;
    }

    public void setVelocidadPromedio(Double velocidadPromedio) {
        this.velocidadPromedio = velocidadPromedio;
    }

    public Integer getPorcentajeVehiculosPesados() {
        return porcentajeVehiculosPesados;
    }

    public void setPorcentajeVehiculosPesados(Integer porcentajeVehiculosPesados) {
        this.porcentajeVehiculosPesados = porcentajeVehiculosPesados;
    }

    public String getEstadoVia() {
        return estadoVia;
    }

    public void setEstadoVia(String estadoVia) {
        this.estadoVia = estadoVia;
    }

    public Integer getTiempoMuestreo() {
        return tiempoMuestreo;
    }

    public void setTiempoMuestreo(Integer tiempoMuestreo) {
        this.tiempoMuestreo = tiempoMuestreo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getDistanciaFuente() {
        return distanciaFuente;
    }

    public void setDistanciaFuente(Double distanciaFuente) {
        this.distanciaFuente = distanciaFuente;
    }

    public String getDireccionGrados() {
        return direccionGrados;
    }

    public void setDireccionGrados(String direccionGrados) {
        this.direccionGrados = direccionGrados;
    }

    public String getFuenteEvaluada() {
        return fuenteEvaluada;
    }

    public void setFuenteEvaluada(String fuenteEvaluada) {
        this.fuenteEvaluada = fuenteEvaluada;
    }

    public Boolean getEncajonada() {
        return encajonada;
    }

    public void setEncajonada(Boolean encajonada) {
        this.encajonada = encajonada;
    }

    public Boolean getLibre() {
        return libre;
    }

    public void setLibre(Boolean libre) {
        this.libre = libre;
    }

    public String getObservacionPuntoCritico() {
        return observacionPuntoCritico;
    }

    public void setObservacionPuntoCritico(String observacionPuntoCritico) {
        this.observacionPuntoCritico = observacionPuntoCritico;
    }

    public String getCiudadesCercanas() {
        return ciudadesCercanas;
    }

    public void setCiudadesCercanas(String ciudadesCercanas) {
        this.ciudadesCercanas = ciudadesCercanas;
    }

    public String getRegionales() {
        return regionales;
    }

    public void setRegionales(String regionales) {
        this.regionales = regionales;
    }

    public String getObservacionesRuralesFondo() {
        return observacionesRuralesFondo;
    }

    public void setObservacionesRuralesFondo(String observacionesRuralesFondo) {
        this.observacionesRuralesFondo = observacionesRuralesFondo;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    public TipoAreaPm getIdTipoArea() {
        return idTipoArea;
    }

    public void setIdTipoArea(TipoAreaPm idTipoArea) {
        this.idTipoArea = idTipoArea;
    }

    public TiempoPm getIdTiempo() {
        return idTiempo;
    }

    public void setIdTiempo(TiempoPm idTiempo) {
        this.idTiempo = idTiempo;
    }

    public EmisionDominantePm getIdEmisionDominante() {
        return idEmisionDominante;
    }

    public void setIdEmisionDominante(EmisionDominantePm idEmisionDominante) {
        this.idEmisionDominante = idEmisionDominante;
    }

    public ClimaPm getIdClima() {
        return idClima;
    }

    public void setIdClima(ClimaPm idClima) {
        this.idClima = idClima;
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
        if (!(object instanceof MacrolocalizacionPm)) {
            return false;
        }
        MacrolocalizacionPm other = (MacrolocalizacionPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.MacrolocalizacionPm[ id=" + id + " ]";
    }
    
}
