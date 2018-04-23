/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.ParametroLabels;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Parametros;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ParametroLabelsJpaController implements Serializable {

    public ParametroLabelsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroLabels parametroLabels) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametros paraId = parametroLabels.getParaId();
            if (paraId != null) {
                paraId = em.getReference(paraId.getClass(), paraId.getParaId());
                parametroLabels.setParaId(paraId);
            }
            em.persist(parametroLabels);
            if (paraId != null) {
                paraId.getParametroLabelsList().add(parametroLabels);
                paraId = em.merge(paraId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroLabels parametroLabels) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroLabels persistentParametroLabels = em.find(ParametroLabels.class, parametroLabels.getPalaId());
            Parametros paraIdOld = persistentParametroLabels.getParaId();
            Parametros paraIdNew = parametroLabels.getParaId();
            if (paraIdNew != null) {
                paraIdNew = em.getReference(paraIdNew.getClass(), paraIdNew.getParaId());
                parametroLabels.setParaId(paraIdNew);
            }
            parametroLabels = em.merge(parametroLabels);
            if (paraIdOld != null && !paraIdOld.equals(paraIdNew)) {
                paraIdOld.getParametroLabelsList().remove(parametroLabels);
                paraIdOld = em.merge(paraIdOld);
            }
            if (paraIdNew != null && !paraIdNew.equals(paraIdOld)) {
                paraIdNew.getParametroLabelsList().add(parametroLabels);
                paraIdNew = em.merge(paraIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroLabels.getPalaId();
                if (findParametroLabels(id) == null) {
                    throw new NonexistentEntityException("The parametroLabels with id " + id + " no longer exists.");
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
            ParametroLabels parametroLabels;
            try {
                parametroLabels = em.getReference(ParametroLabels.class, id);
                parametroLabels.getPalaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroLabels with id " + id + " no longer exists.", enfe);
            }
            Parametros paraId = parametroLabels.getParaId();
            if (paraId != null) {
                paraId.getParametroLabelsList().remove(parametroLabels);
                paraId = em.merge(paraId);
            }
            em.remove(parametroLabels);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroLabels> findParametroLabelsEntities() {
        return findParametroLabelsEntities(true, -1, -1);
    }

    public List<ParametroLabels> findParametroLabelsEntities(int maxResults, int firstResult) {
        return findParametroLabelsEntities(false, maxResults, firstResult);
    }

    private List<ParametroLabels> findParametroLabelsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroLabels.class));
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

    public ParametroLabels findParametroLabels(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroLabels.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroLabelsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroLabels> rt = cq.from(ParametroLabels.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
