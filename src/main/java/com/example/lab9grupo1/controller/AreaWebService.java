package com.example.lab9grupo1.controller;

import com.example.lab9grupo1.entity.*;
import com.example.lab9grupo1.repository.AreaRepository;
import com.example.lab9grupo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class AreaWebService {

    @Autowired
    AreaRepository areaRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/area",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity obtenerListaAreas(){

        return new ResponseEntity(areaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/area/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerArea(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Area> opt = areaRepository.findById(id);
            if (opt.isPresent()) {
                responseMap.put("estado", "ok");
                responseMap.put("area", opt.get());
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el area con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarArea(
            @RequestBody Area area,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        //valida que no mande vacío
        if(area.getNombrearea().isEmpty()){
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un area");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
        areaRepository.save(area);
        if (fetchId) {
            responseMap.put("id", area.getIdarea());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un area");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }



    @GetMapping(value = "/areaConUsuarios/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProyectosActividadades(@PathVariable("id") String idStr){

        HashMap<String,Object> responseMap = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);
            Optional<Area> opt = areaRepository.findById(id);
            if(opt.isPresent()){
                Area area = opt.get();
                List<User> usuarioList = userRepository.findAllByIdareaEquals(area.getIdarea());

                AreaDTO areaDTO = new AreaDTO();
                areaDTO.setIdarea(area.getIdarea());
                areaDTO.setNombrearea(area.getNombrearea());
                areaDTO.setListaUsuarios(usuarioList);

                responseMap.put("area",areaDTO);
                responseMap.put("estado","ok");
                return new ResponseEntity(responseMap,HttpStatus.OK);
            }else{
                responseMap.put("msg","No se encontro el proyecto con el id:"+id);
                responseMap.put("estado","error");
                return new ResponseEntity(responseMap,HttpStatus.BAD_REQUEST);
            }
        }catch (NumberFormatException e){
            responseMap.put("msg","El ID debe ser un número");
            responseMap.put("estado","error");
            return new ResponseEntity(responseMap,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarArea(@RequestBody Area area) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (area.getIdarea() > 0) {
            Optional<Area> opt = areaRepository.findById(area.getIdarea());
            if (opt.isPresent()) {
                areaRepository.save(area);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El area a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/area/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarArea(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (areaRepository.existsById(id)) {
                areaRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el area con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }



}
