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
import com.statics.vo.FactorConversion;
import com.statics.vo.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getFactorConversionList() == null) {
            pais.setFactorConversionList(new ArrayList<FactorConversion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FactorConversion> attachedFactorConversionList = new ArrayList<FactorConversion>();
            for (FactorConversion factorConversionListFactorConversionToAttach : pais.getFactorConversionList()) {
                factorConversionListFactorConversionToAttach = em.getReference(factorConversionListFactorConversionToAttach.getClass(), factorConversionListFactorConversionToAttach.getId());
                attachedFactorConversionList.add(factorConversionListFactorConversionToAttach);
            }
            pais.setFactorConversionList(attachedFactorConversionList);
            em.persist(pais);
            for (FactorConversion factorConversionListFactorConversion : pais.getFactorConversionList()) {
                Pais oldIdPaisOfFactorConversionListFactorConversion = factorConversionListFactorConversion.getIdPais();
                factorConversionListFactorConversion.setIdPais(pais);
                factorConversionListFactorConversion = em.merge(factorConversionListFactorConversion);
                if (oldIdPaisOfFactorConversionListFactorConversion != null) {
                    oldIdPaisOfFactorConversionListFactorConversion.getFactorConversionList().remove(factorConversionListFactorConversion);
                    oldIdPaisOfFactorConversionListFactorConversion = em.merge(oldIdPaisOfFactorConversionListFactorConversion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getId());
            List<FactorConversion> factorConversionListOld = persistentPais.getFactorConversionList();
            List<FactorConversion> factorConversionListNew = pais.getFactorConversionList();
            List<FactorConversion> attachedFactorConversionListNew = new ArrayList<FactorConversion>();
            for (FactorConversion factorConversionListNewFactorConversionToAttach : factorConversionListNew) {
                factorConversionListNewFactorConversionToAttach = em.getReference(factorConversionListNewFactorConversionToAttach.getClass(), factorConversionListNewFactorConversionToAttach.getId());
                attachedFactorConversionListNew.add(factorConversionListNewFactorConversionToAttach);
            }
            factorConversionListNew = attachedFactorConversionListNew;
            pais.setFactorConversionList(factorConversionListNew);
            pais = em.merge(pais);
            for (FactorConversion factorConversionListOldFactorConversion : factorConversionListOld) {
                if (!factorConversionListNew.contains(factorConversionListOldFactorConversion)) {
                    factorConversionListOldFactorConversion.setIdPais(null);
                    factorConversionListOldFactorConversion = em.merge(factorConversionListOldFactorConversion);
                }
            }
            for (FactorConversion factorConversionListNewFactorConversion : factorConversionListNew) {
                if (!factorConversionListOld.contains(factorConversionListNewFactorConversion)) {
                    Pais oldIdPaisOfFactorConversionListNewFactorConversion = factorConversionListNewFactorConversion.getIdPais();
                    factorConversionListNewFactorConversion.setIdPais(pais);
                    factorConversionListNewFactorConversion = em.merge(factorConversionListNewFactorConversion);
                    if (oldIdPaisOfFactorConversionListNewFactorConversion != null && !oldIdPaisOfFactorConversionListNewFactorConversion.equals(pais)) {
                        oldIdPaisOfFactorConversionListNewFactorConversion.getFactorConversionList().remove(factorConversionListNewFactorConversion);
                        oldIdPaisOfFactorConversionListNewFactorConversion = em.merge(oldIdPaisOfFactorConversionListNewFactorConversion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getId();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<FactorConversion> factorConversionList = pais.getFactorConversionList();
            for (FactorConversion factorConversionListFactorConversion : factorConversionList) {
                factorConversionListFactorConversion.setIdPais(null);
                factorConversionListFactorConversion = em.merge(factorConversionListFactorConversion);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
