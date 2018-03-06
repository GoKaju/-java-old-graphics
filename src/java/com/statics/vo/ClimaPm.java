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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author FoxHG
 */
@Entity
@Table(name = "clima_pm")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClimaPm.findAll", query = "SELECT c FROM ClimaPm c")
    , @NamedQuery(name = "ClimaPm.findById", query = "SELECT c FROM ClimaPm c WHERE c.id = :id")
    , @NamedQuery(name = "ClimaPm.findByNombre", query = "SELECT c FROM ClimaPm c WHERE c.nombre = :nombre")})
public class ClimaPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idClima", fetch = FetchType.LAZY)
    private List<MacrolocalizacionPm> macrolocalizacionPmList;

    public ClimaPm() {
    }

    public ClimaPm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<MacrolocalizacionPm> getMacrolocalizacionPmList() {
        return macrolocalizacionPmList;
    }

    public void setMacrolocalizacionPmList(List<MacrolocalizacionPm> macrolocalizacionPmList) {
        this.macrolocalizacionPmList = macrolocalizacionPmList;
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
        if (!(object instanceof ClimaPm)) {
            return false;
        }
        ClimaPm other = (ClimaPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.ClimaPm[ id=" + id + " ]";
    }
    
}
