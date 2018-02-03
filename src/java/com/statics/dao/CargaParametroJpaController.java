/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.IllegalOrphanException;
import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.CargaParametro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Parametros;
import com.statics.vo.Cargas;
import com.statics.vo.Datos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class CargaParametroJpaController implements Serializable {

    public CargaParametroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargaParametro cargaParametro) {
        if (cargaParametro.getDatosList() == null) {
            cargaParametro.setDatosList(new ArrayList<Datos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametros paraId = cargaParametro.getParaId();
            if (paraId != null) {
                paraId = em.getReference(paraId.getClass(), paraId.getParaId());
                cargaParametro.setParaId(paraId);
            }
            Cargas cargId = cargaParametro.getCargId();
            if (cargId != null) {
                cargId = em.getReference(cargId.getClass(), cargId.getCargId());
                cargaParametro.setCargId(cargId);
            }
            List<Datos> attachedDatosList = new ArrayList<Datos>();
            for (Datos datosListDatosToAttach : cargaParametro.getDatosList()) {
                datosListDatosToAttach = em.getReference(datosListDatosToAttach.getClass(), datosListDatosToAttach.getDatoId());
                attachedDatosList.add(datosListDatosToAttach);
            }
            cargaParametro.setDatosList(attachedDatosList);
            em.persist(cargaParametro);
            if (paraId != null) {
                paraId.getCargaParametrolist().add(cargaParametro);
                paraId = em.merge(paraId);
            }
            if (cargId != null) {
                cargId.getCargaParametroList().add(cargaParametro);
                cargId = em.merge(cargId);
            }
            for (Datos datosListDatos : cargaParametro.getDatosList()) {
                CargaParametro oldPapuIdOfDatosListDatos = datosListDatos.getPapuId();
                datosListDatos.setPapuId(cargaParametro);
                datosListDatos = em.merge(datosListDatos);
                if (oldPapuIdOfDatosListDatos != null) {
                    oldPapuIdOfDatosListDatos.getDatosList().remove(datosListDatos);
                    oldPapuIdOfDatosListDatos = em.merge(oldPapuIdOfDatosListDatos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargaParametro cargaParametro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargaParametro persistentCargaParametro = em.find(CargaParametro.class, cargaParametro.getCapaId());
            Parametros paraIdOld = persistentCargaParametro.getParaId();
            Parametros paraIdNew = cargaParametro.getParaId();
            Cargas cargIdOld = persistentCargaParametro.getCargId();
            Cargas cargIdNew = cargaParametro.getCargId();
            List<Datos> datosListOld = persistentCargaParametro.getDatosList();
            List<Datos> datosListNew = cargaParametro.getDatosList();
            List<String> illegalOrphanMessages = null;
            for (Datos datosListOldDatos : datosListOld) {
                if (!datosListNew.contains(datosListOldDatos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Datos " + datosListOldDatos + " since its papuId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paraIdNew != null) {
                paraIdNew = em.getReference(paraIdNew.getClass(), paraIdNew.getParaId());
                cargaParametro.setParaId(paraIdNew);
            }
            if (cargIdNew != null) {
                cargIdNew = em.getReference(cargIdNew.getClass(), cargIdNew.getCargId());
                cargaParametro.setCargId(cargIdNew);
            }
            List<Datos> attachedDatosListNew = new ArrayList<Datos>();
            for (Datos datosListNewDatosToAttach : datosListNew) {
                datosListNewDatosToAttach = em.getReference(datosListNewDatosToAttach.getClass(), datosListNewDatosToAttach.getDatoId());
                attachedDatosListNew.add(datosListNewDatosToAttach);
            }
            datosListNew = attachedDatosListNew;
            cargaParametro.setDatosList(datosListNew);
            cargaParametro = em.merge(cargaParametro);
            if (paraIdOld != null && !paraIdOld.equals(paraIdNew)) {
                paraIdOld.getCargaParametrolist().remove(cargaParametro);
                paraIdOld = em.merge(paraIdOld);
            }
            if (paraIdNew != null && !paraIdNew.equals(paraIdOld)) {
                paraIdNew.getCargaParametrolist().add(cargaParametro);
                paraIdNew = em.merge(paraIdNew);
            }
            if (cargIdOld != null && !cargIdOld.equals(cargIdNew)) {
                cargIdOld.getCargaParametroList().remove(cargaParametro);
                cargIdOld = em.merge(cargIdOld);
            }
            if (cargIdNew != null && !cargIdNew.equals(cargIdOld)) {
                cargIdNew.getCargaParametroList().add(cargaParametro);
                cargIdNew = em.merge(cargIdNew);
            }
            for (Datos datosListNewDatos : datosListNew) {
                if (!datosListOld.contains(datosListNewDatos)) {
                    CargaParametro oldPapuIdOfDatosListNewDatos = datosListNewDatos.getPapuId();
                    datosListNewDatos.setPapuId(cargaParametro);
                    datosListNewDatos = em.merge(datosListNewDatos);
                    if (oldPapuIdOfDatosListNewDatos != null && !oldPapuIdOfDatosListNewDatos.equals(cargaParametro)) {
                        oldPapuIdOfDatosListNewDatos.getDatosList().remove(datosListNewDatos);
                        oldPapuIdOfDatosListNewDatos = em.merge(oldPapuIdOfDatosListNewDatos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargaParametro.getCapaId();
                if (findCargaParametro(id) == null) {
                    throw new NonexistentEntityException("The cargaParametro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargaParametro cargaParametro;
            try {
                cargaParametro = em.getReference(CargaParametro.class, id);
                cargaParametro.getCapaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargaParametro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Datos> datosListOrphanCheck = cargaParametro.getDatosList();
            for (Datos datosListOrphanCheckDatos : datosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CargaParametro (" + cargaParametro + ") cannot be destroyed since the Datos " + datosListOrphanCheckDatos + " in its datosList field has a non-nullable papuId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Parametros paraId = cargaParametro.getParaId();
            if (paraId != null) {
                paraId.getCargaParametrolist().remove(cargaParametro);
                paraId = em.merge(paraId);
            }
            Cargas cargId = cargaParametro.getCargId();
            if (cargId != null) {
                cargId.getCargaParametroList().remove(cargaParametro);
                cargId = em.merge(cargId);
            }
            em.remove(cargaParametro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargaParametro> findCargaParametroEntities() {
        return findCargaParametroEntities(true, -1, -1);
    }

    public List<CargaParametro> findCargaParametroEntities(int maxResults, int firstResult) {
        return findCargaParametroEntities(false, maxResults, firstResult);
    }

    private List<CargaParametro> findCargaParametroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargaParametro.class));
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

    public CargaParametro findCargaParametro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargaParametro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargaParametroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargaParametro> rt = cq.from(CargaParametro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
