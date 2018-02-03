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
import java.util.ArrayList;
import java.util.List;
import com.statics.vo.Usuariorol;
import com.statics.vo.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Usuario
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getGrupoUsuariosList() == null) {
            usuarios.setGrupoUsuariosList(new ArrayList<GrupoUsuarios>());
        }
        if (usuarios.getUsuariorolList() == null) {
            usuarios.setUsuariorolList(new ArrayList<Usuariorol>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoUsuarios> attachedGrupoUsuariosList = new ArrayList<GrupoUsuarios>();
            for (GrupoUsuarios grupoUsuariosListGrupoUsuariosToAttach : usuarios.getGrupoUsuariosList()) {
                grupoUsuariosListGrupoUsuariosToAttach = em.getReference(grupoUsuariosListGrupoUsuariosToAttach.getClass(), grupoUsuariosListGrupoUsuariosToAttach.getGrusId());
                attachedGrupoUsuariosList.add(grupoUsuariosListGrupoUsuariosToAttach);
            }
            usuarios.setGrupoUsuariosList(attachedGrupoUsuariosList);
            List<Usuariorol> attachedUsuariorolList = new ArrayList<Usuariorol>();
            for (Usuariorol usuariorolListUsuariorolToAttach : usuarios.getUsuariorolList()) {
                usuariorolListUsuariorolToAttach = em.getReference(usuariorolListUsuariorolToAttach.getClass(), usuariorolListUsuariorolToAttach.getUsroId());
                attachedUsuariorolList.add(usuariorolListUsuariorolToAttach);
            }
            usuarios.setUsuariorolList(attachedUsuariorolList);
            em.persist(usuarios);
            for (GrupoUsuarios grupoUsuariosListGrupoUsuarios : usuarios.getGrupoUsuariosList()) {
                Usuarios oldUsuaIdOfGrupoUsuariosListGrupoUsuarios = grupoUsuariosListGrupoUsuarios.getUsuaId();
                grupoUsuariosListGrupoUsuarios.setUsuaId(usuarios);
                grupoUsuariosListGrupoUsuarios = em.merge(grupoUsuariosListGrupoUsuarios);
                if (oldUsuaIdOfGrupoUsuariosListGrupoUsuarios != null) {
                    oldUsuaIdOfGrupoUsuariosListGrupoUsuarios.getGrupoUsuariosList().remove(grupoUsuariosListGrupoUsuarios);
                    oldUsuaIdOfGrupoUsuariosListGrupoUsuarios = em.merge(oldUsuaIdOfGrupoUsuariosListGrupoUsuarios);
                }
            }
            for (Usuariorol usuariorolListUsuariorol : usuarios.getUsuariorolList()) {
                Usuarios oldUsuaIdOfUsuariorolListUsuariorol = usuariorolListUsuariorol.getUsuaId();
                usuariorolListUsuariorol.setUsuaId(usuarios);
                usuariorolListUsuariorol = em.merge(usuariorolListUsuariorol);
                if (oldUsuaIdOfUsuariorolListUsuariorol != null) {
                    oldUsuaIdOfUsuariorolListUsuariorol.getUsuariorolList().remove(usuariorolListUsuariorol);
                    oldUsuaIdOfUsuariorolListUsuariorol = em.merge(oldUsuaIdOfUsuariorolListUsuariorol);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getUsuaId());
            List<GrupoUsuarios> grupoUsuariosListOld = persistentUsuarios.getGrupoUsuariosList();
            List<GrupoUsuarios> grupoUsuariosListNew = usuarios.getGrupoUsuariosList();
            List<Usuariorol> usuariorolListOld = persistentUsuarios.getUsuariorolList();
            List<Usuariorol> usuariorolListNew = usuarios.getUsuariorolList();
            List<String> illegalOrphanMessages = null;
            for (GrupoUsuarios grupoUsuariosListOldGrupoUsuarios : grupoUsuariosListOld) {
                if (!grupoUsuariosListNew.contains(grupoUsuariosListOldGrupoUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GrupoUsuarios " + grupoUsuariosListOldGrupoUsuarios + " since its usuaId field is not nullable.");
                }
            }
            for (Usuariorol usuariorolListOldUsuariorol : usuariorolListOld) {
                if (!usuariorolListNew.contains(usuariorolListOldUsuariorol)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuariorol " + usuariorolListOldUsuariorol + " since its usuaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoUsuarios> attachedGrupoUsuariosListNew = new ArrayList<GrupoUsuarios>();
            for (GrupoUsuarios grupoUsuariosListNewGrupoUsuariosToAttach : grupoUsuariosListNew) {
                grupoUsuariosListNewGrupoUsuariosToAttach = em.getReference(grupoUsuariosListNewGrupoUsuariosToAttach.getClass(), grupoUsuariosListNewGrupoUsuariosToAttach.getGrusId());
                attachedGrupoUsuariosListNew.add(grupoUsuariosListNewGrupoUsuariosToAttach);
            }
            grupoUsuariosListNew = attachedGrupoUsuariosListNew;
            usuarios.setGrupoUsuariosList(grupoUsuariosListNew);
            List<Usuariorol> attachedUsuariorolListNew = new ArrayList<Usuariorol>();
            for (Usuariorol usuariorolListNewUsuariorolToAttach : usuariorolListNew) {
                usuariorolListNewUsuariorolToAttach = em.getReference(usuariorolListNewUsuariorolToAttach.getClass(), usuariorolListNewUsuariorolToAttach.getUsroId());
                attachedUsuariorolListNew.add(usuariorolListNewUsuariorolToAttach);
            }
            usuariorolListNew = attachedUsuariorolListNew;
            usuarios.setUsuariorolList(usuariorolListNew);
            usuarios = em.merge(usuarios);
            for (GrupoUsuarios grupoUsuariosListNewGrupoUsuarios : grupoUsuariosListNew) {
                if (!grupoUsuariosListOld.contains(grupoUsuariosListNewGrupoUsuarios)) {
                    Usuarios oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios = grupoUsuariosListNewGrupoUsuarios.getUsuaId();
                    grupoUsuariosListNewGrupoUsuarios.setUsuaId(usuarios);
                    grupoUsuariosListNewGrupoUsuarios = em.merge(grupoUsuariosListNewGrupoUsuarios);
                    if (oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios != null && !oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios.equals(usuarios)) {
                        oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios.getGrupoUsuariosList().remove(grupoUsuariosListNewGrupoUsuarios);
                        oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios = em.merge(oldUsuaIdOfGrupoUsuariosListNewGrupoUsuarios);
                    }
                }
            }
            for (Usuariorol usuariorolListNewUsuariorol : usuariorolListNew) {
                if (!usuariorolListOld.contains(usuariorolListNewUsuariorol)) {
                    Usuarios oldUsuaIdOfUsuariorolListNewUsuariorol = usuariorolListNewUsuariorol.getUsuaId();
                    usuariorolListNewUsuariorol.setUsuaId(usuarios);
                    usuariorolListNewUsuariorol = em.merge(usuariorolListNewUsuariorol);
                    if (oldUsuaIdOfUsuariorolListNewUsuariorol != null && !oldUsuaIdOfUsuariorolListNewUsuariorol.equals(usuarios)) {
                        oldUsuaIdOfUsuariorolListNewUsuariorol.getUsuariorolList().remove(usuariorolListNewUsuariorol);
                        oldUsuaIdOfUsuariorolListNewUsuariorol = em.merge(oldUsuaIdOfUsuariorolListNewUsuariorol);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getUsuaId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getUsuaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GrupoUsuarios> grupoUsuariosListOrphanCheck = usuarios.getGrupoUsuariosList();
            for (GrupoUsuarios grupoUsuariosListOrphanCheckGrupoUsuarios : grupoUsuariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the GrupoUsuarios " + grupoUsuariosListOrphanCheckGrupoUsuarios + " in its grupoUsuariosList field has a non-nullable usuaId field.");
            }
            List<Usuariorol> usuariorolListOrphanCheck = usuarios.getUsuariorolList();
            for (Usuariorol usuariorolListOrphanCheckUsuariorol : usuariorolListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Usuariorol " + usuariorolListOrphanCheckUsuariorol + " in its usuariorolList field has a non-nullable usuaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
