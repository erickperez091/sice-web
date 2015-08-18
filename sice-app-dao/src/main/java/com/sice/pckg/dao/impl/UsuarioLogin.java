/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.dao.impl;

import com.sice.pckg.entidades.Usuario;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Erick
 */
@Repository
public class UsuarioLogin {

    @Autowired
    private SessionFactory sessionFactory;

    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {

        @SuppressWarnings("UnusedAssignment")
        Usuario usuario = null;
        try {
            Criteria criteria = sessionFactory.openSession().createCriteria(Usuario.class).add(Restrictions.eq("usuario", username));
            usuario = (Usuario) criteria.uniqueResult();
            //Falta obtener/crear los roles para los diferentes usuarios
            return usuario;

        } catch (Exception ex) {
            throw ex;
        }
    }

}
