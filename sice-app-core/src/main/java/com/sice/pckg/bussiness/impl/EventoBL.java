/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.bussiness.impl;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.dao.IDAO;
import com.sice.pckg.dao.impl.ColaboradorDAO;
import com.sice.pckg.dao.impl.EventoDAO;
import com.sice.pckg.entidades.Colaborador;
import com.sice.pckg.entidades.Evento;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.jqGridModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author erick
 */
public class EventoBL implements IBussiness<Evento>{
    
    @Autowired
    IDAO eventoDAO;

    public IDAO getEventoDAO() {
        return eventoDAO;
    }

    public void setEventoDAO(IDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }
    
    public Respuesta guardar(Evento evento) {
        return this.getEventoDAO().guardar(evento);
    }

    public Respuesta actualizar(Evento evento) {
        return this.getEventoDAO().actualizar(evento);
    }

    public Respuesta eliminar(Evento evento) {
        return this.getEventoDAO().eliminar(evento);
    }

    public RespuestaGenerica<Evento> obtener(int id) {
        return this.getEventoDAO().obtener(id);
    }

    public RespuestaGenerica<List<Evento>> listar() {
        return this.getEventoDAO().listar();
    }
    
    public RespuestaGenerica<Integer> cantidadRegistros(){
        return this.getEventoDAO().cantidadRegistros();
    }
    
    public RespuestaGenerica<jqGridModel> obtenerTodosGrid(String indice, String orden, int paginaActual, int cantidadRegistros) {
        jqGridModel<Evento> model = ((EventoDAO) this.getEventoDAO()).obtenerTodosGrid(indice, orden, paginaActual, cantidadRegistros).getRespuesta();
        for(Evento evento : model.getRows()){
            evento.setColaboradores(null);
        }
        return new RespuestaGenerica<jqGridModel>(model);
    }
    
    public RespuestaGenerica<List<Evento>> obtenerEventosSinColaborador(int idColaborador){
        return ((EventoDAO)this.getEventoDAO()).obtenerEventosSinColaborador(idColaborador);
    }
}
