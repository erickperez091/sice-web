/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.entidades.util;

import com.sice.pckg.entidades.util.UtilEnum.EstadoRespuesta;

/**
 *
 * @author erick
 */
public class Respuesta {
    private final EstadoRespuesta estado;
    private String errorActual;

    public Respuesta() {
        estado = EstadoRespuesta.EXITOSA;
    }

    public Respuesta(String errorActual) {
        this.estado = EstadoRespuesta.FALLIDA;
        this.errorActual = errorActual;
    }
    
    public Respuesta(Exception exception) {
        this.estado = EstadoRespuesta.FALLIDA;
        this.errorActual = exception.getMessage();
    }
    /**
     * @return Obtiene el valor del Error generado
     */
    public String getErrorActual() {
        return errorActual;
    }

    /**
     * @return Obtiene el Estado de la peticion realizada
     */
    public EstadoRespuesta getEstado() {
        return estado;
    }

    public void setErrorActual(String errorActual) {
        this.errorActual = errorActual;
    }
}
