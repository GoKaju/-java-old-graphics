/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.DatosAdicionalesPm;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.DatosadicionalesMicrolocalizacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class DatosAdicionalesPmJpaController implements Serializable {

    public DatosAdicionalesPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<DatosAdicionalesPm> findDatosadicionalesByMicrolocalizacion(int idMiccrolocalizacion) {
        String sqlQuery = "SELECT da.* FROM datos_adicionales_pm da INNER JOIN datosadicionales_microlocalizacion dam "
                + "ON da.id=dam.id_datos_adicionales WHERE dam.id_microlocalizacion=" + idMiccrolocalizacion;
        List<DatosAdicionalesPm> lista = new ArrayList();
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery(sqlQuery, DatosAdicionalesPm.class);
            lista = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }

    public void create(DatosAdicionalesPm datosAdicionalesPm) {
        if (datosAdicionalesPm.getDatosadicionalesMicrolocalizacionList() == null) {
            datosAdicionalesPm.setDatosadicionalesMicrolocalizacionList(new ArrayList<DatosadicionalesMicrolocalizacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DatosadicionalesMicrolocalizacion> attachedDatosadicionalesMicrolocalizacionList = new ArrayList<DatosadicionalesMicrolocalizacion>();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach : datosAdicionalesPm.getDatosadicionalesMicrolocalizacionList()) {
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach = em.getReference(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach.getClass(), datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach.getId());
                attachedDatosadicionalesMicrolocalizacionList.add(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacionToAttach);
            }
            datosAdicionalesPm.setDatosadicionalesMicrolocalizacionList(attachedDatosadicionalesMicrolocalizacionList);
            em.persist(datosAdicionalesPm);
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion : datosAdicionalesPm.getDatosadicionalesMicrolocalizacionList()) {
                DatosAdicionalesPm oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.getIdDatosAdicionales();
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.setIdDatosAdicionales(datosAdicionalesPm);
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                if (oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion != null) {
                    oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                    oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosAdicionalesPm datosAdicionalesPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosAdicionalesPm persistentDatosAdicionalesPm = em.find(DatosAdicionalesPm.class, datosAdicionalesPm.getId());
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionListOld = persistentDatosAdicionalesPm.getDatosadicionalesMicrolocalizacionList();
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionListNew = datosAdicionalesPm.getDatosadicionalesMicrolocalizacionList();
            List<DatosadicionalesMicrolocalizacion> attachedDatosadicionalesMicrolocalizacionListNew = new ArrayList<DatosadicionalesMicrolocalizacion>();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach : datosadicionalesMicrolocalizacionListNew) {
                datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach = em.getReference(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach.getClass(), datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach.getId());
                attachedDatosadicionalesMicrolocalizacionListNew.add(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacionToAttach);
            }
            datosadicionalesMicrolocalizacionListNew = attachedDatosadicionalesMicrolocalizacionListNew;
            datosAdicionalesPm.setDatosadicionalesMicrolocalizacionList(datosadicionalesMicrolocalizacionListNew);
            datosAdicionalesPm = em.merge(datosAdicionalesPm);
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionListOld) {
                if (!datosadicionalesMicrolocalizacionListNew.contains(datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion)) {
                    datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion.setIdDatosAdicionales(null);
                    datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListOldDatosadicionalesMicrolocalizacion);
                }
            }
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionListNew) {
                if (!datosadicionalesMicrolocalizacionListOld.contains(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion)) {
                    DatosAdicionalesPm oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.getIdDatosAdicionales();
                    datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.setIdDatosAdicionales(datosAdicionalesPm);
                    datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                    if (oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion != null && !oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.equals(datosAdicionalesPm)) {
                        oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                        oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion = em.merge(oldIdDatosAdicionalesOfDatosadicionalesMicrolocalizacionListNewDatosadicionalesMicrolocalizacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datosAdicionalesPm.getId();
                if (findDatosAdicionalesPm(id) == null) {
                    throw new NonexistentEntityException("The datosAdicionalesPm with id " + id + " no longer exists.");
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
            DatosAdicionalesPm datosAdicionalesPm;
            try {
                datosAdicionalesPm = em.getReference(DatosAdicionalesPm.class, id);
                datosAdicionalesPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datosAdicionalesPm with id " + id + " no longer exists.", enfe);
            }
            List<DatosadicionalesMicrolocalizacion> datosadicionalesMicrolocalizacionList = datosAdicionalesPm.getDatosadicionalesMicrolocalizacionList();
            for (DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion : datosadicionalesMicrolocalizacionList) {
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion.setIdDatosAdicionales(null);
                datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacionListDatosadicionalesMicrolocalizacion);
            }
            em.remove(datosAdicionalesPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosAdicionalesPm> findDatosAdicionalesPmEntities() {
        return findDatosAdicionalesPmEntities(true, -1, -1);
    }

    public List<DatosAdicionalesPm> findDatosAdicionalesPmEntities(int maxResults, int firstResult) {
        return findDatosAdicionalesPmEntities(false, maxResults, firstResult);
    }

    private List<DatosAdicionalesPm> findDatosAdicionalesPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatosAdicionalesPm.class));
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

    public DatosAdicionalesPm findDatosAdicionalesPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosAdicionalesPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosAdicionalesPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatosAdicionalesPm> rt = cq.from(DatosAdicionalesPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
