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
import com.statics.vo.GrupoUsuarios;
import com.statics.vo.Estados;
import com.statics.vo.Campanas;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.Estaciones;
import com.statics.vo.Grupo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        if (grupo.getCampanasList() == null) {
            grupo.setCampanasList(new ArrayList<Campanas>());
        }
        if (grupo.getEstacionesList() == null) {
            grupo.setEstacionesList(new ArrayList<Estaciones>());
        }
        if (grupo.getGrupoUsuariosList() == null) {
            grupo.setGrupoUsuariosList(new ArrayList<GrupoUsuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoUsuarios grupoUsuarios = grupo.getGrupoUsuarios();
            if (grupoUsuarios != null) {
                grupoUsuarios = em.getReference(grupoUsuarios.getClass(), grupoUsuarios.getGrusId());
                grupo.setGrupoUsuarios(grupoUsuarios);
            }
            Estados estaId = grupo.getEstaId();
            if (estaId != null) {
                estaId = em.getReference(estaId.getClass(), estaId.getEstaId());
                grupo.setEstaId(estaId);
            }
            List<Campanas> attachedCampanasList = new ArrayList<Campanas>();
            for (Campanas campanasListCampanasToAttach : grupo.getCampanasList()) {
                campanasListCampanasToAttach = em.getReference(campanasListCampanasToAttach.getClass(), campanasListCampanasToAttach.getCampId());
                attachedCampanasList.add(campanasListCampanasToAttach);
            }
            grupo.setCampanasList(attachedCampanasList);
            List<Estaciones> attachedEstacionesList = new ArrayList<Estaciones>();
            for (Estaciones estacionesListEstacionesToAttach : grupo.getEstacionesList()) {
                estacionesListEstacionesToAttach = em.getReference(estacionesListEstacionesToAttach.getClass(), estacionesListEstacionesToAttach.getEstaId());
                attachedEstacionesList.add(estacionesListEstacionesToAttach);
            }
            grupo.setEstacionesList(attachedEstacionesList);
            List<GrupoUsuarios> attachedGrupoUsuariosList = new ArrayList<GrupoUsuarios>();
            for (GrupoUsuarios grupoUsuariosListGrupoUsuariosToAttach : grupo.getGrupoUsuariosList()) {
                grupoUsuariosListGrupoUsuariosToAttach = em.getReference(grupoUsuariosListGrupoUsuariosToAttach.getClass(), grupoUsuariosListGrupoUsuariosToAttach.getGrusId());
                attachedGrupoUsuariosList.add(grupoUsuariosListGrupoUsuariosToAttach);
            }
            grupo.setGrupoUsuariosList(attachedGrupoUsuariosList);
            em.persist(grupo);
            if (grupoUsuarios != null) {
                Grupo oldGrupoOfGrupoUsuarios = grupoUsuarios.getGrupo();
                if (oldGrupoOfGrupoUsuarios != null) {
                    oldGrupoOfGrupoUsuarios.setGrupoUsuarios(null);
                    oldGrupoOfGrupoUsuarios = em.merge(oldGrupoOfGrupoUsuarios);
                }
                grupoUsuarios.setGrupo(grupo);
                grupoUsuarios = em.merge(grupoUsuarios);
            }
            if (estaId != null) {
                estaId.getGrupoList().add(grupo);
                estaId = em.merge(estaId);
            }
            for (Campanas campanasListCampanas : grupo.getCampanasList()) {
                Grupo oldGrupIdOfCampanasListCampanas = campanasListCampanas.getGrupId();
                campanasListCampanas.setGrupId(grupo);
                campanasListCampanas = em.merge(campanasListCampanas);
                if (oldGrupIdOfCampanasListCampanas != null) {
                    oldGrupIdOfCampanasListCampanas.getCampanasList().remove(campanasListCampanas);
                    oldGrupIdOfCampanasListCampanas = em.merge(oldGrupIdOfCampanasListCampanas);
                }
            }
            for (Estaciones estacionesListEstaciones : grupo.getEstacionesList()) {
                Grupo oldGrupIdOfEstacionesListEstaciones = estacionesListEstaciones.getGrupId();
                estacionesListEstaciones.setGrupId(grupo);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
                if (oldGrupIdOfEstacionesListEstaciones != null) {
                    oldGrupIdOfEstacionesListEstaciones.getEstacionesList().remove(estacionesListEstaciones);
                    oldGrupIdOfEstacionesListEstaciones = em.merge(oldGrupIdOfEstacionesListEstaciones);
                }
            }
            for (GrupoUsuarios grupoUsuariosListGrupoUsuarios : grupo.getGrupoUsuariosList()) {
                Grupo oldGrupIdOfGrupoUsuariosListGrupoUsuarios = grupoUsuariosListGrupoUsuarios.getGrupId();
                grupoUsuariosListGrupoUsuarios.setGrupId(grupo);
                grupoUsuariosListGrupoUsuarios = em.merge(grupoUsuariosListGrupoUsuarios);
                if (oldGrupIdOfGrupoUsuariosListGrupoUsuarios != null) {
                    oldGrupIdOfGrupoUsuariosListGrupoUsuarios.getGrupoUsuariosList().remove(grupoUsuariosListGrupoUsuarios);
                    oldGrupIdOfGrupoUsuariosListGrupoUsuarios = em.merge(oldGrupIdOfGrupoUsuariosListGrupoUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupId());
            GrupoUsuarios grupoUsuariosOld = persistentGrupo.getGrupoUsuarios();
            GrupoUsuarios grupoUsuariosNew = grupo.getGrupoUsuarios();
            Estados estaIdOld = persistentGrupo.getEstaId();
            Estados estaIdNew = grupo.getEstaId();
            List<Campanas> campanasListOld = persistentGrupo.getCampanasList();
            List<Campanas> campanasListNew = grupo.getCampanasList();
            List<Estaciones> estacionesListOld = persistentGrupo.getEstacionesList();
            List<Estaciones> estacionesListNew = grupo.getEstacionesList();
            List<GrupoUsuarios> grupoUsuariosListOld = persistentGrupo.getGrupoUsuariosList();
            List<GrupoUsuarios> grupoUsuariosListNew = grupo.getGrupoUsuariosList();
            List<String> illegalOrphanMessages = null;
            if (grupoUsuariosOld != null && !grupoUsuariosOld.equals(grupoUsuariosNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain GrupoUsuarios " + grupoUsuariosOld + " since its grupo field is not nullable.");
            }
            for (Campanas campanasListOldCampanas : campanasListOld) {
                if (!campanasListNew.contains(campanasListOldCampanas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Campanas " + campanasListOldCampanas + " since its grupId field is not nullable.");
                }
            }
            for (GrupoUsuarios grupoUsuariosListOldGrupoUsuarios : grupoUsuariosListOld) {
                if (!grupoUsuariosListNew.contains(grupoUsuariosListOldGrupoUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoUsuarios " + grupoUsuariosListOldGrupoUsuarios + " since its grupId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (grupoUsuariosNew != null) {
                grupoUsuariosNew = em.getReference(grupoUsuariosNew.getClass(), grupoUsuariosNew.getGrusId());
                grupo.setGrupoUsuarios(grupoUsuariosNew);
            }
            if (estaIdNew != null) {
                estaIdNew = em.getReference(estaIdNew.getClass(), estaIdNew.getEstaId());
                grupo.setEstaId(estaIdNew);
            }
            List<Campanas> attachedCampanasListNew = new ArrayList<Campanas>();
            for (Campanas campanasListNewCampanasToAttach : campanasListNew) {
                campanasListNewCampanasToAttach = em.getReference(campanasListNewCampanasToAttach.getClass(), campanasListNewCampanasToAttach.getCampId());
                attachedCampanasListNew.add(campanasListNewCampanasToAttach);
            }
            campanasListNew = attachedCampanasListNew;
            grupo.setCampanasList(campanasListNew);
            List<Estaciones> attachedEstacionesListNew = new ArrayList<Estaciones>();
            for (Estaciones estacionesListNewEstacionesToAttach : estacionesListNew) {
                estacionesListNewEstacionesToAttach = em.getReference(estacionesListNewEstacionesToAttach.getClass(), estacionesListNewEstacionesToAttach.getEstaId());
                attachedEstacionesListNew.add(estacionesListNewEstacionesToAttach);
            }
            estacionesListNew = attachedEstacionesListNew;
            grupo.setEstacionesList(estacionesListNew);
            List<GrupoUsuarios> attachedGrupoUsuariosListNew = new ArrayList<GrupoUsuarios>();
            for (GrupoUsuarios grupoUsuariosListNewGrupoUsuariosToAttach : grupoUsuariosListNew) {
                grupoUsuariosListNewGrupoUsuariosToAttach = em.getReference(grupoUsuariosListNewGrupoUsuariosToAttach.getClass(), grupoUsuariosListNewGrupoUsuariosToAttach.getGrusId());
                attachedGrupoUsuariosListNew.add(grupoUsuariosListNewGrupoUsuariosToAttach);
            }
            grupoUsuariosListNew = attachedGrupoUsuariosListNew;
            grupo.setGrupoUsuariosList(grupoUsuariosListNew);
            grupo = em.merge(grupo);
            if (grupoUsuariosNew != null && !grupoUsuariosNew.equals(grupoUsuariosOld)) {
                Grupo oldGrupoOfGrupoUsuarios = grupoUsuariosNew.getGrupo();
                if (oldGrupoOfGrupoUsuarios != null) {
                    oldGrupoOfGrupoUsuarios.setGrupoUsuarios(null);
                    oldGrupoOfGrupoUsuarios = em.merge(oldGrupoOfGrupoUsuarios);
                }
                grupoUsuariosNew.setGrupo(grupo);
                grupoUsuariosNew = em.merge(grupoUsuariosNew);
            }
            if (estaIdOld != null && !estaIdOld.equals(estaIdNew)) {
                estaIdOld.getGrupoList().remove(grupo);
                estaIdOld = em.merge(estaIdOld);
            }
            if (estaIdNew != null && !estaIdNew.equals(estaIdOld)) {
                estaIdNew.getGrupoList().add(grupo);
                estaIdNew = em.merge(estaIdNew);
            }
            for (Campanas campanasListNewCampanas : campanasListNew) {
                if (!campanasListOld.contains(campanasListNewCampanas)) {
                    Grupo oldGrupIdOfCampanasListNewCampanas = campanasListNewCampanas.getGrupId();
                    campanasListNewCampanas.setGrupId(grupo);
                    campanasListNewCampanas = em.merge(campanasListNewCampanas);
                    if (oldGrupIdOfCampanasListNewCampanas != null && !oldGrupIdOfCampanasListNewCampanas.equals(grupo)) {
                        oldGrupIdOfCampanasListNewCampanas.getCampanasList().remove(campanasListNewCampanas);
                        oldGrupIdOfCampanasListNewCampanas = em.merge(oldGrupIdOfCampanasListNewCampanas);
                    }
                }
            }
            for (Estaciones estacionesListOldEstaciones : estacionesListOld) {
                if (!estacionesListNew.contains(estacionesListOldEstaciones)) {
                    estacionesListOldEstaciones.setGrupId(null);
                    estacionesListOldEstaciones = em.merge(estacionesListOldEstaciones);
                }
            }
            for (Estaciones estacionesListNewEstaciones : estacionesListNew) {
                if (!estacionesListOld.contains(estacionesListNewEstaciones)) {
                    Grupo oldGrupIdOfEstacionesListNewEstaciones = estacionesListNewEstaciones.getGrupId();
                    estacionesListNewEstaciones.setGrupId(grupo);
                    estacionesListNewEstaciones = em.merge(estacionesListNewEstaciones);
                    if (oldGrupIdOfEstacionesListNewEstaciones != null && !oldGrupIdOfEstacionesListNewEstaciones.equals(grupo)) {
                        oldGrupIdOfEstacionesListNewEstaciones.getEstacionesList().remove(estacionesListNewEstaciones);
                        oldGrupIdOfEstacionesListNewEstaciones = em.merge(oldGrupIdOfEstacionesListNewEstaciones);
                    }
                }
            }
            for (GrupoUsuarios grupoUsuariosListNewGrupoUsuarios : grupoUsuariosListNew) {
                if (!grupoUsuariosListOld.contains(grupoUsuariosListNewGrupoUsuarios)) {
                    Grupo oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios = grupoUsuariosListNewGrupoUsuarios.getGrupId();
                    grupoUsuariosListNewGrupoUsuarios.setGrupId(grupo);
                    grupoUsuariosListNewGrupoUsuarios = em.merge(grupoUsuariosListNewGrupoUsuarios);
                    if (oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios != null && !oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios.equals(grupo)) {
                        oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios.getGrupoUsuariosList().remove(grupoUsuariosListNewGrupoUsuarios);
                        oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios = em.merge(oldGrupIdOfGrupoUsuariosListNewGrupoUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getGrupId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            GrupoUsuarios grupoUsuariosOrphanCheck = grupo.getGrupoUsuarios();
            if (grupoUsuariosOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the GrupoUsuarios " + grupoUsuariosOrphanCheck + " in its grupoUsuarios field has a non-nullable grupo field.");
            }
            List<Campanas> campanasListOrphanCheck = grupo.getCampanasList();
            for (Campanas campanasListOrphanCheckCampanas : campanasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the Campanas " + campanasListOrphanCheckCampanas + " in its campanasList field has a non-nullable grupId field.");
            }
            List<GrupoUsuarios> grupoUsuariosListOrphanCheck = grupo.getGrupoUsuariosList();
            for (GrupoUsuarios grupoUsuariosListOrphanCheckGrupoUsuarios : grupoUsuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupo (" + grupo + ") cannot be destroyed since the GrupoUsuarios " + grupoUsuariosListOrphanCheckGrupoUsuarios + " in its grupoUsuariosList field has a non-nullable grupId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estados estaId = grupo.getEstaId();
            if (estaId != null) {
                estaId.getGrupoList().remove(grupo);
                estaId = em.merge(estaId);
            }
            List<Estaciones> estacionesList = grupo.getEstacionesList();
            for (Estaciones estacionesListEstaciones : estacionesList) {
                estacionesListEstaciones.setGrupId(null);
                estacionesListEstaciones = em.merge(estacionesListEstaciones);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
