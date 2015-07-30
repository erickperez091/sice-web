/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.bussiness.impl;

import com.prueba.spring.bussiness.IBussiness;
import com.prueba.spring.dao.IDAO;
import com.prueba.spring.dao.impl.UsuarioDAO;
import com.prueba.spring.entidades.Usuario;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.RespuestaGenerica;
import com.prueba.spring.entidades.util.jqGridModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ERICK
 */
public class UsuarioBL implements IBussiness<Usuario>{

    @Autowired
    IDAO usuarioDAO;

    public IDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(IDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    @Override
    public Respuesta guardar(Usuario usuario) {
        return this.getUsuarioDAO().guardar(usuario);
    }

    @Override
    public Respuesta actualizar(Usuario usuario) {
        return this.getUsuarioDAO().actualizar(usuario);
    }

    @Override
    public Respuesta eliminar(Usuario usuario) {
        return this.getUsuarioDAO().eliminar(usuario);
    }

    @Override
    public RespuestaGenerica<Usuario> obtener(int id) {
        return this.getUsuarioDAO().obtener(id);
    }

    @Override
    public RespuestaGenerica<List<Usuario>> listar() {
        return this.getUsuarioDAO().listar();
    }
    
    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        jqGridModel<Usuario> model = ((UsuarioDAO) this.getUsuarioDAO()).obtenerTodosGrid(indice, orden, paginaActual, cantidadRegistros).getRespuesta();
        model.getRows().forEach((user)-> {user.setContrasenna("");});
        return new RespuestaGenerica<>(model);
    }
    
}
