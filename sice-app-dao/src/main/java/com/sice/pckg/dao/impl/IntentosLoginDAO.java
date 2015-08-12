/*
	 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.dao.impl;

import com.sice.pckg.entidades.IntentosLogin;
import com.sice.pckg.entidades.Usuario;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.LockedException;

/**
 *
 * @author Erick
 */
public class IntentosLoginDAO extends HibernateDaoSupport {
    
    private Session session;
    private Transaction tx;
    private static final String SQL_USERS_COUNT = "SELECT count(*) FROM USERS WHERE username = ?";
    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE Usuario SET bloqueado = :bloqueado WHERE usuario = :usuario";
    
    public void actualizarIntentosFallidos(String usuario) throws HibernateException {
        IntentosLogin intentos = this.obtenerIntentosLogin(usuario).getRespuesta();
        boolean existe = this.usuarioExiste(usuario);
        if (intentos == null) {
            if (existe) {
                intentos = new IntentosLogin(0, usuario, 1, new java.util.Date());
                session.save(intentos);
                tx.commit();
                session.close();
            }
        } else {
            //Aqui se actualiza la cantidad de intentos
            intentos.setCantidad(intentos.getCantidad() + 1);
            intentos.setUltimaModificacion(new java.util.Date());
            session.update(intentos);
            tx.commit();
            if (intentos.getCantidad() >= 3) {
                int result = session.createQuery(SQL_USERS_UPDATE_LOCKED).setParameter("bloqueado", true).setParameter("usuario", usuario).executeUpdate();
                session.close();
                throw new LockedException("User account is locked ");
            }
        }
    }
    
    public void reiniciarIntentosFallidos(String usuario) throws HibernateException {
        try {
            IntentosLogin intentos = this.obtenerIntentosLogin(usuario).getRespuesta();
            if (intentos != null) {
                intentos.setCantidad(0);
                intentos.setUltimaModificacion(new Date());
                session.update(intentos);
                tx.commit();
            }
        } catch (Exception ex) {
        } finally {
            try {
                session.close();
            } catch (Exception ex) {
                Logger.getLogger(IntentosLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private RespuestaGenerica<IntentosLogin> obtenerIntentosLogin(String usuario) throws HibernateException {
        @SuppressWarnings("UnusedAssignment")
        IntentosLogin intentos = null;
        @SuppressWarnings("UnusedAssignment")
        RespuestaGenerica<IntentosLogin> respuesta = null;
        try {
            this.iniciaOperacion();
            Criteria criteria = session.createCriteria(IntentosLogin.class).add(Restrictions.eq("usuario", usuario));
            intentos = (IntentosLogin) criteria.uniqueResult();
            respuesta = new RespuestaGenerica<>(intentos);
        } catch (Exception ex) {
            respuesta = new RespuestaGenerica<>(ex);
        }
        return respuesta;
    }
    
    @Transactional(readOnly = true)
    private boolean usuarioExiste(String usuario) throws HibernateException {
        boolean existe = false;
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("usuario", usuario));
            criteria.setProjection(Projections.rowCount());
            long rowCount = (Long) criteria.uniqueResult();
            int count = (int) Math.max(Math.min(Integer.MAX_VALUE, rowCount), Integer.MIN_VALUE);
            existe = count > 0;
        } catch (Exception ex) {
            Logger.getLogger(IntentosLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
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
