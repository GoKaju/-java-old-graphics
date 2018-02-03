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
import com.statics.vo.CargaParametro;
import com.statics.vo.Datos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class DatosJpaController implements Serializable {

    public DatosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Datos datos) {
        final long start = System.currentTimeMillis();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargaParametro papuId = datos.getPapuId();
            if (papuId != null) {
                papuId = em.getReference(papuId.getClass(), papuId.getCapaId());
                datos.setPapuId(papuId);
            }
            em.persist(datos);
            if (papuId != null) {
                papuId.getDatosList().add(datos);
                papuId = em.merge(papuId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
            System.out.println("-->## Elapsed Datos create " + (System.currentTimeMillis() - start) + " ms");
        }
    }
    public void create(List<Datos> ldatos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            for (Datos datos : ldatos) {
            CargaParametro papuId = datos.getPapuId();
            if (papuId != null) {
                papuId = em.getReference(papuId.getClass(), papuId.getCapaId());
                datos.setPapuId(papuId);
            }
            em.persist(datos);
            
            if (papuId != null) {
                papuId.getDatosList().add(datos);
                papuId = em.merge(papuId);
            }   
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Datos datos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Datos persistentDatos = em.find(Datos.class, datos.getDatoId());
            CargaParametro papuIdOld = persistentDatos.getPapuId();
            CargaParametro papuIdNew = datos.getPapuId();
            if (papuIdNew != null) {
                papuIdNew = em.getReference(papuIdNew.getClass(), papuIdNew.getCapaId());
                datos.setPapuId(papuIdNew);
            }
            datos = em.merge(datos);
            if (papuIdOld != null && !papuIdOld.equals(papuIdNew)) {
                papuIdOld.getDatosList().remove(datos);
                papuIdOld = em.merge(papuIdOld);
            }
            if (papuIdNew != null && !papuIdNew.equals(papuIdOld)) {
                papuIdNew.getDatosList().add(datos);
                papuIdNew = em.merge(papuIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datos.getDatoId();
                if (findDatos(id) == null) {
                    throw new NonexistentEntityException("The datos with id " + id + " no longer exists.");
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
            Datos datos;
            try {
                datos = em.getReference(Datos.class, id);
                datos.getDatoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datos with id " + id + " no longer exists.", enfe);
            }
            CargaParametro papuId = datos.getPapuId();
            if (papuId != null) {
                papuId.getDatosList().remove(datos);
                papuId = em.merge(papuId);
            }
            em.remove(datos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Datos> findDatosEntities() {
        return findDatosEntities(true, -1, -1);
    }

    public List<Datos> findDatosEntities(int maxResults, int firstResult) {
        return findDatosEntities(false, maxResults, firstResult);
    }

    private List<Datos> findDatosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Datos.class));
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

    public Datos findDatos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Datos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Datos> rt = cq.from(Datos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
