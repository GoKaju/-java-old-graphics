/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.IllegalOrphanException;
import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Funcionalidades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Rolfuncionalidad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class FuncionalidadesJpaController implements Serializable {

    public FuncionalidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionalidades funcionalidades) {
        if (funcionalidades.getRolfuncionalidadList() == null) {
            funcionalidades.setRolfuncionalidadList(new ArrayList<Rolfuncionalidad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Rolfuncionalidad> attachedRolfuncionalidadList = new ArrayList<Rolfuncionalidad>();
            for (Rolfuncionalidad rolfuncionalidadListRolfuncionalidadToAttach : funcionalidades.getRolfuncionalidadList()) {
                rolfuncionalidadListRolfuncionalidadToAttach = em.getReference(rolfuncionalidadListRolfuncionalidadToAttach.getClass(), rolfuncionalidadListRolfuncionalidadToAttach.getRofuId());
                attachedRolfuncionalidadList.add(rolfuncionalidadListRolfuncionalidadToAttach);
            }
            funcionalidades.setRolfuncionalidadList(attachedRolfuncionalidadList);
            em.persist(funcionalidades);
            for (Rolfuncionalidad rolfuncionalidadListRolfuncionalidad : funcionalidades.getRolfuncionalidadList()) {
                Funcionalidades oldFuncIdOfRolfuncionalidadListRolfuncionalidad = rolfuncionalidadListRolfuncionalidad.getFuncId();
                rolfuncionalidadListRolfuncionalidad.setFuncId(funcionalidades);
                rolfuncionalidadListRolfuncionalidad = em.merge(rolfuncionalidadListRolfuncionalidad);
                if (oldFuncIdOfRolfuncionalidadListRolfuncionalidad != null) {
                    oldFuncIdOfRolfuncionalidadListRolfuncionalidad.getRolfuncionalidadList().remove(rolfuncionalidadListRolfuncionalidad);
                    oldFuncIdOfRolfuncionalidadListRolfuncionalidad = em.merge(oldFuncIdOfRolfuncionalidadListRolfuncionalidad);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionalidades funcionalidades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionalidades persistentFuncionalidades = em.find(Funcionalidades.class, funcionalidades.getFuncId());
            List<Rolfuncionalidad> rolfuncionalidadListOld = persistentFuncionalidades.getRolfuncionalidadList();
            List<Rolfuncionalidad> rolfuncionalidadListNew = funcionalidades.getRolfuncionalidadList();
            List<String> illegalOrphanMessages = null;
            for (Rolfuncionalidad rolfuncionalidadListOldRolfuncionalidad : rolfuncionalidadListOld) {
                if (!rolfuncionalidadListNew.contains(rolfuncionalidadListOldRolfuncionalidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rolfuncionalidad " + rolfuncionalidadListOldRolfuncionalidad + " since its funcId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Rolfuncionalidad> attachedRolfuncionalidadListNew = new ArrayList<Rolfuncionalidad>();
            for (Rolfuncionalidad rolfuncionalidadListNewRolfuncionalidadToAttach : rolfuncionalidadListNew) {
                rolfuncionalidadListNewRolfuncionalidadToAttach = em.getReference(rolfuncionalidadListNewRolfuncionalidadToAttach.getClass(), rolfuncionalidadListNewRolfuncionalidadToAttach.getRofuId());
                attachedRolfuncionalidadListNew.add(rolfuncionalidadListNewRolfuncionalidadToAttach);
            }
            rolfuncionalidadListNew = attachedRolfuncionalidadListNew;
            funcionalidades.setRolfuncionalidadList(rolfuncionalidadListNew);
            funcionalidades = em.merge(funcionalidades);
            for (Rolfuncionalidad rolfuncionalidadListNewRolfuncionalidad : rolfuncionalidadListNew) {
                if (!rolfuncionalidadListOld.contains(rolfuncionalidadListNewRolfuncionalidad)) {
                    Funcionalidades oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad = rolfuncionalidadListNewRolfuncionalidad.getFuncId();
                    rolfuncionalidadListNewRolfuncionalidad.setFuncId(funcionalidades);
                    rolfuncionalidadListNewRolfuncionalidad = em.merge(rolfuncionalidadListNewRolfuncionalidad);
                    if (oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad != null && !oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad.equals(funcionalidades)) {
                        oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad.getRolfuncionalidadList().remove(rolfuncionalidadListNewRolfuncionalidad);
                        oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad = em.merge(oldFuncIdOfRolfuncionalidadListNewRolfuncionalidad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionalidades.getFuncId();
                if (findFuncionalidades(id) == null) {
                    throw new NonexistentEntityException("The funcionalidades with id " + id + " no longer exists.");
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
            Funcionalidades funcionalidades;
            try {
                funcionalidades = em.getReference(Funcionalidades.class, id);
                funcionalidades.getFuncId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionalidades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rolfuncionalidad> rolfuncionalidadListOrphanCheck = funcionalidades.getRolfuncionalidadList();
            for (Rolfuncionalidad rolfuncionalidadListOrphanCheckRolfuncionalidad : rolfuncionalidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionalidades (" + funcionalidades + ") cannot be destroyed since the Rolfuncionalidad " + rolfuncionalidadListOrphanCheckRolfuncionalidad + " in its rolfuncionalidadList field has a non-nullable funcId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(funcionalidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionalidades> findFuncionalidadesEntities() {
        return findFuncionalidadesEntities(true, -1, -1);
    }

    public List<Funcionalidades> findFuncionalidadesEntities(int maxResults, int firstResult) {
        return findFuncionalidadesEntities(false, maxResults, firstResult);
    }

    private List<Funcionalidades> findFuncionalidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionalidades.class));
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

    public Funcionalidades findFuncionalidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionalidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionalidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionalidades> rt = cq.from(Funcionalidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
