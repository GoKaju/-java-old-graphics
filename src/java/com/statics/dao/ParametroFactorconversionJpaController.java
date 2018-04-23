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
import com.statics.vo.UnidadMedida;
import com.statics.vo.Parametros;
import com.statics.vo.FactorConversion;
import com.statics.vo.DatoProcesado;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.NivelMaximo;
import com.statics.vo.ParametroFactorconversion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class ParametroFactorconversionJpaController implements Serializable {

    public ParametroFactorconversionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroFactorconversion parametroFactorconversion) {
        if (parametroFactorconversion.getDatoProcesadoList() == null) {
            parametroFactorconversion.setDatoProcesadoList(new ArrayList<DatoProcesado>());
        }
        if (parametroFactorconversion.getNivelMaximoList() == null) {
            parametroFactorconversion.setNivelMaximoList(new ArrayList<NivelMaximo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UnidadMedida idUnidadMedida = parametroFactorconversion.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida = em.getReference(idUnidadMedida.getClass(), idUnidadMedida.getId());
                parametroFactorconversion.setIdUnidadMedida(idUnidadMedida);
            }
            Parametros idParametro = parametroFactorconversion.getIdParametro();
            if (idParametro != null) {
                idParametro = em.getReference(idParametro.getClass(), idParametro.getParaId());
                parametroFactorconversion.setIdParametro(idParametro);
            }
            FactorConversion idFactorConversion = parametroFactorconversion.getIdFactorConversion();
            if (idFactorConversion != null) {
                idFactorConversion = em.getReference(idFactorConversion.getClass(), idFactorConversion.getId());
                parametroFactorconversion.setIdFactorConversion(idFactorConversion);
            }
            List<DatoProcesado> attachedDatoProcesadoList = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListDatoProcesadoToAttach : parametroFactorconversion.getDatoProcesadoList()) {
                datoProcesadoListDatoProcesadoToAttach = em.getReference(datoProcesadoListDatoProcesadoToAttach.getClass(), datoProcesadoListDatoProcesadoToAttach.getId());
                attachedDatoProcesadoList.add(datoProcesadoListDatoProcesadoToAttach);
            }
            parametroFactorconversion.setDatoProcesadoList(attachedDatoProcesadoList);
            List<NivelMaximo> attachedNivelMaximoList = new ArrayList<NivelMaximo>();
            for (NivelMaximo nivelMaximoListNivelMaximoToAttach : parametroFactorconversion.getNivelMaximoList()) {
                nivelMaximoListNivelMaximoToAttach = em.getReference(nivelMaximoListNivelMaximoToAttach.getClass(), nivelMaximoListNivelMaximoToAttach.getId());
                attachedNivelMaximoList.add(nivelMaximoListNivelMaximoToAttach);
            }
            parametroFactorconversion.setNivelMaximoList(attachedNivelMaximoList);
            em.persist(parametroFactorconversion);
            if (idUnidadMedida != null) {
                idUnidadMedida.getParametroFactorconversionList().add(parametroFactorconversion);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            if (idParametro != null) {
                idParametro.getParametroFactorconversionList().add(parametroFactorconversion);
                idParametro = em.merge(idParametro);
            }
            if (idFactorConversion != null) {
                idFactorConversion.getParametroFactorconversionList().add(parametroFactorconversion);
                idFactorConversion = em.merge(idFactorConversion);
            }
            for (DatoProcesado datoProcesadoListDatoProcesado : parametroFactorconversion.getDatoProcesadoList()) {
                ParametroFactorconversion oldIdParametroFactorconversionOfDatoProcesadoListDatoProcesado = datoProcesadoListDatoProcesado.getIdParametroFactorconversion();
                datoProcesadoListDatoProcesado.setIdParametroFactorconversion(parametroFactorconversion);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
                if (oldIdParametroFactorconversionOfDatoProcesadoListDatoProcesado != null) {
                    oldIdParametroFactorconversionOfDatoProcesadoListDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListDatoProcesado);
                    oldIdParametroFactorconversionOfDatoProcesadoListDatoProcesado = em.merge(oldIdParametroFactorconversionOfDatoProcesadoListDatoProcesado);
                }
            }
            for (NivelMaximo nivelMaximoListNivelMaximo : parametroFactorconversion.getNivelMaximoList()) {
                ParametroFactorconversion oldIdParametroFactorconversionOfNivelMaximoListNivelMaximo = nivelMaximoListNivelMaximo.getIdParametroFactorconversion();
                nivelMaximoListNivelMaximo.setIdParametroFactorconversion(parametroFactorconversion);
                nivelMaximoListNivelMaximo = em.merge(nivelMaximoListNivelMaximo);
                if (oldIdParametroFactorconversionOfNivelMaximoListNivelMaximo != null) {
                    oldIdParametroFactorconversionOfNivelMaximoListNivelMaximo.getNivelMaximoList().remove(nivelMaximoListNivelMaximo);
                    oldIdParametroFactorconversionOfNivelMaximoListNivelMaximo = em.merge(oldIdParametroFactorconversionOfNivelMaximoListNivelMaximo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroFactorconversion parametroFactorconversion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroFactorconversion persistentParametroFactorconversion = em.find(ParametroFactorconversion.class, parametroFactorconversion.getId());
            UnidadMedida idUnidadMedidaOld = persistentParametroFactorconversion.getIdUnidadMedida();
            UnidadMedida idUnidadMedidaNew = parametroFactorconversion.getIdUnidadMedida();
            Parametros idParametroOld = persistentParametroFactorconversion.getIdParametro();
            Parametros idParametroNew = parametroFactorconversion.getIdParametro();
            FactorConversion idFactorConversionOld = persistentParametroFactorconversion.getIdFactorConversion();
            FactorConversion idFactorConversionNew = parametroFactorconversion.getIdFactorConversion();
            List<DatoProcesado> datoProcesadoListOld = persistentParametroFactorconversion.getDatoProcesadoList();
            List<DatoProcesado> datoProcesadoListNew = parametroFactorconversion.getDatoProcesadoList();
            List<NivelMaximo> nivelMaximoListOld = persistentParametroFactorconversion.getNivelMaximoList();
            List<NivelMaximo> nivelMaximoListNew = parametroFactorconversion.getNivelMaximoList();
            if (idUnidadMedidaNew != null) {
                idUnidadMedidaNew = em.getReference(idUnidadMedidaNew.getClass(), idUnidadMedidaNew.getId());
                parametroFactorconversion.setIdUnidadMedida(idUnidadMedidaNew);
            }
            if (idParametroNew != null) {
                idParametroNew = em.getReference(idParametroNew.getClass(), idParametroNew.getParaId());
                parametroFactorconversion.setIdParametro(idParametroNew);
            }
            if (idFactorConversionNew != null) {
                idFactorConversionNew = em.getReference(idFactorConversionNew.getClass(), idFactorConversionNew.getId());
                parametroFactorconversion.setIdFactorConversion(idFactorConversionNew);
            }
            List<DatoProcesado> attachedDatoProcesadoListNew = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListNewDatoProcesadoToAttach : datoProcesadoListNew) {
                datoProcesadoListNewDatoProcesadoToAttach = em.getReference(datoProcesadoListNewDatoProcesadoToAttach.getClass(), datoProcesadoListNewDatoProcesadoToAttach.getId());
                attachedDatoProcesadoListNew.add(datoProcesadoListNewDatoProcesadoToAttach);
            }
            datoProcesadoListNew = attachedDatoProcesadoListNew;
            parametroFactorconversion.setDatoProcesadoList(datoProcesadoListNew);
            List<NivelMaximo> attachedNivelMaximoListNew = new ArrayList<NivelMaximo>();
            for (NivelMaximo nivelMaximoListNewNivelMaximoToAttach : nivelMaximoListNew) {
                nivelMaximoListNewNivelMaximoToAttach = em.getReference(nivelMaximoListNewNivelMaximoToAttach.getClass(), nivelMaximoListNewNivelMaximoToAttach.getId());
                attachedNivelMaximoListNew.add(nivelMaximoListNewNivelMaximoToAttach);
            }
            nivelMaximoListNew = attachedNivelMaximoListNew;
            parametroFactorconversion.setNivelMaximoList(nivelMaximoListNew);
            parametroFactorconversion = em.merge(parametroFactorconversion);
            if (idUnidadMedidaOld != null && !idUnidadMedidaOld.equals(idUnidadMedidaNew)) {
                idUnidadMedidaOld.getParametroFactorconversionList().remove(parametroFactorconversion);
                idUnidadMedidaOld = em.merge(idUnidadMedidaOld);
            }
            if (idUnidadMedidaNew != null && !idUnidadMedidaNew.equals(idUnidadMedidaOld)) {
                idUnidadMedidaNew.getParametroFactorconversionList().add(parametroFactorconversion);
                idUnidadMedidaNew = em.merge(idUnidadMedidaNew);
            }
            if (idParametroOld != null && !idParametroOld.equals(idParametroNew)) {
                idParametroOld.getParametroFactorconversionList().remove(parametroFactorconversion);
                idParametroOld = em.merge(idParametroOld);
            }
            if (idParametroNew != null && !idParametroNew.equals(idParametroOld)) {
                idParametroNew.getParametroFactorconversionList().add(parametroFactorconversion);
                idParametroNew = em.merge(idParametroNew);
            }
            if (idFactorConversionOld != null && !idFactorConversionOld.equals(idFactorConversionNew)) {
                idFactorConversionOld.getParametroFactorconversionList().remove(parametroFactorconversion);
                idFactorConversionOld = em.merge(idFactorConversionOld);
            }
            if (idFactorConversionNew != null && !idFactorConversionNew.equals(idFactorConversionOld)) {
                idFactorConversionNew.getParametroFactorconversionList().add(parametroFactorconversion);
                idFactorConversionNew = em.merge(idFactorConversionNew);
            }
            for (DatoProcesado datoProcesadoListOldDatoProcesado : datoProcesadoListOld) {
                if (!datoProcesadoListNew.contains(datoProcesadoListOldDatoProcesado)) {
                    datoProcesadoListOldDatoProcesado.setIdParametroFactorconversion(null);
                    datoProcesadoListOldDatoProcesado = em.merge(datoProcesadoListOldDatoProcesado);
                }
            }
            for (DatoProcesado datoProcesadoListNewDatoProcesado : datoProcesadoListNew) {
                if (!datoProcesadoListOld.contains(datoProcesadoListNewDatoProcesado)) {
                    ParametroFactorconversion oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado = datoProcesadoListNewDatoProcesado.getIdParametroFactorconversion();
                    datoProcesadoListNewDatoProcesado.setIdParametroFactorconversion(parametroFactorconversion);
                    datoProcesadoListNewDatoProcesado = em.merge(datoProcesadoListNewDatoProcesado);
                    if (oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado != null && !oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado.equals(parametroFactorconversion)) {
                        oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListNewDatoProcesado);
                        oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado = em.merge(oldIdParametroFactorconversionOfDatoProcesadoListNewDatoProcesado);
                    }
                }
            }
            for (NivelMaximo nivelMaximoListOldNivelMaximo : nivelMaximoListOld) {
                if (!nivelMaximoListNew.contains(nivelMaximoListOldNivelMaximo)) {
                    nivelMaximoListOldNivelMaximo.setIdParametroFactorconversion(null);
                    nivelMaximoListOldNivelMaximo = em.merge(nivelMaximoListOldNivelMaximo);
                }
            }
            for (NivelMaximo nivelMaximoListNewNivelMaximo : nivelMaximoListNew) {
                if (!nivelMaximoListOld.contains(nivelMaximoListNewNivelMaximo)) {
                    ParametroFactorconversion oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo = nivelMaximoListNewNivelMaximo.getIdParametroFactorconversion();
                    nivelMaximoListNewNivelMaximo.setIdParametroFactorconversion(parametroFactorconversion);
                    nivelMaximoListNewNivelMaximo = em.merge(nivelMaximoListNewNivelMaximo);
                    if (oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo != null && !oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo.equals(parametroFactorconversion)) {
                        oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo.getNivelMaximoList().remove(nivelMaximoListNewNivelMaximo);
                        oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo = em.merge(oldIdParametroFactorconversionOfNivelMaximoListNewNivelMaximo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroFactorconversion.getId();
                if (findParametroFactorconversion(id) == null) {
                    throw new NonexistentEntityException("The parametroFactorconversion with id " + id + " no longer exists.");
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
            ParametroFactorconversion parametroFactorconversion;
            try {
                parametroFactorconversion = em.getReference(ParametroFactorconversion.class, id);
                parametroFactorconversion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroFactorconversion with id " + id + " no longer exists.", enfe);
            }
            UnidadMedida idUnidadMedida = parametroFactorconversion.getIdUnidadMedida();
            if (idUnidadMedida != null) {
                idUnidadMedida.getParametroFactorconversionList().remove(parametroFactorconversion);
                idUnidadMedida = em.merge(idUnidadMedida);
            }
            Parametros idParametro = parametroFactorconversion.getIdParametro();
            if (idParametro != null) {
                idParametro.getParametroFactorconversionList().remove(parametroFactorconversion);
                idParametro = em.merge(idParametro);
            }
            FactorConversion idFactorConversion = parametroFactorconversion.getIdFactorConversion();
            if (idFactorConversion != null) {
                idFactorConversion.getParametroFactorconversionList().remove(parametroFactorconversion);
                idFactorConversion = em.merge(idFactorConversion);
            }
            List<DatoProcesado> datoProcesadoList = parametroFactorconversion.getDatoProcesadoList();
            for (DatoProcesado datoProcesadoListDatoProcesado : datoProcesadoList) {
                datoProcesadoListDatoProcesado.setIdParametroFactorconversion(null);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
            }
            List<NivelMaximo> nivelMaximoList = parametroFactorconversion.getNivelMaximoList();
            for (NivelMaximo nivelMaximoListNivelMaximo : nivelMaximoList) {
                nivelMaximoListNivelMaximo.setIdParametroFactorconversion(null);
                nivelMaximoListNivelMaximo = em.merge(nivelMaximoListNivelMaximo);
            }
            em.remove(parametroFactorconversion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroFactorconversion> findParametroFactorconversionEntities() {
        return findParametroFactorconversionEntities(true, -1, -1);
    }

    public List<ParametroFactorconversion> findParametroFactorconversionEntities(int maxResults, int firstResult) {
        return findParametroFactorconversionEntities(false, maxResults, firstResult);
    }

    private List<ParametroFactorconversion> findParametroFactorconversionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroFactorconversion.class));
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

    public ParametroFactorconversion findParametroFactorconversion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroFactorconversion.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroFactorconversionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroFactorconversion> rt = cq.from(ParametroFactorconversion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
