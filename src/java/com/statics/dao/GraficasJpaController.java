/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Graficas;
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
 * @author Usuario
 */
public class GraficasJpaController implements Serializable {

    public GraficasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Graficas graficas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(graficas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Graficas graficas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            graficas = em.merge(graficas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = graficas.getGrafId();
                if (findGraficas(id) == null) {
                    throw new NonexistentEntityException("The graficas with id " + id + " no longer exists.");
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
            Graficas graficas;
            try {
                graficas = em.getReference(Graficas.class, id);
                graficas.getGrafId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The graficas with id " + id + " no longer exists.", enfe);
            }
            em.remove(graficas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Graficas> findGraficasEntities() {
        return findGraficasEntities(true, -1, -1);
    }

    public List<Graficas> findGraficasEntities(int maxResults, int firstResult) {
        return findGraficasEntities(false, maxResults, firstResult);
    }

    private List<Graficas> findGraficasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Graficas.class));
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

    public Graficas findGraficas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Graficas.class, id);
        } finally {
            em.close();
        }
    }

    public int getGraficasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Graficas> rt = cq.from(Graficas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
