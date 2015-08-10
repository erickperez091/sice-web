/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sice.pckg.entidades;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 *
 * @author ERICK
 */
@Entity
@Table(name = "USUARIO", indexes = {
    @Index(unique = true, name = "IX_IDUSUARIO", columnList = "ID_USUARIO"),
    @Index(unique = true, columnList = "USUARIO", name = "IX_USUARIO")})
public class Usuario implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_USUARIO", unique = true, nullable = false)
    private int idUsuario;
    @Column(name = "USUARIO", unique = true, nullable = false)
    private String usuario;
    @Column(name = "CONTRASENNA")
    private String contrasenna;
    @Column(name = "HABILITADO")
    private boolean habilitado;
    @Column(name = "BLOQUEADO")
    private boolean bloqueado;

    public Usuario() {
    }

    public Usuario(int idUsuario, String usuario, String contrasenna, boolean habilitado, boolean bloqueado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasenna = contrasenna;
        this.habilitado = habilitado;
        this.bloqueado = bloqueado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

}
