/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.bussiness.impl.EventoBL;
import com.sice.pckg.entidades.Colaborador;
import com.sice.pckg.entidades.Evento;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.jqGridModel;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author erick
 */
@RestController
@RequestMapping(value = "/Evento")
public class EventoController {
    
    @Autowired
    IBussiness eventoBL;

    public IBussiness getEventoBL() {
        return eventoBL;
    }

    public void setEventoBL(IBussiness eventoBL) {
        this.eventoBL = eventoBL;
    }
    
    @RequestMapping(value = "/ListarEventos")
    public @ResponseBody
    jqGridModel<Evento> listarEventos(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,
            @RequestParam(value = "sidx") String sidx, @RequestParam(value = "sord") String sord) {
        jqGridModel<Evento> model = ((EventoBL) this.getEventoBL()).obtenerTodosGrid(sidx, sord, page, rows).getRespuesta();
        return model;
    }

    @RequestMapping(value = "/AgregarEvento")
    public @ResponseBody
    Respuesta agregarEvento(@RequestBody Evento evento){
        Respuesta respuesta = this.getEventoBL().guardar(new Colaborador());
        return respuesta;
    }
    
    @RequestMapping(value = "/EliminarEvento", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta eliminarEvento(HttpServletRequest request, @ModelAttribute(value = "evento") Evento evento) {
        return this.getEventoBL().eliminar(evento);
    }
}
