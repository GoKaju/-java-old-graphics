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
import com.statics.vo.Campanas;
import com.statics.vo.Cargas;
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class PuntoMuestralJpaController implements Serializable {

    public PuntoMuestralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuntoMuestral puntoMuestral) {
        if (puntoMuestral.getCargasList() == null) {
            puntoMuestral.setCargasList(new ArrayList<Cargas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estaciones estaId = puntoMuestral.getEstaId();
            if (estaId != null) {
                estaId = em.getReference(estaId.getClass(), estaId.getEstaId());
                puntoMuestral.setEstaId(estaId);
            }
            Campanas campId = puntoMuestral.getCampId();
            if (campId != null) {
                campId = em.getReference(campId.getClass(), campId.getCampId());
                puntoMuestral.setCampId(campId);
            }
            List<Cargas> attachedCargasList = new ArrayList<Cargas>();
            for (Cargas cargasListCargasToAttach : puntoMuestral.getCargasList()) {
                cargasListCargasToAttach = em.getReference(cargasListCargasToAttach.getClass(), cargasListCargasToAttach.getCargId());
                attachedCargasList.add(cargasListCargasToAttach);
            }
            puntoMuestral.setCargasList(attachedCargasList);
            em.persist(puntoMuestral);
            if (estaId != null) {
                estaId.getPuntoMuestralList().add(puntoMuestral);
                estaId = em.merge(estaId);
            }
            if (campId != null) {
                campId.getPuntoMuestralList().add(puntoMuestral);
                campId = em.merge(campId);
            }
            for (Cargas cargasListCargas : puntoMuestral.getCargasList()) {
                PuntoMuestral oldPumuIdOfCargasListCargas = cargasListCargas.getPumuId();
                cargasListCargas.setPumuId(puntoMuestral);
                cargasListCargas = em.merge(cargasListCargas);
                if (oldPumuIdOfCargasListCargas != null) {
                    oldPumuIdOfCargasListCargas.getCargasList().remove(cargasListCargas);
                    oldPumuIdOfCargasListCargas = em.merge(oldPumuIdOfCargasListCargas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuntoMuestral puntoMuestral) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoMuestral persistentPuntoMuestral = em.find(PuntoMuestral.class, puntoMuestral.getPumuId());
            Estaciones estaIdOld = persistentPuntoMuestral.getEstaId();
            Estaciones estaIdNew = puntoMuestral.getEstaId();
            Campanas campIdOld = persistentPuntoMuestral.getCampId();
            Campanas campIdNew = puntoMuestral.getCampId();
            List<Cargas> cargasListOld = persistentPuntoMuestral.getCargasList();
            List<Cargas> cargasListNew = puntoMuestral.getCargasList();
            List<String> illegalOrphanMessages = null;
            for (Cargas cargasListOldCargas : cargasListOld) {
                if (!cargasListNew.contains(cargasListOldCargas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cargas " + cargasListOldCargas + " since its pumuId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estaIdNew != null) {
                estaIdNew = em.getReference(estaIdNew.getClass(), estaIdNew.getEstaId());
                puntoMuestral.setEstaId(estaIdNew);
            }
            if (campIdNew != null) {
                campIdNew = em.getReference(campIdNew.getClass(), campIdNew.getCampId());
                puntoMuestral.setCampId(campIdNew);
            }
            List<Cargas> attachedCargasListNew = new ArrayList<Cargas>();
            for (Cargas cargasListNewCargasToAttach : cargasListNew) {
                cargasListNewCargasToAttach = em.getReference(cargasListNewCargasToAttach.getClass(), cargasListNewCargasToAttach.getCargId());
                attachedCargasListNew.add(cargasListNewCargasToAttach);
            }
            cargasListNew = attachedCargasListNew;
            puntoMuestral.setCargasList(cargasListNew);
            puntoMuestral = em.merge(puntoMuestral);
            if (estaIdOld != null && !estaIdOld.equals(estaIdNew)) {
                estaIdOld.getPuntoMuestralList().remove(puntoMuestral);
                estaIdOld = em.merge(estaIdOld);
            }
            if (estaIdNew != null && !estaIdNew.equals(estaIdOld)) {
                estaIdNew.getPuntoMuestralList().add(puntoMuestral);
                estaIdNew = em.merge(estaIdNew);
            }
            if (campIdOld != null && !campIdOld.equals(campIdNew)) {
                campIdOld.getPuntoMuestralList().remove(puntoMuestral);
                campIdOld = em.merge(campIdOld);
            }
            if (campIdNew != null && !campIdNew.equals(campIdOld)) {
                campIdNew.getPuntoMuestralList().add(puntoMuestral);
                campIdNew = em.merge(campIdNew);
            }
            for (Cargas cargasListNewCargas : cargasListNew) {
                if (!cargasListOld.contains(cargasListNewCargas)) {
                    PuntoMuestral oldPumuIdOfCargasListNewCargas = cargasListNewCargas.getPumuId();
                    cargasListNewCargas.setPumuId(puntoMuestral);
                    cargasListNewCargas = em.merge(cargasListNewCargas);
                    if (oldPumuIdOfCargasListNewCargas != null && !oldPumuIdOfCargasListNewCargas.equals(puntoMuestral)) {
                        oldPumuIdOfCargasListNewCargas.getCargasList().remove(cargasListNewCargas);
                        oldPumuIdOfCargasListNewCargas = em.merge(oldPumuIdOfCargasListNewCargas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puntoMuestral.getPumuId();
                if (findPuntoMuestral(id) == null) {
                    throw new NonexistentEntityException("The puntoMuestral with id " + id + " no longer exists.");
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
            PuntoMuestral puntoMuestral;
            try {
                puntoMuestral = em.getReference(PuntoMuestral.class, id);
                puntoMuestral.getPumuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntoMuestral with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cargas> cargasListOrphanCheck = puntoMuestral.getCargasList();
            for (Cargas cargasListOrphanCheckCargas : cargasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PuntoMuestral (" + puntoMuestral + ") cannot be destroyed since the Cargas " + cargasListOrphanCheckCargas + " in its cargasList field has a non-nullable pumuId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estaciones estaId = puntoMuestral.getEstaId();
            if (estaId != null) {
                estaId.getPuntoMuestralList().remove(puntoMuestral);
                estaId = em.merge(estaId);
            }
            Campanas campId = puntoMuestral.getCampId();
            if (campId != null) {
                campId.getPuntoMuestralList().remove(puntoMuestral);
                campId = em.merge(campId);
            }
            em.remove(puntoMuestral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuntoMuestral> findPuntoMuestralEntities() {
        return findPuntoMuestralEntities(true, -1, -1);
    }

    public List<PuntoMuestral> findPuntoMuestralEntities(int maxResults, int firstResult) {
        return findPuntoMuestralEntities(false, maxResults, firstResult);
    }

    private List<PuntoMuestral> findPuntoMuestralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PuntoMuestral.class));
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

    public PuntoMuestral findPuntoMuestral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuntoMuestral.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntoMuestralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PuntoMuestral> rt = cq.from(PuntoMuestral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
