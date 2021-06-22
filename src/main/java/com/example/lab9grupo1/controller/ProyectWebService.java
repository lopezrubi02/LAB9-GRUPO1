package com.example.lab9grupo1.controller;

import com.example.lab9grupo1.entity.Activity;
import com.example.lab9grupo1.entity.Area;
import com.example.lab9grupo1.entity.Proyect;
import com.example.lab9grupo1.entity.ProyectDTO;
import com.example.lab9grupo1.repository.ActivityRepository;
import com.example.lab9grupo1.repository.AreaRepository;
import com.example.lab9grupo1.repository.ProyectRepository;
import com.example.lab9grupo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ProyectWebService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProyectRepository proyectRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    ActivityRepository activityRepository;

    @GetMapping(value = "/proyecto",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProyectos(){
        return new ResponseEntity(proyectRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/proyecto/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity listarProyectoId(@PathVariable("id") String idStr){

        HashMap<String,Object> responseMap = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);
            Optional<Proyect> opt = proyectRepository.findById(id);
            if(opt.isPresent()){
                Proyect proyect = opt.get();
                responseMap.put("proyecto",proyect);
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

    @PostMapping(value = "/proyecto",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarProyecto(
            @RequestBody Proyect proyecto,
            @RequestParam(value = "fetchId", required = false) boolean fetchId){

        HashMap<String,Object> responseMap = new HashMap<>();

        proyectRepository.save(proyecto);
        if (fetchId) {
            responseMap.put("Id", proyecto.getIdproyecto());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }

    @GetMapping(value = "/proyectoConActividades/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProyectosActividadades(@PathVariable("id") String idStr){

        HashMap<String,Object> responseMap = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);
            Optional<Proyect> opt = proyectRepository.findById(id);
            if(opt.isPresent()){
                Proyect proyect = opt.get();
                List<Activity> activityList = activityRepository.findAllByIdproyectoEquals(proyect.getIdproyecto());

                ProyectDTO proyectDTO = new ProyectDTO();
                proyectDTO.setIdproyecto(proyect.getIdproyecto());
                proyectDTO.setNombreproyecto(proyect.getNombreproyecto());
                proyectDTO.setUsuario_owner(proyect.getUsuario_owner());
                proyectDTO.setListaActividades(activityList);

                responseMap.put("proyecto",proyectDTO);
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


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity ExcepcionPost(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST")) {
            responseMap.put("msg", "Debe enviar un proyecto");
            responseMap.put("estado", "error");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }
}
