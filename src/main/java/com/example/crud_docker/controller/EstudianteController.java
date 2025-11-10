/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.crud_docker.controller;

/**
 *
 * @author ferna
 */
import com.example.crud_docker.model.Estudiante;
import com.example.crud_docker.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 1. @RestController: Le dice a Spring que esta clase es un controlador de API
@RestController
// 2. @RequestMapping: Todas las URLs de esta clase empezarán con "/api/estudiantes"
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    // 3. @Autowired: Le pedimos a Spring que nos inyecte (nos pase)
    //    una instancia de nuestro EstudianteRepository
    private final EstudianteRepository repository;

    @Autowired
    public EstudianteController(EstudianteRepository repository) {
        this.repository = repository;
    }

    // --- LOS MÉTODOS DEL CRUD ---

    /**
     * CREATE (Crear)
     * Escuchará peticiones POST en "/api/estudiantes"
     * @RequestBody le dice a Spring que convierta el JSON de la petición
     * en un objeto Estudiante.
     */
    @PostMapping
    public ResponseEntity<String> createEstudiante(@RequestBody Estudiante estudiante) {
        repository.save(estudiante);
        // ResponseEntity nos permite devolver un código HTTP (201 = Creado)
        return new ResponseEntity<>("Estudiante creado exitosamente", HttpStatus.CREATED);
    }

    /**
     * READ (Leer Todos)
     * Escuchará peticiones GET en "/api/estudiantes"
     */
    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return repository.findAll();
    }

    /**
     * READ (Leer Uno por ID)
     * Escuchará peticiones GET en "/api/estudiantes/5" (por ejemplo)
     * @PathVariable extrae el "5" de la URL y lo pasa como el Long id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        Estudiante estudiante = repository.findById(id);
        if (estudiante != null) {
            // Devuelve el estudiante y un código 200 (OK)
            return new ResponseEntity<>(estudiante, HttpStatus.OK);
        } else {
            // Devuelve un código 404 (No Encontrado)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * UPDATE (Actualizar)
     * Escuchará peticiones PUT en "/api/estudiantes/5"
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Estudiante estudiante = repository.findById(id);
        if (estudiante == null) {
            return new ResponseEntity<>("Estudiante no encontrado", HttpStatus.NOT_FOUND);
        }

        // Nos aseguramos de actualizar el estudiante correcto
        estudianteDetails.setId(id); 
        repository.update(estudianteDetails);
        return new ResponseEntity<>("Estudiante actualizado exitosamente", HttpStatus.OK);
    }

    /**
     * DELETE (Borrar)
     * Escuchará peticiones DELETE en "/api/estudiantes/5"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEstudiante(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Estudiante eliminado exitosamente", HttpStatus.OK);
    }
}