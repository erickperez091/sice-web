/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg;

import com.sice.pckg.bussiness.IBussiness;
import com.sice.pckg.bussiness.impl.UsuarioBL;
import com.sice.pckg.entidades.Usuario;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ERICK
 */
@RestController
@RequestMapping(value = "/Usuario")
public class UsuarioController {

    @Autowired
    IBussiness usuarioBL;

    public IBussiness getUsuarioBL() {
        return usuarioBL;
    }

    public void setUsuarioBL(IBussiness usuarioBL) {
        this.usuarioBL = usuarioBL;
    }

    @RequestMapping(value = "/Mantenimiento-Usuarios")
    public ModelAndView mantenimientoUsuarios(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", new Usuario());
        model.put("username", principal != null ? principal.getName() : "");
        return new ModelAndView("Mantenimiento-Usuarios", model);
    }

    @RequestMapping(value = "/ListarUsuarios")
    public @ResponseBody
    jqGridModel<Usuario> listarUsuarios(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,
            @RequestParam(value = "sidx") String sidx, @RequestParam(value = "sord") String sord) {
        jqGridModel<Usuario> model = ((UsuarioBL) this.getUsuarioBL()).obtenerTodosGrid(sidx, sord, page, rows).getRespuesta();
        return model;
    }

    @RequestMapping(value = "/AgregarUsuario", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta agregarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        //String nombre = request.getParameter("usuario");
        Respuesta respuesta = this.getUsuarioBL().guardar(usuario);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }

    @RequestMapping(value = "/ActualizarUsuario", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta actualizarUsuario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        Respuesta respuesta = this.getUsuarioBL().actualizar(usuario);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }

    @RequestMapping(value = "/EliminarUsuario", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta eliminarUsuario(@RequestBody Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        Respuesta respuesta = this.getUsuarioBL().eliminar(usuario);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }

    @RequestMapping(value = "/ObtenerUsuario", method = RequestMethod.GET)
    public @ResponseBody
    RespuestaGenerica<Usuario> obtenerUsuario(int identificacion, HttpServletRequest request, HttpServletResponse response) {
        RespuestaGenerica<Usuario> respuesta = this.getUsuarioBL().obtener(identificacion);
        if (respuesta.getEstado() == UtilEnum.EstadoRespuesta.FALLIDA) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return respuesta;
    }
}
