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
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.DatosadicionalesMicrolocalizacion;
import com.statics.vo.CriterioMicrolocalizacion;
import com.statics.vo.MicrolocalizacionPm;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class MicrolocalizacionPmJpaController implements Serializable {

    public MicrolocalizacionPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MicrolocalizacionPm microlocalizacionPm) {
        if (microlocalizacionPm.getPuntoMuestralList() == null) {
            microlocalizacionPm.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        if (microlocalizacionPm.getDatosadicionalesMicrolocalizacionList() == null) {
            microlocalizacionPm.setDatosadicionalesMicrolocalizacionList(new ArrayList<DatosadicionalesMicrolocalizacion>());
        }
        if (microlocalizacionPm.getCriterioMicrolocalizacionList() == null) {
            microlocalizacionPm.setCriterioMicrolocalizacionList(new ArrayList<CriterioMicrolocalizacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : microlocalizacionPm.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            microlocalizacionPm.setPuntoMuestralList(attachedPuntoMuestralList);
            List<DatosadicionalesMicrolocalizacion> attachedDatosadicionalesMicrolocalizacionList = new ArrayList<DatosadicionalesMicrolocalizacion>();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach : microlocalizacionPm.getDatosadicionalesMicrolocalizacionList()) {
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach = em.getReference(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach.getClass(), datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach.getId());
                attachedDatosadicionalesMicrolocalizacionList.add(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach);
            }
            microlocalizacionPm.setDatosadicionalesMicrolocalizacionList(attachedDatosadicionalesMicrolocalizacionList);
            List<CriterioMicrolocalizacion> attachedCriterioMicrolocalizacionList = new ArrayList<CriterioMicrolocalizacion>();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach : microlocalizacionPm.getCriterioMicrolocalizacionList()) {
                criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach = em.getReference(criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach.getClass(), criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach.getId());
                attachedCriterioMicrolocalizacionList.add(criterioMicrolocalizacionListCriterioMicrolocalizacionToAttach);
            }
            microlocalizacionPm.setCriterioMicrolocalizacionList(attachedCriterioMicrolocalizacionList);
            em.persist(microlocalizacionPm);
            for (PuntoMuestral puntoMuestralListPuntoMuestral : microlocalizacionPm.getPuntoMuestralList()) {
                MicrolocalizacionPm oldIdMicrolocalizacionOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getIdMicrolocalizacion();
                puntoMuestralListPuntoMuestral.setIdMicrolocalizacion(microlocalizacionPm);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldIdMicrolocalizacionOfPuntoMuestralListPuntoMuestral != null) {
                    oldIdMicrolocalizacionOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldIdMicrolocalizacionOfPuntoMuestralListPuntoMuestral = em.merge(oldIdMicrolocalizacionOfPuntoMuestralListPuntoMuestral);
                }
            }
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion : microlocalizacionPm.getDatosadicionalesMicrolocalizacionList()) {
                MicrolocalizacionPm oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.setIdMicrolocalizacion(microlocalizacionPm);
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                if (oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion != null) {
                    oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                    oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                }
            }
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacion : microlocalizacionPm.getCriterioMicrolocalizacionList()) {
                MicrolocalizacionPm oldIdMicrolocalizacionOfCriterioMicrolocalizacionListCriterioMicrolocalizacion = criterioMicrolocalizacionListCriterioMicrolocalizacion.getIdMicrolocalizacion();
                criterioMicrolocalizacionListCriterioMicrolocalizacion.setIdMicrolocalizacion(microlocalizacionPm);
                criterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListCriterioMicrolocalizacion);
                if (oldIdMicrolocalizacionOfCriterioMicrolocalizacionListCriterioMicrolocalizacion != null) {
                    oldIdMicrolocalizacionOfCriterioMicrolocalizacionListCriterioMicrolocalizacion.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacionListCriterioMicrolocalizacion);
                    oldIdMicrolocalizacionOfCriterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(oldIdMicrolocalizacionOfCriterioMicrolocalizacionListCriterioMicrolocalizacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MicrolocalizacionPm microlocalizacionPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MicrolocalizacionPm persistentMicrolocalizacionPm = em.find(MicrolocalizacionPm.class, microlocalizacionPm.getId());
            List<PuntoMuestral> puntoMuestralListOld = persistentMicrolocalizacionPm.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = microlocalizacionPm.getPuntoMuestralList();
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionListOld = persistentMicrolocalizacionPm.getDatosadicionalesMicrolocalizacionList();
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionListNew = microlocalizacionPm.getDatosadicionalesMicrolocalizacionList();
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionListOld = persistentMicrolocalizacionPm.getCriterioMicrolocalizacionList();
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionListNew = microlocalizacionPm.getCriterioMicrolocalizacionList();
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            microlocalizacionPm.setPuntoMuestralList(puntoMuestralListNew);
            List<DatosadicionalesMicrolocalizacion> attachedDatosadicionalesMicrolocalizacionListNew = new ArrayList<DatosadicionalesMicrolocalizacion>();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach : datosadicionalesMicrolocalizacionListNew) {
                datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach = em.getReference(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach.getClass(), datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach.getId());
                attachedDatosadicionalesMicrolocalizacionListNew.add(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach);
            }
            datosadicionalesMicrolocalizacionListNew = attachedDatosadicionalesMicrolocalizacionListNew;
            microlocalizacionPm.setDatosadicionalesMicrolocalizacionList(datosadicionalesMicrolocalizacionListNew);
            List<CriterioMicrolocalizacion> attachedCriterioMicrolocalizacionListNew = new ArrayList<CriterioMicrolocalizacion>();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach : criterioMicrolocalizacionListNew) {
                criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach = em.getReference(criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach.getClass(), criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach.getId());
                attachedCriterioMicrolocalizacionListNew.add(criterioMicrolocalizacionListNewCriterioMicrolocalizacionToAttach);
            }
            criterioMicrolocalizacionListNew = attachedCriterioMicrolocalizacionListNew;
            microlocalizacionPm.setCriterioMicrolocalizacionList(criterioMicrolocalizacionListNew);
            microlocalizacionPm = em.merge(microlocalizacionPm);
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setIdMicrolocalizacion(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    MicrolocalizacionPm oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getIdMicrolocalizacion();
                    puntoMuestralListNewPuntoMuestral.setIdMicrolocalizacion(microlocalizacionPm);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral != null && !oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral.equals(microlocalizacionPm)) {
                        oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral = em.merge(oldIdMicrolocalizacionOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionListOld) {
                if (!datosadicionalesMicrolocalizacionListNew.contains(datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion)) {
                    datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion.setIdMicrolocalizacion(null);
                    datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion);
                }
            }
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionListNew) {
                if (!datosadicionalesMicrolocalizacionListOld.contains(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion)) {
                    MicrolocalizacionPm oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
                    datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.setIdMicrolocalizacion(microlocalizacionPm);
                    datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                    if (oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion != null && !oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.equals(microlocalizacionPm)) {
                        oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                        oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = em.merge(oldIdMicrolocalizacionOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                    }
                }
            }
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListOldCriterioMicrolocalizacion : criterioMicrolocalizacionListOld) {
                if (!criterioMicrolocalizacionListNew.contains(criterioMicrolocalizacionListOldCriterioMicrolocalizacion)) {
                    criterioMicrolocalizacionListOldCriterioMicrolocalizacion.setIdMicrolocalizacion(null);
                    criterioMicrolocalizacionListOldCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListOldCriterioMicrolocalizacion);
                }
            }
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListNewCriterioMicrolocalizacion : criterioMicrolocalizacionListNew) {
                if (!criterioMicrolocalizacionListOld.contains(criterioMicrolocalizacionListNewCriterioMicrolocalizacion)) {
                    MicrolocalizacionPm oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion = criterioMicrolocalizacionListNewCriterioMicrolocalizacion.getIdMicrolocalizacion();
                    criterioMicrolocalizacionListNewCriterioMicrolocalizacion.setIdMicrolocalizacion(microlocalizacionPm);
                    criterioMicrolocalizacionListNewCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                    if (oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion != null && !oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion.equals(microlocalizacionPm)) {
                        oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion.getCriterioMicrolocalizacionList().remove(criterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                        oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion = em.merge(oldIdMicrolocalizacionOfCriterioMicrolocalizacionListNewCriterioMicrolocalizacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = microlocalizacionPm.getId();
                if (findMicrolocalizacionPm(id) == null) {
                    throw new NonexistentEntityException("The microlocalizacionPm with id " + id + " no longer exists.");
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
            MicrolocalizacionPm microlocalizacionPm;
            try {
                microlocalizacionPm = em.getReference(MicrolocalizacionPm.class, id);
                microlocalizacionPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The microlocalizacionPm with id " + id + " no longer exists.", enfe);
            }
            List<PuntoMuestral> puntoMuestralList = microlocalizacionPm.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setIdMicrolocalizacion(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList = microlocalizacionPm.getDatosadicionalesMicrolocalizacionList();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionList) {
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.setIdMicrolocalizacion(null);
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
            }
            List<CriterioMicrolocalizacion> criterioMicrolocalizacionList = microlocalizacionPm.getCriterioMicrolocalizacionList();
            for (CriterioMicrolocalizacion criterioMicrolocalizacionListCriterioMicrolocalizacion : criterioMicrolocalizacionList) {
                criterioMicrolocalizacionListCriterioMicrolocalizacion.setIdMicrolocalizacion(null);
                criterioMicrolocalizacionListCriterioMicrolocalizacion = em.merge(criterioMicrolocalizacionListCriterioMicrolocalizacion);
            }
            em.remove(microlocalizacionPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MicrolocalizacionPm> findMicrolocalizacionPmEntities() {
        return findMicrolocalizacionPmEntities(true, -1, -1);
    }

    public List<MicrolocalizacionPm> findMicrolocalizacionPmEntities(int maxResults, int firstResult) {
        return findMicrolocalizacionPmEntities(false, maxResults, firstResult);
    }

    private List<MicrolocalizacionPm> findMicrolocalizacionPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MicrolocalizacionPm.class));
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

    public MicrolocalizacionPm findMicrolocalizacionPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MicrolocalizacionPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getMicrolocalizacionPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MicrolocalizacionPm> rt = cq.from(MicrolocalizacionPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
