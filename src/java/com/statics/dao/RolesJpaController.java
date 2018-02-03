/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.IllegalOrphanException;
import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Roles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Usuariorol;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.Rolfuncionalidad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getUsuariorolList() == null) {
            roles.setUsuariorolList(new ArrayList<Usuariorol>());
        }
        if (roles.getRolfuncionalidadList() == null) {
            roles.setRolfuncionalidadList(new ArrayList<Rolfuncionalidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuariorol> attachedUsuariorolList = new ArrayList<Usuariorol>();
            for (Usuariorol usuariorolListUsuariorolToAttach : roles.getUsuariorolList()) {
                usuariorolListUsuariorolToAttach = em.getReference(usuariorolListUsuariorolToAttach.getClass(), usuariorolListUsuariorolToAttach.getUsroId());
                attachedUsuariorolList.add(usuariorolListUsuariorolToAttach);
            }
            roles.setUsuariorolList(attachedUsuariorolList);
            List<Rolfuncionalidad> attachedRolfuncionalidadList = new ArrayList<Rolfuncionalidad>();
            for (Rolfuncionalidad rolfuncionalidadListRolfuncionalidadToAttach : roles.getRolfuncionalidadList()) {
                rolfuncionalidadListRolfuncionalidadToAttach = em.getReference(rolfuncionalidadListRolfuncionalidadToAttach.getClass(), rolfuncionalidadListRolfuncionalidadToAttach.getRofuId());
                attachedRolfuncionalidadList.add(rolfuncionalidadListRolfuncionalidadToAttach);
            }
            roles.setRolfuncionalidadList(attachedRolfuncionalidadList);
            em.persist(roles);
            for (Usuariorol usuariorolListUsuariorol : roles.getUsuariorolList()) {
                Roles oldRoleIdOfUsuariorolListUsuariorol = usuariorolListUsuariorol.getRoleId();
                usuariorolListUsuariorol.setRoleId(roles);
                usuariorolListUsuariorol = em.merge(usuariorolListUsuariorol);
                if (oldRoleIdOfUsuariorolListUsuariorol != null) {
                    oldRoleIdOfUsuariorolListUsuariorol.getUsuariorolList().remove(usuariorolListUsuariorol);
                    oldRoleIdOfUsuariorolListUsuariorol = em.merge(oldRoleIdOfUsuariorolListUsuariorol);
                }
            }
            for (Rolfuncionalidad rolfuncionalidadListRolfuncionalidad : roles.getRolfuncionalidadList()) {
                Roles oldRoleIdOfRolfuncionalidadListRolfuncionalidad = rolfuncionalidadListRolfuncionalidad.getRoleId();
                rolfuncionalidadListRolfuncionalidad.setRoleId(roles);
                rolfuncionalidadListRolfuncionalidad = em.merge(rolfuncionalidadListRolfuncionalidad);
                if (oldRoleIdOfRolfuncionalidadListRolfuncionalidad != null) {
                    oldRoleIdOfRolfuncionalidadListRolfuncionalidad.getRolfuncionalidadList().remove(rolfuncionalidadListRolfuncionalidad);
                    oldRoleIdOfRolfuncionalidadListRolfuncionalidad = em.merge(oldRoleIdOfRolfuncionalidadListRolfuncionalidad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getRoleId());
            List<Usuariorol> usuariorolListOld = persistentRoles.getUsuariorolList();
            List<Usuariorol> usuariorolListNew = roles.getUsuariorolList();
            List<Rolfuncionalidad> rolfuncionalidadListOld = persistentRoles.getRolfuncionalidadList();
            List<Rolfuncionalidad> rolfuncionalidadListNew = roles.getRolfuncionalidadList();
            List<String> illegalOrphanMessages = null;
            for (Usuariorol usuariorolListOldUsuariorol : usuariorolListOld) {
                if (!usuariorolListNew.contains(usuariorolListOldUsuariorol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuariorol " + usuariorolListOldUsuariorol + " since its roleId field is not nullable.");
                }
            }
            for (Rolfuncionalidad rolfuncionalidadListOldRolfuncionalidad : rolfuncionalidadListOld) {
                if (!rolfuncionalidadListNew.contains(rolfuncionalidadListOldRolfuncionalidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolfuncionalidad " + rolfuncionalidadListOldRolfuncionalidad + " since its roleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuariorol> attachedUsuariorolListNew = new ArrayList<Usuariorol>();
            for (Usuariorol usuariorolListNewUsuariorolToAttach : usuariorolListNew) {
                usuariorolListNewUsuariorolToAttach = em.getReference(usuariorolListNewUsuariorolToAttach.getClass(), usuariorolListNewUsuariorolToAttach.getUsroId());
                attachedUsuariorolListNew.add(usuariorolListNewUsuariorolToAttach);
            }
            usuariorolListNew = attachedUsuariorolListNew;
            roles.setUsuariorolList(usuariorolListNew);
            List<Rolfuncionalidad> attachedRolfuncionalidadListNew = new ArrayList<Rolfuncionalidad>();
            for (Rolfuncionalidad rolfuncionalidadListNewRolfuncionalidadToAttach : rolfuncionalidadListNew) {
                rolfuncionalidadListNewRolfuncionalidadToAttach = em.getReference(rolfuncionalidadListNewRolfuncionalidadToAttach.getClass(), rolfuncionalidadListNewRolfuncionalidadToAttach.getRofuId());
                attachedRolfuncionalidadListNew.add(rolfuncionalidadListNewRolfuncionalidadToAttach);
            }
            rolfuncionalidadListNew = attachedRolfuncionalidadListNew;
            roles.setRolfuncionalidadList(rolfuncionalidadListNew);
            roles = em.merge(roles);
            for (Usuariorol usuariorolListNewUsuariorol : usuariorolListNew) {
                if (!usuariorolListOld.contains(usuariorolListNewUsuariorol)) {
                    Roles oldRoleIdOfUsuariorolListNewUsuariorol = usuariorolListNewUsuariorol.getRoleId();
                    usuariorolListNewUsuariorol.setRoleId(roles);
                    usuariorolListNewUsuariorol = em.merge(usuariorolListNewUsuariorol);
                    if (oldRoleIdOfUsuariorolListNewUsuariorol != null && !oldRoleIdOfUsuariorolListNewUsuariorol.equals(roles)) {
                        oldRoleIdOfUsuariorolListNewUsuariorol.getUsuariorolList().remove(usuariorolListNewUsuariorol);
                        oldRoleIdOfUsuariorolListNewUsuariorol = em.merge(oldRoleIdOfUsuariorolListNewUsuariorol);
                    }
                }
            }
            for (Rolfuncionalidad rolfuncionalidadListNewRolfuncionalidad : rolfuncionalidadListNew) {
                if (!rolfuncionalidadListOld.contains(rolfuncionalidadListNewRolfuncionalidad)) {
                    Roles oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad = rolfuncionalidadListNewRolfuncionalidad.getRoleId();
                    rolfuncionalidadListNewRolfuncionalidad.setRoleId(roles);
                    rolfuncionalidadListNewRolfuncionalidad = em.merge(rolfuncionalidadListNewRolfuncionalidad);
                    if (oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad != null && !oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad.equals(roles)) {
                        oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad.getRolfuncionalidadList().remove(rolfuncionalidadListNewRolfuncionalidad);
                        oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad = em.merge(oldRoleIdOfRolfuncionalidadListNewRolfuncionalidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getRoleId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getRoleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuariorol> usuariorolListOrphanCheck = roles.getUsuariorolList();
            for (Usuariorol usuariorolListOrphanCheckUsuariorol : usuariorolListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Usuariorol " + usuariorolListOrphanCheckUsuariorol + " in its usuariorolList field has a non-nullable roleId field.");
            }
            List<Rolfuncionalidad> rolfuncionalidadListOrphanCheck = roles.getRolfuncionalidadList();
            for (Rolfuncionalidad rolfuncionalidadListOrphanCheckRolfuncionalidad : rolfuncionalidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Rolfuncionalidad " + rolfuncionalidadListOrphanCheckRolfuncionalidad + " in its rolfuncionalidadList field has a non-nullable roleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
