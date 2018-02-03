/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.IllegalOrphanException;
import com.statics.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Estados;
import com.statics.vo.TipoEstado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class TipoEstadoJpaController implements Serializable {

    public TipoEstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEstado tipoEstado) {
        if (tipoEstado.getEstadosList() == null) {
            tipoEstado.setEstadosList(new ArrayList<Estados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estados> attachedEstadosList = new ArrayList<Estados>();
            for (Estados estadosListEstadosToAttach : tipoEstado.getEstadosList()) {
                estadosListEstadosToAttach = em.getReference(estadosListEstadosToAttach.getClass(), estadosListEstadosToAttach.getEstaId());
                attachedEstadosList.add(estadosListEstadosToAttach);
            }
            tipoEstado.setEstadosList(attachedEstadosList);
            em.persist(tipoEstado);
            for (Estados estadosListEstados : tipoEstado.getEstadosList()) {
                TipoEstado oldTiesIdOfEstadosListEstados = estadosListEstados.getTiesId();
                estadosListEstados.setTiesId(tipoEstado);
                estadosListEstados = em.merge(estadosListEstados);
                if (oldTiesIdOfEstadosListEstados != null) {
                    oldTiesIdOfEstadosListEstados.getEstadosList().remove(estadosListEstados);
                    oldTiesIdOfEstadosListEstados = em.merge(oldTiesIdOfEstadosListEstados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEstado tipoEstado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstado persistentTipoEstado = em.find(TipoEstado.class, tipoEstado.getTiesId());
            List<Estados> estadosListOld = persistentTipoEstado.getEstadosList();
            List<Estados> estadosListNew = tipoEstado.getEstadosList();
            List<String> illegalOrphanMessages = null;
            for (Estados estadosListOldEstados : estadosListOld) {
                if (!estadosListNew.contains(estadosListOldEstados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estados " + estadosListOldEstados + " since its tiesId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estados> attachedEstadosListNew = new ArrayList<Estados>();
            for (Estados estadosListNewEstadosToAttach : estadosListNew) {
                estadosListNewEstadosToAttach = em.getReference(estadosListNewEstadosToAttach.getClass(), estadosListNewEstadosToAttach.getEstaId());
                attachedEstadosListNew.add(estadosListNewEstadosToAttach);
            }
            estadosListNew = attachedEstadosListNew;
            tipoEstado.setEstadosList(estadosListNew);
            tipoEstado = em.merge(tipoEstado);
            for (Estados estadosListNewEstados : estadosListNew) {
                if (!estadosListOld.contains(estadosListNewEstados)) {
                    TipoEstado oldTiesIdOfEstadosListNewEstados = estadosListNewEstados.getTiesId();
                    estadosListNewEstados.setTiesId(tipoEstado);
                    estadosListNewEstados = em.merge(estadosListNewEstados);
                    if (oldTiesIdOfEstadosListNewEstados != null && !oldTiesIdOfEstadosListNewEstados.equals(tipoEstado)) {
                        oldTiesIdOfEstadosListNewEstados.getEstadosList().remove(estadosListNewEstados);
                        oldTiesIdOfEstadosListNewEstados = em.merge(oldTiesIdOfEstadosListNewEstados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEstado.getTiesId();
                if (findTipoEstado(id) == null) {
                    throw new NonexistentEntityException("The tipoEstado with id " + id + " no longer exists.");
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
            TipoEstado tipoEstado;
            try {
                tipoEstado = em.getReference(TipoEstado.class, id);
                tipoEstado.getTiesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEstado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estados> estadosListOrphanCheck = tipoEstado.getEstadosList();
            for (Estados estadosListOrphanCheckEstados : estadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoEstado (" + tipoEstado + ") cannot be destroyed since the Estados " + estadosListOrphanCheckEstados + " in its estadosList field has a non-nullable tiesId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoEstado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEstado> findTipoEstadoEntities() {
        return findTipoEstadoEntities(true, -1, -1);
    }

    public List<TipoEstado> findTipoEstadoEntities(int maxResults, int firstResult) {
        return findTipoEstadoEntities(false, maxResults, firstResult);
    }

    private List<TipoEstado> findTipoEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEstado.class));
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

    public TipoEstado findTipoEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEstado> rt = cq.from(TipoEstado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
