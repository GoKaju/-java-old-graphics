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
import com.statics.vo.Departamento;
import com.statics.vo.Municipio;
import com.statics.vo.PuntoMuestral;
import com.statics.vo.UbicacionPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class UbicacionPmJpaController implements Serializable {

    public UbicacionPmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UbicacionPm ubicacionPm) {
        if (ubicacionPm.getPuntoMuestralList() == null) {
            ubicacionPm.setPuntoMuestralList(new ArrayList<PuntoMuestral>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDepartamento = ubicacionPm.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getId());
                ubicacionPm.setIdDepartamento(idDepartamento);
            }
            Municipio idMunicipio = ubicacionPm.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio = em.getReference(idMunicipio.getClass(), idMunicipio.getId());
                ubicacionPm.setIdMunicipio(idMunicipio);
            }
            List<PuntoMuestral> attachedPuntoMuestralList = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListPuntoMuestralToAttach : ubicacionPm.getPuntoMuestralList()) {
                puntoMuestralListPuntoMuestralToAttach = em.getReference(puntoMuestralListPuntoMuestralToAttach.getClass(), puntoMuestralListPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralList.add(puntoMuestralListPuntoMuestralToAttach);
            }
            ubicacionPm.setPuntoMuestralList(attachedPuntoMuestralList);
            em.persist(ubicacionPm);
            if (idDepartamento != null) {
                idDepartamento.getUbicacionPmList().add(ubicacionPm);
                idDepartamento = em.merge(idDepartamento);
            }
            if (idMunicipio != null) {
                idMunicipio.getUbicacionPmList().add(ubicacionPm);
                idMunicipio = em.merge(idMunicipio);
            }
            for (PuntoMuestral puntoMuestralListPuntoMuestral : ubicacionPm.getPuntoMuestralList()) {
                UbicacionPm oldIdUbicacionOfPuntoMuestralListPuntoMuestral = puntoMuestralListPuntoMuestral.getIdUbicacion();
                puntoMuestralListPuntoMuestral.setIdUbicacion(ubicacionPm);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
                if (oldIdUbicacionOfPuntoMuestralListPuntoMuestral != null) {
                    oldIdUbicacionOfPuntoMuestralListPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListPuntoMuestral);
                    oldIdUbicacionOfPuntoMuestralListPuntoMuestral = em.merge(oldIdUbicacionOfPuntoMuestralListPuntoMuestral);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UbicacionPm ubicacionPm) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UbicacionPm persistentUbicacionPm = em.find(UbicacionPm.class, ubicacionPm.getId());
            Departamento idDepartamentoOld = persistentUbicacionPm.getIdDepartamento();
            Departamento idDepartamentoNew = ubicacionPm.getIdDepartamento();
            Municipio idMunicipioOld = persistentUbicacionPm.getIdMunicipio();
            Municipio idMunicipioNew = ubicacionPm.getIdMunicipio();
            List<PuntoMuestral> puntoMuestralListOld = persistentUbicacionPm.getPuntoMuestralList();
            List<PuntoMuestral> puntoMuestralListNew = ubicacionPm.getPuntoMuestralList();
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getId());
                ubicacionPm.setIdDepartamento(idDepartamentoNew);
            }
            if (idMunicipioNew != null) {
                idMunicipioNew = em.getReference(idMunicipioNew.getClass(), idMunicipioNew.getId());
                ubicacionPm.setIdMunicipio(idMunicipioNew);
            }
            List<PuntoMuestral> attachedPuntoMuestralListNew = new ArrayList<PuntoMuestral>();
            for (PuntoMuestral puntoMuestralListNewPuntoMuestralToAttach : puntoMuestralListNew) {
                puntoMuestralListNewPuntoMuestralToAttach = em.getReference(puntoMuestralListNewPuntoMuestralToAttach.getClass(), puntoMuestralListNewPuntoMuestralToAttach.getPumuId());
                attachedPuntoMuestralListNew.add(puntoMuestralListNewPuntoMuestralToAttach);
            }
            puntoMuestralListNew = attachedPuntoMuestralListNew;
            ubicacionPm.setPuntoMuestralList(puntoMuestralListNew);
            ubicacionPm = em.merge(ubicacionPm);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getUbicacionPmList().remove(ubicacionPm);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getUbicacionPmList().add(ubicacionPm);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            if (idMunicipioOld != null && !idMunicipioOld.equals(idMunicipioNew)) {
                idMunicipioOld.getUbicacionPmList().remove(ubicacionPm);
                idMunicipioOld = em.merge(idMunicipioOld);
            }
            if (idMunicipioNew != null && !idMunicipioNew.equals(idMunicipioOld)) {
                idMunicipioNew.getUbicacionPmList().add(ubicacionPm);
                idMunicipioNew = em.merge(idMunicipioNew);
            }
            for (PuntoMuestral puntoMuestralListOldPuntoMuestral : puntoMuestralListOld) {
                if (!puntoMuestralListNew.contains(puntoMuestralListOldPuntoMuestral)) {
                    puntoMuestralListOldPuntoMuestral.setIdUbicacion(null);
                    puntoMuestralListOldPuntoMuestral = em.merge(puntoMuestralListOldPuntoMuestral);
                }
            }
            for (PuntoMuestral puntoMuestralListNewPuntoMuestral : puntoMuestralListNew) {
                if (!puntoMuestralListOld.contains(puntoMuestralListNewPuntoMuestral)) {
                    UbicacionPm oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral = puntoMuestralListNewPuntoMuestral.getIdUbicacion();
                    puntoMuestralListNewPuntoMuestral.setIdUbicacion(ubicacionPm);
                    puntoMuestralListNewPuntoMuestral = em.merge(puntoMuestralListNewPuntoMuestral);
                    if (oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral != null && !oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral.equals(ubicacionPm)) {
                        oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral.getPuntoMuestralList().remove(puntoMuestralListNewPuntoMuestral);
                        oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral = em.merge(oldIdUbicacionOfPuntoMuestralListNewPuntoMuestral);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ubicacionPm.getId();
                if (findUbicacionPm(id) == null) {
                    throw new NonexistentEntityException("The ubicacionPm with id " + id + " no longer exists.");
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
            UbicacionPm ubicacionPm;
            try {
                ubicacionPm = em.getReference(UbicacionPm.class, id);
                ubicacionPm.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ubicacionPm with id " + id + " no longer exists.", enfe);
            }
            Departamento idDepartamento = ubicacionPm.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getUbicacionPmList().remove(ubicacionPm);
                idDepartamento = em.merge(idDepartamento);
            }
            Municipio idMunicipio = ubicacionPm.getIdMunicipio();
            if (idMunicipio != null) {
                idMunicipio.getUbicacionPmList().remove(ubicacionPm);
                idMunicipio = em.merge(idMunicipio);
            }
            List<PuntoMuestral> puntoMuestralList = ubicacionPm.getPuntoMuestralList();
            for (PuntoMuestral puntoMuestralListPuntoMuestral : puntoMuestralList) {
                puntoMuestralListPuntoMuestral.setIdUbicacion(null);
                puntoMuestralListPuntoMuestral = em.merge(puntoMuestralListPuntoMuestral);
            }
            em.remove(ubicacionPm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UbicacionPm> findUbicacionPmEntities() {
        return findUbicacionPmEntities(true, -1, -1);
    }

    public List<UbicacionPm> findUbicacionPmEntities(int maxResults, int firstResult) {
        return findUbicacionPmEntities(false, maxResults, firstResult);
    }

    private List<UbicacionPm> findUbicacionPmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UbicacionPm.class));
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

    public UbicacionPm findUbicacionPm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UbicacionPm.class, id);
        } finally {
            em.close();
        }
    }

    public int getUbicacionPmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UbicacionPm> rt = cq.from(UbicacionPm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
