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

    private int idproyecto;

    private String usuario_owner;

    private int peso;

    private boolean estado;

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

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getUsuario_owner() {
        return usuario_owner;
    }

    public void setUsuario_owner(String usuario_owner) {
        this.usuario_owner = usuario_owner;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
