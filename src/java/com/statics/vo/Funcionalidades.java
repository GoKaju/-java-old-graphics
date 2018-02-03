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
@Table(name = "funcionalidades")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionalidades.findAll", query = "SELECT f FROM Funcionalidades f")
    , @NamedQuery(name = "Funcionalidades.findByFuncId", query = "SELECT f FROM Funcionalidades f WHERE f.funcId = :funcId")
    , @NamedQuery(name = "Funcionalidades.findByFuncDescripcion", query = "SELECT f FROM Funcionalidades f WHERE f.funcDescripcion = :funcDescripcion")
    , @NamedQuery(name = "Funcionalidades.findByFuncUrl", query = "SELECT f FROM Funcionalidades f WHERE f.funcUrl = :funcUrl")
    , @NamedQuery(name = "Funcionalidades.findByFuncCodigo", query = "SELECT f FROM Funcionalidades f WHERE f.funcCodigo = :funcCodigo")
    , @NamedQuery(name = "Funcionalidades.findByFuncIcon", query = "SELECT f FROM Funcionalidades f WHERE f.funcIcon = :funcIcon")
    , @NamedQuery(name = "Funcionalidades.findByFuncRegistradopor", query = "SELECT f FROM Funcionalidades f WHERE f.funcRegistradopor = :funcRegistradopor")
    , @NamedQuery(name = "Funcionalidades.findByFuncFechacambio", query = "SELECT f FROM Funcionalidades f WHERE f.funcFechacambio = :funcFechacambio")
    , @NamedQuery(name = "Funcionalidades.findByFuncEspecifica", query = "SELECT f FROM Funcionalidades f WHERE f.funcEspecifica = :funcEspecifica")
    , @NamedQuery(name = "Funcionalidades.findByFunTienehijos", query = "SELECT f FROM Funcionalidades f WHERE f.funTienehijos = :funTienehijos")})
public class Funcionalidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "func_id")
    private Integer funcId;
    @Basic(optional = false)
    @Column(name = "func_descripcion")
    private String funcDescripcion;
    @Basic(optional = false)
    @Column(name = "func_url")
    private String funcUrl;
    @Basic(optional = false)
    @Column(name = "func_codigo")
    private String funcCodigo;
    @Column(name = "func_icon")
    private String funcIcon;
    @Basic(optional = false)
    @Column(name = "func_registradopor")
    private int funcRegistradopor;
    @Basic(optional = false)
    @Column(name = "func_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date funcFechacambio;
    @Column(name = "func_especifica")
    private Boolean funcEspecifica;
    @Column(name = "fun_tienehijos")
    private Boolean funTienehijos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcId")
    private List<Rolfuncionalidad> rolfuncionalidadList;

    public Funcionalidades() {
    }

    public Funcionalidades(Integer funcId) {
        this.funcId = funcId;
    }

    public Funcionalidades(Integer funcId, String funcDescripcion, String funcUrl, String funcCodigo, int funcRegistradopor, Date funcFechacambio) {
        this.funcId = funcId;
        this.funcDescripcion = funcDescripcion;
        this.funcUrl = funcUrl;
        this.funcCodigo = funcCodigo;
        this.funcRegistradopor = funcRegistradopor;
        this.funcFechacambio = funcFechacambio;
    }

    public Integer getFuncId() {
        return funcId;
    }

    public void setFuncId(Integer funcId) {
        this.funcId = funcId;
    }

    public String getFuncDescripcion() {
        return funcDescripcion;
    }

    public void setFuncDescripcion(String funcDescripcion) {
        this.funcDescripcion = funcDescripcion;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public String getFuncCodigo() {
        return funcCodigo;
    }

    public void setFuncCodigo(String funcCodigo) {
        this.funcCodigo = funcCodigo;
    }

    public String getFuncIcon() {
        return funcIcon;
    }

    public void setFuncIcon(String funcIcon) {
        this.funcIcon = funcIcon;
    }

    public int getFuncRegistradopor() {
        return funcRegistradopor;
    }

    public void setFuncRegistradopor(int funcRegistradopor) {
        this.funcRegistradopor = funcRegistradopor;
    }

    public Date getFuncFechacambio() {
        return funcFechacambio;
    }

    public void setFuncFechacambio(Date funcFechacambio) {
        this.funcFechacambio = funcFechacambio;
    }

    public Boolean getFuncEspecifica() {
        return funcEspecifica;
    }

    public void setFuncEspecifica(Boolean funcEspecifica) {
        this.funcEspecifica = funcEspecifica;
    }

    public Boolean getFunTienehijos() {
        return funTienehijos;
    }

    public void setFunTienehijos(Boolean funTienehijos) {
        this.funTienehijos = funTienehijos;
    }

    @XmlTransient
    public List<Rolfuncionalidad> getRolfuncionalidadList() {
        return rolfuncionalidadList;
    }

    public void setRolfuncionalidadList(List<Rolfuncionalidad> rolfuncionalidadList) {
        this.rolfuncionalidadList = rolfuncionalidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcId != null ? funcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionalidades)) {
            return false;
        }
        Funcionalidades other = (Funcionalidades) object;
        if ((this.funcId == null && other.funcId != null) || (this.funcId != null && !this.funcId.equals(other.funcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Funcionalidades[ funcId=" + funcId + " ]";
    }
    
}
