package com.example.lab9grupo1.controller;

import com.example.lab9grupo1.entity.Activity;
import com.example.lab9grupo1.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class ActivityWebService {

    @Autowired
    ActivityRepository activityRepository;

    /** listar actividades**/
    @GetMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProductos(){
        return new ResponseEntity(activityRepository.findAll(), HttpStatus.OK);
    }

    /** Crear actividad**/
    @PostMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarActividad(
            @RequestBody Activity actividad,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        activityRepository.save(actividad);
        if (fetchId) {
            responseMap.put("Id", actividad.getIdactividad());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }

    /** Editar actividad **/
    @PutMapping(value = "/actividad",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editarActividad(@RequestBody Activity actividad){
        HashMap<String, Object> responseMap = new HashMap<>();

        if(actividad.getIdproyecto() > 0){

            Optional<Activity> actOpt = activityRepository.findById(actividad.getIdactividad());
            if(actOpt.isPresent()){
                activityRepository.save(actividad);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            }else{
                responseMap.put("estado", "error");
                responseMap.put("msg", "El id de la actividad a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        }else{
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }


    /** Eliminar actividad **/
    @DeleteMapping(value = "/actividad/{idactividad}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@PathVariable("idactividad") String idStr){
        HashMap<String, Object> responseMap = new HashMap<>();

        try{
            int id = Integer.parseInt(idStr);
            if(activityRepository.existsById(id)){
                activityRepository.deleteById(id);
                responseMap.put("estado","borrado exitoso");
                return new ResponseEntity(responseMap,HttpStatus.OK);
            }else{
                responseMap.put("estado","error");
                responseMap.put("msg","no se encontró la actividad con id: " + id);
                return new ResponseEntity(responseMap,HttpStatus.BAD_REQUEST);
            }
        }catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar una actividad");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }


}
