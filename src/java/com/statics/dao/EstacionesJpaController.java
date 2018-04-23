/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Estaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.Estados;
import com.statics.vo.Grupo;
import com.statics.vo.Separadores;
import com.statics.vo.Formatofechas;
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class EstacionesJpaController implements Serializable {

    public EstacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estaciones estaciones) {
        if (estaciones.getPuntoMuestralList() == null) {
            estaciones.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estados estaIdestado = estaciones.getEstaIdestado();
            if (estaIdestado != null) {
                estaIdestado = em.getReference(estaIdestado.getClass(), estaIdestado.getEstaId());
                estaciones.setEstaIdestado(estaIdestado);
            }
            Grupo grupId = estaciones.getGrupId();
            if (grupId != null) {
                grupId = em.getReference(grupId.getClass(), grupId.getGrupId());
                estaciones.setGrupId(grupId);
            }
            Separadores sepaId = estaciones.getSepaId();
            if (sepaId != null) {
                sepaId = em.getReference(sepaId.getClass(), sepaId.getSepaId());
                estaciones.setSepaId(sepaId);
            }
            Formatofechas fofeId = estaciones.getFofeId();
            if (fofeId != null) {
                fofeId = em.getReference(fofeId.getClass(), fofeId.getFofeId());
                estaciones.setFofeId(fofeId);
            }
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : estaciones.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            estaciones.setPuntoMuestralList(attachedPuntoMuestralList);
            em.persist(estaciones);
            if (estaIdestado != null) {
                estaIdestado.getEstacionesList().add(estaciones);
                estaIdestado = em.merge(estaIdestado);
            }
            if (grupId != null) {
                grupId.getEstacionesList().add(estaciones);
                grupId = em.merge(grupId);
            }
            if (sepaId != null) {
                sepaId.getEstacionesList().add(estaciones);
                sepaId = em.merge(sepaId);
            }
            if (fofeId != null) {
                fofeId.getEstacionesList().add(estaciones);
                fofeId = em.merge(fofeId);
            }
            for (PuntoMuestral puntoMuestralListPuntoMuestral : estaciones.getPuntoMuestralList()) {
                Estaciones oldEstaIdOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getEstaId();
                puntoMuestralListPuntoMuestral.setEstaId(estaciones);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldEstaIdOfPuntoMuestralListPuntoMuestral != null) {
                    oldEstaIdOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldEstaIdOfPuntoMuestralListPuntoMuestral = em.merge(oldEstaIdOfPuntoMuestralListPuntoMuestral);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estaciones estaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estaciones persistentEstaciones = em.find(Estaciones.class, estaciones.getEstaId());
            Estados estaIdestadoOld = persistentEstaciones.getEstaIdestado();
            Estados estaIdestadoNew = estaciones.getEstaIdestado();
            Grupo grupIdOld = persistentEstaciones.getGrupId();
            Grupo grupIdNew = estaciones.getGrupId();
            Separadores sepaIdOld = persistentEstaciones.getSepaId();
            Separadores sepaIdNew = estaciones.getSepaId();
            Formatofechas fofeIdOld = persistentEstaciones.getFofeId();
            Formatofechas fofeIdNew = estaciones.getFofeId();
            List<PuntoMuestral> puntoMuestralListOld = persistentEstaciones.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = estaciones.getPuntoMuestralList();
            if (estaIdestadoNew != null) {
                estaIdestadoNew = em.getReference(estaIdestadoNew.getClass(), estaIdestadoNew.getEstaId());
                estaciones.setEstaIdestado(estaIdestadoNew);
            }
            if (grupIdNew != null) {
                grupIdNew = em.getReference(grupIdNew.getClass(), grupIdNew.getGrupId());
                estaciones.setGrupId(grupIdNew);
            }
            if (sepaIdNew != null) {
                sepaIdNew = em.getReference(sepaIdNew.getClass(), sepaIdNew.getSepaId());
                estaciones.setSepaId(sepaIdNew);
            }
            if (fofeIdNew != null) {
                fofeIdNew = em.getReference(fofeIdNew.getClass(), fofeIdNew.getFofeId());
                estaciones.setFofeId(fofeIdNew);
            }
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            estaciones.setPuntoMuestralList(puntoMuestralListNew);
            estaciones = em.merge(estaciones);
            if (estaIdestadoOld != null && !estaIdestadoOld.equals(estaIdestadoNew)) {
                estaIdestadoOld.getEstacionesList().remove(estaciones);
                estaIdestadoOld = em.merge(estaIdestadoOld);
            }
            if (estaIdestadoNew != null && !estaIdestadoNew.equals(estaIdestadoOld)) {
                estaIdestadoNew.getEstacionesList().add(estaciones);
                estaIdestadoNew = em.merge(estaIdestadoNew);
            }
            if (grupIdOld != null && !grupIdOld.equals(grupIdNew)) {
                grupIdOld.getEstacionesList().remove(estaciones);
                grupIdOld = em.merge(grupIdOld);
            }
            if (grupIdNew != null && !grupIdNew.equals(grupIdOld)) {
                grupIdNew.getEstacionesList().add(estaciones);
                grupIdNew = em.merge(grupIdNew);
            }
            if (sepaIdOld != null && !sepaIdOld.equals(sepaIdNew)) {
                sepaIdOld.getEstacionesList().remove(estaciones);
                sepaIdOld = em.merge(sepaIdOld);
            }
            if (sepaIdNew != null && !sepaIdNew.equals(sepaIdOld)) {
                sepaIdNew.getEstacionesList().add(estaciones);
                sepaIdNew = em.merge(sepaIdNew);
            }
            if (fofeIdOld != null && !fofeIdOld.equals(fofeIdNew)) {
                fofeIdOld.getEstacionesList().remove(estaciones);
                fofeIdOld = em.merge(fofeIdOld);
            }
            if (fofeIdNew != null && !fofeIdNew.equals(fofeIdOld)) {
                fofeIdNew.getEstacionesList().add(estaciones);
                fofeIdNew = em.merge(fofeIdNew);
            }
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setEstaId(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    Estaciones oldEstaIdOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getEstaId();
                    puntoMuestralListNewPuntoMuestral.setEstaId(estaciones);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldEstaIdOfPuntoMuestralListNewPuntoMuestral != null && !oldEstaIdOfPuntoMuestralListNewPuntoMuestral.equals(estaciones)) {
                        oldEstaIdOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldEstaIdOfPuntoMuestralListNewPuntoMuestral = em.merge(oldEstaIdOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estaciones.getEstaId();
                if (findEstaciones(id) == null) {
                    throw new NonexistentEntityException("The estaciones with id " + id + " no longer exists.");
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
            Estaciones estaciones;
            try {
                estaciones = em.getReference(Estaciones.class, id);
                estaciones.getEstaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estaciones with id " + id + " no longer exists.", enfe);
            }
            Estados estaIdestado = estaciones.getEstaIdestado();
            if (estaIdestado != null) {
                estaIdestado.getEstacionesList().remove(estaciones);
                estaIdestado = em.merge(estaIdestado);
            }
            Grupo grupId = estaciones.getGrupId();
            if (grupId != null) {
                grupId.getEstacionesList().remove(estaciones);
                grupId = em.merge(grupId);
            }
            Separadores sepaId = estaciones.getSepaId();
            if (sepaId != null) {
                sepaId.getEstacionesList().remove(estaciones);
                sepaId = em.merge(sepaId);
            }
            Formatofechas fofeId = estaciones.getFofeId();
            if (fofeId != null) {
                fofeId.getEstacionesList().remove(estaciones);
                fofeId = em.merge(fofeId);
            }
            List<PuntoMuestral> puntoMuestralList = estaciones.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setEstaId(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            em.remove(estaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estaciones> findEstacionesEntities() {
        return findEstacionesEntities(true, -1, -1);
    }

    public List<Estaciones> findEstacionesEntities(int maxResults, int firstResult) {
        return findEstacionesEntities(false, maxResults, firstResult);
    }

    private List<Estaciones> findEstacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estaciones.class));
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

    public Estaciones findEstaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estaciones> rt = cq.from(Estaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
