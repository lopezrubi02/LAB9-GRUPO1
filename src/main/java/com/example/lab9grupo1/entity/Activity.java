package com.example.lab9grupo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "actividades")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idactividad;

    @Column(name = "nombreActividad")
    private String nombreactividad;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idproyecto")
    private Proyect proyecto;

    @ManyToOne
    @JoinColumn(name = "usuario_owner")
    private User usuarioowner;

    private int peso;

    private int estado;

    public int getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(int idactividad) {
        this.idactividad = idactividad;
    }

    public String getNombreactividad() {
        return nombreactividad;
    }

    public void setNombreactividad(String nombreactividad) {
        this.nombreactividad = nombreactividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Proyect getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyect proyecto) {
        this.proyecto = proyecto;
    }

    public User getUsuarioowner() {
        return usuarioowner;
    }

    public void setUsuarioowner(User usuarioowner) {
        this.usuarioowner = usuarioowner;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
