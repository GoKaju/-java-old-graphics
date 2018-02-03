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
import com.statics.vo.Estaciones;
import com.statics.vo.Separadores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class SeparadoresJpaController implements Serializable {

    public SeparadoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Separadores separadores) {
        if (separadores.getEstacionesList() == null) {
            separadores.setEstacionesList(new ArrayList<Estaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estaciones> attachedEstacionesList = new ArrayList<Estaciones>();
            for (Estaciones estacionesListEstacionesToAttach : separadores.getEstacionesList()) {
                estacionesListEstacionesToAttach = em.getReference(estacionesListEstacionesToAttach.getClass(), estacionesListEstacionesToAttach.getEstaId());
                attachedEstacionesList.add(estacionesListEstacionesToAttach);
            }
            separadores.setEstacionesList(attachedEstacionesList);
            em.persist(separadores);
            for (Estaciones estacionesListEstaciones : separadores.getEstacionesList()) {
                Separadores oldSepaIdOfEstacionesListEstaciones = estacionesListEstaciones.getSepaId();
                estacionesListEstaciones.setSepaId(separadores);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
                if (oldSepaIdOfEstacionesListEstaciones != null) {
                    oldSepaIdOfEstacionesListEstaciones.getEstacionesList().remove(estacionesListEstaciones);
                    oldSepaIdOfEstacionesListEstaciones = em.merge(oldSepaIdOfEstacionesListEstaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Separadores separadores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Separadores persistentSeparadores = em.find(Separadores.class, separadores.getSepaId());
            List<Estaciones> estacionesListOld = persistentSeparadores.getEstacionesList();
            List<Estaciones> estacionesListNew = separadores.getEstacionesList();
            List<Estaciones> attachedEstacionesListNew = new ArrayList<Estaciones>();
            for (Estaciones estacionesListNewEstacionesToAttach : estacionesListNew) {
                estacionesListNewEstacionesToAttach = em.getReference(estacionesListNewEstacionesToAttach.getClass(), estacionesListNewEstacionesToAttach.getEstaId());
                attachedEstacionesListNew.add(estacionesListNewEstacionesToAttach);
            }
            estacionesListNew = attachedEstacionesListNew;
            separadores.setEstacionesList(estacionesListNew);
            separadores = em.merge(separadores);
            for (Estaciones estacionesListOldEstaciones : estacionesListOld) {
                if (!estacionesListNew.contains(estacionesListOldEstaciones)) {
                    estacionesListOldEstaciones.setSepaId(null);
                    estacionesListOldEstaciones = em.merge(estacionesListOldEstaciones);
                }
            }
            for (Estaciones estacionesListNewEstaciones : estacionesListNew) {
                if (!estacionesListOld.contains(estacionesListNewEstaciones)) {
                    Separadores oldSepaIdOfEstacionesListNewEstaciones = estacionesListNewEstaciones.getSepaId();
                    estacionesListNewEstaciones.setSepaId(separadores);
                    estacionesListNewEstaciones = em.merge(estacionesListNewEstaciones);
                    if (oldSepaIdOfEstacionesListNewEstaciones != null && !oldSepaIdOfEstacionesListNewEstaciones.equals(separadores)) {
                        oldSepaIdOfEstacionesListNewEstaciones.getEstacionesList().remove(estacionesListNewEstaciones);
                        oldSepaIdOfEstacionesListNewEstaciones = em.merge(oldSepaIdOfEstacionesListNewEstaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = separadores.getSepaId();
                if (findSeparadores(id) == null) {
                    throw new NonexistentEntityException("The separadores with id " + id + " no longer exists.");
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
            Separadores separadores;
            try {
                separadores = em.getReference(Separadores.class, id);
                separadores.getSepaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The separadores with id " + id + " no longer exists.", enfe);
            }
            List<Estaciones> estacionesList = separadores.getEstacionesList();
            for (Estaciones estacionesListEstaciones : estacionesList) {
                estacionesListEstaciones.setSepaId(null);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
            }
            em.remove(separadores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Separadores> findSeparadoresEntities() {
        return findSeparadoresEntities(true, -1, -1);
    }

    public List<Separadores> findSeparadoresEntities(int maxResults, int firstResult) {
        return findSeparadoresEntities(false, maxResults, firstResult);
    }

    private List<Separadores> findSeparadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Separadores.class));
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

    public Separadores findSeparadores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Separadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeparadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Separadores> rt = cq.from(Separadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
