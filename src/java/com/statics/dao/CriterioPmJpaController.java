/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.CriterioMicrolocalizacion;
import com.statics.vo.CriterioPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class CriterioPmJpaController implements Serializable {

    public CriterioPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CriterioPm criterioPm) {
        if (criterioPm.getCriterioMicrolocalizacionList() == null) {
            criterioPm.setCriterioMicrolocalizacionList(new ArrayList<CriterioMicrolocalizacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CriterioMicrolocalizacion> attachedCriterioMicrolocalizacionList = new ArrayList<CriterioMicrolocalizacion>();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach : criterioPm.getCriterioMicrolocalizacionList()) {
                criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach = em.getReference(criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach.getClass(), criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach.getId());
                attachedCriterioMicrolocalizacionList.add(criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach);
            }
            criterioPm.setCriterioMicrolocalizacionList(attachedCriterioMicrolocalizacionList);
            em.persist(criterioPm);
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacion : criterioPm.getCriterioMicrolocalizacionList()) {
                CriterioPm oldIdCriterioOfCriterioMicrolocalizacionListCriterioMicrolocalizacion = criterioMicrolocalizacionListCriterioMicrolocalizacion.getIdCriterio();
                criterioMicrolocalizacionListCriterioMicrolocalizacion.setIdCriterio(criterioPm);
                criterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListCriterioMicrolocalizacion);
                if (oldIdCriterioOfCriterioMicrolocalizacionListCriterioMicrolocalizacion != null) {
                    oldIdCriterioOfCriterioMicrolocalizacionListCriterioMicrolocalizacion.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacionListCriterioMicrolocalizacion);
                    oldIdCriterioOfCriterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(oldIdCriterioOfCriterioMicrolocalizacionListCriterioMicrolocalizacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CriterioPm criterioPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CriterioPm persistentCriterioPm = em.find(CriterioPm.class, criterioPm.getId());
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionListOld = persistentCriterioPm.getCriterioMicrolocalizacionList();
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionListNew = criterioPm.getCriterioMicrolocalizacionList();
            List<CriterioMicrolocalizacion> attachedCriterioMicrolocalizacionListNew = new ArrayList<CriterioMicrolocalizacion>();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach : criterioMicrolocalizacionListNew) {
                criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach = em.getReference(criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach.getClass(), criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach.getId());
                attachedCriterioMicrolocalizacionListNew.add(criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach);
            }
            criterioMicrolocalizacionListNew = attachedCriterioMicrolocalizacionListNew;
            criterioPm.setCriterioMicrolocalizacionList(criterioMicrolocalizacionListNew);
            criterioPm = em.merge(criterioPm);
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListOldCriterioMicrolocalizacion : criterioMicrolocalizacionListOld) {
                if (!criterioMicrolocalizacionListNew.contains(criterioMicrolocalizacionListOldCriterioMicrolocalizacion)) {
                    criterioMicrolocalizacionListOldCriterioMicrolocalizacion.setIdCriterio(null);
                    criterioMicrolocalizacionListOldCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListOldCriterioMicrolocalizacion);
                }
            }
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListNewCriterioMicrolocalizacion : criterioMicrolocalizacionListNew) {
                if (!criterioMicrolocalizacionListOld.contains(criterioMicrolocalizacionListNewCriterioMicrolocalizacion)) {
                    CriterioPm oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion = criterioMicrolocalizacionListNewCriterioMicrolocalizacion.getIdCriterio();
                    criterioMicrolocalizacionListNewCriterioMicrolocalizacion.setIdCriterio(criterioPm);
                    criterioMicrolocalizacionListNewCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                    if (oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion != null && !oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion.equals(criterioPm)) {
                        oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                        oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion = em.merge(oldIdCriterioOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = criterioPm.getId();
                if (findCriterioPm(id) == null) {
                    throw new NonexistentEntityException("The criterioPm with id " + id + " no longer exists.");
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
            CriterioPm criterioPm;
            try {
                criterioPm = em.getReference(CriterioPm.class, id);
                criterioPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The criterioPm with id " + id + " no longer exists.", enfe);
            }
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionList = criterioPm.getCriterioMicrolocalizacionList();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacion : criterioMicrolocalizacionList) {
                criterioMicrolocalizacionListCriterioMicrolocalizacion.setIdCriterio(null);
                criterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListCriterioMicrolocalizacion);
            }
            em.remove(criterioPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CriterioPm> findCriterioPmEntities() {
        return findCriterioPmEntities(true, -1, -1);
    }

    public List<CriterioPm> findCriterioPmEntities(int maxResults, int firstResult) {
        return findCriterioPmEntities(false, maxResults, firstResult);
    }

    private List<CriterioPm> findCriterioPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CriterioPm.class));
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

    public CriterioPm findCriterioPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CriterioPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriterioPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CriterioPm> rt = cq.from(CriterioPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
