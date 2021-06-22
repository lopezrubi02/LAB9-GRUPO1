package com.example.lab9grupo1.repository;

import com.example.lab9grupo1.entity.Activity;
import com.example.lab9grupo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    List<User> findAllByIdareaEquals(int idArea);
}
