/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.UbicacionPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) {
        if (departamento.getUbicacionPmList() == null) {
            departamento.setUbicacionPmList(new ArrayList<UbicacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UbicacionPm> attachedUbicacionPmList = new ArrayList<UbicacionPm>();
            for (UbicacionPm ubicacionPmListUbicacionPmToAttach : departamento.getUbicacionPmList()) {
                ubicacionPmListUbicacionPmToAttach = em.getReference(ubicacionPmListUbicacionPmToAttach.getClass(), ubicacionPmListUbicacionPmToAttach.getId());
                attachedUbicacionPmList.add(ubicacionPmListUbicacionPmToAttach);
            }
            departamento.setUbicacionPmList(attachedUbicacionPmList);
            em.persist(departamento);
            for (UbicacionPm ubicacionPmListUbicacionPm : departamento.getUbicacionPmList()) {
                Departamento oldIdDepartamentoOfUbicacionPmListUbicacionPm = ubicacionPmListUbicacionPm.getIdDepartamento();
                ubicacionPmListUbicacionPm.setIdDepartamento(departamento);
                ubicacionPmListUbicacionPm = em.merge(ubicacionPmListUbicacionPm);
                if (oldIdDepartamentoOfUbicacionPmListUbicacionPm != null) {
                    oldIdDepartamentoOfUbicacionPmListUbicacionPm.getUbicacionPmList().remove(ubicacionPmListUbicacionPm);
                    oldIdDepartamentoOfUbicacionPmListUbicacionPm = em.merge(oldIdDepartamentoOfUbicacionPmListUbicacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getId());
            List<UbicacionPm> ubicacionPmListOld = persistentDepartamento.getUbicacionPmList();
            List<UbicacionPm> ubicacionPmListNew = departamento.getUbicacionPmList();
            List<UbicacionPm> attachedUbicacionPmListNew = new ArrayList<UbicacionPm>();
            for (UbicacionPm ubicacionPmListNewUbicacionPmToAttach : ubicacionPmListNew) {
                ubicacionPmListNewUbicacionPmToAttach = em.getReference(ubicacionPmListNewUbicacionPmToAttach.getClass(), ubicacionPmListNewUbicacionPmToAttach.getId());
                attachedUbicacionPmListNew.add(ubicacionPmListNewUbicacionPmToAttach);
            }
            ubicacionPmListNew = attachedUbicacionPmListNew;
            departamento.setUbicacionPmList(ubicacionPmListNew);
            departamento = em.merge(departamento);
            for (UbicacionPm ubicacionPmListOldUbicacionPm : ubicacionPmListOld) {
                if (!ubicacionPmListNew.contains(ubicacionPmListOldUbicacionPm)) {
                    ubicacionPmListOldUbicacionPm.setIdDepartamento(null);
                    ubicacionPmListOldUbicacionPm = em.merge(ubicacionPmListOldUbicacionPm);
                }
            }
            for (UbicacionPm ubicacionPmListNewUbicacionPm : ubicacionPmListNew) {
                if (!ubicacionPmListOld.contains(ubicacionPmListNewUbicacionPm)) {
                    Departamento oldIdDepartamentoOfUbicacionPmListNewUbicacionPm = ubicacionPmListNewUbicacionPm.getIdDepartamento();
                    ubicacionPmListNewUbicacionPm.setIdDepartamento(departamento);
                    ubicacionPmListNewUbicacionPm = em.merge(ubicacionPmListNewUbicacionPm);
                    if (oldIdDepartamentoOfUbicacionPmListNewUbicacionPm != null && !oldIdDepartamentoOfUbicacionPmListNewUbicacionPm.equals(departamento)) {
                        oldIdDepartamentoOfUbicacionPmListNewUbicacionPm.getUbicacionPmList().remove(ubicacionPmListNewUbicacionPm);
                        oldIdDepartamentoOfUbicacionPmListNewUbicacionPm = em.merge(oldIdDepartamentoOfUbicacionPmListNewUbicacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getId();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<UbicacionPm> ubicacionPmList = departamento.getUbicacionPmList();
            for (UbicacionPm ubicacionPmListUbicacionPm : ubicacionPmList) {
                ubicacionPmListUbicacionPm.setIdDepartamento(null);
                ubicacionPmListUbicacionPm = em.merge(ubicacionPmListUbicacionPm);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
