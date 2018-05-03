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
import com.statics.vo.UnidadMedida;
import com.statics.vo.Parametros;
import com.statics.vo.UnidadmedidaParametro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author IMAGINAMOS
 */
public class UnidadmedidaParametroJpaController implements Serializable {

    public UnidadmedidaParametroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadmedidaParametro unidadmedidaParametro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida idUnidadMedida = unidadmedidaParametro.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida = em.getReference(idUnidadMedida.getClass(), idUnidadMedida.getId());
                unidadmedidaParametro.setIdUnidadMedida(idUnidadMedida);
            }
            Parametros idParametro = unidadmedidaParametro.getIdParametro();
            if (idParametro != null) {
                idParametro = em.getReference(idParametro.getClass(), idParametro.getParaId());
                unidadmedidaParametro.setIdParametro(idParametro);
            }
            em.persist(unidadmedidaParametro);
            if (idUnidadMedida != null) {
                idUnidadMedida.getUnidadmedidaParametroList().add(unidadmedidaParametro);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            if (idParametro != null) {
                idParametro.getUnidadmedidaParametroList().add(unidadmedidaParametro);
                idParametro = em.merge(idParametro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadmedidaParametro unidadmedidaParametro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadmedidaParametro persistentUnidadmedidaParametro = em.find(UnidadmedidaParametro.class, unidadmedidaParametro.getId());
            UnidadMedida idUnidadMedidaOld = persistentUnidadmedidaParametro.getIdUnidadMedida();
            UnidadMedida idUnidadMedidaNew = unidadmedidaParametro.getIdUnidadMedida();
            Parametros idParametroOld = persistentUnidadmedidaParametro.getIdParametro();
            Parametros idParametroNew = unidadmedidaParametro.getIdParametro();
            if (idUnidadMedidaNew != null) {
                idUnidadMedidaNew = em.getReference(idUnidadMedidaNew.getClass(), idUnidadMedidaNew.getId());
                unidadmedidaParametro.setIdUnidadMedida(idUnidadMedidaNew);
            }
            if (idParametroNew != null) {
                idParametroNew = em.getReference(idParametroNew.getClass(), idParametroNew.getParaId());
                unidadmedidaParametro.setIdParametro(idParametroNew);
            }
            unidadmedidaParametro = em.merge(unidadmedidaParametro);
            if (idUnidadMedidaOld != null && !idUnidadMedidaOld.equals(idUnidadMedidaNew)) {
                idUnidadMedidaOld.getUnidadmedidaParametroList().remove(unidadmedidaParametro);
                idUnidadMedidaOld = em.merge(idUnidadMedidaOld);
            }
            if (idUnidadMedidaNew != null && !idUnidadMedidaNew.equals(idUnidadMedidaOld)) {
                idUnidadMedidaNew.getUnidadmedidaParametroList().add(unidadmedidaParametro);
                idUnidadMedidaNew = em.merge(idUnidadMedidaNew);
            }
            if (idParametroOld != null && !idParametroOld.equals(idParametroNew)) {
                idParametroOld.getUnidadmedidaParametroList().remove(unidadmedidaParametro);
                idParametroOld = em.merge(idParametroOld);
            }
            if (idParametroNew != null && !idParametroNew.equals(idParametroOld)) {
                idParametroNew.getUnidadmedidaParametroList().add(unidadmedidaParametro);
                idParametroNew = em.merge(idParametroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadmedidaParametro.getId();
                if (findUnidadmedidaParametro(id) == null) {
                    throw new NonexistentEntityException("The unidadmedidaParametro with id " + id + " no longer exists.");
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
            UnidadmedidaParametro unidadmedidaParametro;
            try {
                unidadmedidaParametro = em.getReference(UnidadmedidaParametro.class, id);
                unidadmedidaParametro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadmedidaParametro with id " + id + " no longer exists.", enfe);
            }
            UnidadMedida idUnidadMedida = unidadmedidaParametro.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida.getUnidadmedidaParametroList().remove(unidadmedidaParametro);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            Parametros idParametro = unidadmedidaParametro.getIdParametro();
            if (idParametro != null) {
                idParametro.getUnidadmedidaParametroList().remove(unidadmedidaParametro);
                idParametro = em.merge(idParametro);
            }
            em.remove(unidadmedidaParametro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadmedidaParametro> findUnidadmedidaParametroEntities() {
        return findUnidadmedidaParametroEntities(true, -1, -1);
    }

    public List<UnidadmedidaParametro> findUnidadmedidaParametroEntities(int maxResults, int firstResult) {
        return findUnidadmedidaParametroEntities(false, maxResults, firstResult);
    }

    private List<UnidadmedidaParametro> findUnidadmedidaParametroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadmedidaParametro.class));
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

    public UnidadmedidaParametro findUnidadmedidaParametro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadmedidaParametro.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadmedidaParametroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadmedidaParametro> rt = cq.from(UnidadmedidaParametro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
