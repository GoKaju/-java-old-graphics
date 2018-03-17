/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.dao;

import com.statics.dao.exceptions.NonexistentEntityException;
import com.statics.vo.FotoPuntomuestral;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.statics.vo.PuntoMuestral;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class FotoPuntomuestralJpaController implements Serializable {

    public FotoPuntomuestralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public List<FotoPuntomuestral> findFotosByPuntoMuestral(int idPuntoMuestral){
        String sqlQuery="SELECT * FROM foto_puntomuestral WHERE id_punto="+idPuntoMuestral;
        List<FotoPuntomuestral> lista=new ArrayList();
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q=em.createNativeQuery(sqlQuery, FotoPuntomuestral.class);
            lista= q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }

    public void create(FotoPuntomuestral fotoPuntomuestral) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoMuestral idPunto = fotoPuntomuestral.getIdPunto();
            if (idPunto != null) {
                idPunto = em.getReference(idPunto.getClass(), idPunto.getPumuId());
                fotoPuntomuestral.setIdPunto(idPunto);
            }
            em.persist(fotoPuntomuestral);
            if (idPunto != null) {
                idPunto.getFotoPuntomuestralList().add(fotoPuntomuestral);
                idPunto = em.merge(idPunto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FotoPuntomuestral fotoPuntomuestral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FotoPuntomuestral persistentFotoPuntomuestral = em.find(FotoPuntomuestral.class, fotoPuntomuestral.getId());
            PuntoMuestral idPuntoOld = persistentFotoPuntomuestral.getIdPunto();
            PuntoMuestral idPuntoNew = fotoPuntomuestral.getIdPunto();
            if (idPuntoNew != null) {
                idPuntoNew = em.getReference(idPuntoNew.getClass(), idPuntoNew.getPumuId());
                fotoPuntomuestral.setIdPunto(idPuntoNew);
            }
            fotoPuntomuestral = em.merge(fotoPuntomuestral);
            if (idPuntoOld != null && !idPuntoOld.equals(idPuntoNew)) {
                idPuntoOld.getFotoPuntomuestralList().remove(fotoPuntomuestral);
                idPuntoOld = em.merge(idPuntoOld);
            }
            if (idPuntoNew != null && !idPuntoNew.equals(idPuntoOld)) {
                idPuntoNew.getFotoPuntomuestralList().add(fotoPuntomuestral);
                idPuntoNew = em.merge(idPuntoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fotoPuntomuestral.getId();
                if (findFotoPuntomuestral(id) == null) {
                    throw new NonexistentEntityException("The fotoPuntomuestral with id " + id + " no longer exists.");
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
            FotoPuntomuestral fotoPuntomuestral;
            try {
                fotoPuntomuestral = em.getReference(FotoPuntomuestral.class, id);
                fotoPuntomuestral.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotoPuntomuestral with id " + id + " no longer exists.", enfe);
            }
            PuntoMuestral idPunto = fotoPuntomuestral.getIdPunto();
            if (idPunto != null) {
                idPunto.getFotoPuntomuestralList().remove(fotoPuntomuestral);
                idPunto = em.merge(idPunto);
            }
            em.remove(fotoPuntomuestral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FotoPuntomuestral> findFotoPuntomuestralEntities() {
        return findFotoPuntomuestralEntities(true, -1, -1);
    }

    public List<FotoPuntomuestral> findFotoPuntomuestralEntities(int maxResults, int firstResult) {
        return findFotoPuntomuestralEntities(false, maxResults, firstResult);
    }

    private List<FotoPuntomuestral> findFotoPuntomuestralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FotoPuntomuestral.class));
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

    public FotoPuntomuestral findFotoPuntomuestral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FotoPuntomuestral.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoPuntomuestralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FotoPuntomuestral> rt = cq.from(FotoPuntomuestral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
