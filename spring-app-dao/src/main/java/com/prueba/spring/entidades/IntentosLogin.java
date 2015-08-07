/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.entidades;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Erick
 */
@Entity
@Table(name = "IntentosLogin", indexes = {
    @Index(name = "IX_IntentosLogin", unique = true, columnList = "ID_INTENTOSLOGIN")})
public class IntentosLogin implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_INTENTOSLOGIN", unique = true, nullable = false)
    private int idIntentosLogin;
    
    @Column(name = "USUARIO")
    private String usuario;
    
    @Column(name = "CANTIDAD_INTENTOS")
    private int cantidad;
    
    @Column(name = "ULTIMA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ultimaModificacion;

    public IntentosLogin() {
    }

    public IntentosLogin(int idIntentosLogin, String usuario, int cantidad, Date ultimaModificacion) {
        this.idIntentosLogin = idIntentosLogin;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.ultimaModificacion = ultimaModificacion;
    }

    public int getIdIntentosLogin() {
        return idIntentosLogin;
    }

    public void setIdIntentosLogin(int idIntentosLogin) {
        this.idIntentosLogin = idIntentosLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }
    
}
