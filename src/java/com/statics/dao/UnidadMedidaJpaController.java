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
import com.statics.vo.ParametroFactorconversion;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.FactorConversion;
import com.statics.vo.UnidadMedida;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class UnidadMedidaJpaController implements Serializable {

    public UnidadMedidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadMedida unidadMedida) {
        if (unidadMedida.getParametroFactorconversionList() == null) {
            unidadMedida.setParametroFactorconversionList(new ArrayList<ParametroFactorconversion>());
        }
        if (unidadMedida.getFactorConversionList() == null) {
            unidadMedida.setFactorConversionList(new ArrayList<FactorConversion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ParametroFactorconversion> attachedParametroFactorconversionList = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversionToAttach : unidadMedida.getParametroFactorconversionList()) {
                parametroFactorconversionListParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListParametroFactorconversionToAttach.getClass(), parametroFactorconversionListParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionList.add(parametroFactorconversionListParametroFactorconversionToAttach);
            }
            unidadMedida.setParametroFactorconversionList(attachedParametroFactorconversionList);
            List<FactorConversion> attachedFactorConversionList = new ArrayList<FactorConversion>();
            for (FactorConversion factorConversionListFactorConversionToAttach : unidadMedida.getFactorConversionList()) {
                factorConversionListFactorConversionToAttach = em.getReference(factorConversionListFactorConversionToAttach.getClass(), factorConversionListFactorConversionToAttach.getId());
                attachedFactorConversionList.add(factorConversionListFactorConversionToAttach);
            }
            unidadMedida.setFactorConversionList(attachedFactorConversionList);
            em.persist(unidadMedida);
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : unidadMedida.getParametroFactorconversionList()) {
                UnidadMedida oldIdUnidadMedidaOfParametroFactorconversionListParametroFactorconversion = parametroFactorconversionListParametroFactorconversion.getIdUnidadMedida();
                parametroFactorconversionListParametroFactorconversion.setIdUnidadMedida(unidadMedida);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
                if (oldIdUnidadMedidaOfParametroFactorconversionListParametroFactorconversion != null) {
                    oldIdUnidadMedidaOfParametroFactorconversionListParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListParametroFactorconversion);
                    oldIdUnidadMedidaOfParametroFactorconversionListParametroFactorconversion = em.merge(oldIdUnidadMedidaOfParametroFactorconversionListParametroFactorconversion);
                }
            }
            for (FactorConversion factorConversionListFactorConversion : unidadMedida.getFactorConversionList()) {
                UnidadMedida oldIdUnidadDestinoOfFactorConversionListFactorConversion = factorConversionListFactorConversion.getIdUnidadDestino();
                factorConversionListFactorConversion.setIdUnidadDestino(unidadMedida);
                factorConversionListFactorConversion = em.merge(factorConversionListFactorConversion);
                if (oldIdUnidadDestinoOfFactorConversionListFactorConversion != null) {
                    oldIdUnidadDestinoOfFactorConversionListFactorConversion.getFactorConversionList().remove(factorConversionListFactorConversion);
                    oldIdUnidadDestinoOfFactorConversionListFactorConversion = em.merge(oldIdUnidadDestinoOfFactorConversionListFactorConversion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadMedida unidadMedida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida persistentUnidadMedida = em.find(UnidadMedida.class, unidadMedida.getId());
            List<ParametroFactorconversion> parametroFactorconversionListOld = persistentUnidadMedida.getParametroFactorconversionList();
            List<ParametroFactorconversion> parametroFactorconversionListNew = unidadMedida.getParametroFactorconversionList();
            List<FactorConversion> factorConversionListOld = persistentUnidadMedida.getFactorConversionList();
            List<FactorConversion> factorConversionListNew = unidadMedida.getFactorConversionList();
            List<ParametroFactorconversion> attachedParametroFactorconversionListNew = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversionToAttach : parametroFactorconversionListNew) {
                parametroFactorconversionListNewParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListNewParametroFactorconversionToAttach.getClass(), parametroFactorconversionListNewParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionListNew.add(parametroFactorconversionListNewParametroFactorconversionToAttach);
            }
            parametroFactorconversionListNew = attachedParametroFactorconversionListNew;
            unidadMedida.setParametroFactorconversionList(parametroFactorconversionListNew);
            List<FactorConversion> attachedFactorConversionListNew = new ArrayList<FactorConversion>();
            for (FactorConversion factorConversionListNewFactorConversionToAttach : factorConversionListNew) {
                factorConversionListNewFactorConversionToAttach = em.getReference(factorConversionListNewFactorConversionToAttach.getClass(), factorConversionListNewFactorConversionToAttach.getId());
                attachedFactorConversionListNew.add(factorConversionListNewFactorConversionToAttach);
            }
            factorConversionListNew = attachedFactorConversionListNew;
            unidadMedida.setFactorConversionList(factorConversionListNew);
            unidadMedida = em.merge(unidadMedida);
            for (ParametroFactorconversion parametroFactorconversionListOldParametroFactorconversion : parametroFactorconversionListOld) {
                if (!parametroFactorconversionListNew.contains(parametroFactorconversionListOldParametroFactorconversion)) {
                    parametroFactorconversionListOldParametroFactorconversion.setIdUnidadMedida(null);
                    parametroFactorconversionListOldParametroFactorconversion = em.merge(parametroFactorconversionListOldParametroFactorconversion);
                }
            }
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversion : parametroFactorconversionListNew) {
                if (!parametroFactorconversionListOld.contains(parametroFactorconversionListNewParametroFactorconversion)) {
                    UnidadMedida oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion = parametroFactorconversionListNewParametroFactorconversion.getIdUnidadMedida();
                    parametroFactorconversionListNewParametroFactorconversion.setIdUnidadMedida(unidadMedida);
                    parametroFactorconversionListNewParametroFactorconversion = em.merge(parametroFactorconversionListNewParametroFactorconversion);
                    if (oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion != null && !oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion.equals(unidadMedida)) {
                        oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListNewParametroFactorconversion);
                        oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion = em.merge(oldIdUnidadMedidaOfParametroFactorconversionListNewParametroFactorconversion);
                    }
                }
            }
            for (FactorConversion factorConversionListOldFactorConversion : factorConversionListOld) {
                if (!factorConversionListNew.contains(factorConversionListOldFactorConversion)) {
                    factorConversionListOldFactorConversion.setIdUnidadDestino(null);
                    factorConversionListOldFactorConversion = em.merge(factorConversionListOldFactorConversion);
                }
            }
            for (FactorConversion factorConversionListNewFactorConversion : factorConversionListNew) {
                if (!factorConversionListOld.contains(factorConversionListNewFactorConversion)) {
                    UnidadMedida oldIdUnidadDestinoOfFactorConversionListNewFactorConversion = factorConversionListNewFactorConversion.getIdUnidadDestino();
                    factorConversionListNewFactorConversion.setIdUnidadDestino(unidadMedida);
                    factorConversionListNewFactorConversion = em.merge(factorConversionListNewFactorConversion);
                    if (oldIdUnidadDestinoOfFactorConversionListNewFactorConversion != null && !oldIdUnidadDestinoOfFactorConversionListNewFactorConversion.equals(unidadMedida)) {
                        oldIdUnidadDestinoOfFactorConversionListNewFactorConversion.getFactorConversionList().remove(factorConversionListNewFactorConversion);
                        oldIdUnidadDestinoOfFactorConversionListNewFactorConversion = em.merge(oldIdUnidadDestinoOfFactorConversionListNewFactorConversion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadMedida.getId();
                if (findUnidadMedida(id) == null) {
                    throw new NonexistentEntityException("The unidadMedida with id " + id + " no longer exists.");
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
            UnidadMedida unidadMedida;
            try {
                unidadMedida = em.getReference(UnidadMedida.class, id);
                unidadMedida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadMedida with id " + id + " no longer exists.", enfe);
            }
            List<ParametroFactorconversion> parametroFactorconversionList = unidadMedida.getParametroFactorconversionList();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : parametroFactorconversionList) {
                parametroFactorconversionListParametroFactorconversion.setIdUnidadMedida(null);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
            }
            List<FactorConversion> factorConversionList = unidadMedida.getFactorConversionList();
            for (FactorConversion factorConversionListFactorConversion : factorConversionList) {
                factorConversionListFactorConversion.setIdUnidadDestino(null);
                factorConversionListFactorConversion = em.merge(factorConversionListFactorConversion);
            }
            em.remove(unidadMedida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadMedida> findUnidadMedidaEntities() {
        return findUnidadMedidaEntities(true, -1, -1);
    }

    public List<UnidadMedida> findUnidadMedidaEntities(int maxResults, int firstResult) {
        return findUnidadMedidaEntities(false, maxResults, firstResult);
    }

    private List<UnidadMedida> findUnidadMedidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadMedida.class));
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

    public UnidadMedida findUnidadMedida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadMedida.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadMedidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadMedida> rt = cq.from(UnidadMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
