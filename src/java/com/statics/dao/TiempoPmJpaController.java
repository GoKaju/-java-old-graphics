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
import com.statics.vo.MacrolocalizacionPm;
import com.statics.vo.TiempoPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class TiempoPmJpaController implements Serializable {

    public TiempoPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiempoPm tiempoPm) {
        if (tiempoPm.getMacrolocalizacionPmList() == null) {
            tiempoPm.setMacrolocalizacionPmList(new ArrayList<MacrolocalizacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmList = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPmToAttach : tiempoPm.getMacrolocalizacionPmList()) {
                macrolocalizacionPmListMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmList.add(macrolocalizacionPmListMacrolocalizacionPmToAttach);
            }
            tiempoPm.setMacrolocalizacionPmList(attachedMacrolocalizacionPmList);
            em.persist(tiempoPm);
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : tiempoPm.getMacrolocalizacionPmList()) {
                TiempoPm oldIdTiempoOfMacrolocalizacionPmListMacrolocalizacionPm = macrolocalizacionPmListMacrolocalizacionPm.getIdTiempo();
                macrolocalizacionPmListMacrolocalizacionPm.setIdTiempo(tiempoPm);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
                if (oldIdTiempoOfMacrolocalizacionPmListMacrolocalizacionPm != null) {
                    oldIdTiempoOfMacrolocalizacionPmListMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListMacrolocalizacionPm);
                    oldIdTiempoOfMacrolocalizacionPmListMacrolocalizacionPm = em.merge(oldIdTiempoOfMacrolocalizacionPmListMacrolocalizacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiempoPm tiempoPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiempoPm persistentTiempoPm = em.find(TiempoPm.class, tiempoPm.getId());
            List<MacrolocalizacionPm> macrolocalizacionPmListOld = persistentTiempoPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> macrolocalizacionPmListNew = tiempoPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmListNew = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPmToAttach : macrolocalizacionPmListNew) {
                macrolocalizacionPmListNewMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmListNew.add(macrolocalizacionPmListNewMacrolocalizacionPmToAttach);
            }
            macrolocalizacionPmListNew = attachedMacrolocalizacionPmListNew;
            tiempoPm.setMacrolocalizacionPmList(macrolocalizacionPmListNew);
            tiempoPm = em.merge(tiempoPm);
            for (MacrolocalizacionPm macrolocalizacionPmListOldMacrolocalizacionPm : macrolocalizacionPmListOld) {
                if (!macrolocalizacionPmListNew.contains(macrolocalizacionPmListOldMacrolocalizacionPm)) {
                    macrolocalizacionPmListOldMacrolocalizacionPm.setIdTiempo(null);
                    macrolocalizacionPmListOldMacrolocalizacionPm = em.merge(macrolocalizacionPmListOldMacrolocalizacionPm);
                }
            }
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPm : macrolocalizacionPmListNew) {
                if (!macrolocalizacionPmListOld.contains(macrolocalizacionPmListNewMacrolocalizacionPm)) {
                    TiempoPm oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm = macrolocalizacionPmListNewMacrolocalizacionPm.getIdTiempo();
                    macrolocalizacionPmListNewMacrolocalizacionPm.setIdTiempo(tiempoPm);
                    macrolocalizacionPmListNewMacrolocalizacionPm = em.merge(macrolocalizacionPmListNewMacrolocalizacionPm);
                    if (oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm != null && !oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm.equals(tiempoPm)) {
                        oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListNewMacrolocalizacionPm);
                        oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm = em.merge(oldIdTiempoOfMacrolocalizacionPmListNewMacrolocalizacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiempoPm.getId();
                if (findTiempoPm(id) == null) {
                    throw new NonexistentEntityException("The tiempoPm with id " + id + " no longer exists.");
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
            TiempoPm tiempoPm;
            try {
                tiempoPm = em.getReference(TiempoPm.class, id);
                tiempoPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiempoPm with id " + id + " no longer exists.", enfe);
            }
            List<MacrolocalizacionPm> macrolocalizacionPmList = tiempoPm.getMacrolocalizacionPmList();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : macrolocalizacionPmList) {
                macrolocalizacionPmListMacrolocalizacionPm.setIdTiempo(null);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
            }
            em.remove(tiempoPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiempoPm> findTiempoPmEntities() {
        return findTiempoPmEntities(true, -1, -1);
    }

    public List<TiempoPm> findTiempoPmEntities(int maxResults, int firstResult) {
        return findTiempoPmEntities(false, maxResults, firstResult);
    }

    private List<TiempoPm> findTiempoPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiempoPm.class));
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

    public TiempoPm findTiempoPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiempoPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiempoPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiempoPm> rt = cq.from(TiempoPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
