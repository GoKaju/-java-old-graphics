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
import com.statics.vo.Grupo;
import com.statics.vo.GrupoUsuarios;
import com.statics.vo.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class GrupoUsuariosJpaController implements Serializable {

    public GrupoUsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoUsuarios grupoUsuarios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupId = grupoUsuarios.getGrupId();
            if (grupId != null) {
                grupId = em.getReference(grupId.getClass(), grupId.getGrupId());
                grupoUsuarios.setGrupId(grupId);
            }
            Usuarios usuaId = grupoUsuarios.getUsuaId();
            if (usuaId != null) {
                usuaId = em.getReference(usuaId.getClass(), usuaId.getUsuaId());
                grupoUsuarios.setUsuaId(usuaId);
            }
            em.persist(grupoUsuarios);
            if (grupId != null) {
                grupId.getGrupoUsuariosList().add(grupoUsuarios);
                grupId = em.merge(grupId);
            }
            if (usuaId != null) {
                usuaId.getGrupoUsuariosList().add(grupoUsuarios);
                usuaId = em.merge(usuaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoUsuarios grupoUsuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoUsuarios persistentGrupoUsuarios = em.find(GrupoUsuarios.class, grupoUsuarios.getGrusId());
            Grupo grupIdOld = persistentGrupoUsuarios.getGrupId();
            Grupo grupIdNew = grupoUsuarios.getGrupId();
            Usuarios usuaIdOld = persistentGrupoUsuarios.getUsuaId();
            Usuarios usuaIdNew = grupoUsuarios.getUsuaId();
            if (grupIdNew != null) {
                grupIdNew = em.getReference(grupIdNew.getClass(), grupIdNew.getGrupId());
                grupoUsuarios.setGrupId(grupIdNew);
            }
            if (usuaIdNew != null) {
                usuaIdNew = em.getReference(usuaIdNew.getClass(), usuaIdNew.getUsuaId());
                grupoUsuarios.setUsuaId(usuaIdNew);
            }
            grupoUsuarios = em.merge(grupoUsuarios);
            if (grupIdOld != null && !grupIdOld.equals(grupIdNew)) {
                grupIdOld.getGrupoUsuariosList().remove(grupoUsuarios);
                grupIdOld = em.merge(grupIdOld);
            }
            if (grupIdNew != null && !grupIdNew.equals(grupIdOld)) {
                grupIdNew.getGrupoUsuariosList().add(grupoUsuarios);
                grupIdNew = em.merge(grupIdNew);
            }
            if (usuaIdOld != null && !usuaIdOld.equals(usuaIdNew)) {
                usuaIdOld.getGrupoUsuariosList().remove(grupoUsuarios);
                usuaIdOld = em.merge(usuaIdOld);
            }
            if (usuaIdNew != null && !usuaIdNew.equals(usuaIdOld)) {
                usuaIdNew.getGrupoUsuariosList().add(grupoUsuarios);
                usuaIdNew = em.merge(usuaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoUsuarios.getGrusId();
                if (findGrupoUsuarios(id) == null) {
                    throw new NonexistentEntityException("The grupoUsuarios with id " + id + " no longer exists.");
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
            GrupoUsuarios grupoUsuarios;
            try {
                grupoUsuarios = em.getReference(GrupoUsuarios.class, id);
                grupoUsuarios.getGrusId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoUsuarios with id " + id + " no longer exists.", enfe);
            }
            Grupo grupId = grupoUsuarios.getGrupId();
            if (grupId != null) {
                grupId.getGrupoUsuariosList().remove(grupoUsuarios);
                grupId = em.merge(grupId);
            }
            Usuarios usuaId = grupoUsuarios.getUsuaId();
            if (usuaId != null) {
                usuaId.getGrupoUsuariosList().remove(grupoUsuarios);
                usuaId = em.merge(usuaId);
            }
            em.remove(grupoUsuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoUsuarios> findGrupoUsuariosEntities() {
        return findGrupoUsuariosEntities(true, -1, -1);
    }

    public List<GrupoUsuarios> findGrupoUsuariosEntities(int maxResults, int firstResult) {
        return findGrupoUsuariosEntities(false, maxResults, firstResult);
    }

    private List<GrupoUsuarios> findGrupoUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoUsuarios.class));
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

    public GrupoUsuarios findGrupoUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoUsuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoUsuarios> rt = cq.from(GrupoUsuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
