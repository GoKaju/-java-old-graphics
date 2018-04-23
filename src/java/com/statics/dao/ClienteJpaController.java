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
import com.statics.vo.Campanas;
import com.statics.vo.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getCampanasList() == null) {
            cliente.setCampanasList(new ArrayList<Campanas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Campanas> attachedCampanasList = new ArrayList<Campanas>();
            for (Campanas campanasListCampanasToAttach : cliente.getCampanasList()) {
                campanasListCampanasToAttach = em.getReference(campanasListCampanasToAttach.getClass(), campanasListCampanasToAttach.getCampId());
                attachedCampanasList.add(campanasListCampanasToAttach);
            }
            cliente.setCampanasList(attachedCampanasList);
            em.persist(cliente);
            for (Campanas campanasListCampanas : cliente.getCampanasList()) {
                Cliente oldIdClienteOfCampanasListCampanas = campanasListCampanas.getIdCliente();
                campanasListCampanas.setIdCliente(cliente);
                campanasListCampanas = em.merge(campanasListCampanas);
                if (oldIdClienteOfCampanasListCampanas != null) {
                    oldIdClienteOfCampanasListCampanas.getCampanasList().remove(campanasListCampanas);
                    oldIdClienteOfCampanasListCampanas = em.merge(oldIdClienteOfCampanasListCampanas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<Campanas> campanasListOld = persistentCliente.getCampanasList();
            List<Campanas> campanasListNew = cliente.getCampanasList();
            List<Campanas> attachedCampanasListNew = new ArrayList<Campanas>();
            for (Campanas campanasListNewCampanasToAttach : campanasListNew) {
                campanasListNewCampanasToAttach = em.getReference(campanasListNewCampanasToAttach.getClass(), campanasListNewCampanasToAttach.getCampId());
                attachedCampanasListNew.add(campanasListNewCampanasToAttach);
            }
            campanasListNew = attachedCampanasListNew;
            cliente.setCampanasList(campanasListNew);
            cliente = em.merge(cliente);
            for (Campanas campanasListOldCampanas : campanasListOld) {
                if (!campanasListNew.contains(campanasListOldCampanas)) {
                    campanasListOldCampanas.setIdCliente(null);
                    campanasListOldCampanas = em.merge(campanasListOldCampanas);
                }
            }
            for (Campanas campanasListNewCampanas : campanasListNew) {
                if (!campanasListOld.contains(campanasListNewCampanas)) {
                    Cliente oldIdClienteOfCampanasListNewCampanas = campanasListNewCampanas.getIdCliente();
                    campanasListNewCampanas.setIdCliente(cliente);
                    campanasListNewCampanas = em.merge(campanasListNewCampanas);
                    if (oldIdClienteOfCampanasListNewCampanas != null && !oldIdClienteOfCampanasListNewCampanas.equals(cliente)) {
                        oldIdClienteOfCampanasListNewCampanas.getCampanasList().remove(campanasListNewCampanas);
                        oldIdClienteOfCampanasListNewCampanas = em.merge(oldIdClienteOfCampanasListNewCampanas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Campanas> campanasList = cliente.getCampanasList();
            for (Campanas campanasListCampanas : campanasList) {
                campanasListCampanas.setIdCliente(null);
                campanasListCampanas = em.merge(campanasListCampanas);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
