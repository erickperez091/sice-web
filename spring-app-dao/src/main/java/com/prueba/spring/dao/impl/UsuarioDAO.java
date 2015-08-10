/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.dao.impl;

import com.prueba.spring.dao.IDAO;
import com.prueba.spring.entidades.Usuario;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.RespuestaGenerica;
import com.prueba.spring.entidades.util.jqGridModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author ERICK
 */
public class UsuarioDAO extends HibernateDaoSupport implements IDAO<Usuario>, UserDetailsService {

    private Session session;
    private Transaction tx;
    private SaltSource saltSource;
    private MessageDigestPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Respuesta guardar(Usuario usuario) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            User user = new User(usuario.getUsuario(), usuario.getContrasenna(), true, true, true, true, new ArrayList());
            Object salt = saltSource.getSalt(user);
            usuario.setContrasenna(passwordEncoder.encodePassword(usuario.getContrasenna(), salt));
            this.session.save(usuario);
            tx.commit();
            respuesta = new RespuestaGenerica(usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    @Override
    public Respuesta actualizar(Usuario usuario) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.update(usuario);
            tx.commit();
            respuesta = new RespuestaGenerica(usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    @Override
    public Respuesta eliminar(Usuario usuario) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.delete(usuario);
            tx.commit();
            respuesta = new RespuestaGenerica(usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<Usuario> obtener(int id) {
        Usuario usuario;
        RespuestaGenerica<Usuario> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Usuario.class).add(Restrictions.eq("idUsuario", id));
            usuario = (Usuario) criteria.uniqueResult();
            respuesta = new RespuestaGenerica(usuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<List<Usuario>> listar() {
        List<Usuario> lista = null;
        RespuestaGenerica<List<Usuario>> respuesta;
        try {
            this.iniciaOperacion();
            Query consulta = session.createQuery("FROM Usuario u");
            lista = consulta.list();
            respuesta = new RespuestaGenerica(lista);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<Integer> cantidadRegistros() {
        RespuestaGenerica<Integer> respuesta;
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.setProjection(Projections.rowCount());
            long rowCount = (Long) criteria.uniqueResult();
            int count = (int) Math.max(Math.min(Integer.MAX_VALUE, rowCount), Integer.MIN_VALUE);
            respuesta = new RespuestaGenerica<Integer>(count);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<jqGridModel<Usuario>> obtenerTodosGrid(String indice,
            String orden, int paginaActual, int cantidadRegistros) {
        RespuestaGenerica<jqGridModel<Usuario>> respuesta;
        try {
            this.iniciaOperacion();
            jqGridModel<Usuario> model = new jqGridModel();
            model.setPage(paginaActual);
            model.setRecords(this.cantidadRegistros().getRespuesta());
            model.setTotal((int) Math.ceil((double) model.getRecords() / (double) cantidadRegistros));
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.setFirstResult((paginaActual - 1) * cantidadRegistros);
            criteria.setMaxResults(cantidadRegistros);
            if (orden.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(indice));
            } else if (orden.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(indice));
            }
            model.setRows(criteria.list());
            respuesta = new RespuestaGenerica(model);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Usuario.class).add(Restrictions.eq("usuario", username));
            usuario = (Usuario) criteria.uniqueResult();
            ArrayList<GrantedAuthority> listaRoles = new ArrayList<>();
            listaRoles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(usuario.getUsuario(), usuario.getContrasenna(), usuario.isHabilitado(), true, true, !usuario.isBloqueado(), listaRoles);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void iniciaOperacion() throws HibernateException {
        try {
            session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        } catch (Exception ex) {
            session = this.getHibernateTemplate().getSessionFactory().openSession();
        } finally {
            tx = session.beginTransaction();
        }
    }

    public MessageDigestPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(MessageDigestPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public SaltSource getSaltSource() {
        return saltSource;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }
}
