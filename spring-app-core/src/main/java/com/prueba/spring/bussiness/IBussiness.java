/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.bussiness;

import com.prueba.spring.entidades.util.Respuesta;
import com.prueba.spring.entidades.util.RespuestaGenerica;
import java.util.List;

/**
 *
 * @author erick
 */
public interface IBussiness<T> {
    
    Respuesta guardar(T t);
    
    Respuesta actualizar(T t);
    
    Respuesta eliminar(T t);
    
    RespuestaGenerica<T> obtener(int id);
    
    RespuestaGenerica<List<T>> listar();
}
