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
import javax.persistence.Lob;
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
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByUsuaId", query = "SELECT u FROM Usuarios u WHERE u.usuaId = :usuaId")
    , @NamedQuery(name = "Usuarios.findByUsuaNombres", query = "SELECT u FROM Usuarios u WHERE u.usuaNombres = :usuaNombres")
    , @NamedQuery(name = "Usuarios.findByUsuaMail", query = "SELECT u FROM Usuarios u WHERE u.usuaMail = :usuaMail")
    , @NamedQuery(name = "Usuarios.findByUsuaUsuario", query = "SELECT u FROM Usuarios u WHERE u.usuaUsuario = :usuaUsuario")
    , @NamedQuery(name = "Usuarios.findByUsuaContrasena", query = "SELECT u FROM Usuarios u WHERE u.usuaContrasena = :usuaContrasena")
    , @NamedQuery(name = "Usuarios.findByUsuaUltimoacceso", query = "SELECT u FROM Usuarios u WHERE u.usuaUltimoacceso = :usuaUltimoacceso")
    , @NamedQuery(name = "Usuarios.findByUsuaUltimaip", query = "SELECT u FROM Usuarios u WHERE u.usuaUltimaip = :usuaUltimaip")
    , @NamedQuery(name = "Usuarios.findByUsuaTele", query = "SELECT u FROM Usuarios u WHERE u.usuaTele = :usuaTele")
    , @NamedQuery(name = "Usuarios.findByUsuaEstado", query = "SELECT u FROM Usuarios u WHERE u.usuaEstado = :usuaEstado")
    , @NamedQuery(name = "Usuarios.findByUsuaRegistradopor", query = "SELECT u FROM Usuarios u WHERE u.usuaRegistradopor = :usuaRegistradopor")
    , @NamedQuery(name = "Usuarios.findByUsuaFechacambio", query = "SELECT u FROM Usuarios u WHERE u.usuaFechacambio = :usuaFechacambio")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usua_id")
    private Integer usuaId;
    @Column(name = "usua_nombres")
    private String usuaNombres;
    @Column(name = "usua_mail")
    private String usuaMail;
    @Basic(optional = false)
    @Column(name = "usua_usuario")
    private String usuaUsuario;
    @Basic(optional = false)
    @Column(name = "usua_contrasena")
    private String usuaContrasena;
    @Column(name = "usua_ultimoacceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuaUltimoacceso;
    @Column(name = "usua_ultimaip")
    private String usuaUltimaip;
    @Column(name = "usua_tele")
    private String usuaTele;
    @Column(name = "usua_estado")
    private String usuaEstado;
    @Basic(optional = false)
    @Column(name = "usua_registradopor")
    private int usuaRegistradopor;
    @Basic(optional = false)
    @Column(name = "usua_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuaFechacambio;
    @Lob
    @Column(name = "usua_img")
    private String usuaImg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuaId")
    private List<GrupoUsuarios> grupoUsuariosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuaId")
    private List<Usuariorol> usuariorolList;

    public Usuarios() {
    }

    public Usuarios(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public Usuarios(Integer usuaId, String usuaUsuario, String usuaContrasena, int usuaRegistradopor, Date usuaFechacambio) {
        this.usuaId = usuaId;
        this.usuaUsuario = usuaUsuario;
        this.usuaContrasena = usuaContrasena;
        this.usuaRegistradopor = usuaRegistradopor;
        this.usuaFechacambio = usuaFechacambio;
    }

    public Integer getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public String getUsuaNombres() {
        return usuaNombres;
    }

    public void setUsuaNombres(String usuaNombres) {
        this.usuaNombres = usuaNombres;
    }

    public String getUsuaMail() {
        return usuaMail;
    }

    public void setUsuaMail(String usuaMail) {
        this.usuaMail = usuaMail;
    }

    public String getUsuaUsuario() {
        return usuaUsuario;
    }

    public void setUsuaUsuario(String usuaUsuario) {
        this.usuaUsuario = usuaUsuario;
    }

    public String getUsuaContrasena() {
        return usuaContrasena;
    }

    public void setUsuaContrasena(String usuaContrasena) {
        this.usuaContrasena = usuaContrasena;
    }

    public Date getUsuaUltimoacceso() {
        return usuaUltimoacceso;
    }

    public void setUsuaUltimoacceso(Date usuaUltimoacceso) {
        this.usuaUltimoacceso = usuaUltimoacceso;
    }

    public String getUsuaUltimaip() {
        return usuaUltimaip;
    }

    public void setUsuaUltimaip(String usuaUltimaip) {
        this.usuaUltimaip = usuaUltimaip;
    }

    public String getUsuaTele() {
        return usuaTele;
    }

    public void setUsuaTele(String usuaTele) {
        this.usuaTele = usuaTele;
    }

    public String getUsuaEstado() {
        return usuaEstado;
    }

    public void setUsuaEstado(String usuaEstado) {
        this.usuaEstado = usuaEstado;
    }

    public int getUsuaRegistradopor() {
        return usuaRegistradopor;
    }

    public void setUsuaRegistradopor(int usuaRegistradopor) {
        this.usuaRegistradopor = usuaRegistradopor;
    }

    public Date getUsuaFechacambio() {
        return usuaFechacambio;
    }

    public void setUsuaFechacambio(Date usuaFechacambio) {
        this.usuaFechacambio = usuaFechacambio;
    }

    public String getUsuaImg() {
        return usuaImg;
    }

    public void setUsuaImg(String usuaImg) {
        this.usuaImg = usuaImg;
    }

    @XmlTransient
    public List<GrupoUsuarios> getGrupoUsuariosList() {
        return grupoUsuariosList;
    }

    public void setGrupoUsuariosList(List<GrupoUsuarios> grupoUsuariosList) {
        this.grupoUsuariosList = grupoUsuariosList;
    }

    @XmlTransient
    public List<Usuariorol> getUsuariorolList() {
        return usuariorolList;
    }

    public void setUsuariorolList(List<Usuariorol> usuariorolList) {
        this.usuariorolList = usuariorolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuaId != null ? usuaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuaId == null && other.usuaId != null) || (this.usuaId != null && !this.usuaId.equals(other.usuaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Usuarios[ usuaId=" + usuaId + " ]";
    }
    
}
