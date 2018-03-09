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
import com.statics.vo.Usuarios;
import com.statics.vo.Roles;
import com.statics.vo.Usuariorol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class UsuariorolJpaController implements Serializable {

    public UsuariorolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuariorol usuariorol) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuaId = usuariorol.getUsuaId();
            if (usuaId != null) {
                usuaId = em.getReference(usuaId.getClass(), usuaId.getUsuaId());
                usuariorol.setUsuaId(usuaId);
            }
            Roles roleId = usuariorol.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getRoleId());
                usuariorol.setRoleId(roleId);
            }
            em.persist(usuariorol);
            if (usuaId != null) {
                usuaId.getUsuariorolList().add(usuariorol);
                usuaId = em.merge(usuaId);
            }
            if (roleId != null) {
                roleId.getUsuariorolList().add(usuariorol);
                roleId = em.merge(roleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuariorol usuariorol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuariorol persistentUsuariorol = em.find(Usuariorol.class, usuariorol.getUsroId());
            Usuarios usuaIdOld = persistentUsuariorol.getUsuaId();
            Usuarios usuaIdNew = usuariorol.getUsuaId();
            Roles roleIdOld = persistentUsuariorol.getRoleId();
            Roles roleIdNew = usuariorol.getRoleId();
            if (usuaIdNew != null) {
                usuaIdNew = em.getReference(usuaIdNew.getClass(), usuaIdNew.getUsuaId());
                usuariorol.setUsuaId(usuaIdNew);
            }
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getRoleId());
                usuariorol.setRoleId(roleIdNew);
            }
            usuariorol = em.merge(usuariorol);
            if (usuaIdOld != null && !usuaIdOld.equals(usuaIdNew)) {
                usuaIdOld.getUsuariorolList().remove(usuariorol);
                usuaIdOld = em.merge(usuaIdOld);
            }
            if (usuaIdNew != null && !usuaIdNew.equals(usuaIdOld)) {
                usuaIdNew.getUsuariorolList().add(usuariorol);
                usuaIdNew = em.merge(usuaIdNew);
            }
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getUsuariorolList().remove(usuariorol);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getUsuariorolList().add(usuariorol);
                roleIdNew = em.merge(roleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuariorol.getUsroId();
                if (findUsuariorol(id) == null) {
                    throw new NonexistentEntityException("The usuariorol with id " + id + " no longer exists.");
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
            Usuariorol usuariorol;
            try {
                usuariorol = em.getReference(Usuariorol.class, id);
                usuariorol.getUsroId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariorol with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuaId = usuariorol.getUsuaId();
            if (usuaId != null) {
                usuaId.getUsuariorolList().remove(usuariorol);
                usuaId = em.merge(usuaId);
            }
            Roles roleId = usuariorol.getRoleId();
            if (roleId != null) {
                roleId.getUsuariorolList().remove(usuariorol);
                roleId = em.merge(roleId);
            }
            em.remove(usuariorol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuariorol> findUsuariorolEntities() {
        return findUsuariorolEntities(true, -1, -1);
    }

    public List<Usuariorol> findUsuariorolEntities(int maxResults, int firstResult) {
        return findUsuariorolEntities(false, maxResults, firstResult);
    }

    private List<Usuariorol> findUsuariorolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuariorol.class));
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

    public Usuariorol findUsuariorol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuariorol.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariorolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuariorol> rt = cq.from(Usuariorol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
