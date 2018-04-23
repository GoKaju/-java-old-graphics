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
import com.statics.vo.PuntoMuestral;
import com.statics.vo.CargaErrores;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.CargaParametro;
import com.statics.vo.Cargas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class CargasJpaController implements Serializable {

    public CargasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargas cargas) {
        if (cargas.getCargaErroresList() == null) {
            cargas.setCargaErroresList(new ArrayList<CargaErrores>());
        }
        if (cargas.getCargaParametroList() == null) {
            cargas.setCargaParametroList(new ArrayList<CargaParametro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoMuestral pumuId = cargas.getPumuId();
            if (pumuId != null) {
                pumuId = em.getReference(pumuId.getClass(), pumuId.getPumuId());
                cargas.setPumuId(pumuId);
            }
            List<CargaErrores> attachedCargaErroresList = new ArrayList<CargaErrores>();
            for (CargaErrores cargaErroresListCargaErroresToAttach : cargas.getCargaErroresList()) {
                cargaErroresListCargaErroresToAttach = em.getReference(cargaErroresListCargaErroresToAttach.getClass(), cargaErroresListCargaErroresToAttach.getCaerId());
                attachedCargaErroresList.add(cargaErroresListCargaErroresToAttach);
            }
            cargas.setCargaErroresList(attachedCargaErroresList);
            List<CargaParametro> attachedCargaParametroList = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametroListCargaParametroToAttach : cargas.getCargaParametroList()) {
                cargaParametroListCargaParametroToAttach = em.getReference(cargaParametroListCargaParametroToAttach.getClass(), cargaParametroListCargaParametroToAttach.getCapaId());
                attachedCargaParametroList.add(cargaParametroListCargaParametroToAttach);
            }
            cargas.setCargaParametroList(attachedCargaParametroList);
            em.persist(cargas);
            if (pumuId != null) {
                pumuId.getCargasList().add(cargas);
                pumuId = em.merge(pumuId);
            }
            for (CargaErrores cargaErroresListCargaErrores : cargas.getCargaErroresList()) {
                Cargas oldCargIdOfCargaErroresListCargaErrores = cargaErroresListCargaErrores.getCargId();
                cargaErroresListCargaErrores.setCargId(cargas);
                cargaErroresListCargaErrores = em.merge(cargaErroresListCargaErrores);
                if (oldCargIdOfCargaErroresListCargaErrores != null) {
                    oldCargIdOfCargaErroresListCargaErrores.getCargaErroresList().remove(cargaErroresListCargaErrores);
                    oldCargIdOfCargaErroresListCargaErrores = em.merge(oldCargIdOfCargaErroresListCargaErrores);
                }
            }
            for (CargaParametro cargaParametroListCargaParametro : cargas.getCargaParametroList()) {
                Cargas oldCargIdOfCargaParametroListCargaParametro = cargaParametroListCargaParametro.getCargId();
                cargaParametroListCargaParametro.setCargId(cargas);
                cargaParametroListCargaParametro = em.merge(cargaParametroListCargaParametro);
                if (oldCargIdOfCargaParametroListCargaParametro != null) {
                    oldCargIdOfCargaParametroListCargaParametro.getCargaParametroList().remove(cargaParametroListCargaParametro);
                    oldCargIdOfCargaParametroListCargaParametro = em.merge(oldCargIdOfCargaParametroListCargaParametro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargas cargas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargas persistentCargas = em.find(Cargas.class, cargas.getCargId());
            PuntoMuestral pumuIdOld = persistentCargas.getPumuId();
            PuntoMuestral pumuIdNew = cargas.getPumuId();
            List<CargaErrores> cargaErroresListOld = persistentCargas.getCargaErroresList();
            List<CargaErrores> cargaErroresListNew = cargas.getCargaErroresList();
            List<CargaParametro> cargaParametroListOld = persistentCargas.getCargaParametroList();
            List<CargaParametro> cargaParametroListNew = cargas.getCargaParametroList();
            List<String> illegalOrphanMessages = null;
            for (CargaErrores cargaErroresListOldCargaErrores : cargaErroresListOld) {
                if (!cargaErroresListNew.contains(cargaErroresListOldCargaErrores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargaErrores " + cargaErroresListOldCargaErrores + " since its cargId field is not nullable.");
                }
            }
            for (CargaParametro cargaParametroListOldCargaParametro : cargaParametroListOld) {
                if (!cargaParametroListNew.contains(cargaParametroListOldCargaParametro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargaParametro " + cargaParametroListOldCargaParametro + " since its cargId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pumuIdNew != null) {
                pumuIdNew = em.getReference(pumuIdNew.getClass(), pumuIdNew.getPumuId());
                cargas.setPumuId(pumuIdNew);
            }
            List<CargaErrores> attachedCargaErroresListNew = new ArrayList<CargaErrores>();
            for (CargaErrores cargaErroresListNewCargaErroresToAttach : cargaErroresListNew) {
                cargaErroresListNewCargaErroresToAttach = em.getReference(cargaErroresListNewCargaErroresToAttach.getClass(), cargaErroresListNewCargaErroresToAttach.getCaerId());
                attachedCargaErroresListNew.add(cargaErroresListNewCargaErroresToAttach);
            }
            cargaErroresListNew = attachedCargaErroresListNew;
            cargas.setCargaErroresList(cargaErroresListNew);
            List<CargaParametro> attachedCargaParametroListNew = new ArrayList<CargaParametro>();
            for (CargaParametro cargaParametroListNewCargaParametroToAttach : cargaParametroListNew) {
                cargaParametroListNewCargaParametroToAttach = em.getReference(cargaParametroListNewCargaParametroToAttach.getClass(), cargaParametroListNewCargaParametroToAttach.getCapaId());
                attachedCargaParametroListNew.add(cargaParametroListNewCargaParametroToAttach);
            }
            cargaParametroListNew = attachedCargaParametroListNew;
            cargas.setCargaParametroList(cargaParametroListNew);
            cargas = em.merge(cargas);
            if (pumuIdOld != null && !pumuIdOld.equals(pumuIdNew)) {
                pumuIdOld.getCargasList().remove(cargas);
                pumuIdOld = em.merge(pumuIdOld);
            }
            if (pumuIdNew != null && !pumuIdNew.equals(pumuIdOld)) {
                pumuIdNew.getCargasList().add(cargas);
                pumuIdNew = em.merge(pumuIdNew);
            }
            for (CargaErrores cargaErroresListNewCargaErrores : cargaErroresListNew) {
                if (!cargaErroresListOld.contains(cargaErroresListNewCargaErrores)) {
                    Cargas oldCargIdOfCargaErroresListNewCargaErrores = cargaErroresListNewCargaErrores.getCargId();
                    cargaErroresListNewCargaErrores.setCargId(cargas);
                    cargaErroresListNewCargaErrores = em.merge(cargaErroresListNewCargaErrores);
                    if (oldCargIdOfCargaErroresListNewCargaErrores != null && !oldCargIdOfCargaErroresListNewCargaErrores.equals(cargas)) {
                        oldCargIdOfCargaErroresListNewCargaErrores.getCargaErroresList().remove(cargaErroresListNewCargaErrores);
                        oldCargIdOfCargaErroresListNewCargaErrores = em.merge(oldCargIdOfCargaErroresListNewCargaErrores);
                    }
                }
            }
            for (CargaParametro cargaParametroListNewCargaParametro : cargaParametroListNew) {
                if (!cargaParametroListOld.contains(cargaParametroListNewCargaParametro)) {
                    Cargas oldCargIdOfCargaParametroListNewCargaParametro = cargaParametroListNewCargaParametro.getCargId();
                    cargaParametroListNewCargaParametro.setCargId(cargas);
                    cargaParametroListNewCargaParametro = em.merge(cargaParametroListNewCargaParametro);
                    if (oldCargIdOfCargaParametroListNewCargaParametro != null && !oldCargIdOfCargaParametroListNewCargaParametro.equals(cargas)) {
                        oldCargIdOfCargaParametroListNewCargaParametro.getCargaParametroList().remove(cargaParametroListNewCargaParametro);
                        oldCargIdOfCargaParametroListNewCargaParametro = em.merge(oldCargIdOfCargaParametroListNewCargaParametro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargas.getCargId();
                if (findCargas(id) == null) {
                    throw new NonexistentEntityException("The cargas with id " + id + " no longer exists.");
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
            Cargas cargas;
            try {
                cargas = em.getReference(Cargas.class, id);
                cargas.getCargId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CargaErrores> cargaErroresListOrphanCheck = cargas.getCargaErroresList();
            for (CargaErrores cargaErroresListOrphanCheckCargaErrores : cargaErroresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargas (" + cargas + ") cannot be destroyed since the CargaErrores " + cargaErroresListOrphanCheckCargaErrores + " in its cargaErroresList field has a non-nullable cargId field.");
            }
            List<CargaParametro> cargaParametroListOrphanCheck = cargas.getCargaParametroList();
            for (CargaParametro cargaParametroListOrphanCheckCargaParametro : cargaParametroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargas (" + cargas + ") cannot be destroyed since the CargaParametro " + cargaParametroListOrphanCheckCargaParametro + " in its cargaParametroList field has a non-nullable cargId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PuntoMuestral pumuId = cargas.getPumuId();
            if (pumuId != null) {
                pumuId.getCargasList().remove(cargas);
                pumuId = em.merge(pumuId);
            }
            em.remove(cargas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargas> findCargasEntities() {
        return findCargasEntities(true, -1, -1);
    }

    public List<Cargas> findCargasEntities(int maxResults, int firstResult) {
        return findCargasEntities(false, maxResults, firstResult);
    }

    private List<Cargas> findCargasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargas.class));
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

    public Cargas findCargas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargas> rt = cq.from(Cargas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
