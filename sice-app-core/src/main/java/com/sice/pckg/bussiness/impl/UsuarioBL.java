/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.bussiness.impl;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.dao.IDAO;
import com.sice.pckg.dao.impl.UsuarioDAO;
import com.sice.pckg.entidades.Usuario;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.jqGridModel;
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
        usuario.setHabilitado(true);
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
        RespuestaGenerica<Usuario> respuesta = this.getUsuarioDAO().obtener(id);
        respuesta.getRespuesta().setContrasenna("");
        return respuesta;
    }

    @Override
    public RespuestaGenerica<List<Usuario>> listar() {
        return this.getUsuarioDAO().listar();
    }
    
    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        System.out.println(this.usuarioDAO.getClass());
        jqGridModel<Usuario> model = ((UsuarioDAO) this.getUsuarioDAO()).obtenerTodosGrid(indice, orden, paginaActual, cantidadRegistros).getRespuesta();
        model.getRows().forEach((user)-> {user.setContrasenna("");});
        return new RespuestaGenerica<>(model);
    }
    
}
