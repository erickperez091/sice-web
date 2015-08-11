/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.bussiness.impl.ColaboradorBL;
import com.sice.pckg.entidades.Colaborador;
import com.sice.pckg.entidades.util.Respuesta;
import com.sice.pckg.entidades.util.RespuestaGenerica;
import com.sice.pckg.entidades.util.UtilEnum;
import com.sice.pckg.entidades.util.jqGridModel;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author erick
 */
@RestController
@RequestMapping(value = "/Colaborador")
public class ColaboradorController {

    @Autowired
    IBussiness colaboradorBL;

    public IBussiness getColaboradorBL() {
        return colaboradorBL;
    }

    public void setColaboradorBL(IBussiness colaboradorBL) {
        this.colaboradorBL = colaboradorBL;
    }

    @RequestMapping(value = "/Mantenimiento-Colaboradores")
    public ModelAndView mantenimientoColaboradores(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Map<String, Object> model = new HashMap<>();
        model.put("colaborador", new Colaborador());
        model.put("username", principal.getName());
        return new ModelAndView("Mantenimiento-Colaboradores", model);
    }

    @RequestMapping(value = "/ListarColaboradores")
    public @ResponseBody
    jqGridModel<Colaborador> listarColaboradores(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,
            @RequestParam(value = "sidx") String sidx, @RequestParam(value = "sord") String sord) {
        jqGridModel<Colaborador> model = ((ColaboradorBL) this.getColaboradorBL()).obtenerTodosGrid(sidx, sord, page, rows).getRespuesta();
        return model;
    }

    @RequestMapping(value = "/AgregarColaborador", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta agregarColaborador(@ModelAttribute("colaborador") Colaborador colaborador, HttpServletRequest request, HttpServletResponse response) {
        Respuesta respuesta = this.getColaboradorBL().guardar(colaborador);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }
    
    @RequestMapping(value = "/ActualizarColaborador", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta actualizarColaborador(@ModelAttribute("colaborador") Colaborador colaborador, HttpServletRequest request, HttpServletResponse response) {
        Respuesta respuesta = this.getColaboradorBL().actualizar(colaborador);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }
    
    @RequestMapping(value = "/EliminarColaborador", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta eliminarColaborador(@ModelAttribute(value = "colaborador") Colaborador colaborador, HttpServletRequest request, HttpServletResponse response) {
        Respuesta respuesta = this.getColaboradorBL().eliminar(colaborador);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }
    
    @RequestMapping(value = "/ObtenerColaborador", method = RequestMethod.GET)
    public @ResponseBody
    RespuestaGenerica<Colaborador> obtenerColaborador(int identificacion, HttpServletRequest request, HttpServletResponse response) {
        RespuestaGenerica<Colaborador> respuesta = this.getColaboradorBL().obtener(identificacion);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }
}
