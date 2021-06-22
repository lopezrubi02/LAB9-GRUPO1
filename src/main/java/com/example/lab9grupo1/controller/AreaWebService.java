package com.example.lab9grupo1.controller;

import com.example.lab9grupo1.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
public class AreaWebService {

    @Autowired
    AreaRepository areaRepository;

    @GetMapping(value = "/area",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity obtenerListaAreas(){

        return new ResponseEntity(areaRepository.findAll(), HttpStatus.OK);
    }



}
