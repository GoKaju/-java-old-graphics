/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.IllegalOrphanException;
import com.statics.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Cargas;
import com.statics.vo.CaerCampos;
import com.statics.vo.CargaErrores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class CargaErroresJpaController implements Serializable {

    public CargaErroresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargaErrores cargaErrores) {
        if (cargaErrores.getCaerCamposList() == null) {
            cargaErrores.setCaerCamposList(new ArrayList<CaerCampos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargas cargId = cargaErrores.getCargId();
            if (cargId != null) {
                cargId = em.getReference(cargId.getClass(), cargId.getCargId());
                cargaErrores.setCargId(cargId);
            }
            List<CaerCampos> attachedCaerCamposList = new ArrayList<CaerCampos>();
            for (CaerCampos caerCamposListCaerCamposToAttach : cargaErrores.getCaerCamposList()) {
                caerCamposListCaerCamposToAttach = em.getReference(caerCamposListCaerCamposToAttach.getClass(), caerCamposListCaerCamposToAttach.getCacaId());
                attachedCaerCamposList.add(caerCamposListCaerCamposToAttach);
            }
            cargaErrores.setCaerCamposList(attachedCaerCamposList);
            em.persist(cargaErrores);
            if (cargId != null) {
                cargId.getCargaErroresList().add(cargaErrores);
                cargId = em.merge(cargId);
            }
            for (CaerCampos caerCamposListCaerCampos : cargaErrores.getCaerCamposList()) {
                CargaErrores oldCaerIdOfCaerCamposListCaerCampos = caerCamposListCaerCampos.getCaerId();
                caerCamposListCaerCampos.setCaerId(cargaErrores);
                caerCamposListCaerCampos = em.merge(caerCamposListCaerCampos);
                if (oldCaerIdOfCaerCamposListCaerCampos != null) {
                    oldCaerIdOfCaerCamposListCaerCampos.getCaerCamposList().remove(caerCamposListCaerCampos);
                    oldCaerIdOfCaerCamposListCaerCampos = em.merge(oldCaerIdOfCaerCamposListCaerCampos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargaErrores cargaErrores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargaErrores persistentCargaErrores = em.find(CargaErrores.class, cargaErrores.getCaerId());
            Cargas cargIdOld = persistentCargaErrores.getCargId();
            Cargas cargIdNew = cargaErrores.getCargId();
            List<CaerCampos> caerCamposListOld = persistentCargaErrores.getCaerCamposList();
            List<CaerCampos> caerCamposListNew = cargaErrores.getCaerCamposList();
            List<String> illegalOrphanMessages = null;
            for (CaerCampos caerCamposListOldCaerCampos : caerCamposListOld) {
                if (!caerCamposListNew.contains(caerCamposListOldCaerCampos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CaerCampos " + caerCamposListOldCaerCampos + " since its caerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cargIdNew != null) {
                cargIdNew = em.getReference(cargIdNew.getClass(), cargIdNew.getCargId());
                cargaErrores.setCargId(cargIdNew);
            }
            List<CaerCampos> attachedCaerCamposListNew = new ArrayList<CaerCampos>();
            for (CaerCampos caerCamposListNewCaerCamposToAttach : caerCamposListNew) {
                caerCamposListNewCaerCamposToAttach = em.getReference(caerCamposListNewCaerCamposToAttach.getClass(), caerCamposListNewCaerCamposToAttach.getCacaId());
                attachedCaerCamposListNew.add(caerCamposListNewCaerCamposToAttach);
            }
            caerCamposListNew = attachedCaerCamposListNew;
            cargaErrores.setCaerCamposList(caerCamposListNew);
            cargaErrores = em.merge(cargaErrores);
            if (cargIdOld != null && !cargIdOld.equals(cargIdNew)) {
                cargIdOld.getCargaErroresList().remove(cargaErrores);
                cargIdOld = em.merge(cargIdOld);
            }
            if (cargIdNew != null && !cargIdNew.equals(cargIdOld)) {
                cargIdNew.getCargaErroresList().add(cargaErrores);
                cargIdNew = em.merge(cargIdNew);
            }
            for (CaerCampos caerCamposListNewCaerCampos : caerCamposListNew) {
                if (!caerCamposListOld.contains(caerCamposListNewCaerCampos)) {
                    CargaErrores oldCaerIdOfCaerCamposListNewCaerCampos = caerCamposListNewCaerCampos.getCaerId();
                    caerCamposListNewCaerCampos.setCaerId(cargaErrores);
                    caerCamposListNewCaerCampos = em.merge(caerCamposListNewCaerCampos);
                    if (oldCaerIdOfCaerCamposListNewCaerCampos != null && !oldCaerIdOfCaerCamposListNewCaerCampos.equals(cargaErrores)) {
                        oldCaerIdOfCaerCamposListNewCaerCampos.getCaerCamposList().remove(caerCamposListNewCaerCampos);
                        oldCaerIdOfCaerCamposListNewCaerCampos = em.merge(oldCaerIdOfCaerCamposListNewCaerCampos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargaErrores.getCaerId();
                if (findCargaErrores(id) == null) {
                    throw new NonexistentEntityException("The cargaErrores with id " + id + " no longer exists.");
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
            CargaErrores cargaErrores;
            try {
                cargaErrores = em.getReference(CargaErrores.class, id);
                cargaErrores.getCaerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargaErrores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CaerCampos> caerCamposListOrphanCheck = cargaErrores.getCaerCamposList();
            for (CaerCampos caerCamposListOrphanCheckCaerCampos : caerCamposListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CargaErrores (" + cargaErrores + ") cannot be destroyed since the CaerCampos " + caerCamposListOrphanCheckCaerCampos + " in its caerCamposList field has a non-nullable caerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargas cargId = cargaErrores.getCargId();
            if (cargId != null) {
                cargId.getCargaErroresList().remove(cargaErrores);
                cargId = em.merge(cargId);
            }
            em.remove(cargaErrores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargaErrores> findCargaErroresEntities() {
        return findCargaErroresEntities(true, -1, -1);
    }

    public List<CargaErrores> findCargaErroresEntities(int maxResults, int firstResult) {
        return findCargaErroresEntities(false, maxResults, firstResult);
    }

    private List<CargaErrores> findCargaErroresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargaErrores.class));
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

    public CargaErrores findCargaErrores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargaErrores.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargaErroresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargaErrores> rt = cq.from(CargaErrores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
