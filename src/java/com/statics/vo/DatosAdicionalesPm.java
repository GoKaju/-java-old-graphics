/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "datos_adicionales_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosAdicionalesPm.findAll", query = "SELECT d FROM DatosAdicionalesPm d")
    , @NamedQuery(name = "DatosAdicionalesPm.findById", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.id = :id")
    , @NamedQuery(name = "DatosAdicionalesPm.findByDecripcion", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.decripcion = :decripcion")
    , @NamedQuery(name = "DatosAdicionalesPm.findByNombreApellido", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.nombreApellido = :nombreApellido")
    , @NamedQuery(name = "DatosAdicionalesPm.findByCelular", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.celular = :celular")
    , @NamedQuery(name = "DatosAdicionalesPm.findByFijo", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.fijo = :fijo")
    , @NamedQuery(name = "DatosAdicionalesPm.findByEmail", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.email = :email")
    , @NamedQuery(name = "DatosAdicionalesPm.findByOtros", query = "SELECT d FROM DatosAdicionalesPm d WHERE d.otros = :otros")})
public class DatosAdicionalesPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "decripcion")
    private String decripcion;
    @Column(name = "nombre_apellido")
    private String nombreApellido;
    @Column(name = "celular")
    private Long celular;
    @Column(name = "fijo")
    private Integer fijo;
    @Column(name = "email")
    private String email;
    @Column(name = "otros")
    private String otros;
    @OneToMany(mappedBy = "idDatosAdicionales", fetch = FetchType.LAZY)
    private List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList;

    public DatosAdicionalesPm() {
    }

    public DatosAdicionalesPm(Integer id) {
        this.id = id;
    }

    public DatosAdicionalesPm(String decripcion, String nombreApellido, Long celular, Integer fijo, String email, String otros) {
        this.decripcion = decripcion;
        this.nombreApellido = nombreApellido;
        this.celular = celular;
        this.fijo = fijo;
        this.email = email;
        this.otros = otros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Integer getFijo() {
        return fijo;
    }

    public void setFijo(Integer fijo) {
        this.fijo = fijo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    @XmlTransient
    public List<DatosadicionalesMicrolocalizacion> getDatosadicionalesMicrolocalizacionList() {
        return datosadicionalesMicrolocalizacionList;
    }

    public void setDatosadicionalesMicrolocalizacionList(List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList) {
        this.datosadicionalesMicrolocalizacionList = datosadicionalesMicrolocalizacionList;
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
        if (!(object instanceof DatosAdicionalesPm)) {
            return false;
        }
        DatosAdicionalesPm other = (DatosAdicionalesPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.DatosAdicionalesPm[ id=" + id + " ]";
    }
    
}
