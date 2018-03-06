/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.ClimaPm;
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
public class ClimaPmJpaController implements Serializable {

    public ClimaPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClimaPm climaPm) {
        if (climaPm.getMacrolocalizacionPmList() == null) {
            climaPm.setMacrolocalizacionPmList(new ArrayList<MacrolocalizacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmList = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPmToAttach : climaPm.getMacrolocalizacionPmList()) {
                macrolocalizacionPmListMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmList.add(macrolocalizacionPmListMacrolocalizacionPmToAttach);
            }
            climaPm.setMacrolocalizacionPmList(attachedMacrolocalizacionPmList);
            em.persist(climaPm);
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : climaPm.getMacrolocalizacionPmList()) {
                ClimaPm oldIdClimaOfMacrolocalizacionPmListMacrolocalizacionPm = macrolocalizacionPmListMacrolocalizacionPm.getIdClima();
                macrolocalizacionPmListMacrolocalizacionPm.setIdClima(climaPm);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
                if (oldIdClimaOfMacrolocalizacionPmListMacrolocalizacionPm != null) {
                    oldIdClimaOfMacrolocalizacionPmListMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListMacrolocalizacionPm);
                    oldIdClimaOfMacrolocalizacionPmListMacrolocalizacionPm = em.merge(oldIdClimaOfMacrolocalizacionPmListMacrolocalizacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClimaPm climaPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClimaPm persistentClimaPm = em.find(ClimaPm.class, climaPm.getId());
            List<MacrolocalizacionPm> macrolocalizacionPmListOld = persistentClimaPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> macrolocalizacionPmListNew = climaPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmListNew = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPmToAttach : macrolocalizacionPmListNew) {
                macrolocalizacionPmListNewMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmListNew.add(macrolocalizacionPmListNewMacrolocalizacionPmToAttach);
            }
            macrolocalizacionPmListNew = attachedMacrolocalizacionPmListNew;
            climaPm.setMacrolocalizacionPmList(macrolocalizacionPmListNew);
            climaPm = em.merge(climaPm);
            for (MacrolocalizacionPm macrolocalizacionPmListOldMacrolocalizacionPm : macrolocalizacionPmListOld) {
                if (!macrolocalizacionPmListNew.contains(macrolocalizacionPmListOldMacrolocalizacionPm)) {
                    macrolocalizacionPmListOldMacrolocalizacionPm.setIdClima(null);
                    macrolocalizacionPmListOldMacrolocalizacionPm = em.merge(macrolocalizacionPmListOldMacrolocalizacionPm);
                }
            }
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPm : macrolocalizacionPmListNew) {
                if (!macrolocalizacionPmListOld.contains(macrolocalizacionPmListNewMacrolocalizacionPm)) {
                    ClimaPm oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm = macrolocalizacionPmListNewMacrolocalizacionPm.getIdClima();
                    macrolocalizacionPmListNewMacrolocalizacionPm.setIdClima(climaPm);
                    macrolocalizacionPmListNewMacrolocalizacionPm = em.merge(macrolocalizacionPmListNewMacrolocalizacionPm);
                    if (oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm != null && !oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm.equals(climaPm)) {
                        oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListNewMacrolocalizacionPm);
                        oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm = em.merge(oldIdClimaOfMacrolocalizacionPmListNewMacrolocalizacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = climaPm.getId();
                if (findClimaPm(id) == null) {
                    throw new NonexistentEntityException("The climaPm with id " + id + " no longer exists.");
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
            ClimaPm climaPm;
            try {
                climaPm = em.getReference(ClimaPm.class, id);
                climaPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The climaPm with id " + id + " no longer exists.", enfe);
            }
            List<MacrolocalizacionPm> macrolocalizacionPmList = climaPm.getMacrolocalizacionPmList();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : macrolocalizacionPmList) {
                macrolocalizacionPmListMacrolocalizacionPm.setIdClima(null);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
            }
            em.remove(climaPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClimaPm> findClimaPmEntities() {
        return findClimaPmEntities(true, -1, -1);
    }

    public List<ClimaPm> findClimaPmEntities(int maxResults, int firstResult) {
        return findClimaPmEntities(false, maxResults, firstResult);
    }

    private List<ClimaPm> findClimaPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClimaPm.class));
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

    public ClimaPm findClimaPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClimaPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getClimaPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClimaPm> rt = cq.from(ClimaPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
