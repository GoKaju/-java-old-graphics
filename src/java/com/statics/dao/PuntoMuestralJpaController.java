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
import com.statics.vo.Estaciones;
import com.statics.vo.Campanas;
import com.statics.vo.MacrolocalizacionPm;
import com.statics.vo.MicrolocalizacionPm;
import com.statics.vo.UbicacionPm;
import com.statics.vo.LogisticaPm;
import com.statics.vo.DatoProcesado;
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.FotoPuntomuestral;
import com.statics.vo.Cargas;
import com.statics.vo.PuntoMuestral;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FoxHG
 */
public class PuntoMuestralJpaController implements Serializable {

    public PuntoMuestralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PuntoMuestral puntoMuestral) {
        if (puntoMuestral.getDatoProcesadoList() == null) {
            puntoMuestral.setDatoProcesadoList(new ArrayList<DatoProcesado>());
        }
        if (puntoMuestral.getFotoPuntomuestralList() == null) {
            puntoMuestral.setFotoPuntomuestralList(new ArrayList<FotoPuntomuestral>());
        }
        if (puntoMuestral.getCargasList() == null) {
            puntoMuestral.setCargasList(new ArrayList<Cargas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estaciones estaId = puntoMuestral.getEstaId();
            if (estaId != null) {
                estaId = em.getReference(estaId.getClass(), estaId.getEstaId());
                puntoMuestral.setEstaId(estaId);
            }
            Campanas campId = puntoMuestral.getCampId();
            if (campId != null) {
                campId = em.getReference(campId.getClass(), campId.getCampId());
                puntoMuestral.setCampId(campId);
            }
            MacrolocalizacionPm idMacrolocalizacion = puntoMuestral.getIdMacrolocalizacion();
            if (idMacrolocalizacion != null) {
                idMacrolocalizacion = em.getReference(idMacrolocalizacion.getClass(), idMacrolocalizacion.getId());
                puntoMuestral.setIdMacrolocalizacion(idMacrolocalizacion);
            }
            MicrolocalizacionPm idMicrolocalizacion = puntoMuestral.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion = em.getReference(idMicrolocalizacion.getClass(), idMicrolocalizacion.getId());
                puntoMuestral.setIdMicrolocalizacion(idMicrolocalizacion);
            }
            UbicacionPm idUbicacion = puntoMuestral.getIdUbicacion();
            if (idUbicacion != null) {
                idUbicacion = em.getReference(idUbicacion.getClass(), idUbicacion.getId());
                puntoMuestral.setIdUbicacion(idUbicacion);
            }
            LogisticaPm idLogistica = puntoMuestral.getIdLogistica();
            if (idLogistica != null) {
                idLogistica = em.getReference(idLogistica.getClass(), idLogistica.getId());
                puntoMuestral.setIdLogistica(idLogistica);
            }
            List<DatoProcesado> attachedDatoProcesadoList = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListDatoProcesadoToAttach : puntoMuestral.getDatoProcesadoList()) {
                datoProcesadoListDatoProcesadoToAttach = em.getReference(datoProcesadoListDatoProcesadoToAttach.getClass(), datoProcesadoListDatoProcesadoToAttach.getId());
                attachedDatoProcesadoList.add(datoProcesadoListDatoProcesadoToAttach);
            }
            puntoMuestral.setDatoProcesadoList(attachedDatoProcesadoList);
            List<FotoPuntomuestral> attachedFotoPuntomuestralList = new ArrayList<FotoPuntomuestral>();
            for (FotoPuntomuestral fotoPuntomuestralListFotoPuntomuestralToAttach : puntoMuestral.getFotoPuntomuestralList()) {
                fotoPuntomuestralListFotoPuntomuestralToAttach = em.getReference(fotoPuntomuestralListFotoPuntomuestralToAttach.getClass(), fotoPuntomuestralListFotoPuntomuestralToAttach.getId());
                attachedFotoPuntomuestralList.add(fotoPuntomuestralListFotoPuntomuestralToAttach);
            }
            puntoMuestral.setFotoPuntomuestralList(attachedFotoPuntomuestralList);
            List<Cargas> attachedCargasList = new ArrayList<Cargas>();
            for (Cargas cargasListCargasToAttach : puntoMuestral.getCargasList()) {
                cargasListCargasToAttach = em.getReference(cargasListCargasToAttach.getClass(), cargasListCargasToAttach.getCargId());
                attachedCargasList.add(cargasListCargasToAttach);
            }
            puntoMuestral.setCargasList(attachedCargasList);
            em.persist(puntoMuestral);
            if (estaId != null) {
                estaId.getPuntoMuestralList().add(puntoMuestral);
                estaId = em.merge(estaId);
            }
            if (campId != null) {
                campId.getPuntoMuestralList().add(puntoMuestral);
                campId = em.merge(campId);
            }
            if (idMacrolocalizacion != null) {
                idMacrolocalizacion.getPuntoMuestralList().add(puntoMuestral);
                idMacrolocalizacion = em.merge(idMacrolocalizacion);
            }
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getPuntoMuestralList().add(puntoMuestral);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            if (idUbicacion != null) {
                idUbicacion.getPuntoMuestralList().add(puntoMuestral);
                idUbicacion = em.merge(idUbicacion);
            }
            if (idLogistica != null) {
                idLogistica.getPuntoMuestralList().add(puntoMuestral);
                idLogistica = em.merge(idLogistica);
            }
            for (DatoProcesado datoProcesadoListDatoProcesado : puntoMuestral.getDatoProcesadoList()) {
                PuntoMuestral oldIdPuntoMuestralOfDatoProcesadoListDatoProcesado = datoProcesadoListDatoProcesado.getIdPuntoMuestral();
                datoProcesadoListDatoProcesado.setIdPuntoMuestral(puntoMuestral);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
                if (oldIdPuntoMuestralOfDatoProcesadoListDatoProcesado != null) {
                    oldIdPuntoMuestralOfDatoProcesadoListDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListDatoProcesado);
                    oldIdPuntoMuestralOfDatoProcesadoListDatoProcesado = em.merge(oldIdPuntoMuestralOfDatoProcesadoListDatoProcesado);
                }
            }
            for (FotoPuntomuestral fotoPuntomuestralListFotoPuntomuestral : puntoMuestral.getFotoPuntomuestralList()) {
                PuntoMuestral oldIdPuntoOfFotoPuntomuestralListFotoPuntomuestral = fotoPuntomuestralListFotoPuntomuestral.getIdPunto();
                fotoPuntomuestralListFotoPuntomuestral.setIdPunto(puntoMuestral);
                fotoPuntomuestralListFotoPuntomuestral = em.merge(fotoPuntomuestralListFotoPuntomuestral);
                if (oldIdPuntoOfFotoPuntomuestralListFotoPuntomuestral != null) {
                    oldIdPuntoOfFotoPuntomuestralListFotoPuntomuestral.getFotoPuntomuestralList().remove(fotoPuntomuestralListFotoPuntomuestral);
                    oldIdPuntoOfFotoPuntomuestralListFotoPuntomuestral = em.merge(oldIdPuntoOfFotoPuntomuestralListFotoPuntomuestral);
                }
            }
            for (Cargas cargasListCargas : puntoMuestral.getCargasList()) {
                PuntoMuestral oldPumuIdOfCargasListCargas = cargasListCargas.getPumuId();
                cargasListCargas.setPumuId(puntoMuestral);
                cargasListCargas = em.merge(cargasListCargas);
                if (oldPumuIdOfCargasListCargas != null) {
                    oldPumuIdOfCargasListCargas.getCargasList().remove(cargasListCargas);
                    oldPumuIdOfCargasListCargas = em.merge(oldPumuIdOfCargasListCargas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PuntoMuestral puntoMuestral) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PuntoMuestral persistentPuntoMuestral = em.find(PuntoMuestral.class, puntoMuestral.getPumuId());
            Estaciones estaIdOld = persistentPuntoMuestral.getEstaId();
            Estaciones estaIdNew = puntoMuestral.getEstaId();
            Campanas campIdOld = persistentPuntoMuestral.getCampId();
            Campanas campIdNew = puntoMuestral.getCampId();
            MacrolocalizacionPm idMacrolocalizacionOld = persistentPuntoMuestral.getIdMacrolocalizacion();
            MacrolocalizacionPm idMacrolocalizacionNew = puntoMuestral.getIdMacrolocalizacion();
            MicrolocalizacionPm idMicrolocalizacionOld = persistentPuntoMuestral.getIdMicrolocalizacion();
            MicrolocalizacionPm idMicrolocalizacionNew = puntoMuestral.getIdMicrolocalizacion();
            UbicacionPm idUbicacionOld = persistentPuntoMuestral.getIdUbicacion();
            UbicacionPm idUbicacionNew = puntoMuestral.getIdUbicacion();
            LogisticaPm idLogisticaOld = persistentPuntoMuestral.getIdLogistica();
            LogisticaPm idLogisticaNew = puntoMuestral.getIdLogistica();
            List<DatoProcesado> datoProcesadoListOld = persistentPuntoMuestral.getDatoProcesadoList();
            List<DatoProcesado> datoProcesadoListNew = puntoMuestral.getDatoProcesadoList();
            List<FotoPuntomuestral> fotoPuntomuestralListOld = persistentPuntoMuestral.getFotoPuntomuestralList();
            List<FotoPuntomuestral> fotoPuntomuestralListNew = puntoMuestral.getFotoPuntomuestralList();
            List<Cargas> cargasListOld = persistentPuntoMuestral.getCargasList();
            List<Cargas> cargasListNew = puntoMuestral.getCargasList();
            List<String> illegalOrphanMessages = null;
            for (Cargas cargasListOldCargas : cargasListOld) {
                if (!cargasListNew.contains(cargasListOldCargas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cargas " + cargasListOldCargas + " since its pumuId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estaIdNew != null) {
                estaIdNew = em.getReference(estaIdNew.getClass(), estaIdNew.getEstaId());
                puntoMuestral.setEstaId(estaIdNew);
            }
            if (campIdNew != null) {
                campIdNew = em.getReference(campIdNew.getClass(), campIdNew.getCampId());
                puntoMuestral.setCampId(campIdNew);
            }
            if (idMacrolocalizacionNew != null) {
                idMacrolocalizacionNew = em.getReference(idMacrolocalizacionNew.getClass(), idMacrolocalizacionNew.getId());
                puntoMuestral.setIdMacrolocalizacion(idMacrolocalizacionNew);
            }
            if (idMicrolocalizacionNew != null) {
                idMicrolocalizacionNew = em.getReference(idMicrolocalizacionNew.getClass(), idMicrolocalizacionNew.getId());
                puntoMuestral.setIdMicrolocalizacion(idMicrolocalizacionNew);
            }
            if (idUbicacionNew != null) {
                idUbicacionNew = em.getReference(idUbicacionNew.getClass(), idUbicacionNew.getId());
                puntoMuestral.setIdUbicacion(idUbicacionNew);
            }
            if (idLogisticaNew != null) {
                idLogisticaNew = em.getReference(idLogisticaNew.getClass(), idLogisticaNew.getId());
                puntoMuestral.setIdLogistica(idLogisticaNew);
            }
            List<DatoProcesado> attachedDatoProcesadoListNew = new ArrayList<DatoProcesado>();
            for (DatoProcesado datoProcesadoListNewDatoProcesadoToAttach : datoProcesadoListNew) {
                datoProcesadoListNewDatoProcesadoToAttach = em.getReference(datoProcesadoListNewDatoProcesadoToAttach.getClass(), datoProcesadoListNewDatoProcesadoToAttach.getId());
                attachedDatoProcesadoListNew.add(datoProcesadoListNewDatoProcesadoToAttach);
            }
            datoProcesadoListNew = attachedDatoProcesadoListNew;
            puntoMuestral.setDatoProcesadoList(datoProcesadoListNew);
            List<FotoPuntomuestral> attachedFotoPuntomuestralListNew = new ArrayList<FotoPuntomuestral>();
            for (FotoPuntomuestral fotoPuntomuestralListNewFotoPuntomuestralToAttach : fotoPuntomuestralListNew) {
                fotoPuntomuestralListNewFotoPuntomuestralToAttach = em.getReference(fotoPuntomuestralListNewFotoPuntomuestralToAttach.getClass(), fotoPuntomuestralListNewFotoPuntomuestralToAttach.getId());
                attachedFotoPuntomuestralListNew.add(fotoPuntomuestralListNewFotoPuntomuestralToAttach);
            }
            fotoPuntomuestralListNew = attachedFotoPuntomuestralListNew;
            puntoMuestral.setFotoPuntomuestralList(fotoPuntomuestralListNew);
            List<Cargas> attachedCargasListNew = new ArrayList<Cargas>();
            for (Cargas cargasListNewCargasToAttach : cargasListNew) {
                cargasListNewCargasToAttach = em.getReference(cargasListNewCargasToAttach.getClass(), cargasListNewCargasToAttach.getCargId());
                attachedCargasListNew.add(cargasListNewCargasToAttach);
            }
            cargasListNew = attachedCargasListNew;
            puntoMuestral.setCargasList(cargasListNew);
            puntoMuestral = em.merge(puntoMuestral);
            if (estaIdOld != null && !estaIdOld.equals(estaIdNew)) {
                estaIdOld.getPuntoMuestralList().remove(puntoMuestral);
                estaIdOld = em.merge(estaIdOld);
            }
            if (estaIdNew != null && !estaIdNew.equals(estaIdOld)) {
                estaIdNew.getPuntoMuestralList().add(puntoMuestral);
                estaIdNew = em.merge(estaIdNew);
            }
            if (campIdOld != null && !campIdOld.equals(campIdNew)) {
                campIdOld.getPuntoMuestralList().remove(puntoMuestral);
                campIdOld = em.merge(campIdOld);
            }
            if (campIdNew != null && !campIdNew.equals(campIdOld)) {
                campIdNew.getPuntoMuestralList().add(puntoMuestral);
                campIdNew = em.merge(campIdNew);
            }
            if (idMacrolocalizacionOld != null && !idMacrolocalizacionOld.equals(idMacrolocalizacionNew)) {
                idMacrolocalizacionOld.getPuntoMuestralList().remove(puntoMuestral);
                idMacrolocalizacionOld = em.merge(idMacrolocalizacionOld);
            }
            if (idMacrolocalizacionNew != null && !idMacrolocalizacionNew.equals(idMacrolocalizacionOld)) {
                idMacrolocalizacionNew.getPuntoMuestralList().add(puntoMuestral);
                idMacrolocalizacionNew = em.merge(idMacrolocalizacionNew);
            }
            if (idMicrolocalizacionOld != null && !idMicrolocalizacionOld.equals(idMicrolocalizacionNew)) {
                idMicrolocalizacionOld.getPuntoMuestralList().remove(puntoMuestral);
                idMicrolocalizacionOld = em.merge(idMicrolocalizacionOld);
            }
            if (idMicrolocalizacionNew != null && !idMicrolocalizacionNew.equals(idMicrolocalizacionOld)) {
                idMicrolocalizacionNew.getPuntoMuestralList().add(puntoMuestral);
                idMicrolocalizacionNew = em.merge(idMicrolocalizacionNew);
            }
            if (idUbicacionOld != null && !idUbicacionOld.equals(idUbicacionNew)) {
                idUbicacionOld.getPuntoMuestralList().remove(puntoMuestral);
                idUbicacionOld = em.merge(idUbicacionOld);
            }
            if (idUbicacionNew != null && !idUbicacionNew.equals(idUbicacionOld)) {
                idUbicacionNew.getPuntoMuestralList().add(puntoMuestral);
                idUbicacionNew = em.merge(idUbicacionNew);
            }
            if (idLogisticaOld != null && !idLogisticaOld.equals(idLogisticaNew)) {
                idLogisticaOld.getPuntoMuestralList().remove(puntoMuestral);
                idLogisticaOld = em.merge(idLogisticaOld);
            }
            if (idLogisticaNew != null && !idLogisticaNew.equals(idLogisticaOld)) {
                idLogisticaNew.getPuntoMuestralList().add(puntoMuestral);
                idLogisticaNew = em.merge(idLogisticaNew);
            }
            for (DatoProcesado datoProcesadoListOldDatoProcesado : datoProcesadoListOld) {
                if (!datoProcesadoListNew.contains(datoProcesadoListOldDatoProcesado)) {
                    datoProcesadoListOldDatoProcesado.setIdPuntoMuestral(null);
                    datoProcesadoListOldDatoProcesado = em.merge(datoProcesadoListOldDatoProcesado);
                }
            }
            for (DatoProcesado datoProcesadoListNewDatoProcesado : datoProcesadoListNew) {
                if (!datoProcesadoListOld.contains(datoProcesadoListNewDatoProcesado)) {
                    PuntoMuestral oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado = datoProcesadoListNewDatoProcesado.getIdPuntoMuestral();
                    datoProcesadoListNewDatoProcesado.setIdPuntoMuestral(puntoMuestral);
                    datoProcesadoListNewDatoProcesado = em.merge(datoProcesadoListNewDatoProcesado);
                    if (oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado != null && !oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado.equals(puntoMuestral)) {
                        oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado.getDatoProcesadoList().remove(datoProcesadoListNewDatoProcesado);
                        oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado = em.merge(oldIdPuntoMuestralOfDatoProcesadoListNewDatoProcesado);
                    }
                }
            }
            for (FotoPuntomuestral fotoPuntomuestralListOldFotoPuntomuestral : fotoPuntomuestralListOld) {
                if (!fotoPuntomuestralListNew.contains(fotoPuntomuestralListOldFotoPuntomuestral)) {
                    fotoPuntomuestralListOldFotoPuntomuestral.setIdPunto(null);
                    fotoPuntomuestralListOldFotoPuntomuestral = em.merge(fotoPuntomuestralListOldFotoPuntomuestral);
                }
            }
            for (FotoPuntomuestral fotoPuntomuestralListNewFotoPuntomuestral : fotoPuntomuestralListNew) {
                if (!fotoPuntomuestralListOld.contains(fotoPuntomuestralListNewFotoPuntomuestral)) {
                    PuntoMuestral oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral = fotoPuntomuestralListNewFotoPuntomuestral.getIdPunto();
                    fotoPuntomuestralListNewFotoPuntomuestral.setIdPunto(puntoMuestral);
                    fotoPuntomuestralListNewFotoPuntomuestral = em.merge(fotoPuntomuestralListNewFotoPuntomuestral);
                    if (oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral != null && !oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral.equals(puntoMuestral)) {
                        oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral.getFotoPuntomuestralList().remove(fotoPuntomuestralListNewFotoPuntomuestral);
                        oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral = em.merge(oldIdPuntoOfFotoPuntomuestralListNewFotoPuntomuestral);
                    }
                }
            }
            for (Cargas cargasListNewCargas : cargasListNew) {
                if (!cargasListOld.contains(cargasListNewCargas)) {
                    PuntoMuestral oldPumuIdOfCargasListNewCargas = cargasListNewCargas.getPumuId();
                    cargasListNewCargas.setPumuId(puntoMuestral);
                    cargasListNewCargas = em.merge(cargasListNewCargas);
                    if (oldPumuIdOfCargasListNewCargas != null && !oldPumuIdOfCargasListNewCargas.equals(puntoMuestral)) {
                        oldPumuIdOfCargasListNewCargas.getCargasList().remove(cargasListNewCargas);
                        oldPumuIdOfCargasListNewCargas = em.merge(oldPumuIdOfCargasListNewCargas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = puntoMuestral.getPumuId();
                if (findPuntoMuestral(id) == null) {
                    throw new NonexistentEntityException("The puntoMuestral with id " + id + " no longer exists.");
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
            PuntoMuestral puntoMuestral;
            try {
                puntoMuestral = em.getReference(PuntoMuestral.class, id);
                puntoMuestral.getPumuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puntoMuestral with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cargas> cargasListOrphanCheck = puntoMuestral.getCargasList();
            for (Cargas cargasListOrphanCheckCargas : cargasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PuntoMuestral (" + puntoMuestral + ") cannot be destroyed since the Cargas " + cargasListOrphanCheckCargas + " in its cargasList field has a non-nullable pumuId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estaciones estaId = puntoMuestral.getEstaId();
            if (estaId != null) {
                estaId.getPuntoMuestralList().remove(puntoMuestral);
                estaId = em.merge(estaId);
            }
            Campanas campId = puntoMuestral.getCampId();
            if (campId != null) {
                campId.getPuntoMuestralList().remove(puntoMuestral);
                campId = em.merge(campId);
            }
            MacrolocalizacionPm idMacrolocalizacion = puntoMuestral.getIdMacrolocalizacion();
            if (idMacrolocalizacion != null) {
                idMacrolocalizacion.getPuntoMuestralList().remove(puntoMuestral);
                idMacrolocalizacion = em.merge(idMacrolocalizacion);
            }
            MicrolocalizacionPm idMicrolocalizacion = puntoMuestral.getIdMicrolocalizacion();
            if (idMicrolocalizacion != null) {
                idMicrolocalizacion.getPuntoMuestralList().remove(puntoMuestral);
                idMicrolocalizacion = em.merge(idMicrolocalizacion);
            }
            UbicacionPm idUbicacion = puntoMuestral.getIdUbicacion();
            if (idUbicacion != null) {
                idUbicacion.getPuntoMuestralList().remove(puntoMuestral);
                idUbicacion = em.merge(idUbicacion);
            }
            LogisticaPm idLogistica = puntoMuestral.getIdLogistica();
            if (idLogistica != null) {
                idLogistica.getPuntoMuestralList().remove(puntoMuestral);
                idLogistica = em.merge(idLogistica);
            }
            List<DatoProcesado> datoProcesadoList = puntoMuestral.getDatoProcesadoList();
            for (DatoProcesado datoProcesadoListDatoProcesado : datoProcesadoList) {
                datoProcesadoListDatoProcesado.setIdPuntoMuestral(null);
                datoProcesadoListDatoProcesado = em.merge(datoProcesadoListDatoProcesado);
            }
            List<FotoPuntomuestral> fotoPuntomuestralList = puntoMuestral.getFotoPuntomuestralList();
            for (FotoPuntomuestral fotoPuntomuestralListFotoPuntomuestral : fotoPuntomuestralList) {
                fotoPuntomuestralListFotoPuntomuestral.setIdPunto(null);
                fotoPuntomuestralListFotoPuntomuestral = em.merge(fotoPuntomuestralListFotoPuntomuestral);
            }
            em.remove(puntoMuestral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PuntoMuestral> findPuntoMuestralEntities() {
        return findPuntoMuestralEntities(true, -1, -1);
    }

    public List<PuntoMuestral> findPuntoMuestralEntities(int maxResults, int firstResult) {
        return findPuntoMuestralEntities(false, maxResults, firstResult);
    }

    private List<PuntoMuestral> findPuntoMuestralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PuntoMuestral.class));
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

    public PuntoMuestral findPuntoMuestral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PuntoMuestral.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuntoMuestralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PuntoMuestral> rt = cq.from(PuntoMuestral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
