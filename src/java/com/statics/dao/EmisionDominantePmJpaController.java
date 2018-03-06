/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.EmisionDominantePm;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.MacrolocalizacionPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class EmisionDominantePmJpaController implements Serializable {

    public EmisionDominantePmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmisionDominantePm emisionDominantePm) {
        if (emisionDominantePm.getMacrolocalizacionPmList() == null) {
            emisionDominantePm.setMacrolocalizacionPmList(new ArrayList<MacrolocalizacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmList = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPmToAttach : emisionDominantePm.getMacrolocalizacionPmList()) {
                macrolocalizacionPmListMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmList.add(macrolocalizacionPmListMacrolocalizacionPmToAttach);
            }
            emisionDominantePm.setMacrolocalizacionPmList(attachedMacrolocalizacionPmList);
            em.persist(emisionDominantePm);
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : emisionDominantePm.getMacrolocalizacionPmList()) {
                EmisionDominantePm oldIdEmisionDominanteOfMacrolocalizacionPmListMacrolocalizacionPm = macrolocalizacionPmListMacrolocalizacionPm.getIdEmisionDominante();
                macrolocalizacionPmListMacrolocalizacionPm.setIdEmisionDominante(emisionDominantePm);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
                if (oldIdEmisionDominanteOfMacrolocalizacionPmListMacrolocalizacionPm != null) {
                    oldIdEmisionDominanteOfMacrolocalizacionPmListMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListMacrolocalizacionPm);
                    oldIdEmisionDominanteOfMacrolocalizacionPmListMacrolocalizacionPm = em.merge(oldIdEmisionDominanteOfMacrolocalizacionPmListMacrolocalizacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmisionDominantePm emisionDominantePm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmisionDominantePm persistentEmisionDominantePm = em.find(EmisionDominantePm.class, emisionDominantePm.getId());
            List<MacrolocalizacionPm> macrolocalizacionPmListOld = persistentEmisionDominantePm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> macrolocalizacionPmListNew = emisionDominantePm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmListNew = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPmToAttach : macrolocalizacionPmListNew) {
                macrolocalizacionPmListNewMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmListNew.add(macrolocalizacionPmListNewMacrolocalizacionPmToAttach);
            }
            macrolocalizacionPmListNew = attachedMacrolocalizacionPmListNew;
            emisionDominantePm.setMacrolocalizacionPmList(macrolocalizacionPmListNew);
            emisionDominantePm = em.merge(emisionDominantePm);
            for (MacrolocalizacionPm macrolocalizacionPmListOldMacrolocalizacionPm : macrolocalizacionPmListOld) {
                if (!macrolocalizacionPmListNew.contains(macrolocalizacionPmListOldMacrolocalizacionPm)) {
                    macrolocalizacionPmListOldMacrolocalizacionPm.setIdEmisionDominante(null);
                    macrolocalizacionPmListOldMacrolocalizacionPm = em.merge(macrolocalizacionPmListOldMacrolocalizacionPm);
                }
            }
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPm : macrolocalizacionPmListNew) {
                if (!macrolocalizacionPmListOld.contains(macrolocalizacionPmListNewMacrolocalizacionPm)) {
                    EmisionDominantePm oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm = macrolocalizacionPmListNewMacrolocalizacionPm.getIdEmisionDominante();
                    macrolocalizacionPmListNewMacrolocalizacionPm.setIdEmisionDominante(emisionDominantePm);
                    macrolocalizacionPmListNewMacrolocalizacionPm = em.merge(macrolocalizacionPmListNewMacrolocalizacionPm);
                    if (oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm != null && !oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm.equals(emisionDominantePm)) {
                        oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListNewMacrolocalizacionPm);
                        oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm = em.merge(oldIdEmisionDominanteOfMacrolocalizacionPmListNewMacrolocalizacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = emisionDominantePm.getId();
                if (findEmisionDominantePm(id) == null) {
                    throw new NonexistentEntityException("The emisionDominantePm with id " + id + " no longer exists.");
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
            EmisionDominantePm emisionDominantePm;
            try {
                emisionDominantePm = em.getReference(EmisionDominantePm.class, id);
                emisionDominantePm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emisionDominantePm with id " + id + " no longer exists.", enfe);
            }
            List<MacrolocalizacionPm> macrolocalizacionPmList = emisionDominantePm.getMacrolocalizacionPmList();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : macrolocalizacionPmList) {
                macrolocalizacionPmListMacrolocalizacionPm.setIdEmisionDominante(null);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
            }
            em.remove(emisionDominantePm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmisionDominantePm> findEmisionDominantePmEntities() {
        return findEmisionDominantePmEntities(true, -1, -1);
    }

    public List<EmisionDominantePm> findEmisionDominantePmEntities(int maxResults, int firstResult) {
        return findEmisionDominantePmEntities(false, maxResults, firstResult);
    }

    private List<EmisionDominantePm> findEmisionDominantePmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmisionDominantePm.class));
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

    public EmisionDominantePm findEmisionDominantePm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmisionDominantePm.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmisionDominantePmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmisionDominantePm> rt = cq.from(EmisionDominantePm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
