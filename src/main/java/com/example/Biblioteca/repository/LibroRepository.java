package com.example.Biblioteca.repository;

import com.example.Biblioteca.model.Libro;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * permite realizar operaciones de base de datos relacionadas con la entidad Libro
 * sin tener que escribir implementaciones específicas de consultas y operaciones CRUD,
 * ya que Spring Data JPA se encarga de proporcionar implementaciones automáticamente.
 */

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
