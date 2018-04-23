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
import com.statics.vo.ParametroLabels;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.ParametroFactorconversion;
import com.statics.vo.CargaParametro;
import com.statics.vo.Parametros;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ParametrosJpaController implements Serializable {

    public ParametrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parametros parametros) {
        if (parametros.getParametroLabelsList() == null) {
            parametros.setParametroLabelsList(new ArrayList<ParametroLabels>());
        }
        if (parametros.getParametroFactorconversionList() == null) {
            parametros.setParametroFactorconversionList(new ArrayList<ParametroFactorconversion>());
        }
        if (parametros.getCargaParametroList() == null) {
            parametros.setCargaParametroList(new ArrayList<CargaParametro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ParametroLabels> attachedParametroLabelsList = new ArrayList<ParametroLabels>();
            for (ParametroLabels parametroLabelsListParametroLabelsToAttach : parametros.getParametroLabelsList()) {
                parametroLabelsListParametroLabelsToAttach = em.getReference(parametroLabelsListParametroLabelsToAttach.getClass(), parametroLabelsListParametroLabelsToAttach.getPalaId());
                attachedParametroLabelsList.add(parametroLabelsListParametroLabelsToAttach);
            }
            parametros.setParametroLabelsList(attachedParametroLabelsList);
            List<ParametroFactorconversion> attachedParametroFactorconversionList = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversionToAttach : parametros.getParametroFactorconversionList()) {
                parametroFactorconversionListParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListParametroFactorconversionToAttach.getClass(), parametroFactorconversionListParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionList.add(parametroFactorconversionListParametroFactorconversionToAttach);
            }
            parametros.setParametroFactorconversionList(attachedParametroFactorconversionList);
            List<CargaParametro> attachedCargaParametroList = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametroListCargaParametroToAttach : parametros.getCargaParametroList()) {
                cargaParametroListCargaParametroToAttach = em.getReference(cargaParametroListCargaParametroToAttach.getClass(), cargaParametroListCargaParametroToAttach.getCapaId());
                attachedCargaParametroList.add(cargaParametroListCargaParametroToAttach);
            }
            parametros.setCargaParametroList(attachedCargaParametroList);
            em.persist(parametros);
            for (ParametroLabels parametroLabelsListParametroLabels : parametros.getParametroLabelsList()) {
                Parametros oldParaIdOfParametroLabelsListParametroLabels = parametroLabelsListParametroLabels.getParaId();
                parametroLabelsListParametroLabels.setParaId(parametros);
                parametroLabelsListParametroLabels = em.merge(parametroLabelsListParametroLabels);
                if (oldParaIdOfParametroLabelsListParametroLabels != null) {
                    oldParaIdOfParametroLabelsListParametroLabels.getParametroLabelsList().remove(parametroLabelsListParametroLabels);
                    oldParaIdOfParametroLabelsListParametroLabels = em.merge(oldParaIdOfParametroLabelsListParametroLabels);
                }
            }
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : parametros.getParametroFactorconversionList()) {
                Parametros oldIdParametroOfParametroFactorconversionListParametroFactorconversion = parametroFactorconversionListParametroFactorconversion.getIdParametro();
                parametroFactorconversionListParametroFactorconversion.setIdParametro(parametros);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
                if (oldIdParametroOfParametroFactorconversionListParametroFactorconversion != null) {
                    oldIdParametroOfParametroFactorconversionListParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListParametroFactorconversion);
                    oldIdParametroOfParametroFactorconversionListParametroFactorconversion = em.merge(oldIdParametroOfParametroFactorconversionListParametroFactorconversion);
                }
            }
            for (CargaParametro cargaParametroListCargaParametro : parametros.getCargaParametroList()) {
                Parametros oldParaIdOfCargaParametroListCargaParametro = cargaParametroListCargaParametro.getParaId();
                cargaParametroListCargaParametro.setParaId(parametros);
                cargaParametroListCargaParametro = em.merge(cargaParametroListCargaParametro);
                if (oldParaIdOfCargaParametroListCargaParametro != null) {
                    oldParaIdOfCargaParametroListCargaParametro.getCargaParametroList().remove(cargaParametroListCargaParametro);
                    oldParaIdOfCargaParametroListCargaParametro = em.merge(oldParaIdOfCargaParametroListCargaParametro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parametros parametros) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametros persistentParametros = em.find(Parametros.class, parametros.getParaId());
            List<ParametroLabels> parametroLabelsListOld = persistentParametros.getParametroLabelsList();
            List<ParametroLabels> parametroLabelsListNew = parametros.getParametroLabelsList();
            List<ParametroFactorconversion> parametroFactorconversionListOld = persistentParametros.getParametroFactorconversionList();
            List<ParametroFactorconversion> parametroFactorconversionListNew = parametros.getParametroFactorconversionList();
            List<CargaParametro> cargaParametroListOld = persistentParametros.getCargaParametroList();
            List<CargaParametro> cargaParametroListNew = parametros.getCargaParametroList();
            List<String> illegalOrphanMessages = null;
            for (ParametroLabels parametroLabelsListOldParametroLabels : parametroLabelsListOld) {
                if (!parametroLabelsListNew.contains(parametroLabelsListOldParametroLabels)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParametroLabels " + parametroLabelsListOldParametroLabels + " since its paraId field is not nullable.");
                }
            }
            for (CargaParametro cargaParametroListOldCargaParametro : cargaParametroListOld) {
                if (!cargaParametroListNew.contains(cargaParametroListOldCargaParametro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargaParametro " + cargaParametroListOldCargaParametro + " since its paraId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ParametroLabels> attachedParametroLabelsListNew = new ArrayList<ParametroLabels>();
            for (ParametroLabels parametroLabelsListNewParametroLabelsToAttach : parametroLabelsListNew) {
                parametroLabelsListNewParametroLabelsToAttach = em.getReference(parametroLabelsListNewParametroLabelsToAttach.getClass(), parametroLabelsListNewParametroLabelsToAttach.getPalaId());
                attachedParametroLabelsListNew.add(parametroLabelsListNewParametroLabelsToAttach);
            }
            parametroLabelsListNew = attachedParametroLabelsListNew;
            parametros.setParametroLabelsList(parametroLabelsListNew);
            List<ParametroFactorconversion> attachedParametroFactorconversionListNew = new ArrayList<ParametroFactorconversion>();
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversionToAttach : parametroFactorconversionListNew) {
                parametroFactorconversionListNewParametroFactorconversionToAttach = em.getReference(parametroFactorconversionListNewParametroFactorconversionToAttach.getClass(), parametroFactorconversionListNewParametroFactorconversionToAttach.getId());
                attachedParametroFactorconversionListNew.add(parametroFactorconversionListNewParametroFactorconversionToAttach);
            }
            parametroFactorconversionListNew = attachedParametroFactorconversionListNew;
            parametros.setParametroFactorconversionList(parametroFactorconversionListNew);
            List<CargaParametro> attachedCargaParametroListNew = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametroListNewCargaParametroToAttach : cargaParametroListNew) {
                cargaParametroListNewCargaParametroToAttach = em.getReference(cargaParametroListNewCargaParametroToAttach.getClass(), cargaParametroListNewCargaParametroToAttach.getCapaId());
                attachedCargaParametroListNew.add(cargaParametroListNewCargaParametroToAttach);
            }
            cargaParametroListNew = attachedCargaParametroListNew;
            parametros.setCargaParametroList(cargaParametroListNew);
            parametros = em.merge(parametros);
            for (ParametroLabels parametroLabelsListNewParametroLabels : parametroLabelsListNew) {
                if (!parametroLabelsListOld.contains(parametroLabelsListNewParametroLabels)) {
                    Parametros oldParaIdOfParametroLabelsListNewParametroLabels = parametroLabelsListNewParametroLabels.getParaId();
                    parametroLabelsListNewParametroLabels.setParaId(parametros);
                    parametroLabelsListNewParametroLabels = em.merge(parametroLabelsListNewParametroLabels);
                    if (oldParaIdOfParametroLabelsListNewParametroLabels != null && !oldParaIdOfParametroLabelsListNewParametroLabels.equals(parametros)) {
                        oldParaIdOfParametroLabelsListNewParametroLabels.getParametroLabelsList().remove(parametroLabelsListNewParametroLabels);
                        oldParaIdOfParametroLabelsListNewParametroLabels = em.merge(oldParaIdOfParametroLabelsListNewParametroLabels);
                    }
                }
            }
            for (ParametroFactorconversion parametroFactorconversionListOldParametroFactorconversion : parametroFactorconversionListOld) {
                if (!parametroFactorconversionListNew.contains(parametroFactorconversionListOldParametroFactorconversion)) {
                    parametroFactorconversionListOldParametroFactorconversion.setIdParametro(null);
                    parametroFactorconversionListOldParametroFactorconversion = em.merge(parametroFactorconversionListOldParametroFactorconversion);
                }
            }
            for (ParametroFactorconversion parametroFactorconversionListNewParametroFactorconversion : parametroFactorconversionListNew) {
                if (!parametroFactorconversionListOld.contains(parametroFactorconversionListNewParametroFactorconversion)) {
                    Parametros oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion = parametroFactorconversionListNewParametroFactorconversion.getIdParametro();
                    parametroFactorconversionListNewParametroFactorconversion.setIdParametro(parametros);
                    parametroFactorconversionListNewParametroFactorconversion = em.merge(parametroFactorconversionListNewParametroFactorconversion);
                    if (oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion != null && !oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion.equals(parametros)) {
                        oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion.getParametroFactorconversionList().remove(parametroFactorconversionListNewParametroFactorconversion);
                        oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion = em.merge(oldIdParametroOfParametroFactorconversionListNewParametroFactorconversion);
                    }
                }
            }
            for (CargaParametro cargaParametroListNewCargaParametro : cargaParametroListNew) {
                if (!cargaParametroListOld.contains(cargaParametroListNewCargaParametro)) {
                    Parametros oldParaIdOfCargaParametroListNewCargaParametro = cargaParametroListNewCargaParametro.getParaId();
                    cargaParametroListNewCargaParametro.setParaId(parametros);
                    cargaParametroListNewCargaParametro = em.merge(cargaParametroListNewCargaParametro);
                    if (oldParaIdOfCargaParametroListNewCargaParametro != null && !oldParaIdOfCargaParametroListNewCargaParametro.equals(parametros)) {
                        oldParaIdOfCargaParametroListNewCargaParametro.getCargaParametroList().remove(cargaParametroListNewCargaParametro);
                        oldParaIdOfCargaParametroListNewCargaParametro = em.merge(oldParaIdOfCargaParametroListNewCargaParametro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametros.getParaId();
                if (findParametros(id) == null) {
                    throw new NonexistentEntityException("The parametros with id " + id + " no longer exists.");
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
            Parametros parametros;
            try {
                parametros = em.getReference(Parametros.class, id);
                parametros.getParaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametros with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ParametroLabels> parametroLabelsListOrphanCheck = parametros.getParametroLabelsList();
            for (ParametroLabels parametroLabelsListOrphanCheckParametroLabels : parametroLabelsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parametros (" + parametros + ") cannot be destroyed since the ParametroLabels " + parametroLabelsListOrphanCheckParametroLabels + " in its parametroLabelsList field has a non-nullable paraId field.");
            }
            List<CargaParametro> cargaParametroListOrphanCheck = parametros.getCargaParametroList();
            for (CargaParametro cargaParametroListOrphanCheckCargaParametro : cargaParametroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parametros (" + parametros + ") cannot be destroyed since the CargaParametro " + cargaParametroListOrphanCheckCargaParametro + " in its cargaParametroList field has a non-nullable paraId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ParametroFactorconversion> parametroFactorconversionList = parametros.getParametroFactorconversionList();
            for (ParametroFactorconversion parametroFactorconversionListParametroFactorconversion : parametroFactorconversionList) {
                parametroFactorconversionListParametroFactorconversion.setIdParametro(null);
                parametroFactorconversionListParametroFactorconversion = em.merge(parametroFactorconversionListParametroFactorconversion);
            }
            em.remove(parametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parametros> findParametrosEntities() {
        return findParametrosEntities(true, -1, -1);
    }

    public List<Parametros> findParametrosEntities(int maxResults, int firstResult) {
        return findParametrosEntities(false, maxResults, firstResult);
    }

    private List<Parametros> findParametrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parametros.class));
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

    public Parametros findParametros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parametros.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parametros> rt = cq.from(Parametros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
