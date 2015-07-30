/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring;

import com.prueba.spring.bussiness.IBussiness;
import com.prueba.spring.bussiness.impl.UsuarioBL;
import com.prueba.spring.entidades.Usuario;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.jqGridModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public ModelAndView mantenimientoUsuarios() {
        return new ModelAndView("Mantenimiento-Usuarios", "usuario", new Usuario());
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
    Respuesta agregarUsuario(@RequestBody Usuario usuario, HttpServletRequest request, HttpServletResponse response) {
        //String nombre = request.getParameter("usuario");
        Respuesta respuesta = this.getUsuarioBL().guardar(usuario);
        return respuesta;
    }
}