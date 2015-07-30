/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prueba.spring.entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.commons.collections.functors.InstantiateFactory;
import org.apache.commons.collections.list.LazyList;
/**
 *
 * @author erick
 */
@Entity
@Table(name = "COLABORADOR", indexes = {@Index(unique = true, name = "IX_COLABORADOR", columnList = "ID_COLABORADOR")})
public class Colaborador implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_COLABORADOR", unique = true, nullable = false)
    private int idColaborador;
    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;
    @Column(name = "IDENTIFICACION", unique = true, nullable = false, length = 255)
    private String identificacion;
    @Column(name = "TELEFONO", nullable = false, length = 255)
    private String telefono;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "EDAD")
    private int edad;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "COLABORADOR_EVENTO", 
            joinColumns = {@JoinColumn(name = "ID_COLABORADOR", referencedColumnName = "ID_COLABORADOR", unique = true, nullable = false, updatable = true)
            }, 
            inverseJoinColumns = {@JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", unique = true, nullable = false, updatable = true)},
            uniqueConstraints = {@UniqueConstraint(name = "CTX_COLABORADOREVENTO", columnNames = {"ID_COLABORADOR","ID_EVENTO"})}, 
            foreignKey = @ForeignKey(name = "ID_COLABORADOR", value = ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(name = "ID_EVENTO", value = ConstraintMode.CONSTRAINT)
    )
    List<Evento> eventos;

    public Colaborador() {
        eventos = LazyList.decorate(new ArrayList<Evento>(), new InstantiateFactory(Evento.class));
        //eventos = ListOrderedSet.decorate(new HashSet<Evento>());
        //eventos = TypedSortedSet.decorate(new HashSet<Evento>(), Evento.class);
    }

    public Colaborador(int idColaborador, String nombre, String identificacion, String telefono, String direccion, int edad, List<Evento> eventos) {
        this.idColaborador = idColaborador;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
        this.eventos = eventos;
    }
    
    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
