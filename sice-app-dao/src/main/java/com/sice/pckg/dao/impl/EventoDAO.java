/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.dao.impl;

import com.sice.pckg.dao.IDAO;
import com.sice.pckg.entidades.Evento;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.jqGridModel;
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public RespuestaGenerica<Evento> obtener(int id) {
        Evento evento;
        RespuestaGenerica<Evento> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Evento.class).add(Restrictions.eq("idEvento", id));
            evento = (Evento) criteria.uniqueResult();
            respuesta = new RespuestaGenerica<>(evento);
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
    @Override
    public RespuestaGenerica<List<Evento>> listar() {
        @SuppressWarnings("UnusedAssignment")
        List<Evento> lista = null;
        RespuestaGenerica<List<Evento>> respuesta;
        try {
            this.iniciaOperacion();
            Query consulta = session.createQuery("FROM Evento e");
            lista = consulta.list();
            respuesta = new RespuestaGenerica<>(lista);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica<>(ex);
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
    @Override
    public RespuestaGenerica<Integer> cantidadRegistros() {
        RespuestaGenerica<Integer> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Evento.class);
            criteria.setProjection(Projections.rowCount());
            long rowCount = (Long) criteria.uniqueResult();
            int count = (int) Math.max(Math.min(Integer.MAX_VALUE, rowCount), Integer.MIN_VALUE);
            respuesta = new RespuestaGenerica<>(count);
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
            jqGridModel<Evento> model = new jqGridModel<>();
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
            respuesta = new RespuestaGenerica<>(model);
        } catch (Exception ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica<>(ex);
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
            respuesta = new RespuestaGenerica<>(eventos);
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
