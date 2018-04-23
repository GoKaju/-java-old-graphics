/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.NivelMaximo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.ParametroFactorconversion;
import com.statics.vo.UnidadTiempo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class NivelMaximoJpaController implements Serializable {

    public NivelMaximoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelMaximo nivelMaximo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroFactorconversion idParametroFactorconversion = nivelMaximo.getIdParametroFactorconversion();
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion = em.getReference(idParametroFactorconversion.getClass(), idParametroFactorconversion.getId());
                nivelMaximo.setIdParametroFactorconversion(idParametroFactorconversion);
            }
            UnidadTiempo idUnidadTiempo = nivelMaximo.getIdUnidadTiempo();
            if (idUnidadTiempo != null) {
                idUnidadTiempo = em.getReference(idUnidadTiempo.getClass(), idUnidadTiempo.getId());
                nivelMaximo.setIdUnidadTiempo(idUnidadTiempo);
            }
            em.persist(nivelMaximo);
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion.getNivelMaximoList().add(nivelMaximo);
                idParametroFactorconversion = em.merge(idParametroFactorconversion);
            }
            if (idUnidadTiempo != null) {
                idUnidadTiempo.getNivelMaximoList().add(nivelMaximo);
                idUnidadTiempo = em.merge(idUnidadTiempo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelMaximo nivelMaximo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelMaximo persistentNivelMaximo = em.find(NivelMaximo.class, nivelMaximo.getId());
            ParametroFactorconversion idParametroFactorconversionOld = persistentNivelMaximo.getIdParametroFactorconversion();
            ParametroFactorconversion idParametroFactorconversionNew = nivelMaximo.getIdParametroFactorconversion();
            UnidadTiempo idUnidadTiempoOld = persistentNivelMaximo.getIdUnidadTiempo();
            UnidadTiempo idUnidadTiempoNew = nivelMaximo.getIdUnidadTiempo();
            if (idParametroFactorconversionNew != null) {
                idParametroFactorconversionNew = em.getReference(idParametroFactorconversionNew.getClass(), idParametroFactorconversionNew.getId());
                nivelMaximo.setIdParametroFactorconversion(idParametroFactorconversionNew);
            }
            if (idUnidadTiempoNew != null) {
                idUnidadTiempoNew = em.getReference(idUnidadTiempoNew.getClass(), idUnidadTiempoNew.getId());
                nivelMaximo.setIdUnidadTiempo(idUnidadTiempoNew);
            }
            nivelMaximo = em.merge(nivelMaximo);
            if (idParametroFactorconversionOld != null && !idParametroFactorconversionOld.equals(idParametroFactorconversionNew)) {
                idParametroFactorconversionOld.getNivelMaximoList().remove(nivelMaximo);
                idParametroFactorconversionOld = em.merge(idParametroFactorconversionOld);
            }
            if (idParametroFactorconversionNew != null && !idParametroFactorconversionNew.equals(idParametroFactorconversionOld)) {
                idParametroFactorconversionNew.getNivelMaximoList().add(nivelMaximo);
                idParametroFactorconversionNew = em.merge(idParametroFactorconversionNew);
            }
            if (idUnidadTiempoOld != null && !idUnidadTiempoOld.equals(idUnidadTiempoNew)) {
                idUnidadTiempoOld.getNivelMaximoList().remove(nivelMaximo);
                idUnidadTiempoOld = em.merge(idUnidadTiempoOld);
            }
            if (idUnidadTiempoNew != null && !idUnidadTiempoNew.equals(idUnidadTiempoOld)) {
                idUnidadTiempoNew.getNivelMaximoList().add(nivelMaximo);
                idUnidadTiempoNew = em.merge(idUnidadTiempoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nivelMaximo.getId();
                if (findNivelMaximo(id) == null) {
                    throw new NonexistentEntityException("The nivelMaximo with id " + id + " no longer exists.");
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
            NivelMaximo nivelMaximo;
            try {
                nivelMaximo = em.getReference(NivelMaximo.class, id);
                nivelMaximo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelMaximo with id " + id + " no longer exists.", enfe);
            }
            ParametroFactorconversion idParametroFactorconversion = nivelMaximo.getIdParametroFactorconversion();
            if (idParametroFactorconversion != null) {
                idParametroFactorconversion.getNivelMaximoList().remove(nivelMaximo);
                idParametroFactorconversion = em.merge(idParametroFactorconversion);
            }
            UnidadTiempo idUnidadTiempo = nivelMaximo.getIdUnidadTiempo();
            if (idUnidadTiempo != null) {
                idUnidadTiempo.getNivelMaximoList().remove(nivelMaximo);
                idUnidadTiempo = em.merge(idUnidadTiempo);
            }
            em.remove(nivelMaximo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelMaximo> findNivelMaximoEntities() {
        return findNivelMaximoEntities(true, -1, -1);
    }

    public List<NivelMaximo> findNivelMaximoEntities(int maxResults, int firstResult) {
        return findNivelMaximoEntities(false, maxResults, firstResult);
    }

    private List<NivelMaximo> findNivelMaximoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelMaximo.class));
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

    public NivelMaximo findNivelMaximo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelMaximo.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelMaximoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelMaximo> rt = cq.from(NivelMaximo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
