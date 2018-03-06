/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.LogisticaPm;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author FoxHG
 */
public class LogisticaPmJpaController implements Serializable {

    public LogisticaPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogisticaPm logisticaPm) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(logisticaPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogisticaPm logisticaPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            logisticaPm = em.merge(logisticaPm);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logisticaPm.getId();
                if (findLogisticaPm(id) == null) {
                    throw new NonexistentEntityException("The logisticaPm with id " + id + " no longer exists.");
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
            LogisticaPm logisticaPm;
            try {
                logisticaPm = em.getReference(LogisticaPm.class, id);
                logisticaPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logisticaPm with id " + id + " no longer exists.", enfe);
            }
            em.remove(logisticaPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogisticaPm> findLogisticaPmEntities() {
        return findLogisticaPmEntities(true, -1, -1);
    }

    public List<LogisticaPm> findLogisticaPmEntities(int maxResults, int firstResult) {
        return findLogisticaPmEntities(false, maxResults, firstResult);
    }

    private List<LogisticaPm> findLogisticaPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogisticaPm.class));
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

    public LogisticaPm findLogisticaPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogisticaPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogisticaPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogisticaPm> rt = cq.from(LogisticaPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
