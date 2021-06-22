package com.example.lab9grupo1.controller;

import com.example.lab9grupo1.entity.User;
import com.example.lab9grupo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserWebService {

    @Autowired
    UserRepository userRepository;

    /** Listar usuarios **/
    @GetMapping(value = "/usuario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarUsuarios(){
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    /** Crear usuario**/
    @PostMapping(value = "/usuario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarUsuario(
            @RequestBody User usuario,
            @RequestParam(value = "fetchCorreo", required = false) boolean fetchCorreo) {

        HashMap<String, Object> responseMap = new HashMap<>();
        userRepository.save(usuario);
        if (fetchCorreo) {
            responseMap.put("correo", usuario.getCorreo());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);
    }

    /** Editar usuario **/
    @PutMapping(value = "/usuario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editarUsuario(@RequestBody User usuario){
        HashMap<String, Object> responseMap = new HashMap<>();

        Optional<User> userOpt = userRepository.findById(usuario.getCorreo());
        if(userOpt.isPresent()){
            userRepository.save(usuario);
            responseMap.put("estado", "actualizado");
            return new ResponseEntity(responseMap, HttpStatus.OK);
        }else{
            responseMap.put("estado", "error");
            responseMap.put("msg", "El correo del usuario a actualizar no existe");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }


    /** Eliminar usuario **/
    @DeleteMapping(value = "/usuario/{correo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarUsuario(@PathVariable("correo") String correo){
        HashMap<String, Object> responseMap = new HashMap<>();

        if(userRepository.existsById(correo)){
            userRepository.deleteById(correo);
            responseMap.put("estado","borrado exitoso");
            return new ResponseEntity(responseMap,HttpStatus.OK);
        }else{
            responseMap.put("estado","error");
            responseMap.put("msg","no se encontró el usuario con correo" + correo);
            return new ResponseEntity(responseMap,HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un usuario");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }


}
