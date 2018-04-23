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
@Table(name = "usuariorol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariorol.findAll", query = "SELECT u FROM Usuariorol u")
    , @NamedQuery(name = "Usuariorol.findByUsroId", query = "SELECT u FROM Usuariorol u WHERE u.usroId = :usroId")
    , @NamedQuery(name = "Usuariorol.findByUsroRegistradopor", query = "SELECT u FROM Usuariorol u WHERE u.usroRegistradopor = :usroRegistradopor")
    , @NamedQuery(name = "Usuariorol.findByUsroFechacambio", query = "SELECT u FROM Usuariorol u WHERE u.usroFechacambio = :usroFechacambio")})
public class Usuariorol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usro_id")
    private Integer usroId;
    @Basic(optional = false)
    @Column(name = "usro_registradopor")
    private int usroRegistradopor;
    @Basic(optional = false)
    @Column(name = "usro_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usroFechacambio;
    @JoinColumn(name = "usua_id", referencedColumnName = "usua_id")
    @ManyToOne(optional = false)
    private Usuarios usuaId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Roles roleId;

    public Usuariorol() {
    }

    public Usuariorol(Integer usroId) {
        this.usroId = usroId;
    }

    public Usuariorol(Integer usroId, int usroRegistradopor, Date usroFechacambio) {
        this.usroId = usroId;
        this.usroRegistradopor = usroRegistradopor;
        this.usroFechacambio = usroFechacambio;
    }

    public Integer getUsroId() {
        return usroId;
    }

    public void setUsroId(Integer usroId) {
        this.usroId = usroId;
    }

    public int getUsroRegistradopor() {
        return usroRegistradopor;
    }

    public void setUsroRegistradopor(int usroRegistradopor) {
        this.usroRegistradopor = usroRegistradopor;
    }

    public Date getUsroFechacambio() {
        return usroFechacambio;
    }

    public void setUsroFechacambio(Date usroFechacambio) {
        this.usroFechacambio = usroFechacambio;
    }

    public Usuarios getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Usuarios usuaId) {
        this.usuaId = usuaId;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usroId != null ? usroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariorol)) {
            return false;
        }
        Usuariorol other = (Usuariorol) object;
        if ((this.usroId == null && other.usroId != null) || (this.usroId != null && !this.usroId.equals(other.usroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Usuariorol[ usroId=" + usroId + " ]";
    }
    
}
