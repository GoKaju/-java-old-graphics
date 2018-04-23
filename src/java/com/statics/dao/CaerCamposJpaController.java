/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.CaerCampos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.CargaErrores;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class CaerCamposJpaController implements Serializable {

    public CaerCamposJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CaerCampos caerCampos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargaErrores caerId = caerCampos.getCaerId();
            if (caerId != null) {
                caerId = em.getReference(caerId.getClass(), caerId.getCaerId());
                caerCampos.setCaerId(caerId);
            }
            em.persist(caerCampos);
            if (caerId != null) {
                caerId.getCaerCamposList().add(caerCampos);
                caerId = em.merge(caerId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CaerCampos caerCampos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CaerCampos persistentCaerCampos = em.find(CaerCampos.class, caerCampos.getCacaId());
            CargaErrores caerIdOld = persistentCaerCampos.getCaerId();
            CargaErrores caerIdNew = caerCampos.getCaerId();
            if (caerIdNew != null) {
                caerIdNew = em.getReference(caerIdNew.getClass(), caerIdNew.getCaerId());
                caerCampos.setCaerId(caerIdNew);
            }
            caerCampos = em.merge(caerCampos);
            if (caerIdOld != null && !caerIdOld.equals(caerIdNew)) {
                caerIdOld.getCaerCamposList().remove(caerCampos);
                caerIdOld = em.merge(caerIdOld);
            }
            if (caerIdNew != null && !caerIdNew.equals(caerIdOld)) {
                caerIdNew.getCaerCamposList().add(caerCampos);
                caerIdNew = em.merge(caerIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = caerCampos.getCacaId();
                if (findCaerCampos(id) == null) {
                    throw new NonexistentEntityException("The caerCampos with id " + id + " no longer exists.");
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
            CaerCampos caerCampos;
            try {
                caerCampos = em.getReference(CaerCampos.class, id);
                caerCampos.getCacaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caerCampos with id " + id + " no longer exists.", enfe);
            }
            CargaErrores caerId = caerCampos.getCaerId();
            if (caerId != null) {
                caerId.getCaerCamposList().remove(caerCampos);
                caerId = em.merge(caerId);
            }
            em.remove(caerCampos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CaerCampos> findCaerCamposEntities() {
        return findCaerCamposEntities(true, -1, -1);
    }

    public List<CaerCampos> findCaerCamposEntities(int maxResults, int firstResult) {
        return findCaerCamposEntities(false, maxResults, firstResult);
    }

    private List<CaerCampos> findCaerCamposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CaerCampos.class));
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

    public CaerCampos findCaerCampos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CaerCampos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaerCamposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CaerCampos> rt = cq.from(CaerCampos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
