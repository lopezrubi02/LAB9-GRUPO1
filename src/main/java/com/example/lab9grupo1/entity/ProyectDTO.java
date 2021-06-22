package com.example.lab9grupo1.entity;

import java.util.List;

public class ProyectDTO {

    private int idproyecto;
    private String nombreproyecto;
    private String usuario_owner;
    private List<Activity> listaActividades;


    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getNombreproyecto() {
        return nombreproyecto;
    }

    public void setNombreproyecto(String nombreproyecto) {
        this.nombreproyecto = nombreproyecto;
    }

    public String getUsuario_owner() {
        return usuario_owner;
    }

    public void setUsuario_owner(String usuario_owner) {
        this.usuario_owner = usuario_owner;
    }

    public List<Activity> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(List<Activity> listaActividades) {
        this.listaActividades = listaActividades;
    }
}
