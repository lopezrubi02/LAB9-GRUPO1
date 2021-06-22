package com.example.lab9grupo1.repository;

import com.example.lab9grupo1.entity.Proyect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProyectRepository extends JpaRepository<Proyect,Integer> {

}
