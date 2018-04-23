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
import com.statics.vo.Estaciones;
import com.statics.vo.Formatofechas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class FormatofechasJpaController implements Serializable {

    public FormatofechasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formatofechas formatofechas) {
        if (formatofechas.getEstacionesList() == null) {
            formatofechas.setEstacionesList(new ArrayList<Estaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estaciones> attachedEstacionesList = new ArrayList<Estaciones>();
            for (Estaciones estacionesListEstacionesToAttach : formatofechas.getEstacionesList()) {
                estacionesListEstacionesToAttach = em.getReference(estacionesListEstacionesToAttach.getClass(), estacionesListEstacionesToAttach.getEstaId());
                attachedEstacionesList.add(estacionesListEstacionesToAttach);
            }
            formatofechas.setEstacionesList(attachedEstacionesList);
            em.persist(formatofechas);
            for (Estaciones estacionesListEstaciones : formatofechas.getEstacionesList()) {
                Formatofechas oldFofeIdOfEstacionesListEstaciones = estacionesListEstaciones.getFofeId();
                estacionesListEstaciones.setFofeId(formatofechas);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
                if (oldFofeIdOfEstacionesListEstaciones != null) {
                    oldFofeIdOfEstacionesListEstaciones.getEstacionesList().remove(estacionesListEstaciones);
                    oldFofeIdOfEstacionesListEstaciones = em.merge(oldFofeIdOfEstacionesListEstaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formatofechas formatofechas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Formatofechas persistentFormatofechas = em.find(Formatofechas.class, formatofechas.getFofeId());
            List<Estaciones> estacionesListOld = persistentFormatofechas.getEstacionesList();
            List<Estaciones> estacionesListNew = formatofechas.getEstacionesList();
            List<String> illegalOrphanMessages = null;
            for (Estaciones estacionesListOldEstaciones : estacionesListOld) {
                if (!estacionesListNew.contains(estacionesListOldEstaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estaciones " + estacionesListOldEstaciones + " since its fofeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estaciones> attachedEstacionesListNew = new ArrayList<Estaciones>();
            for (Estaciones estacionesListNewEstacionesToAttach : estacionesListNew) {
                estacionesListNewEstacionesToAttach = em.getReference(estacionesListNewEstacionesToAttach.getClass(), estacionesListNewEstacionesToAttach.getEstaId());
                attachedEstacionesListNew.add(estacionesListNewEstacionesToAttach);
            }
            estacionesListNew = attachedEstacionesListNew;
            formatofechas.setEstacionesList(estacionesListNew);
            formatofechas = em.merge(formatofechas);
            for (Estaciones estacionesListNewEstaciones : estacionesListNew) {
                if (!estacionesListOld.contains(estacionesListNewEstaciones)) {
                    Formatofechas oldFofeIdOfEstacionesListNewEstaciones = estacionesListNewEstaciones.getFofeId();
                    estacionesListNewEstaciones.setFofeId(formatofechas);
                    estacionesListNewEstaciones = em.merge(estacionesListNewEstaciones);
                    if (oldFofeIdOfEstacionesListNewEstaciones != null && !oldFofeIdOfEstacionesListNewEstaciones.equals(formatofechas)) {
                        oldFofeIdOfEstacionesListNewEstaciones.getEstacionesList().remove(estacionesListNewEstaciones);
                        oldFofeIdOfEstacionesListNewEstaciones = em.merge(oldFofeIdOfEstacionesListNewEstaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formatofechas.getFofeId();
                if (findFormatofechas(id) == null) {
                    throw new NonexistentEntityException("The formatofechas with id " + id + " no longer exists.");
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
            Formatofechas formatofechas;
            try {
                formatofechas = em.getReference(Formatofechas.class, id);
                formatofechas.getFofeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formatofechas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estaciones> estacionesListOrphanCheck = formatofechas.getEstacionesList();
            for (Estaciones estacionesListOrphanCheckEstaciones : estacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Formatofechas (" + formatofechas + ") cannot be destroyed since the Estaciones " + estacionesListOrphanCheckEstaciones + " in its estacionesList field has a non-nullable fofeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formatofechas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formatofechas> findFormatofechasEntities() {
        return findFormatofechasEntities(true, -1, -1);
    }

    public List<Formatofechas> findFormatofechasEntities(int maxResults, int firstResult) {
        return findFormatofechasEntities(false, maxResults, firstResult);
    }

    private List<Formatofechas> findFormatofechasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formatofechas.class));
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

    public Formatofechas findFormatofechas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formatofechas.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormatofechasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formatofechas> rt = cq.from(Formatofechas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
