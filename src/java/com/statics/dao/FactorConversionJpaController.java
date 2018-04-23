/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.FactorConversion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.UnidadMedida;
import com.statics.vo.Pais;
import com.statics.vo.ParametroFactorconversion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class FactorConversionJpaController implements Serializable {

    public FactorConversionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FactorConversion factorConversion) {
        if (factorConversion.getParametroFactorconversionList() == null) {
            factorConversion.setParametroFactorconversionList(new ArrayList<ParametroFactorconversion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida idUnidadDestino = factorConversion.getIdUnidadDestino();
            if (idUnidadDestino != null) {
                idUnidadDestino = em.getReference(idUnidadDestino.getClass(), idUnidadDestino.getId());
                factorConversion.setIdUnidadDestino(idUnidadDestino);
            }
            Pais idPais = factorConversion.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getId());
                factorConversion.setIdPais(idPais);
            }
            List<ParametroFactorconversion> attachedParametroFactorconversionList = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversionToAttach : factorConversion.getParametroFactorconversionList()) {
                parametroFactorconversionListParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListParametroFactorconversionToAttach.getClass(), parametroFactorconversionListParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionList.add(parametroFactorconversionListParametroFactorconversionToAttach);
            }
            factorConversion.setParametroFactorconversionList(attachedParametroFactorconversionList);
            em.persist(factorConversion);
            if (idUnidadDestino != null) {
                idUnidadDestino.getFactorConversionList().add(factorConversion);
                idUnidadDestino = em.merge(idUnidadDestino);
            }
            if (idPais != null) {
                idPais.getFactorConversionList().add(factorConversion);
                idPais = em.merge(idPais);
            }
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : factorConversion.getParametroFactorconversionList()) {
                FactorConversion oldIdFactorConversionOfParametroFactorconversionListParametroFactorconversion = parametroFactorconversionListParametroFactorconversion.getIdFactorConversion();
                parametroFactorconversionListParametroFactorconversion.setIdFactorConversion(factorConversion);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
                if (oldIdFactorConversionOfParametroFactorconversionListParametroFactorconversion != null) {
                    oldIdFactorConversionOfParametroFactorconversionListParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListParametroFactorconversion);
                    oldIdFactorConversionOfParametroFactorconversionListParametroFactorconversion = em.merge(oldIdFactorConversionOfParametroFactorconversionListParametroFactorconversion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FactorConversion factorConversion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FactorConversion persistentFactorConversion = em.find(FactorConversion.class, factorConversion.getId());
            UnidadMedida idUnidadDestinoOld = persistentFactorConversion.getIdUnidadDestino();
            UnidadMedida idUnidadDestinoNew = factorConversion.getIdUnidadDestino();
            Pais idPaisOld = persistentFactorConversion.getIdPais();
            Pais idPaisNew = factorConversion.getIdPais();
            List<ParametroFactorconversion> parametroFactorconversionListOld = persistentFactorConversion.getParametroFactorconversionList();
            List<ParametroFactorconversion> parametroFactorconversionListNew = factorConversion.getParametroFactorconversionList();
            if (idUnidadDestinoNew != null) {
                idUnidadDestinoNew = em.getReference(idUnidadDestinoNew.getClass(), idUnidadDestinoNew.getId());
                factorConversion.setIdUnidadDestino(idUnidadDestinoNew);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getId());
                factorConversion.setIdPais(idPaisNew);
            }
            List<ParametroFactorconversion> attachedParametroFactorconversionListNew = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversionToAttach : parametroFactorconversionListNew) {
                parametroFactorconversionListNewParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListNewParametroFactorconversionToAttach.getClass(), parametroFactorconversionListNewParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionListNew.add(parametroFactorconversionListNewParametroFactorconversionToAttach);
            }
            parametroFactorconversionListNew = attachedParametroFactorconversionListNew;
            factorConversion.setParametroFactorconversionList(parametroFactorconversionListNew);
            factorConversion = em.merge(factorConversion);
            if (idUnidadDestinoOld != null && !idUnidadDestinoOld.equals(idUnidadDestinoNew)) {
                idUnidadDestinoOld.getFactorConversionList().remove(factorConversion);
                idUnidadDestinoOld = em.merge(idUnidadDestinoOld);
            }
            if (idUnidadDestinoNew != null && !idUnidadDestinoNew.equals(idUnidadDestinoOld)) {
                idUnidadDestinoNew.getFactorConversionList().add(factorConversion);
                idUnidadDestinoNew = em.merge(idUnidadDestinoNew);
            }
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getFactorConversionList().remove(factorConversion);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getFactorConversionList().add(factorConversion);
                idPaisNew = em.merge(idPaisNew);
            }
            for (ParametroFactorconversion parametroFactorconversionListOldParametroFactorconversion : parametroFactorconversionListOld) {
                if (!parametroFactorconversionListNew.contains(parametroFactorconversionListOldParametroFactorconversion)) {
                    parametroFactorconversionListOldParametroFactorconversion.setIdFactorConversion(null);
                    parametroFactorconversionListOldParametroFactorconversion = em.merge(parametroFactorconversionListOldParametroFactorconversion);
                }
            }
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversion : parametroFactorconversionListNew) {
                if (!parametroFactorconversionListOld.contains(parametroFactorconversionListNewParametroFactorconversion)) {
                    FactorConversion oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion = parametroFactorconversionListNewParametroFactorconversion.getIdFactorConversion();
                    parametroFactorconversionListNewParametroFactorconversion.setIdFactorConversion(factorConversion);
                    parametroFactorconversionListNewParametroFactorconversion = em.merge(parametroFactorconversionListNewParametroFactorconversion);
                    if (oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion != null && !oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion.equals(factorConversion)) {
                        oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListNewParametroFactorconversion);
                        oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion = em.merge(oldIdFactorConversionOfParametroFactorconversionListNewParametroFactorconversion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factorConversion.getId();
                if (findFactorConversion(id) == null) {
                    throw new NonexistentEntityException("The factorConversion with id " + id + " no longer exists.");
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
            FactorConversion factorConversion;
            try {
                factorConversion = em.getReference(FactorConversion.class, id);
                factorConversion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factorConversion with id " + id + " no longer exists.", enfe);
            }
            UnidadMedida idUnidadDestino = factorConversion.getIdUnidadDestino();
            if (idUnidadDestino != null) {
                idUnidadDestino.getFactorConversionList().remove(factorConversion);
                idUnidadDestino = em.merge(idUnidadDestino);
            }
            Pais idPais = factorConversion.getIdPais();
            if (idPais != null) {
                idPais.getFactorConversionList().remove(factorConversion);
                idPais = em.merge(idPais);
            }
            List<ParametroFactorconversion> parametroFactorconversionList = factorConversion.getParametroFactorconversionList();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : parametroFactorconversionList) {
                parametroFactorconversionListParametroFactorconversion.setIdFactorConversion(null);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
            }
            em.remove(factorConversion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FactorConversion> findFactorConversionEntities() {
        return findFactorConversionEntities(true, -1, -1);
    }

    public List<FactorConversion> findFactorConversionEntities(int maxResults, int firstResult) {
        return findFactorConversionEntities(false, maxResults, firstResult);
    }

    private List<FactorConversion> findFactorConversionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FactorConversion.class));
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

    public FactorConversion findFactorConversion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FactorConversion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactorConversionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FactorConversion> rt = cq.from(FactorConversion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
