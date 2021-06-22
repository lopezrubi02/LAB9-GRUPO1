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
}
