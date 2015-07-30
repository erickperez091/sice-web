/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring;

import com.prueba.spring.bussiness.IBussiness;
import com.prueba.spring.bussiness.impl.ColaboradorBL;
import com.prueba.spring.entidades.Colaborador;
import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.jqGridModel;
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

    @RequestMapping(value = "/ListarColaboradores")
    public @ResponseBody
    jqGridModel<Colaborador> listarColaboradores(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,
            @RequestParam(value = "sidx") String sidx, @RequestParam(value = "sord") String sord) {
        jqGridModel<Colaborador> model = ((ColaboradorBL) this.getColaboradorBL()).obtenerTodosGrid(sidx, sord, page, rows).getRespuesta();
        return model;
    }

    @RequestMapping(value = "/AgregarColaborador")
    public @ResponseBody
    Respuesta agregarColaborador(@RequestBody Colaborador colaborador){
        Respuesta respuesta = this.getColaboradorBL().guardar(new Colaborador());
        return respuesta;
    }
    
    @RequestMapping(value = "/EliminarColaborador", method = RequestMethod.POST)
    public @ResponseBody
    Respuesta eliminarColaborador(HttpServletRequest request, @ModelAttribute(value = "colaborador") Colaborador colaborador) {
        return this.getColaboradorBL().eliminar(colaborador);
    }
}
