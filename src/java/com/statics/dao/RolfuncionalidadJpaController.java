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
import com.statics.vo.Roles;
import com.statics.vo.Funcionalidades;
import com.statics.vo.Rolfuncionalidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class RolfuncionalidadJpaController implements Serializable {

    public RolfuncionalidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rolfuncionalidad rolfuncionalidad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles roleId = rolfuncionalidad.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getRoleId());
                rolfuncionalidad.setRoleId(roleId);
            }
            Funcionalidades funcId = rolfuncionalidad.getFuncId();
            if (funcId != null) {
                funcId = em.getReference(funcId.getClass(), funcId.getFuncId());
                rolfuncionalidad.setFuncId(funcId);
            }
            em.persist(rolfuncionalidad);
            if (roleId != null) {
                roleId.getRolfuncionalidadList().add(rolfuncionalidad);
                roleId = em.merge(roleId);
            }
            if (funcId != null) {
                funcId.getRolfuncionalidadList().add(rolfuncionalidad);
                funcId = em.merge(funcId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rolfuncionalidad rolfuncionalidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rolfuncionalidad persistentRolfuncionalidad = em.find(Rolfuncionalidad.class, rolfuncionalidad.getRofuId());
            Roles roleIdOld = persistentRolfuncionalidad.getRoleId();
            Roles roleIdNew = rolfuncionalidad.getRoleId();
            Funcionalidades funcIdOld = persistentRolfuncionalidad.getFuncId();
            Funcionalidades funcIdNew = rolfuncionalidad.getFuncId();
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getRoleId());
                rolfuncionalidad.setRoleId(roleIdNew);
            }
            if (funcIdNew != null) {
                funcIdNew = em.getReference(funcIdNew.getClass(), funcIdNew.getFuncId());
                rolfuncionalidad.setFuncId(funcIdNew);
            }
            rolfuncionalidad = em.merge(rolfuncionalidad);
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getRolfuncionalidadList().remove(rolfuncionalidad);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getRolfuncionalidadList().add(rolfuncionalidad);
                roleIdNew = em.merge(roleIdNew);
            }
            if (funcIdOld != null && !funcIdOld.equals(funcIdNew)) {
                funcIdOld.getRolfuncionalidadList().remove(rolfuncionalidad);
                funcIdOld = em.merge(funcIdOld);
            }
            if (funcIdNew != null && !funcIdNew.equals(funcIdOld)) {
                funcIdNew.getRolfuncionalidadList().add(rolfuncionalidad);
                funcIdNew = em.merge(funcIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolfuncionalidad.getRofuId();
                if (findRolfuncionalidad(id) == null) {
                    throw new NonexistentEntityException("The rolfuncionalidad with id " + id + " no longer exists.");
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
            Rolfuncionalidad rolfuncionalidad;
            try {
                rolfuncionalidad = em.getReference(Rolfuncionalidad.class, id);
                rolfuncionalidad.getRofuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolfuncionalidad with id " + id + " no longer exists.", enfe);
            }
            Roles roleId = rolfuncionalidad.getRoleId();
            if (roleId != null) {
                roleId.getRolfuncionalidadList().remove(rolfuncionalidad);
                roleId = em.merge(roleId);
            }
            Funcionalidades funcId = rolfuncionalidad.getFuncId();
            if (funcId != null) {
                funcId.getRolfuncionalidadList().remove(rolfuncionalidad);
                funcId = em.merge(funcId);
            }
            em.remove(rolfuncionalidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rolfuncionalidad> findRolfuncionalidadEntities() {
        return findRolfuncionalidadEntities(true, -1, -1);
    }

    public List<Rolfuncionalidad> findRolfuncionalidadEntities(int maxResults, int firstResult) {
        return findRolfuncionalidadEntities(false, maxResults, firstResult);
    }

    private List<Rolfuncionalidad> findRolfuncionalidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rolfuncionalidad.class));
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

    public Rolfuncionalidad findRolfuncionalidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rolfuncionalidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolfuncionalidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rolfuncionalidad> rt = cq.from(Rolfuncionalidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
