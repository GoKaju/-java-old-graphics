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
@Table(name = "ubicacion_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbicacionPm.findAll", query = "SELECT u FROM UbicacionPm u")
    , @NamedQuery(name = "UbicacionPm.findById", query = "SELECT u FROM UbicacionPm u WHERE u.id = :id")
    , @NamedQuery(name = "UbicacionPm.findByDireccion", query = "SELECT u FROM UbicacionPm u WHERE u.direccion = :direccion")})
public class UbicacionPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "idUbicacion", fetch = FetchType.LAZY)
    private List<PuntoMuestral> puntoMuestralList;
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento idDepartamento;
    @JoinColumn(name = "id_municipio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Municipio idMunicipio;

    public UbicacionPm() {
    }

    public UbicacionPm(Integer id) {
        this.id = id;
    }

    public UbicacionPm(String direccion, Departamento idDepartamento, Municipio idMunicipio) {
        this.direccion = direccion;
        this.idDepartamento = idDepartamento;
        this.idMunicipio = idMunicipio;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Municipio getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Municipio idMunicipio) {
        this.idMunicipio = idMunicipio;
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
        if (!(object instanceof UbicacionPm)) {
            return false;
        }
        UbicacionPm other = (UbicacionPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.UbicacionPm[ id=" + id + " ]";
    }
    
}
