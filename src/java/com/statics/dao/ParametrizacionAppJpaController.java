/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.dao.exceptions.PreexistingEntityException;
import com.statics.vo.ParametrizacionApp;
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
public class ParametrizacionAppJpaController implements Serializable {

    public ParametrizacionAppJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametrizacionApp parametrizacionApp) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(parametrizacionApp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametrizacionApp(parametrizacionApp.getPaapId()) != null) {
                throw new PreexistingEntityException("ParametrizacionApp " + parametrizacionApp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametrizacionApp parametrizacionApp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            parametrizacionApp = em.merge(parametrizacionApp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametrizacionApp.getPaapId();
                if (findParametrizacionApp(id) == null) {
                    throw new NonexistentEntityException("The parametrizacionApp with id " + id + " no longer exists.");
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
            ParametrizacionApp parametrizacionApp;
            try {
                parametrizacionApp = em.getReference(ParametrizacionApp.class, id);
                parametrizacionApp.getPaapId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametrizacionApp with id " + id + " no longer exists.", enfe);
            }
            em.remove(parametrizacionApp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametrizacionApp> findParametrizacionAppEntities() {
        return findParametrizacionAppEntities(true, -1, -1);
    }

    public List<ParametrizacionApp> findParametrizacionAppEntities(int maxResults, int firstResult) {
        return findParametrizacionAppEntities(false, maxResults, firstResult);
    }

    private List<ParametrizacionApp> findParametrizacionAppEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametrizacionApp.class));
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

    public ParametrizacionApp findParametrizacionApp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametrizacionApp.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametrizacionAppCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametrizacionApp> rt = cq.from(ParametrizacionApp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
