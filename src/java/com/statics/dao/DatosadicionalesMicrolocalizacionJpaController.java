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
import com.statics.vo.DatosAdicionalesPm;
import com.statics.vo.DatosadicionalesMicrolocalizacion;
import com.statics.vo.MicrolocalizacionPm;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class DatosadicionalesMicrolocalizacionJpaController implements Serializable {

    public DatosadicionalesMicrolocalizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosAdicionalesPm idDatosAdicionales = datosadicionalesMicrolocalizacion.getIdDatosAdicionales();
            if (idDatosAdicionales != null) {
                idDatosAdicionales = em.getReference(idDatosAdicionales.getClass(), idDatosAdicionales.getId());
                datosadicionalesMicrolocalizacion.setIdDatosAdicionales(idDatosAdicionales);
            }
            MicrolocalizacionPm idMicrolocalizacion = datosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion = em.getReference(idMicrolocalizacion.getClass(), idMicrolocalizacion.getId());
                datosadicionalesMicrolocalizacion.setIdMicrolocalizacion(idMicrolocalizacion);
            }
            em.persist(datosadicionalesMicrolocalizacion);
            if (idDatosAdicionales != null) {
                idDatosAdicionales.getDatosadicionalesMicrolocalizacionList().add(datosadicionalesMicrolocalizacion);
                idDatosAdicionales = em.merge(idDatosAdicionales);
            }
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().add(datosadicionalesMicrolocalizacion);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosadicionalesMicrolocalizacion persistentDatosadicionalesMicrolocalizacion = em.find(DatosadicionalesMicrolocalizacion.class, datosadicionalesMicrolocalizacion.getId());
            DatosAdicionalesPm idDatosAdicionalesOld = persistentDatosadicionalesMicrolocalizacion.getIdDatosAdicionales();
            DatosAdicionalesPm idDatosAdicionalesNew = datosadicionalesMicrolocalizacion.getIdDatosAdicionales();
            MicrolocalizacionPm idMicrolocalizacionOld = persistentDatosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
            MicrolocalizacionPm idMicrolocalizacionNew = datosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
            if (idDatosAdicionalesNew != null) {
                idDatosAdicionalesNew = em.getReference(idDatosAdicionalesNew.getClass(), idDatosAdicionalesNew.getId());
                datosadicionalesMicrolocalizacion.setIdDatosAdicionales(idDatosAdicionalesNew);
            }
            if (idMicrolocalizacionNew != null) {
                idMicrolocalizacionNew = em.getReference(idMicrolocalizacionNew.getClass(), idMicrolocalizacionNew.getId());
                datosadicionalesMicrolocalizacion.setIdMicrolocalizacion(idMicrolocalizacionNew);
            }
            datosadicionalesMicrolocalizacion = em.merge(datosadicionalesMicrolocalizacion);
            if (idDatosAdicionalesOld != null && !idDatosAdicionalesOld.equals(idDatosAdicionalesNew)) {
                idDatosAdicionalesOld.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacion);
                idDatosAdicionalesOld = em.merge(idDatosAdicionalesOld);
            }
            if (idDatosAdicionalesNew != null && !idDatosAdicionalesNew.equals(idDatosAdicionalesOld)) {
                idDatosAdicionalesNew.getDatosadicionalesMicrolocalizacionList().add(datosadicionalesMicrolocalizacion);
                idDatosAdicionalesNew = em.merge(idDatosAdicionalesNew);
            }
            if (idMicrolocalizacionOld != null && !idMicrolocalizacionOld.equals(idMicrolocalizacionNew)) {
                idMicrolocalizacionOld.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacion);
                idMicrolocalizacionOld = em.merge(idMicrolocalizacionOld);
            }
            if (idMicrolocalizacionNew != null && !idMicrolocalizacionNew.equals(idMicrolocalizacionOld)) {
                idMicrolocalizacionNew.getDatosadicionalesMicrolocalizacionList().add(datosadicionalesMicrolocalizacion);
                idMicrolocalizacionNew = em.merge(idMicrolocalizacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datosadicionalesMicrolocalizacion.getId();
                if (findDatosadicionalesMicrolocalizacion(id) == null) {
                    throw new NonexistentEntityException("The datosadicionalesMicrolocalizacion with id " + id + " no longer exists.");
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
            DatosadicionalesMicrolocalizacion datosadicionalesMicrolocalizacion;
            try {
                datosadicionalesMicrolocalizacion = em.getReference(DatosadicionalesMicrolocalizacion.class, id);
                datosadicionalesMicrolocalizacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datosadicionalesMicrolocalizacion with id " + id + " no longer exists.", enfe);
            }
            DatosAdicionalesPm idDatosAdicionales = datosadicionalesMicrolocalizacion.getIdDatosAdicionales();
            if (idDatosAdicionales != null) {
                idDatosAdicionales.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacion);
                idDatosAdicionales = em.merge(idDatosAdicionales);
            }
            MicrolocalizacionPm idMicrolocalizacion = datosadicionalesMicrolocalizacion.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getDatosadicionalesMicrolocalizacionList().remove(datosadicionalesMicrolocalizacion);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            em.remove(datosadicionalesMicrolocalizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosadicionalesMicrolocalizacion> findDatosadicionalesMicrolocalizacionEntities() {
        return findDatosadicionalesMicrolocalizacionEntities(true, -1, -1);
    }

    public List<DatosadicionalesMicrolocalizacion> findDatosadicionalesMicrolocalizacionEntities(int maxResults, int firstResult) {
        return findDatosadicionalesMicrolocalizacionEntities(false, maxResults, firstResult);
    }

    private List<DatosadicionalesMicrolocalizacion> findDatosadicionalesMicrolocalizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatosadicionalesMicrolocalizacion.class));
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

    public DatosadicionalesMicrolocalizacion findDatosadicionalesMicrolocalizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosadicionalesMicrolocalizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosadicionalesMicrolocalizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatosadicionalesMicrolocalizacion> rt = cq.from(DatosadicionalesMicrolocalizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
