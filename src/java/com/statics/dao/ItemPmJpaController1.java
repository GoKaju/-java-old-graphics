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
import com.statics.vo.ItemLogistica;
import com.statics.vo.ItemPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ItemPmJpaController1 implements Serializable {

    public ItemPmJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemPm itemPm) {
        if (itemPm.getItemLogisticaList() == null) {
            itemPm.setItemLogisticaList(new ArrayList<ItemLogistica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ItemLogistica> attachedItemLogisticaList = new ArrayList<ItemLogistica>();
            for (ItemLogistica itemLogisticaListItemLogisticaToAttach : itemPm.getItemLogisticaList()) {
                itemLogisticaListItemLogisticaToAttach = em.getReference(itemLogisticaListItemLogisticaToAttach.getClass(), itemLogisticaListItemLogisticaToAttach.getId());
                attachedItemLogisticaList.add(itemLogisticaListItemLogisticaToAttach);
            }
            itemPm.setItemLogisticaList(attachedItemLogisticaList);
            em.persist(itemPm);
            for (ItemLogistica itemLogisticaListItemLogistica : itemPm.getItemLogisticaList()) {
                ItemPm oldIdItemOfItemLogisticaListItemLogistica = itemLogisticaListItemLogistica.getIdItem();
                itemLogisticaListItemLogistica.setIdItem(itemPm);
                itemLogisticaListItemLogistica = em.merge(itemLogisticaListItemLogistica);
                if (oldIdItemOfItemLogisticaListItemLogistica != null) {
                    oldIdItemOfItemLogisticaListItemLogistica.getItemLogisticaList().remove(itemLogisticaListItemLogistica);
                    oldIdItemOfItemLogisticaListItemLogistica = em.merge(oldIdItemOfItemLogisticaListItemLogistica);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemPm itemPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPm persistentItemPm = em.find(ItemPm.class, itemPm.getId());
            List<ItemLogistica> itemLogisticaListOld = persistentItemPm.getItemLogisticaList();
            List<ItemLogistica> itemLogisticaListNew = itemPm.getItemLogisticaList();
            List<ItemLogistica> attachedItemLogisticaListNew = new ArrayList<ItemLogistica>();
            for (ItemLogistica itemLogisticaListNewItemLogisticaToAttach : itemLogisticaListNew) {
                itemLogisticaListNewItemLogisticaToAttach = em.getReference(itemLogisticaListNewItemLogisticaToAttach.getClass(), itemLogisticaListNewItemLogisticaToAttach.getId());
                attachedItemLogisticaListNew.add(itemLogisticaListNewItemLogisticaToAttach);
            }
            itemLogisticaListNew = attachedItemLogisticaListNew;
            itemPm.setItemLogisticaList(itemLogisticaListNew);
            itemPm = em.merge(itemPm);
            for (ItemLogistica itemLogisticaListOldItemLogistica : itemLogisticaListOld) {
                if (!itemLogisticaListNew.contains(itemLogisticaListOldItemLogistica)) {
                    itemLogisticaListOldItemLogistica.setIdItem(null);
                    itemLogisticaListOldItemLogistica = em.merge(itemLogisticaListOldItemLogistica);
                }
            }
            for (ItemLogistica itemLogisticaListNewItemLogistica : itemLogisticaListNew) {
                if (!itemLogisticaListOld.contains(itemLogisticaListNewItemLogistica)) {
                    ItemPm oldIdItemOfItemLogisticaListNewItemLogistica = itemLogisticaListNewItemLogistica.getIdItem();
                    itemLogisticaListNewItemLogistica.setIdItem(itemPm);
                    itemLogisticaListNewItemLogistica = em.merge(itemLogisticaListNewItemLogistica);
                    if (oldIdItemOfItemLogisticaListNewItemLogistica != null && !oldIdItemOfItemLogisticaListNewItemLogistica.equals(itemPm)) {
                        oldIdItemOfItemLogisticaListNewItemLogistica.getItemLogisticaList().remove(itemLogisticaListNewItemLogistica);
                        oldIdItemOfItemLogisticaListNewItemLogistica = em.merge(oldIdItemOfItemLogisticaListNewItemLogistica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemPm.getId();
                if (findItemPm(id) == null) {
                    throw new NonexistentEntityException("The itemPm with id " + id + " no longer exists.");
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
            ItemPm itemPm;
            try {
                itemPm = em.getReference(ItemPm.class, id);
                itemPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemPm with id " + id + " no longer exists.", enfe);
            }
            List<ItemLogistica> itemLogisticaList = itemPm.getItemLogisticaList();
            for (ItemLogistica itemLogisticaListItemLogistica : itemLogisticaList) {
                itemLogisticaListItemLogistica.setIdItem(null);
                itemLogisticaListItemLogistica = em.merge(itemLogisticaListItemLogistica);
            }
            em.remove(itemPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemPm> findItemPmEntities() {
        return findItemPmEntities(true, -1, -1);
    }

    public List<ItemPm> findItemPmEntities(int maxResults, int firstResult) {
        return findItemPmEntities(false, maxResults, firstResult);
    }

    private List<ItemPm> findItemPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemPm.class));
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

    public ItemPm findItemPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemPm> rt = cq.from(ItemPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
