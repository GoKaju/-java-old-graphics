/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.ItemLogistica;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.ItemPm;
import com.statics.vo.LogisticaPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ItemLogisticaJpaController implements Serializable {

    public ItemLogisticaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
        public List<ItemLogistica> findItemLogisticaByLogistica(int idLogistica) {
        String sqlQuery="SELECT * FROM item_logistica WHERE id_logistica="+idLogistica;
        List<ItemLogistica> lista=new ArrayList();
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q=em.createNativeQuery(sqlQuery, ItemLogistica.class);
            lista=q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }


    public void create(ItemLogistica itemLogistica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPm idItem = itemLogistica.getIdItem();
            if (idItem != null) {
                idItem = em.getReference(idItem.getClass(), idItem.getId());
                itemLogistica.setIdItem(idItem);
            }
            LogisticaPm idLogistica = itemLogistica.getIdLogistica();
            if (idLogistica != null) {
                idLogistica = em.getReference(idLogistica.getClass(), idLogistica.getId());
                itemLogistica.setIdLogistica(idLogistica);
            }
            em.persist(itemLogistica);
            if (idItem != null) {
                idItem.getItemLogisticaList().add(itemLogistica);
                idItem = em.merge(idItem);
            }
            if (idLogistica != null) {
                idLogistica.getItemLogisticaList().add(itemLogistica);
                idLogistica = em.merge(idLogistica);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemLogistica itemLogistica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemLogistica persistentItemLogistica = em.find(ItemLogistica.class, itemLogistica.getId());
            ItemPm idItemOld = persistentItemLogistica.getIdItem();
            ItemPm idItemNew = itemLogistica.getIdItem();
            LogisticaPm idLogisticaOld = persistentItemLogistica.getIdLogistica();
            LogisticaPm idLogisticaNew = itemLogistica.getIdLogistica();
            if (idItemNew != null) {
                idItemNew = em.getReference(idItemNew.getClass(), idItemNew.getId());
                itemLogistica.setIdItem(idItemNew);
            }
            if (idLogisticaNew != null) {
                idLogisticaNew = em.getReference(idLogisticaNew.getClass(), idLogisticaNew.getId());
                itemLogistica.setIdLogistica(idLogisticaNew);
            }
            itemLogistica = em.merge(itemLogistica);
            if (idItemOld != null && !idItemOld.equals(idItemNew)) {
                idItemOld.getItemLogisticaList().remove(itemLogistica);
                idItemOld = em.merge(idItemOld);
            }
            if (idItemNew != null && !idItemNew.equals(idItemOld)) {
                idItemNew.getItemLogisticaList().add(itemLogistica);
                idItemNew = em.merge(idItemNew);
            }
            if (idLogisticaOld != null && !idLogisticaOld.equals(idLogisticaNew)) {
                idLogisticaOld.getItemLogisticaList().remove(itemLogistica);
                idLogisticaOld = em.merge(idLogisticaOld);
            }
            if (idLogisticaNew != null && !idLogisticaNew.equals(idLogisticaOld)) {
                idLogisticaNew.getItemLogisticaList().add(itemLogistica);
                idLogisticaNew = em.merge(idLogisticaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemLogistica.getId();
                if (findItemLogistica(id) == null) {
                    throw new NonexistentEntityException("The itemLogistica with id " + id + " no longer exists.");
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
            ItemLogistica itemLogistica;
            try {
                itemLogistica = em.getReference(ItemLogistica.class, id);
                itemLogistica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemLogistica with id " + id + " no longer exists.", enfe);
            }
            ItemPm idItem = itemLogistica.getIdItem();
            if (idItem != null) {
                idItem.getItemLogisticaList().remove(itemLogistica);
                idItem = em.merge(idItem);
            }
            LogisticaPm idLogistica = itemLogistica.getIdLogistica();
            if (idLogistica != null) {
                idLogistica.getItemLogisticaList().remove(itemLogistica);
                idLogistica = em.merge(idLogistica);
            }
            em.remove(itemLogistica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemLogistica> findItemLogisticaEntities() {
        return findItemLogisticaEntities(true, -1, -1);
    }

    public List<ItemLogistica> findItemLogisticaEntities(int maxResults, int firstResult) {
        return findItemLogisticaEntities(false, maxResults, firstResult);
    }

    private List<ItemLogistica> findItemLogisticaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemLogistica.class));
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

    public ItemLogistica findItemLogistica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemLogistica.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemLogisticaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemLogistica> rt = cq.from(ItemLogistica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
