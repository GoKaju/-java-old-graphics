/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.CriterioMicrolocalizacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.MicrolocalizacionPm;
import com.statics.vo.CriterioPm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class CriterioMicrolocalizacionJpaController implements Serializable {

    public CriterioMicrolocalizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriterioMicrolocalizacion criterioMicrolocalizacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MicrolocalizacionPm idMicrolocalizacion = criterioMicrolocalizacion.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion = em.getReference(idMicrolocalizacion.getClass(), idMicrolocalizacion.getId());
                criterioMicrolocalizacion.setIdMicrolocalizacion(idMicrolocalizacion);
            }
            CriterioPm idCriterio = criterioMicrolocalizacion.getIdCriterio();
            if (idCriterio != null) {
                idCriterio = em.getReference(idCriterio.getClass(), idCriterio.getId());
                criterioMicrolocalizacion.setIdCriterio(idCriterio);
            }
            em.persist(criterioMicrolocalizacion);
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getCriterioMicrolocalizacionList().add(criterioMicrolocalizacion);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            if (idCriterio != null) {
                idCriterio.getCriterioMicrolocalizacionList().add(criterioMicrolocalizacion);
                idCriterio = em.merge(idCriterio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriterioMicrolocalizacion criterioMicrolocalizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CriterioMicrolocalizacion persistentCriterioMicrolocalizacion = em.find(CriterioMicrolocalizacion.class, criterioMicrolocalizacion.getId());
            MicrolocalizacionPm idMicrolocalizacionOld = persistentCriterioMicrolocalizacion.getIdMicrolocalizacion();
            MicrolocalizacionPm idMicrolocalizacionNew = criterioMicrolocalizacion.getIdMicrolocalizacion();
            CriterioPm idCriterioOld = persistentCriterioMicrolocalizacion.getIdCriterio();
            CriterioPm idCriterioNew = criterioMicrolocalizacion.getIdCriterio();
            if (idMicrolocalizacionNew != null) {
                idMicrolocalizacionNew = em.getReference(idMicrolocalizacionNew.getClass(), idMicrolocalizacionNew.getId());
                criterioMicrolocalizacion.setIdMicrolocalizacion(idMicrolocalizacionNew);
            }
            if (idCriterioNew != null) {
                idCriterioNew = em.getReference(idCriterioNew.getClass(), idCriterioNew.getId());
                criterioMicrolocalizacion.setIdCriterio(idCriterioNew);
            }
            criterioMicrolocalizacion = em.merge(criterioMicrolocalizacion);
            if (idMicrolocalizacionOld != null && !idMicrolocalizacionOld.equals(idMicrolocalizacionNew)) {
                idMicrolocalizacionOld.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacion);
                idMicrolocalizacionOld = em.merge(idMicrolocalizacionOld);
            }
            if (idMicrolocalizacionNew != null && !idMicrolocalizacionNew.equals(idMicrolocalizacionOld)) {
                idMicrolocalizacionNew.getCriterioMicrolocalizacionList().add(criterioMicrolocalizacion);
                idMicrolocalizacionNew = em.merge(idMicrolocalizacionNew);
            }
            if (idCriterioOld != null && !idCriterioOld.equals(idCriterioNew)) {
                idCriterioOld.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacion);
                idCriterioOld = em.merge(idCriterioOld);
            }
            if (idCriterioNew != null && !idCriterioNew.equals(idCriterioOld)) {
                idCriterioNew.getCriterioMicrolocalizacionList().add(criterioMicrolocalizacion);
                idCriterioNew = em.merge(idCriterioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criterioMicrolocalizacion.getId();
                if (findCriterioMicrolocalizacion(id) == null) {
                    throw new NonexistentEntityException("The criterioMicrolocalizacion with id " + id + " no longer exists.");
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
            CriterioMicrolocalizacion criterioMicrolocalizacion;
            try {
                criterioMicrolocalizacion = em.getReference(CriterioMicrolocalizacion.class, id);
                criterioMicrolocalizacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criterioMicrolocalizacion with id " + id + " no longer exists.", enfe);
            }
            MicrolocalizacionPm idMicrolocalizacion = criterioMicrolocalizacion.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacion);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            CriterioPm idCriterio = criterioMicrolocalizacion.getIdCriterio();
            if (idCriterio != null) {
                idCriterio.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacion);
                idCriterio = em.merge(idCriterio);
            }
            em.remove(criterioMicrolocalizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriterioMicrolocalizacion> findCriterioMicrolocalizacionEntities() {
        return findCriterioMicrolocalizacionEntities(true, -1, -1);
    }

    public List<CriterioMicrolocalizacion> findCriterioMicrolocalizacionEntities(int maxResults, int firstResult) {
        return findCriterioMicrolocalizacionEntities(false, maxResults, firstResult);
    }

    private List<CriterioMicrolocalizacion> findCriterioMicrolocalizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriterioMicrolocalizacion.class));
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

    public CriterioMicrolocalizacion findCriterioMicrolocalizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriterioMicrolocalizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriterioMicrolocalizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriterioMicrolocalizacion> rt = cq.from(CriterioMicrolocalizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
