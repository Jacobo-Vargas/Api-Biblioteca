package com.example.Biblioteca.controllers;

import com.example.Biblioteca.model.Libro;
import com.example.Biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * hace uso de una clase JpaRepository que realiza metodos CRUD de forma automatica
 */

@RestController
@RequestMapping("apiLibros")
public class Api {

    @Autowired
    private LibroRepository libroRepositorio;

    // Endpoint para obtener todos los libros
    @GetMapping
    public ResponseEntity<List<Libro>> obtenerLibros() {
        List<Libro> libros = libroRepositorio.findAll();
        return ResponseEntity.ok().body(libros);
    }

    // Endpoint para obtener un libro por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroRepositorio.findById(id)
                .map(libro -> ResponseEntity.ok().body(libro))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para agregar un nuevo libro
    @PostMapping
    public ResponseEntity<Libro> agregarLibro(@RequestBody Libro libro) {
        Libro nuevoLibro = libroRepositorio.save(libro);
        return ResponseEntity.ok().body(nuevoLibro);
    }

    // Endpoint para actualizar un libro existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        return libroRepositorio.findById(id)
                .map(libroExistente -> {
                    libroExistente.setTitulo(libroActualizado.getTitulo());
                    libroExistente.setAutor(libroActualizado.getAutor());
                    libroExistente.setAnio(libroActualizado.getAnio());
                    libroExistente.setGenero(libroActualizado.getGenero());
                    Libro libroActualizadoEntity = libroRepositorio.save(libroExistente);
                    return ResponseEntity.ok().body(libroActualizadoEntity);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para eliminar un libro por su ID
    @DeleteMapping("/{id}")
    public void eliminarLibro(@PathVariable Long id) {
        libroRepositorio.deleteById(id);
    }
}


