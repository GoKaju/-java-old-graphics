/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.DatoProcesado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.PuntoMuestral;
import com.statics.vo.UnidadTiempo;
import com.statics.vo.ParametroFactorconversion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class DatoProcesadoJpaController implements Serializable {

    public DatoProcesadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatoProcesado datoProcesado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoMuestral idPuntoMuestral = datoProcesado.getIdPuntoMuestral();
            if (idPuntoMuestral != null) {
                idPuntoMuestral = em.getReference(idPuntoMuestral.getClass(), idPuntoMuestral.getPumuId());
                datoProcesado.setIdPuntoMuestral(idPuntoMuestral);
            }
            UnidadTiempo idUnidadTiempo = datoProcesado.getIdUnidadTiempo();
            if (idUnidadTiempo != null) {
                idUnidadTiempo = em.getReference(idUnidadTiempo.getClass(), idUnidadTiempo.getId());
                datoProcesado.setIdUnidadTiempo(idUnidadTiempo);
            }
            ParametroFactorconversion idParametroFactorconversion = datoProcesado.getIdParametroFactorconversion();
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion = em.getReference(idParametroFactorconversion.getClass(), idParametroFactorconversion.getId());
                datoProcesado.setIdParametroFactorconversion(idParametroFactorconversion);
            }
            em.persist(datoProcesado);
            if (idPuntoMuestral != null) {
                idPuntoMuestral.getDatoProcesadoList().add(datoProcesado);
                idPuntoMuestral = em.merge(idPuntoMuestral);
            }
            if (idUnidadTiempo != null) {
                idUnidadTiempo.getDatoProcesadoList().add(datoProcesado);
                idUnidadTiempo = em.merge(idUnidadTiempo);
            }
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion.getDatoProcesadoList().add(datoProcesado);
                idParametroFactorconversion = em.merge(idParametroFactorconversion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatoProcesado datoProcesado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatoProcesado persistentDatoProcesado = em.find(DatoProcesado.class, datoProcesado.getId());
            PuntoMuestral idPuntoMuestralOld = persistentDatoProcesado.getIdPuntoMuestral();
            PuntoMuestral idPuntoMuestralNew = datoProcesado.getIdPuntoMuestral();
            UnidadTiempo idUnidadTiempoOld = persistentDatoProcesado.getIdUnidadTiempo();
            UnidadTiempo idUnidadTiempoNew = datoProcesado.getIdUnidadTiempo();
            ParametroFactorconversion idParametroFactorconversionOld = persistentDatoProcesado.getIdParametroFactorconversion();
            ParametroFactorconversion idParametroFactorconversionNew = datoProcesado.getIdParametroFactorconversion();
            if (idPuntoMuestralNew != null) {
                idPuntoMuestralNew = em.getReference(idPuntoMuestralNew.getClass(), idPuntoMuestralNew.getPumuId());
                datoProcesado.setIdPuntoMuestral(idPuntoMuestralNew);
            }
            if (idUnidadTiempoNew != null) {
                idUnidadTiempoNew = em.getReference(idUnidadTiempoNew.getClass(), idUnidadTiempoNew.getId());
                datoProcesado.setIdUnidadTiempo(idUnidadTiempoNew);
            }
            if (idParametroFactorconversionNew != null) {
                idParametroFactorconversionNew = em.getReference(idParametroFactorconversionNew.getClass(), idParametroFactorconversionNew.getId());
                datoProcesado.setIdParametroFactorconversion(idParametroFactorconversionNew);
            }
            datoProcesado = em.merge(datoProcesado);
            if (idPuntoMuestralOld != null && !idPuntoMuestralOld.equals(idPuntoMuestralNew)) {
                idPuntoMuestralOld.getDatoProcesadoList().remove(datoProcesado);
                idPuntoMuestralOld = em.merge(idPuntoMuestralOld);
            }
            if (idPuntoMuestralNew != null && !idPuntoMuestralNew.equals(idPuntoMuestralOld)) {
                idPuntoMuestralNew.getDatoProcesadoList().add(datoProcesado);
                idPuntoMuestralNew = em.merge(idPuntoMuestralNew);
            }
            if (idUnidadTiempoOld != null && !idUnidadTiempoOld.equals(idUnidadTiempoNew)) {
                idUnidadTiempoOld.getDatoProcesadoList().remove(datoProcesado);
                idUnidadTiempoOld = em.merge(idUnidadTiempoOld);
            }
            if (idUnidadTiempoNew != null && !idUnidadTiempoNew.equals(idUnidadTiempoOld)) {
                idUnidadTiempoNew.getDatoProcesadoList().add(datoProcesado);
                idUnidadTiempoNew = em.merge(idUnidadTiempoNew);
            }
            if (idParametroFactorconversionOld != null && !idParametroFactorconversionOld.equals(idParametroFactorconversionNew)) {
                idParametroFactorconversionOld.getDatoProcesadoList().remove(datoProcesado);
                idParametroFactorconversionOld = em.merge(idParametroFactorconversionOld);
            }
            if (idParametroFactorconversionNew != null && !idParametroFactorconversionNew.equals(idParametroFactorconversionOld)) {
                idParametroFactorconversionNew.getDatoProcesadoList().add(datoProcesado);
                idParametroFactorconversionNew = em.merge(idParametroFactorconversionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datoProcesado.getId();
                if (findDatoProcesado(id) == null) {
                    throw new NonexistentEntityException("The datoProcesado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatoProcesado datoProcesado;
            try {
                datoProcesado = em.getReference(DatoProcesado.class, id);
                datoProcesado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datoProcesado with id " + id + " no longer exists.", enfe);
            }
            PuntoMuestral idPuntoMuestral = datoProcesado.getIdPuntoMuestral();
            if (idPuntoMuestral != null) {
                idPuntoMuestral.getDatoProcesadoList().remove(datoProcesado);
                idPuntoMuestral = em.merge(idPuntoMuestral);
            }
            UnidadTiempo idUnidadTiempo = datoProcesado.getIdUnidadTiempo();
            if (idUnidadTiempo != null) {
                idUnidadTiempo.getDatoProcesadoList().remove(datoProcesado);
                idUnidadTiempo = em.merge(idUnidadTiempo);
            }
            ParametroFactorconversion idParametroFactorconversion = datoProcesado.getIdParametroFactorconversion();
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion.getDatoProcesadoList().remove(datoProcesado);
                idParametroFactorconversion = em.merge(idParametroFactorconversion);
            }
            em.remove(datoProcesado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatoProcesado> findDatoProcesadoEntities() {
        return findDatoProcesadoEntities(true, -1, -1);
    }

    public List<DatoProcesado> findDatoProcesadoEntities(int maxResults, int firstResult) {
        return findDatoProcesadoEntities(false, maxResults, firstResult);
    }

    private List<DatoProcesado> findDatoProcesadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatoProcesado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DatoProcesado findDatoProcesado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatoProcesado.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatoProcesadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatoProcesado> rt = cq.from(DatoProcesado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
