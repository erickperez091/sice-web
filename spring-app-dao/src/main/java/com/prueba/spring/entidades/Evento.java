/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.apache.commons.collections.functors.InstantiateFactory;
import org.apache.commons.collections.list.LazyList;

/**
 *
 * @author erick
 */
@Entity
@Table(name = "EVENTO", indexes = {@Index(unique = true, name = "IX_EVENTO", columnList = "ID_EVENTO")})
public class Evento implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_EVENTO", unique = true, nullable = false)
    private int idEvento;
    @Column(name = "DESCRIPCION", nullable = false, length = 255)
    private String descripcion;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "FECHA_EVENTO", nullable = false)
    private Date fechaEvento;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "eventos")
    List<Colaborador> colaboradores;

    public Evento(int idEvento, String descripcion, Date fechaEvento, List<Colaborador> colaboradores) {
        this.idEvento = idEvento;
        this.descripcion = descripcion;
        this.fechaEvento = fechaEvento;
        this.colaboradores = colaboradores;
    }


    public Evento() {
        colaboradores = LazyList.decorate(new ArrayList<Colaborador>(), new InstantiateFactory(Colaborador.class));
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }
}
