/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.dao.impl;

import com.sice.pckg.dao.IDAO;
import com.sice.pckg.entidades.Colaborador;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.jqGridModel;
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

/**
 *
 * @author erick
 */
public class ColaboradorDAO extends HibernateDaoSupport implements
        IDAO<Colaborador> {

    private Session session;
    private Transaction tx;

    @Transactional
    @Override
    public Respuesta guardar(Colaborador colaborador) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.save(colaborador);
            tx.commit();
            respuesta = new RespuestaGenerica(colaborador);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    @Override
    public Respuesta actualizar(Colaborador colaborador) {
        Respuesta respuesta;
        try {
            if (session != null) {
                if (session.isOpen()) {
                    session.disconnect();
                    session.close();
                    session = null;
                }
            }
            this.iniciaOperacion();
            this.session.update(colaborador);
            tx.commit();
            respuesta = new RespuestaGenerica(colaborador);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional
    @Override
    public Respuesta eliminar(Colaborador colaborador) {
        Respuesta respuesta;
        try {
            this.iniciaOperacion();
            this.session.delete(colaborador);
            tx.commit();
            respuesta = new RespuestaGenerica(colaborador);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            tx.rollback();
            respuesta = new Respuesta(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<Colaborador> obtener(int id) {
        Colaborador colaborador;
        RespuestaGenerica<Colaborador> respuesta;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(Colaborador.class).add(
                    Restrictions.eq("idColaborador", id));
            colaborador = (Colaborador) criteria.uniqueResult();
            respuesta = new RespuestaGenerica<>(colaborador);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            respuesta = new RespuestaGenerica(ex);
        } finally {
            try {
                // session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<List<Colaborador>> listar() {
        RespuestaGenerica<List<Colaborador>> respuesta;
        @SuppressWarnings("UnusedAssignment")
        List<Colaborador> lista = null;
        try {
            this.iniciaOperacion();
            Query consulta = session.createQuery("FROM Colaborador c");
            lista = consulta.list();
            respuesta = new RespuestaGenerica<>(lista);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
            respuesta = new RespuestaGenerica<>(ex);
        } finally {
            try {
                // session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    @Override
    public RespuestaGenerica<Integer> cantidadRegistros() {
        RespuestaGenerica<Integer> respuesta;
        try {
            Criteria criteria = session.createCriteria(Colaborador.class);
            criteria.setProjection(Projections.rowCount());
            long rowCount = (Long) criteria.uniqueResult();
            int count = (int) Math.max(Math.min(Integer.MAX_VALUE, rowCount), Integer.MIN_VALUE);
            respuesta = new RespuestaGenerica<>(count);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica(ex);
        }
        return respuesta;
    }

    @Transactional(readOnly = true)
    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        RespuestaGenerica<jqGridModel> respuesta;
        try {
            this.iniciaOperacion();
            jqGridModel<Colaborador> model = new jqGridModel<>();
            model.setPage(paginaActual);
            model.setRecords(this.cantidadRegistros().getRespuesta());
            model.setTotal((int) Math.ceil((double) model.getRecords() / (double) cantidadRegistros));
            Criteria criteria = session.createCriteria(Colaborador.class);
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
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            respuesta = new RespuestaGenerica<>(ex);
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
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
