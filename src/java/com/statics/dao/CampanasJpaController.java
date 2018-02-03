/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Campanas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Grupo;
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class CampanasJpaController implements Serializable {

    public CampanasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campanas campanas) {
        if (campanas.getPuntoMuestralList() == null) {
            campanas.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupId = campanas.getGrupId();
            if (grupId != null) {
                grupId = em.getReference(grupId.getClass(), grupId.getGrupId());
                campanas.setGrupId(grupId);
            }
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : campanas.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            campanas.setPuntoMuestralList(attachedPuntoMuestralList);
            em.persist(campanas);
            if (grupId != null) {
                grupId.getCampanasList().add(campanas);
                grupId = em.merge(grupId);
            }
            for (PuntoMuestral puntoMuestralListPuntoMuestral : campanas.getPuntoMuestralList()) {
                Campanas oldCampIdOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getCampId();
                puntoMuestralListPuntoMuestral.setCampId(campanas);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldCampIdOfPuntoMuestralListPuntoMuestral != null) {
                    oldCampIdOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldCampIdOfPuntoMuestralListPuntoMuestral = em.merge(oldCampIdOfPuntoMuestralListPuntoMuestral);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campanas campanas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campanas persistentCampanas = em.find(Campanas.class, campanas.getCampId());
            Grupo grupIdOld = persistentCampanas.getGrupId();
            Grupo grupIdNew = campanas.getGrupId();
            List<PuntoMuestral> puntoMuestralListOld = persistentCampanas.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = campanas.getPuntoMuestralList();
            if (grupIdNew != null) {
                grupIdNew = em.getReference(grupIdNew.getClass(), grupIdNew.getGrupId());
                campanas.setGrupId(grupIdNew);
            }
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            campanas.setPuntoMuestralList(puntoMuestralListNew);
            campanas = em.merge(campanas);
            if (grupIdOld != null && !grupIdOld.equals(grupIdNew)) {
                grupIdOld.getCampanasList().remove(campanas);
                grupIdOld = em.merge(grupIdOld);
            }
            if (grupIdNew != null && !grupIdNew.equals(grupIdOld)) {
                grupIdNew.getCampanasList().add(campanas);
                grupIdNew = em.merge(grupIdNew);
            }
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setCampId(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    Campanas oldCampIdOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getCampId();
                    puntoMuestralListNewPuntoMuestral.setCampId(campanas);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldCampIdOfPuntoMuestralListNewPuntoMuestral != null && !oldCampIdOfPuntoMuestralListNewPuntoMuestral.equals(campanas)) {
                        oldCampIdOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldCampIdOfPuntoMuestralListNewPuntoMuestral = em.merge(oldCampIdOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = campanas.getCampId();
                if (findCampanas(id) == null) {
                    throw new NonexistentEntityException("The campanas with id " + id + " no longer exists.");
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
            Campanas campanas;
            try {
                campanas = em.getReference(Campanas.class, id);
                campanas.getCampId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campanas with id " + id + " no longer exists.", enfe);
            }
            Grupo grupId = campanas.getGrupId();
            if (grupId != null) {
                grupId.getCampanasList().remove(campanas);
                grupId = em.merge(grupId);
            }
            List<PuntoMuestral> puntoMuestralList = campanas.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setCampId(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            em.remove(campanas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campanas> findCampanasEntities() {
        return findCampanasEntities(true, -1, -1);
    }

    public List<Campanas> findCampanasEntities(int maxResults, int firstResult) {
        return findCampanasEntities(false, maxResults, firstResult);
    }

    private List<Campanas> findCampanasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campanas.class));
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

    public Campanas findCampanas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campanas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampanasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campanas> rt = cq.from(Campanas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
