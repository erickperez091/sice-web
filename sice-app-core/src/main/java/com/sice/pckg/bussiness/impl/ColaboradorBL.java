/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.bussiness.impl;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.dao.IDAO;
import com.sice.pckg.dao.impl.ColaboradorDAO;
import com.sice.pckg.entidades.Colaborador;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.jqGridModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author erick
 */
public class ColaboradorBL implements IBussiness<Colaborador> {

  @Autowired
  IDAO<Colaborador> colaboradorDAO;

  public IDAO<Colaborador> getColaboradorDAO() {
    return colaboradorDAO;
  }

  public void setColaboradorDAO(IDAO<Colaborador> colaboradorDAO) {
    this.colaboradorDAO = colaboradorDAO;
  }

  @Override
  public Respuesta guardar(Colaborador colaborador) {
    return this.getColaboradorDAO().guardar(colaborador);
  }

  @Override
  public Respuesta actualizar(Colaborador colaborador) {
    return this.getColaboradorDAO().actualizar(colaborador);
  }

  @Override
  public Respuesta eliminar(Colaborador colaborador) {
    return this.getColaboradorDAO().eliminar(colaborador);
  }

  @Override
  public RespuestaGenerica<Colaborador> obtener(int id) {
    RespuestaGenerica<Colaborador> respuesta = this.getColaboradorDAO().obtener(id);
    Colaborador col = respuesta.getRespuesta();
    if (col.getEventos().size() > 0) {
      col.getEventos().forEach((evento) -> {
        evento.setColaboradores(null);
      });
    } else {
      col.setEventos(null);
    }
    respuesta.setRespuesta(col);
    return respuesta;
  }

  @Override
  public RespuestaGenerica<List<Colaborador>> listar() {
    return this.getColaboradorDAO().listar();
  }

  @SuppressWarnings("unchecked")
  public RespuestaGenerica<Integer> cantidadRegistros() {
    return this.getColaboradorDAO().cantidadRegistros();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
    jqGridModel<Colaborador> model = ((ColaboradorDAO) this.getColaboradorDAO()).obtenerTodosGrid(indice, orden, paginaActual, cantidadRegistros).getRespuesta();
    model.getRows().forEach((colaborador) -> {
      colaborador.setEventos(null);
    });
    return new RespuestaGenerica<>(model);
  }
}
