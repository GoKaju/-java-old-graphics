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
import com.statics.vo.TipoAreaPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class TipoAreaPmJpaController implements Serializable {

    public TipoAreaPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAreaPm tipoAreaPm) {
        if (tipoAreaPm.getMacrolocalizacionPmList() == null) {
            tipoAreaPm.setMacrolocalizacionPmList(new ArrayList<MacrolocalizacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmList = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPmToAttach : tipoAreaPm.getMacrolocalizacionPmList()) {
                macrolocalizacionPmListMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmList.add(macrolocalizacionPmListMacrolocalizacionPmToAttach);
            }
            tipoAreaPm.setMacrolocalizacionPmList(attachedMacrolocalizacionPmList);
            em.persist(tipoAreaPm);
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : tipoAreaPm.getMacrolocalizacionPmList()) {
                TipoAreaPm oldIdTipoAreaOfMacrolocalizacionPmListMacrolocalizacionPm = macrolocalizacionPmListMacrolocalizacionPm.getIdTipoArea();
                macrolocalizacionPmListMacrolocalizacionPm.setIdTipoArea(tipoAreaPm);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
                if (oldIdTipoAreaOfMacrolocalizacionPmListMacrolocalizacionPm != null) {
                    oldIdTipoAreaOfMacrolocalizacionPmListMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListMacrolocalizacionPm);
                    oldIdTipoAreaOfMacrolocalizacionPmListMacrolocalizacionPm = em.merge(oldIdTipoAreaOfMacrolocalizacionPmListMacrolocalizacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAreaPm tipoAreaPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAreaPm persistentTipoAreaPm = em.find(TipoAreaPm.class, tipoAreaPm.getId());
            List<MacrolocalizacionPm> macrolocalizacionPmListOld = persistentTipoAreaPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> macrolocalizacionPmListNew = tipoAreaPm.getMacrolocalizacionPmList();
            List<MacrolocalizacionPm> attachedMacrolocalizacionPmListNew = new ArrayList<MacrolocalizacionPm>();
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPmToAttach : macrolocalizacionPmListNew) {
                macrolocalizacionPmListNewMacrolocalizacionPmToAttach = em.getReference(macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getClass(), macrolocalizacionPmListNewMacrolocalizacionPmToAttach.getId());
                attachedMacrolocalizacionPmListNew.add(macrolocalizacionPmListNewMacrolocalizacionPmToAttach);
            }
            macrolocalizacionPmListNew = attachedMacrolocalizacionPmListNew;
            tipoAreaPm.setMacrolocalizacionPmList(macrolocalizacionPmListNew);
            tipoAreaPm = em.merge(tipoAreaPm);
            for (MacrolocalizacionPm macrolocalizacionPmListOldMacrolocalizacionPm : macrolocalizacionPmListOld) {
                if (!macrolocalizacionPmListNew.contains(macrolocalizacionPmListOldMacrolocalizacionPm)) {
                    macrolocalizacionPmListOldMacrolocalizacionPm.setIdTipoArea(null);
                    macrolocalizacionPmListOldMacrolocalizacionPm = em.merge(macrolocalizacionPmListOldMacrolocalizacionPm);
                }
            }
            for (MacrolocalizacionPm macrolocalizacionPmListNewMacrolocalizacionPm : macrolocalizacionPmListNew) {
                if (!macrolocalizacionPmListOld.contains(macrolocalizacionPmListNewMacrolocalizacionPm)) {
                    TipoAreaPm oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm = macrolocalizacionPmListNewMacrolocalizacionPm.getIdTipoArea();
                    macrolocalizacionPmListNewMacrolocalizacionPm.setIdTipoArea(tipoAreaPm);
                    macrolocalizacionPmListNewMacrolocalizacionPm = em.merge(macrolocalizacionPmListNewMacrolocalizacionPm);
                    if (oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm != null && !oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm.equals(tipoAreaPm)) {
                        oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm.getMacrolocalizacionPmList().remove(macrolocalizacionPmListNewMacrolocalizacionPm);
                        oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm = em.merge(oldIdTipoAreaOfMacrolocalizacionPmListNewMacrolocalizacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAreaPm.getId();
                if (findTipoAreaPm(id) == null) {
                    throw new NonexistentEntityException("The tipoAreaPm with id " + id + " no longer exists.");
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
            TipoAreaPm tipoAreaPm;
            try {
                tipoAreaPm = em.getReference(TipoAreaPm.class, id);
                tipoAreaPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAreaPm with id " + id + " no longer exists.", enfe);
            }
            List<MacrolocalizacionPm> macrolocalizacionPmList = tipoAreaPm.getMacrolocalizacionPmList();
            for (MacrolocalizacionPm macrolocalizacionPmListMacrolocalizacionPm : macrolocalizacionPmList) {
                macrolocalizacionPmListMacrolocalizacionPm.setIdTipoArea(null);
                macrolocalizacionPmListMacrolocalizacionPm = em.merge(macrolocalizacionPmListMacrolocalizacionPm);
            }
            em.remove(tipoAreaPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAreaPm> findTipoAreaPmEntities() {
        return findTipoAreaPmEntities(true, -1, -1);
    }

    public List<TipoAreaPm> findTipoAreaPmEntities(int maxResults, int firstResult) {
        return findTipoAreaPmEntities(false, maxResults, firstResult);
    }

    private List<TipoAreaPm> findTipoAreaPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAreaPm.class));
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

    public TipoAreaPm findTipoAreaPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAreaPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAreaPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAreaPm> rt = cq.from(TipoAreaPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
