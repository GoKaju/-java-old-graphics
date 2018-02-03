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
import com.statics.vo.TipoEstado;
import com.statics.vo.Grupo;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.Estaciones;
import com.statics.vo.Estados;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class EstadosJpaController implements Serializable {

    public EstadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estados estados) {
        if (estados.getGrupoList() == null) {
            estados.setGrupoList(new ArrayList<Grupo>());
        }
        if (estados.getEstacionesList() == null) {
            estados.setEstacionesList(new ArrayList<Estaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEstado tiesId = estados.getTiesId();
            if (tiesId != null) {
                tiesId = em.getReference(tiesId.getClass(), tiesId.getTiesId());
                estados.setTiesId(tiesId);
            }
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : estados.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getGrupId());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            estados.setGrupoList(attachedGrupoList);
            List<Estaciones> attachedEstacionesList = new ArrayList<Estaciones>();
            for (Estaciones estacionesListEstacionesToAttach : estados.getEstacionesList()) {
                estacionesListEstacionesToAttach = em.getReference(estacionesListEstacionesToAttach.getClass(), estacionesListEstacionesToAttach.getEstaId());
                attachedEstacionesList.add(estacionesListEstacionesToAttach);
            }
            estados.setEstacionesList(attachedEstacionesList);
            em.persist(estados);
            if (tiesId != null) {
                tiesId.getEstadosList().add(estados);
                tiesId = em.merge(tiesId);
            }
            for (Grupo grupoListGrupo : estados.getGrupoList()) {
                Estados oldEstaIdOfGrupoListGrupo = grupoListGrupo.getEstaId();
                grupoListGrupo.setEstaId(estados);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldEstaIdOfGrupoListGrupo != null) {
                    oldEstaIdOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldEstaIdOfGrupoListGrupo = em.merge(oldEstaIdOfGrupoListGrupo);
                }
            }
            for (Estaciones estacionesListEstaciones : estados.getEstacionesList()) {
                Estados oldEstaIdestadoOfEstacionesListEstaciones = estacionesListEstaciones.getEstaIdestado();
                estacionesListEstaciones.setEstaIdestado(estados);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
                if (oldEstaIdestadoOfEstacionesListEstaciones != null) {
                    oldEstaIdestadoOfEstacionesListEstaciones.getEstacionesList().remove(estacionesListEstaciones);
                    oldEstaIdestadoOfEstacionesListEstaciones = em.merge(oldEstaIdestadoOfEstacionesListEstaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estados estados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados persistentEstados = em.find(Estados.class, estados.getEstaId());
            TipoEstado tiesIdOld = persistentEstados.getTiesId();
            TipoEstado tiesIdNew = estados.getTiesId();
            List<Grupo> grupoListOld = persistentEstados.getGrupoList();
            List<Grupo> grupoListNew = estados.getGrupoList();
            List<Estaciones> estacionesListOld = persistentEstados.getEstacionesList();
            List<Estaciones> estacionesListNew = estados.getEstacionesList();
            List<String> illegalOrphanMessages = null;
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoListOldGrupo + " since its estaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tiesIdNew != null) {
                tiesIdNew = em.getReference(tiesIdNew.getClass(), tiesIdNew.getTiesId());
                estados.setTiesId(tiesIdNew);
            }
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getGrupId());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            estados.setGrupoList(grupoListNew);
            List<Estaciones> attachedEstacionesListNew = new ArrayList<Estaciones>();
            for (Estaciones estacionesListNewEstacionesToAttach : estacionesListNew) {
                estacionesListNewEstacionesToAttach = em.getReference(estacionesListNewEstacionesToAttach.getClass(), estacionesListNewEstacionesToAttach.getEstaId());
                attachedEstacionesListNew.add(estacionesListNewEstacionesToAttach);
            }
            estacionesListNew = attachedEstacionesListNew;
            estados.setEstacionesList(estacionesListNew);
            estados = em.merge(estados);
            if (tiesIdOld != null && !tiesIdOld.equals(tiesIdNew)) {
                tiesIdOld.getEstadosList().remove(estados);
                tiesIdOld = em.merge(tiesIdOld);
            }
            if (tiesIdNew != null && !tiesIdNew.equals(tiesIdOld)) {
                tiesIdNew.getEstadosList().add(estados);
                tiesIdNew = em.merge(tiesIdNew);
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Estados oldEstaIdOfGrupoListNewGrupo = grupoListNewGrupo.getEstaId();
                    grupoListNewGrupo.setEstaId(estados);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldEstaIdOfGrupoListNewGrupo != null && !oldEstaIdOfGrupoListNewGrupo.equals(estados)) {
                        oldEstaIdOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldEstaIdOfGrupoListNewGrupo = em.merge(oldEstaIdOfGrupoListNewGrupo);
                    }
                }
            }
            for (Estaciones estacionesListOldEstaciones : estacionesListOld) {
                if (!estacionesListNew.contains(estacionesListOldEstaciones)) {
                    estacionesListOldEstaciones.setEstaIdestado(null);
                    estacionesListOldEstaciones = em.merge(estacionesListOldEstaciones);
                }
            }
            for (Estaciones estacionesListNewEstaciones : estacionesListNew) {
                if (!estacionesListOld.contains(estacionesListNewEstaciones)) {
                    Estados oldEstaIdestadoOfEstacionesListNewEstaciones = estacionesListNewEstaciones.getEstaIdestado();
                    estacionesListNewEstaciones.setEstaIdestado(estados);
                    estacionesListNewEstaciones = em.merge(estacionesListNewEstaciones);
                    if (oldEstaIdestadoOfEstacionesListNewEstaciones != null && !oldEstaIdestadoOfEstacionesListNewEstaciones.equals(estados)) {
                        oldEstaIdestadoOfEstacionesListNewEstaciones.getEstacionesList().remove(estacionesListNewEstaciones);
                        oldEstaIdestadoOfEstacionesListNewEstaciones = em.merge(oldEstaIdestadoOfEstacionesListNewEstaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estados.getEstaId();
                if (findEstados(id) == null) {
                    throw new NonexistentEntityException("The estados with id " + id + " no longer exists.");
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
            Estados estados;
            try {
                estados = em.getReference(Estados.class, id);
                estados.getEstaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Grupo> grupoListOrphanCheck = estados.getGrupoList();
            for (Grupo grupoListOrphanCheckGrupo : grupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estados (" + estados + ") cannot be destroyed since the Grupo " + grupoListOrphanCheckGrupo + " in its grupoList field has a non-nullable estaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoEstado tiesId = estados.getTiesId();
            if (tiesId != null) {
                tiesId.getEstadosList().remove(estados);
                tiesId = em.merge(tiesId);
            }
            List<Estaciones> estacionesList = estados.getEstacionesList();
            for (Estaciones estacionesListEstaciones : estacionesList) {
                estacionesListEstaciones.setEstaIdestado(null);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
            }
            em.remove(estados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estados> findEstadosEntities() {
        return findEstadosEntities(true, -1, -1);
    }

    public List<Estados> findEstadosEntities(int maxResults, int firstResult) {
        return findEstadosEntities(false, maxResults, firstResult);
    }

    private List<Estados> findEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estados.class));
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

    public Estados findEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estados> rt = cq.from(Estados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
