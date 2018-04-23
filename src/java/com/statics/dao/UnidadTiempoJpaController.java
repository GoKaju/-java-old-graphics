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
import com.statics.vo.DatoProcesado;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.NivelMaximo;
import com.statics.vo.UnidadTiempo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class UnidadTiempoJpaController implements Serializable {

    public UnidadTiempoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UnidadTiempo unidadTiempo) {
        if (unidadTiempo.getDatoProcesadoList() == null) {
            unidadTiempo.setDatoProcesadoList(new ArrayList<DatoProcesado>());
        }
        if (unidadTiempo.getNivelMaximoList() == null) {
            unidadTiempo.setNivelMaximoList(new ArrayList<NivelMaximo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DatoProcesado> attachedDatoProcesadoList = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListDatoProcesadoToAttach : unidadTiempo.getDatoProcesadoList()) {
                datoProcesadoListDatoProcesadoToAttach = em.getReference(datoProcesadoListDatoProcesadoToAttach.getClass(), datoProcesadoListDatoProcesadoToAttach.getId());
                attachedDatoProcesadoList.add(datoProcesadoListDatoProcesadoToAttach);
            }
            unidadTiempo.setDatoProcesadoList(attachedDatoProcesadoList);
            List<NivelMaximo> attachedNivelMaximoList = new ArrayList<NivelMaximo>();
            for (NivelMaximo nivelMaximoListNivelMaximoToAttach : unidadTiempo.getNivelMaximoList()) {
                nivelMaximoListNivelMaximoToAttach = em.getReference(nivelMaximoListNivelMaximoToAttach.getClass(), nivelMaximoListNivelMaximoToAttach.getId());
                attachedNivelMaximoList.add(nivelMaximoListNivelMaximoToAttach);
            }
            unidadTiempo.setNivelMaximoList(attachedNivelMaximoList);
            em.persist(unidadTiempo);
            for (DatoProcesado datoProcesadoListDatoProcesado : unidadTiempo.getDatoProcesadoList()) {
                UnidadTiempo oldIdUnidadTiempoOfDatoProcesadoListDatoProcesado = datoProcesadoListDatoProcesado.getIdUnidadTiempo();
                datoProcesadoListDatoProcesado.setIdUnidadTiempo(unidadTiempo);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
                if (oldIdUnidadTiempoOfDatoProcesadoListDatoProcesado != null) {
                    oldIdUnidadTiempoOfDatoProcesadoListDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListDatoProcesado);
                    oldIdUnidadTiempoOfDatoProcesadoListDatoProcesado = em.merge(oldIdUnidadTiempoOfDatoProcesadoListDatoProcesado);
                }
            }
            for (NivelMaximo nivelMaximoListNivelMaximo : unidadTiempo.getNivelMaximoList()) {
                UnidadTiempo oldIdUnidadTiempoOfNivelMaximoListNivelMaximo = nivelMaximoListNivelMaximo.getIdUnidadTiempo();
                nivelMaximoListNivelMaximo.setIdUnidadTiempo(unidadTiempo);
                nivelMaximoListNivelMaximo = em.merge(nivelMaximoListNivelMaximo);
                if (oldIdUnidadTiempoOfNivelMaximoListNivelMaximo != null) {
                    oldIdUnidadTiempoOfNivelMaximoListNivelMaximo.getNivelMaximoList().remove(nivelMaximoListNivelMaximo);
                    oldIdUnidadTiempoOfNivelMaximoListNivelMaximo = em.merge(oldIdUnidadTiempoOfNivelMaximoListNivelMaximo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UnidadTiempo unidadTiempo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadTiempo persistentUnidadTiempo = em.find(UnidadTiempo.class, unidadTiempo.getId());
            List<DatoProcesado> datoProcesadoListOld = persistentUnidadTiempo.getDatoProcesadoList();
            List<DatoProcesado> datoProcesadoListNew = unidadTiempo.getDatoProcesadoList();
            List<NivelMaximo> nivelMaximoListOld = persistentUnidadTiempo.getNivelMaximoList();
            List<NivelMaximo> nivelMaximoListNew = unidadTiempo.getNivelMaximoList();
            List<DatoProcesado> attachedDatoProcesadoListNew = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListNewDatoProcesadoToAttach : datoProcesadoListNew) {
                datoProcesadoListNewDatoProcesadoToAttach = em.getReference(datoProcesadoListNewDatoProcesadoToAttach.getClass(), datoProcesadoListNewDatoProcesadoToAttach.getId());
                attachedDatoProcesadoListNew.add(datoProcesadoListNewDatoProcesadoToAttach);
            }
            datoProcesadoListNew = attachedDatoProcesadoListNew;
            unidadTiempo.setDatoProcesadoList(datoProcesadoListNew);
            List<NivelMaximo> attachedNivelMaximoListNew = new ArrayList<NivelMaximo>();
            for (NivelMaximo nivelMaximoListNewNivelMaximoToAttach : nivelMaximoListNew) {
                nivelMaximoListNewNivelMaximoToAttach = em.getReference(nivelMaximoListNewNivelMaximoToAttach.getClass(), nivelMaximoListNewNivelMaximoToAttach.getId());
                attachedNivelMaximoListNew.add(nivelMaximoListNewNivelMaximoToAttach);
            }
            nivelMaximoListNew = attachedNivelMaximoListNew;
            unidadTiempo.setNivelMaximoList(nivelMaximoListNew);
            unidadTiempo = em.merge(unidadTiempo);
            for (DatoProcesado datoProcesadoListOldDatoProcesado : datoProcesadoListOld) {
                if (!datoProcesadoListNew.contains(datoProcesadoListOldDatoProcesado)) {
                    datoProcesadoListOldDatoProcesado.setIdUnidadTiempo(null);
                    datoProcesadoListOldDatoProcesado = em.merge(datoProcesadoListOldDatoProcesado);
                }
            }
            for (DatoProcesado datoProcesadoListNewDatoProcesado : datoProcesadoListNew) {
                if (!datoProcesadoListOld.contains(datoProcesadoListNewDatoProcesado)) {
                    UnidadTiempo oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado = datoProcesadoListNewDatoProcesado.getIdUnidadTiempo();
                    datoProcesadoListNewDatoProcesado.setIdUnidadTiempo(unidadTiempo);
                    datoProcesadoListNewDatoProcesado = em.merge(datoProcesadoListNewDatoProcesado);
                    if (oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado != null && !oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado.equals(unidadTiempo)) {
                        oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListNewDatoProcesado);
                        oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado = em.merge(oldIdUnidadTiempoOfDatoProcesadoListNewDatoProcesado);
                    }
                }
            }
            for (NivelMaximo nivelMaximoListOldNivelMaximo : nivelMaximoListOld) {
                if (!nivelMaximoListNew.contains(nivelMaximoListOldNivelMaximo)) {
                    nivelMaximoListOldNivelMaximo.setIdUnidadTiempo(null);
                    nivelMaximoListOldNivelMaximo = em.merge(nivelMaximoListOldNivelMaximo);
                }
            }
            for (NivelMaximo nivelMaximoListNewNivelMaximo : nivelMaximoListNew) {
                if (!nivelMaximoListOld.contains(nivelMaximoListNewNivelMaximo)) {
                    UnidadTiempo oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo = nivelMaximoListNewNivelMaximo.getIdUnidadTiempo();
                    nivelMaximoListNewNivelMaximo.setIdUnidadTiempo(unidadTiempo);
                    nivelMaximoListNewNivelMaximo = em.merge(nivelMaximoListNewNivelMaximo);
                    if (oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo != null && !oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo.equals(unidadTiempo)) {
                        oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo.getNivelMaximoList().remove(nivelMaximoListNewNivelMaximo);
                        oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo = em.merge(oldIdUnidadTiempoOfNivelMaximoListNewNivelMaximo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadTiempo.getId();
                if (findUnidadTiempo(id) == null) {
                    throw new NonexistentEntityException("The unidadTiempo with id " + id + " no longer exists.");
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
            UnidadTiempo unidadTiempo;
            try {
                unidadTiempo = em.getReference(UnidadTiempo.class, id);
                unidadTiempo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadTiempo with id " + id + " no longer exists.", enfe);
            }
            List<DatoProcesado> datoProcesadoList = unidadTiempo.getDatoProcesadoList();
            for (DatoProcesado datoProcesadoListDatoProcesado : datoProcesadoList) {
                datoProcesadoListDatoProcesado.setIdUnidadTiempo(null);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
            }
            List<NivelMaximo> nivelMaximoList = unidadTiempo.getNivelMaximoList();
            for (NivelMaximo nivelMaximoListNivelMaximo : nivelMaximoList) {
                nivelMaximoListNivelMaximo.setIdUnidadTiempo(null);
                nivelMaximoListNivelMaximo = em.merge(nivelMaximoListNivelMaximo);
            }
            em.remove(unidadTiempo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UnidadTiempo> findUnidadTiempoEntities() {
        return findUnidadTiempoEntities(true, -1, -1);
    }

    public List<UnidadTiempo> findUnidadTiempoEntities(int maxResults, int firstResult) {
        return findUnidadTiempoEntities(false, maxResults, firstResult);
    }

    private List<UnidadTiempo> findUnidadTiempoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UnidadTiempo.class));
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

    public UnidadTiempo findUnidadTiempo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UnidadTiempo.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadTiempoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UnidadTiempo> rt = cq.from(UnidadTiempo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
