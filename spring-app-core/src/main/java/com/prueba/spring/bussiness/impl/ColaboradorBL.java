/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.bussiness.impl;

import com.prueba.spring.bussiness.IBussiness;
import com.prueba.spring.dao.IDAO;
import com.prueba.spring.dao.impl.ColaboradorDAO;
import com.prueba.spring.entidades.Colaborador;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.RespuestaGenerica;
import com.prueba.spring.entidades.util.jqGridModel;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author erick
 */
public class ColaboradorBL implements IBussiness<Colaborador> {

    @Autowired
    IDAO colaboradorDAO;

    public IDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(IDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }

    public Respuesta guardar(Colaborador colaborador) {
        return this.getColaboradorDAO().guardar(colaborador);
    }

    public Respuesta actualizar(Colaborador colaborador) {
        return this.getColaboradorDAO().actualizar(colaborador);
    }

    public Respuesta eliminar(Colaborador colaborador) {
        return this.getColaboradorDAO().eliminar(colaborador);
    }

    public RespuestaGenerica<Colaborador> obtener(int id) {
        return this.getColaboradorDAO().obtener(id);
    }

    public RespuestaGenerica<List<Colaborador>> listar() {
        return this.getColaboradorDAO().listar();
    }

    public RespuestaGenerica<Integer> cantidadRegistros() {
        return this.getColaboradorDAO().cantidadRegistros();
    }

    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        jqGridModel<Colaborador> model = ((ColaboradorDAO) this.getColaboradorDAO()).obtenerTodosGrid(indice, orden, paginaActual, cantidadRegistros).getRespuesta();
        model.getRows().forEach((col)-> {col.setEventos(null);});
        return new RespuestaGenerica<>(model);
    }
}
