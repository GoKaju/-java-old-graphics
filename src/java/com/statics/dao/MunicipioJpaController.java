/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.Municipio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.UbicacionPm;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
        public List<Municipio> findMunicipiosByDepartamento(int idDepartamento){
        String sqlQuery="SELECT * FROM municipio WHERE id_departamento="+idDepartamento;
        List<Municipio> lista=new ArrayList();
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createNativeQuery(sqlQuery, Municipio.class);
            lista=q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if (em!=null) {
                em.close();
            }
        }
        return lista;
    }

    public void create(Municipio municipio) {
        if (municipio.getUbicacionPmList() == null) {
            municipio.setUbicacionPmList(new ArrayList<UbicacionPm>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<UbicacionPm> attachedUbicacionPmList = new ArrayList<UbicacionPm>();
            for (UbicacionPm ubicacionPmListUbicacionPmToAttach : municipio.getUbicacionPmList()) {
                ubicacionPmListUbicacionPmToAttach = em.getReference(ubicacionPmListUbicacionPmToAttach.getClass(), ubicacionPmListUbicacionPmToAttach.getId());
                attachedUbicacionPmList.add(ubicacionPmListUbicacionPmToAttach);
            }
            municipio.setUbicacionPmList(attachedUbicacionPmList);
            em.persist(municipio);
            for (UbicacionPm ubicacionPmListUbicacionPm : municipio.getUbicacionPmList()) {
                Municipio oldIdMunicipioOfUbicacionPmListUbicacionPm = ubicacionPmListUbicacionPm.getIdMunicipio();
                ubicacionPmListUbicacionPm.setIdMunicipio(municipio);
                ubicacionPmListUbicacionPm = em.merge(ubicacionPmListUbicacionPm);
                if (oldIdMunicipioOfUbicacionPmListUbicacionPm != null) {
                    oldIdMunicipioOfUbicacionPmListUbicacionPm.getUbicacionPmList().remove(ubicacionPmListUbicacionPm);
                    oldIdMunicipioOfUbicacionPmListUbicacionPm = em.merge(oldIdMunicipioOfUbicacionPmListUbicacionPm);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getId());
            List<UbicacionPm> ubicacionPmListOld = persistentMunicipio.getUbicacionPmList();
            List<UbicacionPm> ubicacionPmListNew = municipio.getUbicacionPmList();
            List<UbicacionPm> attachedUbicacionPmListNew = new ArrayList<UbicacionPm>();
            for (UbicacionPm ubicacionPmListNewUbicacionPmToAttach : ubicacionPmListNew) {
                ubicacionPmListNewUbicacionPmToAttach = em.getReference(ubicacionPmListNewUbicacionPmToAttach.getClass(), ubicacionPmListNewUbicacionPmToAttach.getId());
                attachedUbicacionPmListNew.add(ubicacionPmListNewUbicacionPmToAttach);
            }
            ubicacionPmListNew = attachedUbicacionPmListNew;
            municipio.setUbicacionPmList(ubicacionPmListNew);
            municipio = em.merge(municipio);
            for (UbicacionPm ubicacionPmListOldUbicacionPm : ubicacionPmListOld) {
                if (!ubicacionPmListNew.contains(ubicacionPmListOldUbicacionPm)) {
                    ubicacionPmListOldUbicacionPm.setIdMunicipio(null);
                    ubicacionPmListOldUbicacionPm = em.merge(ubicacionPmListOldUbicacionPm);
                }
            }
            for (UbicacionPm ubicacionPmListNewUbicacionPm : ubicacionPmListNew) {
                if (!ubicacionPmListOld.contains(ubicacionPmListNewUbicacionPm)) {
                    Municipio oldIdMunicipioOfUbicacionPmListNewUbicacionPm = ubicacionPmListNewUbicacionPm.getIdMunicipio();
                    ubicacionPmListNewUbicacionPm.setIdMunicipio(municipio);
                    ubicacionPmListNewUbicacionPm = em.merge(ubicacionPmListNewUbicacionPm);
                    if (oldIdMunicipioOfUbicacionPmListNewUbicacionPm != null && !oldIdMunicipioOfUbicacionPmListNewUbicacionPm.equals(municipio)) {
                        oldIdMunicipioOfUbicacionPmListNewUbicacionPm.getUbicacionPmList().remove(ubicacionPmListNewUbicacionPm);
                        oldIdMunicipioOfUbicacionPmListNewUbicacionPm = em.merge(oldIdMunicipioOfUbicacionPmListNewUbicacionPm);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getId();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<UbicacionPm> ubicacionPmList = municipio.getUbicacionPmList();
            for (UbicacionPm ubicacionPmListUbicacionPm : ubicacionPmList) {
                ubicacionPmListUbicacionPm.setIdMunicipio(null);
                ubicacionPmListUbicacionPm = em.merge(ubicacionPmListUbicacionPm);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
