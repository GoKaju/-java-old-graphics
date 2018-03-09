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
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.ItemLogistica;
import com.statics.vo.LogisticaPm;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class LogisticaPmJpaController1 implements Serializable {

    public LogisticaPmJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogisticaPm logisticaPm) {
        if (logisticaPm.getPuntoMuestralList() == null) {
            logisticaPm.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        if (logisticaPm.getItemLogisticaList() == null) {
            logisticaPm.setItemLogisticaList(new ArrayList<ItemLogistica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : logisticaPm.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            logisticaPm.setPuntoMuestralList(attachedPuntoMuestralList);
            List<ItemLogistica> attachedItemLogisticaList = new ArrayList<ItemLogistica>();
            for (ItemLogistica itemLogisticaListItemLogisticaToAttach : logisticaPm.getItemLogisticaList()) {
                itemLogisticaListItemLogisticaToAttach = em.getReference(itemLogisticaListItemLogisticaToAttach.getClass(), itemLogisticaListItemLogisticaToAttach.getId());
                attachedItemLogisticaList.add(itemLogisticaListItemLogisticaToAttach);
            }
            logisticaPm.setItemLogisticaList(attachedItemLogisticaList);
            em.persist(logisticaPm);
            for (PuntoMuestral puntoMuestralListPuntoMuestral : logisticaPm.getPuntoMuestralList()) {
                LogisticaPm oldIdLogisticaOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getIdLogistica();
                puntoMuestralListPuntoMuestral.setIdLogistica(logisticaPm);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldIdLogisticaOfPuntoMuestralListPuntoMuestral != null) {
                    oldIdLogisticaOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldIdLogisticaOfPuntoMuestralListPuntoMuestral = em.merge(oldIdLogisticaOfPuntoMuestralListPuntoMuestral);
                }
            }
            for (ItemLogistica itemLogisticaListItemLogistica : logisticaPm.getItemLogisticaList()) {
                LogisticaPm oldIdLogisticaOfItemLogisticaListItemLogistica = itemLogisticaListItemLogistica.getIdLogistica();
                itemLogisticaListItemLogistica.setIdLogistica(logisticaPm);
                itemLogisticaListItemLogistica = em.merge(itemLogisticaListItemLogistica);
                if (oldIdLogisticaOfItemLogisticaListItemLogistica != null) {
                    oldIdLogisticaOfItemLogisticaListItemLogistica.getItemLogisticaList().remove(itemLogisticaListItemLogistica);
                    oldIdLogisticaOfItemLogisticaListItemLogistica = em.merge(oldIdLogisticaOfItemLogisticaListItemLogistica);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogisticaPm logisticaPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LogisticaPm persistentLogisticaPm = em.find(LogisticaPm.class, logisticaPm.getId());
            List<PuntoMuestral> puntoMuestralListOld = persistentLogisticaPm.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = logisticaPm.getPuntoMuestralList();
            List<ItemLogistica> itemLogisticaListOld = persistentLogisticaPm.getItemLogisticaList();
            List<ItemLogistica> itemLogisticaListNew = logisticaPm.getItemLogisticaList();
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            logisticaPm.setPuntoMuestralList(puntoMuestralListNew);
            List<ItemLogistica> attachedItemLogisticaListNew = new ArrayList<ItemLogistica>();
            for (ItemLogistica itemLogisticaListNewItemLogisticaToAttach : itemLogisticaListNew) {
                itemLogisticaListNewItemLogisticaToAttach = em.getReference(itemLogisticaListNewItemLogisticaToAttach.getClass(), itemLogisticaListNewItemLogisticaToAttach.getId());
                attachedItemLogisticaListNew.add(itemLogisticaListNewItemLogisticaToAttach);
            }
            itemLogisticaListNew = attachedItemLogisticaListNew;
            logisticaPm.setItemLogisticaList(itemLogisticaListNew);
            logisticaPm = em.merge(logisticaPm);
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setIdLogistica(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    LogisticaPm oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getIdLogistica();
                    puntoMuestralListNewPuntoMuestral.setIdLogistica(logisticaPm);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral != null && !oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral.equals(logisticaPm)) {
                        oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral = em.merge(oldIdLogisticaOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            for (ItemLogistica itemLogisticaListOldItemLogistica : itemLogisticaListOld) {
                if (!itemLogisticaListNew.contains(itemLogisticaListOldItemLogistica)) {
                    itemLogisticaListOldItemLogistica.setIdLogistica(null);
                    itemLogisticaListOldItemLogistica = em.merge(itemLogisticaListOldItemLogistica);
                }
            }
            for (ItemLogistica itemLogisticaListNewItemLogistica : itemLogisticaListNew) {
                if (!itemLogisticaListOld.contains(itemLogisticaListNewItemLogistica)) {
                    LogisticaPm oldIdLogisticaOfItemLogisticaListNewItemLogistica = itemLogisticaListNewItemLogistica.getIdLogistica();
                    itemLogisticaListNewItemLogistica.setIdLogistica(logisticaPm);
                    itemLogisticaListNewItemLogistica = em.merge(itemLogisticaListNewItemLogistica);
                    if (oldIdLogisticaOfItemLogisticaListNewItemLogistica != null && !oldIdLogisticaOfItemLogisticaListNewItemLogistica.equals(logisticaPm)) {
                        oldIdLogisticaOfItemLogisticaListNewItemLogistica.getItemLogisticaList().remove(itemLogisticaListNewItemLogistica);
                        oldIdLogisticaOfItemLogisticaListNewItemLogistica = em.merge(oldIdLogisticaOfItemLogisticaListNewItemLogistica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logisticaPm.getId();
                if (findLogisticaPm(id) == null) {
                    throw new NonexistentEntityException("The logisticaPm with id " + id + " no longer exists.");
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
            LogisticaPm logisticaPm;
            try {
                logisticaPm = em.getReference(LogisticaPm.class, id);
                logisticaPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logisticaPm with id " + id + " no longer exists.", enfe);
            }
            List<PuntoMuestral> puntoMuestralList = logisticaPm.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setIdLogistica(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            List<ItemLogistica> itemLogisticaList = logisticaPm.getItemLogisticaList();
            for (ItemLogistica itemLogisticaListItemLogistica : itemLogisticaList) {
                itemLogisticaListItemLogistica.setIdLogistica(null);
                itemLogisticaListItemLogistica = em.merge(itemLogisticaListItemLogistica);
            }
            em.remove(logisticaPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogisticaPm> findLogisticaPmEntities() {
        return findLogisticaPmEntities(true, -1, -1);
    }

    public List<LogisticaPm> findLogisticaPmEntities(int maxResults, int firstResult) {
        return findLogisticaPmEntities(false, maxResults, firstResult);
    }

    private List<LogisticaPm> findLogisticaPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogisticaPm.class));
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

    public LogisticaPm findLogisticaPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogisticaPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogisticaPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogisticaPm> rt = cq.from(LogisticaPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
