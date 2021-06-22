package com.example.lab9grupo1.entity;


import javax.persistence.*;

@Entity
@Table(name="proyectos")
public class Proyect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idproyecto")
    private int idproyecto;

    @Column(name="nombreProyecto")
    private String nombreProyecto;

    @ManyToOne
    @Column(name="usuario_owner")
    private User usuarioOwner;



}
