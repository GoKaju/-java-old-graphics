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
@Table(name = "roles")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r")
    , @NamedQuery(name = "Roles.findByRoleId", query = "SELECT r FROM Roles r WHERE r.roleId = :roleId")
    , @NamedQuery(name = "Roles.findByRoleDescripcion", query = "SELECT r FROM Roles r WHERE r.roleDescripcion = :roleDescripcion")
    , @NamedQuery(name = "Roles.findByRoleFechacambio", query = "SELECT r FROM Roles r WHERE r.roleFechacambio = :roleFechacambio")
    , @NamedQuery(name = "Roles.findByRoleregistradopor", query = "SELECT r FROM Roles r WHERE r.roleregistradopor = :roleregistradopor")
    , @NamedQuery(name = "Roles.findByRoleEstado", query = "SELECT r FROM Roles r WHERE r.roleEstado = :roleEstado")})
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id")
    private Integer roleId;
    @Basic(optional = false)
    @Column(name = "role_descripcion")
    private String roleDescripcion;
    @Basic(optional = false)
    @Column(name = "role_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date roleFechacambio;
    @Basic(optional = false)
    @Column(name = "roleregistradopor")
    private int roleregistradopor;
    @Column(name = "role_estado")
    private String roleEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private List<Usuariorol> usuariorolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
    private List<Rolfuncionalidad> rolfuncionalidadList;

    public Roles() {
    }

    public Roles(Integer roleId) {
        this.roleId = roleId;
    }

    public Roles(Integer roleId, String roleDescripcion, Date roleFechacambio, int roleregistradopor) {
        this.roleId = roleId;
        this.roleDescripcion = roleDescripcion;
        this.roleFechacambio = roleFechacambio;
        this.roleregistradopor = roleregistradopor;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescripcion() {
        return roleDescripcion;
    }

    public void setRoleDescripcion(String roleDescripcion) {
        this.roleDescripcion = roleDescripcion;
    }

    public Date getRoleFechacambio() {
        return roleFechacambio;
    }

    public void setRoleFechacambio(Date roleFechacambio) {
        this.roleFechacambio = roleFechacambio;
    }

    public int getRoleregistradopor() {
        return roleregistradopor;
    }

    public void setRoleregistradopor(int roleregistradopor) {
        this.roleregistradopor = roleregistradopor;
    }

    public String getRoleEstado() {
        return roleEstado;
    }

    public void setRoleEstado(String roleEstado) {
        this.roleEstado = roleEstado;
    }

    @XmlTransient
    public List<Usuariorol> getUsuariorolList() {
        return usuariorolList;
    }

    public void setUsuariorolList(List<Usuariorol> usuariorolList) {
        this.usuariorolList = usuariorolList;
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
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Roles[ roleId=" + roleId + " ]";
    }
    
}
