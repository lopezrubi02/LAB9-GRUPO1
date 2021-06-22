package com.example.lab9grupo1.repository;

import com.example.lab9grupo1.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    List<Activity> findAllByIdproyectoEquals(int idProyecto);
}
