/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.entidades.util;

/**
 *
 * @author erick
 * @param <T> Indica el tipo de dato de la RespuestaGenerica
 */
 
public class RespuestaGenerica<T> extends Respuesta{
    private T respuesta;

    public RespuestaGenerica(Exception exception) {
        super(exception);
        respuesta = null;
    }

    public RespuestaGenerica(String errorActual) {
        super(errorActual);
        respuesta = null;
    }

    public RespuestaGenerica() {
        super();
        this.setErrorActual(null);
    }

    public RespuestaGenerica(T respuesta) {
        super();
        this.setErrorActual(null);
        this.respuesta = respuesta;
    }

    public void setRespuesta(T respuesta) {
        this.respuesta = respuesta;
    }

    public T getRespuesta() {
        return respuesta;
    }
}
