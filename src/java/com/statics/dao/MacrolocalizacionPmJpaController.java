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
import com.statics.vo.TipoAreaPm;
import com.statics.vo.TiempoPm;
import com.statics.vo.EmisionDominantePm;
import com.statics.vo.ClimaPm;
import com.statics.vo.MacrolocalizacionPm;
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class MacrolocalizacionPmJpaController implements Serializable {

    public MacrolocalizacionPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MacrolocalizacionPm macrolocalizacionPm) {
        if (macrolocalizacionPm.getPuntoMuestralList() == null) {
            macrolocalizacionPm.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAreaPm idTipoArea = macrolocalizacionPm.getIdTipoArea();
            if (idTipoArea != null) {
                idTipoArea = em.getReference(idTipoArea.getClass(), idTipoArea.getId());
                macrolocalizacionPm.setIdTipoArea(idTipoArea);
            }
            TiempoPm idTiempo = macrolocalizacionPm.getIdTiempo();
            if (idTiempo != null) {
                idTiempo = em.getReference(idTiempo.getClass(), idTiempo.getId());
                macrolocalizacionPm.setIdTiempo(idTiempo);
            }
            EmisionDominantePm idEmisionDominante = macrolocalizacionPm.getIdEmisionDominante();
            if (idEmisionDominante != null) {
                idEmisionDominante = em.getReference(idEmisionDominante.getClass(), idEmisionDominante.getId());
                macrolocalizacionPm.setIdEmisionDominante(idEmisionDominante);
            }
            ClimaPm idClima = macrolocalizacionPm.getIdClima();
            if (idClima != null) {
                idClima = em.getReference(idClima.getClass(), idClima.getId());
                macrolocalizacionPm.setIdClima(idClima);
            }
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : macrolocalizacionPm.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            macrolocalizacionPm.setPuntoMuestralList(attachedPuntoMuestralList);
            em.persist(macrolocalizacionPm);
            if (idTipoArea != null) {
                idTipoArea.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idTipoArea = em.merge(idTipoArea);
            }
            if (idTiempo != null) {
                idTiempo.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idTiempo = em.merge(idTiempo);
            }
            if (idEmisionDominante != null) {
                idEmisionDominante.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idEmisionDominante = em.merge(idEmisionDominante);
            }
            if (idClima != null) {
                idClima.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idClima = em.merge(idClima);
            }
            for (PuntoMuestral puntoMuestralListPuntoMuestral : macrolocalizacionPm.getPuntoMuestralList()) {
                MacrolocalizacionPm oldIdMacrolocalizacionOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getIdMacrolocalizacion();
                puntoMuestralListPuntoMuestral.setIdMacrolocalizacion(macrolocalizacionPm);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldIdMacrolocalizacionOfPuntoMuestralListPuntoMuestral != null) {
                    oldIdMacrolocalizacionOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldIdMacrolocalizacionOfPuntoMuestralListPuntoMuestral = em.merge(oldIdMacrolocalizacionOfPuntoMuestralListPuntoMuestral);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MacrolocalizacionPm macrolocalizacionPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MacrolocalizacionPm persistentMacrolocalizacionPm = em.find(MacrolocalizacionPm.class, macrolocalizacionPm.getId());
            TipoAreaPm idTipoAreaOld = persistentMacrolocalizacionPm.getIdTipoArea();
            TipoAreaPm idTipoAreaNew = macrolocalizacionPm.getIdTipoArea();
            TiempoPm idTiempoOld = persistentMacrolocalizacionPm.getIdTiempo();
            TiempoPm idTiempoNew = macrolocalizacionPm.getIdTiempo();
            EmisionDominantePm idEmisionDominanteOld = persistentMacrolocalizacionPm.getIdEmisionDominante();
            EmisionDominantePm idEmisionDominanteNew = macrolocalizacionPm.getIdEmisionDominante();
            ClimaPm idClimaOld = persistentMacrolocalizacionPm.getIdClima();
            ClimaPm idClimaNew = macrolocalizacionPm.getIdClima();
            List<PuntoMuestral> puntoMuestralListOld = persistentMacrolocalizacionPm.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = macrolocalizacionPm.getPuntoMuestralList();
            if (idTipoAreaNew != null) {
                idTipoAreaNew = em.getReference(idTipoAreaNew.getClass(), idTipoAreaNew.getId());
                macrolocalizacionPm.setIdTipoArea(idTipoAreaNew);
            }
            if (idTiempoNew != null) {
                idTiempoNew = em.getReference(idTiempoNew.getClass(), idTiempoNew.getId());
                macrolocalizacionPm.setIdTiempo(idTiempoNew);
            }
            if (idEmisionDominanteNew != null) {
                idEmisionDominanteNew = em.getReference(idEmisionDominanteNew.getClass(), idEmisionDominanteNew.getId());
                macrolocalizacionPm.setIdEmisionDominante(idEmisionDominanteNew);
            }
            if (idClimaNew != null) {
                idClimaNew = em.getReference(idClimaNew.getClass(), idClimaNew.getId());
                macrolocalizacionPm.setIdClima(idClimaNew);
            }
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            macrolocalizacionPm.setPuntoMuestralList(puntoMuestralListNew);
            macrolocalizacionPm = em.merge(macrolocalizacionPm);
            if (idTipoAreaOld != null && !idTipoAreaOld.equals(idTipoAreaNew)) {
                idTipoAreaOld.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idTipoAreaOld = em.merge(idTipoAreaOld);
            }
            if (idTipoAreaNew != null && !idTipoAreaNew.equals(idTipoAreaOld)) {
                idTipoAreaNew.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idTipoAreaNew = em.merge(idTipoAreaNew);
            }
            if (idTiempoOld != null && !idTiempoOld.equals(idTiempoNew)) {
                idTiempoOld.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idTiempoOld = em.merge(idTiempoOld);
            }
            if (idTiempoNew != null && !idTiempoNew.equals(idTiempoOld)) {
                idTiempoNew.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idTiempoNew = em.merge(idTiempoNew);
            }
            if (idEmisionDominanteOld != null && !idEmisionDominanteOld.equals(idEmisionDominanteNew)) {
                idEmisionDominanteOld.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idEmisionDominanteOld = em.merge(idEmisionDominanteOld);
            }
            if (idEmisionDominanteNew != null && !idEmisionDominanteNew.equals(idEmisionDominanteOld)) {
                idEmisionDominanteNew.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idEmisionDominanteNew = em.merge(idEmisionDominanteNew);
            }
            if (idClimaOld != null && !idClimaOld.equals(idClimaNew)) {
                idClimaOld.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idClimaOld = em.merge(idClimaOld);
            }
            if (idClimaNew != null && !idClimaNew.equals(idClimaOld)) {
                idClimaNew.getMacrolocalizacionPmList().add(macrolocalizacionPm);
                idClimaNew = em.merge(idClimaNew);
            }
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setIdMacrolocalizacion(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    MacrolocalizacionPm oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getIdMacrolocalizacion();
                    puntoMuestralListNewPuntoMuestral.setIdMacrolocalizacion(macrolocalizacionPm);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral != null && !oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral.equals(macrolocalizacionPm)) {
                        oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral = em.merge(oldIdMacrolocalizacionOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = macrolocalizacionPm.getId();
                if (findMacrolocalizacionPm(id) == null) {
                    throw new NonexistentEntityException("The macrolocalizacionPm with id " + id + " no longer exists.");
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
            MacrolocalizacionPm macrolocalizacionPm;
            try {
                macrolocalizacionPm = em.getReference(MacrolocalizacionPm.class, id);
                macrolocalizacionPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The macrolocalizacionPm with id " + id + " no longer exists.", enfe);
            }
            TipoAreaPm idTipoArea = macrolocalizacionPm.getIdTipoArea();
            if (idTipoArea != null) {
                idTipoArea.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idTipoArea = em.merge(idTipoArea);
            }
            TiempoPm idTiempo = macrolocalizacionPm.getIdTiempo();
            if (idTiempo != null) {
                idTiempo.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idTiempo = em.merge(idTiempo);
            }
            EmisionDominantePm idEmisionDominante = macrolocalizacionPm.getIdEmisionDominante();
            if (idEmisionDominante != null) {
                idEmisionDominante.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idEmisionDominante = em.merge(idEmisionDominante);
            }
            ClimaPm idClima = macrolocalizacionPm.getIdClima();
            if (idClima != null) {
                idClima.getMacrolocalizacionPmList().remove(macrolocalizacionPm);
                idClima = em.merge(idClima);
            }
            List<PuntoMuestral> puntoMuestralList = macrolocalizacionPm.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setIdMacrolocalizacion(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            em.remove(macrolocalizacionPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MacrolocalizacionPm> findMacrolocalizacionPmEntities() {
        return findMacrolocalizacionPmEntities(true, -1, -1);
    }

    public List<MacrolocalizacionPm> findMacrolocalizacionPmEntities(int maxResults, int firstResult) {
        return findMacrolocalizacionPmEntities(false, maxResults, firstResult);
    }

    private List<MacrolocalizacionPm> findMacrolocalizacionPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MacrolocalizacionPm.class));
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

    public MacrolocalizacionPm findMacrolocalizacionPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MacrolocalizacionPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getMacrolocalizacionPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MacrolocalizacionPm> rt = cq.from(MacrolocalizacionPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
