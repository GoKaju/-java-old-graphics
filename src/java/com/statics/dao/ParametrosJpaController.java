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
import com.statics.vo.CargaParametro;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.ParametroLabels;
import com.statics.vo.Parametros;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
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
        if (parametros.getCargaParametrolist() == null) {
            parametros.setCargaParametrolist(new ArrayList<CargaParametro>());
        }
        if (parametros.getParametroLabelsList() == null) {
            parametros.setParametroLabelsList(new ArrayList<ParametroLabels>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CargaParametro> attachedCargaParametrolist = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametrolistCargaParametroToAttach : parametros.getCargaParametrolist()) {
                cargaParametrolistCargaParametroToAttach = em.getReference(cargaParametrolistCargaParametroToAttach.getClass(), cargaParametrolistCargaParametroToAttach.getCapaId());
                attachedCargaParametrolist.add(cargaParametrolistCargaParametroToAttach);
            }
            parametros.setCargaParametrolist(attachedCargaParametrolist);
            List<ParametroLabels> attachedParametroLabelsList = new ArrayList<ParametroLabels>();
            for (ParametroLabels parametroLabelsListParametroLabelsToAttach : parametros.getParametroLabelsList()) {
                parametroLabelsListParametroLabelsToAttach = em.getReference(parametroLabelsListParametroLabelsToAttach.getClass(), parametroLabelsListParametroLabelsToAttach.getPalaId());
                attachedParametroLabelsList.add(parametroLabelsListParametroLabelsToAttach);
            }
            parametros.setParametroLabelsList(attachedParametroLabelsList);
            em.persist(parametros);
            for (CargaParametro cargaParametrolistCargaParametro : parametros.getCargaParametrolist()) {
                Parametros oldParaIdOfCargaParametrolistCargaParametro = cargaParametrolistCargaParametro.getParaId();
                cargaParametrolistCargaParametro.setParaId(parametros);
                cargaParametrolistCargaParametro = em.merge(cargaParametrolistCargaParametro);
                if (oldParaIdOfCargaParametrolistCargaParametro != null) {
                    oldParaIdOfCargaParametrolistCargaParametro.getCargaParametrolist().remove(cargaParametrolistCargaParametro);
                    oldParaIdOfCargaParametrolistCargaParametro = em.merge(oldParaIdOfCargaParametrolistCargaParametro);
                }
            }
            for (ParametroLabels parametroLabelsListParametroLabels : parametros.getParametroLabelsList()) {
                Parametros oldParaIdOfParametroLabelsListParametroLabels = parametroLabelsListParametroLabels.getParaId();
                parametroLabelsListParametroLabels.setParaId(parametros);
                parametroLabelsListParametroLabels = em.merge(parametroLabelsListParametroLabels);
                if (oldParaIdOfParametroLabelsListParametroLabels != null) {
                    oldParaIdOfParametroLabelsListParametroLabels.getParametroLabelsList().remove(parametroLabelsListParametroLabels);
                    oldParaIdOfParametroLabelsListParametroLabels = em.merge(oldParaIdOfParametroLabelsListParametroLabels);
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
            List<CargaParametro> cargaParametrolistOld = persistentParametros.getCargaParametrolist();
            List<CargaParametro> cargaParametrolistNew = parametros.getCargaParametrolist();
            List<ParametroLabels> parametroLabelsListOld = persistentParametros.getParametroLabelsList();
            List<ParametroLabels> parametroLabelsListNew = parametros.getParametroLabelsList();
            List<String> illegalOrphanMessages = null;
            for (CargaParametro cargaParametrolistOldCargaParametro : cargaParametrolistOld) {
                if (!cargaParametrolistNew.contains(cargaParametrolistOldCargaParametro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargaParametro " + cargaParametrolistOldCargaParametro + " since its paraId field is not nullable.");
                }
            }
            for (ParametroLabels parametroLabelsListOldParametroLabels : parametroLabelsListOld) {
                if (!parametroLabelsListNew.contains(parametroLabelsListOldParametroLabels)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ParametroLabels " + parametroLabelsListOldParametroLabels + " since its paraId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CargaParametro> attachedCargaParametrolistNew = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametrolistNewCargaParametroToAttach : cargaParametrolistNew) {
                cargaParametrolistNewCargaParametroToAttach = em.getReference(cargaParametrolistNewCargaParametroToAttach.getClass(), cargaParametrolistNewCargaParametroToAttach.getCapaId());
                attachedCargaParametrolistNew.add(cargaParametrolistNewCargaParametroToAttach);
            }
            cargaParametrolistNew = attachedCargaParametrolistNew;
            parametros.setCargaParametrolist(cargaParametrolistNew);
            List<ParametroLabels> attachedParametroLabelsListNew = new ArrayList<ParametroLabels>();
            for (ParametroLabels parametroLabelsListNewParametroLabelsToAttach : parametroLabelsListNew) {
                parametroLabelsListNewParametroLabelsToAttach = em.getReference(parametroLabelsListNewParametroLabelsToAttach.getClass(), parametroLabelsListNewParametroLabelsToAttach.getPalaId());
                attachedParametroLabelsListNew.add(parametroLabelsListNewParametroLabelsToAttach);
            }
            parametroLabelsListNew = attachedParametroLabelsListNew;
            parametros.setParametroLabelsList(parametroLabelsListNew);
            parametros = em.merge(parametros);
            for (CargaParametro cargaParametrolistNewCargaParametro : cargaParametrolistNew) {
                if (!cargaParametrolistOld.contains(cargaParametrolistNewCargaParametro)) {
                    Parametros oldParaIdOfCargaParametrolistNewCargaParametro = cargaParametrolistNewCargaParametro.getParaId();
                    cargaParametrolistNewCargaParametro.setParaId(parametros);
                    cargaParametrolistNewCargaParametro = em.merge(cargaParametrolistNewCargaParametro);
                    if (oldParaIdOfCargaParametrolistNewCargaParametro != null && !oldParaIdOfCargaParametrolistNewCargaParametro.equals(parametros)) {
                        oldParaIdOfCargaParametrolistNewCargaParametro.getCargaParametrolist().remove(cargaParametrolistNewCargaParametro);
                        oldParaIdOfCargaParametrolistNewCargaParametro = em.merge(oldParaIdOfCargaParametrolistNewCargaParametro);
                    }
                }
            }
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
            List<CargaParametro> cargaParametrolistOrphanCheck = parametros.getCargaParametrolist();
            for (CargaParametro cargaParametrolistOrphanCheckCargaParametro : cargaParametrolistOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parametros (" + parametros + ") cannot be destroyed since the CargaParametro " + cargaParametrolistOrphanCheckCargaParametro + " in its cargaParametrolist field has a non-nullable paraId field.");
            }
            List<ParametroLabels> parametroLabelsListOrphanCheck = parametros.getParametroLabelsList();
            for (ParametroLabels parametroLabelsListOrphanCheckParametroLabels : parametroLabelsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Parametros (" + parametros + ") cannot be destroyed since the ParametroLabels " + parametroLabelsListOrphanCheckParametroLabels + " in its parametroLabelsList field has a non-nullable paraId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
