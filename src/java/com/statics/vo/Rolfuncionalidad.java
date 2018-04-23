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

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "rolfuncionalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolfuncionalidad.findAll", query = "SELECT r FROM Rolfuncionalidad r")
    , @NamedQuery(name = "Rolfuncionalidad.findByRofuId", query = "SELECT r FROM Rolfuncionalidad r WHERE r.rofuId = :rofuId")
    , @NamedQuery(name = "Rolfuncionalidad.findByRofuFechacambio", query = "SELECT r FROM Rolfuncionalidad r WHERE r.rofuFechacambio = :rofuFechacambio")
    , @NamedQuery(name = "Rolfuncionalidad.findByRoleRegistradopor", query = "SELECT r FROM Rolfuncionalidad r WHERE r.roleRegistradopor = :roleRegistradopor")
    , @NamedQuery(name = "Rolfuncionalidad.findByRofuOperacion", query = "SELECT r FROM Rolfuncionalidad r WHERE r.rofuOperacion = :rofuOperacion")})
public class Rolfuncionalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rofu_id")
    private Integer rofuId;
    @Basic(optional = false)
    @Column(name = "rofu_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rofuFechacambio;
    @Basic(optional = false)
    @Column(name = "role_registradopor")
    private int roleRegistradopor;
    @Column(name = "rofu_operacion")
    private String rofuOperacion;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Roles roleId;
    @JoinColumn(name = "func_id", referencedColumnName = "func_id")
    @ManyToOne(optional = false)
    private Funcionalidades funcId;

    public Rolfuncionalidad() {
    }

    public Rolfuncionalidad(Integer rofuId) {
        this.rofuId = rofuId;
    }

    public Rolfuncionalidad(Integer rofuId, Date rofuFechacambio, int roleRegistradopor) {
        this.rofuId = rofuId;
        this.rofuFechacambio = rofuFechacambio;
        this.roleRegistradopor = roleRegistradopor;
    }

    public Integer getRofuId() {
        return rofuId;
    }

    public void setRofuId(Integer rofuId) {
        this.rofuId = rofuId;
    }

    public Date getRofuFechacambio() {
        return rofuFechacambio;
    }

    public void setRofuFechacambio(Date rofuFechacambio) {
        this.rofuFechacambio = rofuFechacambio;
    }

    public int getRoleRegistradopor() {
        return roleRegistradopor;
    }

    public void setRoleRegistradopor(int roleRegistradopor) {
        this.roleRegistradopor = roleRegistradopor;
    }

    public String getRofuOperacion() {
        return rofuOperacion;
    }

    public void setRofuOperacion(String rofuOperacion) {
        this.rofuOperacion = rofuOperacion;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public Funcionalidades getFuncId() {
        return funcId;
    }

    public void setFuncId(Funcionalidades funcId) {
        this.funcId = funcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rofuId != null ? rofuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolfuncionalidad)) {
            return false;
        }
        Rolfuncionalidad other = (Rolfuncionalidad) object;
        if ((this.rofuId == null && other.rofuId != null) || (this.rofuId != null && !this.rofuId.equals(other.rofuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Rolfuncionalidad[ rofuId=" + rofuId + " ]";
    }
    
}
