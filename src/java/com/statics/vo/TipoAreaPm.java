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
@Table(name = "tipo_area_pm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAreaPm.findAll", query = "SELECT t FROM TipoAreaPm t")
    , @NamedQuery(name = "TipoAreaPm.findById", query = "SELECT t FROM TipoAreaPm t WHERE t.id = :id")
    , @NamedQuery(name = "TipoAreaPm.findByNombre", query = "SELECT t FROM TipoAreaPm t WHERE t.nombre = :nombre")})
public class TipoAreaPm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoArea")
    private List<MacrolocalizacionPm> macrolocalizacionPmList;

    public TipoAreaPm() {
    }

    public TipoAreaPm(Integer id) {
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
        if (!(object instanceof TipoAreaPm)) {
            return false;
        }
        TipoAreaPm other = (TipoAreaPm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.TipoAreaPm[ id=" + id + " ]";
    }
    
}
