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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "campanas")
@Cache(expiry = -1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Campanas.findAll", query = "SELECT c FROM Campanas c")
    , @NamedQuery(name = "Campanas.findByCampId", query = "SELECT c FROM Campanas c WHERE c.campId = :campId")
    , @NamedQuery(name = "Campanas.findByGroupId", query = "SELECT c FROM Campanas c inner join Grupo AS g on c.grupId = g WHERE c.estaId <>0 and g.grupId = :grupId")
    , @NamedQuery(name = "Campanas.findByGroupIdActivas", query = "SELECT c FROM Campanas c inner join Grupo AS g on c.grupId = g WHERE c.estaId =1 and g.grupId = :grupId ")
    , @NamedQuery(name = "Campanas.activas", query = "SELECT c FROM Campanas c WHERE c.estaId = 1")
    , @NamedQuery(name = "Campanas.findByCampNombre", query = "SELECT c FROM Campanas c WHERE c.campNombre = :campNombre")
    , @NamedQuery(name = "Campanas.findByCampDescripcion", query = "SELECT c FROM Campanas c WHERE c.campDescripcion = :campDescripcion")
    , @NamedQuery(name = "Campanas.findByCampRegistradapor", query = "SELECT c FROM Campanas c WHERE c.campRegistradapor = :campRegistradapor")
    , @NamedQuery(name = "Campanas.findByCampFechacambio", query = "SELECT c FROM Campanas c WHERE c.campFechacambio = :campFechacambio")})
public class Campanas implements Serializable {

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "camp_bucket")
    private String campBucket;

    @Basic(optional = false)
    @Column(name = "esta_id")
    private int estaId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "camp_id")
    private Integer campId;
    @Basic(optional = false)
    @Column(name = "camp_nombre")
    private String campNombre;
    @Column(name = "camp_descripcion")
    private String campDescripcion;
    @Basic(optional = false)
    @Column(name = "camp_registradapor")
    private int campRegistradapor;
    @Basic(optional = false)
    @Column(name = "camp_fechacambio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date campFechacambio;
    @OneToMany(mappedBy = "campId")
    private List<PuntoMuestral> puntoMuestralList;
    @JoinColumn(name = "grup_id", referencedColumnName = "grup_id")
    @ManyToOne(optional = false)
    private Grupo grupId;

    public Campanas() {
    }

    public Campanas(Integer campId) {
        this.campId = campId;
    }

    public Campanas(Integer campId, String campNombre, int campRegistradapor, Date campFechacambio) {
        this.campId = campId;
        this.campNombre = campNombre;
        this.campRegistradapor = campRegistradapor;
        this.campFechacambio = campFechacambio;
    }

    public Integer getCampId() {
        return campId;
    }

    public void setCampId(Integer campId) {
        this.campId = campId;
    }

    public String getCampNombre() {
        return campNombre;
    }

    public void setCampNombre(String campNombre) {
        this.campNombre = campNombre;
    }

    public String getCampDescripcion() {
        return campDescripcion;
    }

    public void setCampDescripcion(String campDescripcion) {
        this.campDescripcion = campDescripcion;
    }

    public int getCampRegistradapor() {
        return campRegistradapor;
    }

    public void setCampRegistradapor(int campRegistradapor) {
        this.campRegistradapor = campRegistradapor;
    }

    public Date getCampFechacambio() {
        return campFechacambio;
    }

    public void setCampFechacambio(Date campFechacambio) {
        this.campFechacambio = campFechacambio;
    }

    @XmlTransient
    public List<PuntoMuestral> getPuntoMuestralList() {
        return puntoMuestralList;
    }

    public void setPuntoMuestralList(List<PuntoMuestral> puntoMuestralList) {
        this.puntoMuestralList = puntoMuestralList;
    }

    public Grupo getGrupId() {
        return grupId;
    }

    public void setGrupId(Grupo grupId) {
        this.grupId = grupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (campId != null ? campId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campanas)) {
            return false;
        }
        Campanas other = (Campanas) object;
        if ((this.campId == null && other.campId != null) || (this.campId != null && !this.campId.equals(other.campId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.statics.vo.Campanas[ campId=" + campId + " ]";
    }

    public int getEstaId() {
        return estaId;
    }

    public void setEstaId(int estaId) {
        this.estaId = estaId;
    }

    public String getCampBucket() {
        return campBucket;
    }

    public void setCampBucket(String campBucket) {
        this.campBucket = campBucket;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
}
