/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.dao.impl;

import com.prueba.spring.dao.IDAO;
import com.prueba.spring.entidades.Colaborador;
import com.prueba.spring.entidades.Evento;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.RespuestaGenerica;
import com.prueba.spring.entidades.util.jqGridModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author erick
 */
public class EventoDAO extends HibernateDaoSupport implements IDAO<Evento> {

    private Session session;
    private Transaction tx;

    @Transactional
    public Respuesta guardar(Evento evento) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.save(evento);
            tx.commit();
            respuesta = new RespuestaGenerica(evento);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    public Respuesta actualizar(Evento evento) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.update(evento);
            tx.commit();
            respuesta = new RespuestaGenerica(evento);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    public Respuesta eliminar(Evento evento) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.delete(evento);
            tx.commit();
            respuesta = new RespuestaGenerica(evento);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<Evento> obtener(int id) {
        Evento evento;
        RespuestaGenerica<Evento> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Evento.class).add(Restrictions.eq("idEvento", id));
            evento = (Evento) criteria.uniqueResult();
            respuesta = new RespuestaGenerica<Evento>(evento);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<List<Evento>> listar() {
        List<Evento> lista = null;
        RespuestaGenerica<List<Evento>> respuesta;
        try {
            this.iniciaOperacion();
            Query consulta = session.createQuery("FROM Evento e");
            lista = consulta.list();
            respuesta = new RespuestaGenerica<List<Evento>>(lista);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica<List<Evento>>(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<Integer> cantidadRegistros() {
        RespuestaGenerica<Integer> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Evento.class);
            criteria.setProjection(Projections.rowCount());
            long rowCount = (Long) criteria.uniqueResult();
            int count = (int) Math.max(Math.min(Integer.MAX_VALUE, rowCount), Integer.MIN_VALUE);
            respuesta = new RespuestaGenerica<Integer>(count);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        RespuestaGenerica<jqGridModel> respuesta;
        try {
            jqGridModel<Evento> model = new jqGridModel<Evento>();
            model.setPage(paginaActual);
            model.setRecords(this.cantidadRegistros().getRespuesta());
            model.setTotal((int) Math.ceil((double) model.getRecords() / (double) cantidadRegistros));
            Criteria criteria = session.createCriteria(Evento.class);
            criteria.setFirstResult((paginaActual - 1) * cantidadRegistros);
            criteria.setMaxResults(cantidadRegistros);
            if (orden.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(indice));
            } else if (orden.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(indice));
            }
            model.setRows(criteria.list());
            respuesta = new RespuestaGenerica<jqGridModel>(model);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica<jqGridModel>(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<List<Evento>> obtenerEventosSinColaborador(int idColaborador) {
        RespuestaGenerica<List<Evento>> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Evento.class);
            criteria.createAlias("colaboradores", "c", JoinType.LEFT_OUTER_JOIN)
                    .add(Restrictions.or(Restrictions.ne("c.idColaborador", idColaborador), Restrictions.isNull("c.idColaborador")));
            List<Evento> eventos = criteria.list();
            respuesta = new RespuestaGenerica<List<Evento>>(eventos);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
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

}
