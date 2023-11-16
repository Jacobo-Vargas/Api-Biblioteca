package com.example.Biblioteca;

import com.example.Biblioteca.controllers.Api;
import com.example.Biblioteca.model.Libro;
import com.example.Biblioteca.repository.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Api.class)
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibroRepository libroRepository;

    @Test
    public void shouldReturnAllLibros() throws Exception {
        Libro libro = new Libro("El señor de los anillos", "J.R.R. Tolkien", 1954, "Fantasía");
        List<Libro> libros = Arrays.asList(libro);

        given(libroRepository.findAll()).willReturn(libros);

        mockMvc.perform(get("/apiLibros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo", is(libro.getTitulo())));
    }

    @Test
    public void shouldReturnLibroById() throws Exception {
        Long id = 1L;
        Libro libro = new Libro( "El señor de los anillos", "J.R.R. Tolkien", 1954, "Fantasía");

        given(libroRepository.findById(id)).willReturn(Optional.of(libro));

        mockMvc.perform(get("/apiLibros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(libro.getId())))
                .andExpect(jsonPath("$.titulo", is(libro.getTitulo())));
    }

    @Test
    public void shouldReturnNotFoundForInvalidLibroId() throws Exception {
        Long id = 1L;

        given(libroRepository.findById(id)).willReturn(Optional.empty());

        mockMvc.perform(get("/apiLibros/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewLibro() throws Exception {
        Libro libro = new Libro("El señor de los anillos", "J.R.R. Tolkien", 1954, "Fantasía");

        given(libroRepository.save(any(Libro.class))).willReturn(libro);

        mockMvc.perform(post("/apiLibros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(libro.getTitulo())));
    }

    @Test
    public void shouldUpdateLibro() throws Exception {
        Long id = 1L;
        Libro existingLibro = new Libro( "El señor de los anillos", "J.R.R. Tolkien", 1954, "Fantasía");
        Libro updatedLibro = new Libro("Nuevo título", "Nuevo autor", 2023, "Nuevo género");

        given(libroRepository.findById(id)).willReturn(Optional.of(existingLibro));
        given(libroRepository.save(any(Libro.class))).willReturn(updatedLibro);

        mockMvc.perform(put("/apiLibros/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLibro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(updatedLibro.getTitulo())))
                .andExpect(jsonPath("$.autor", is(updatedLibro.getAutor())))
                .andExpect(jsonPath("$.anio", is(updatedLibro.getAnio())))
                .andExpect(jsonPath("$.genero", is(updatedLibro.getGenero())));
    }

    @Test
    public void shouldReturnNotFoundForInvalidLibroUpdateId() throws Exception {
        Long id = 1L;
        Libro updatedLibro = new Libro( "Nuevo título", "Nuevo autor", 2023, "Nuevo género");

        given(libroRepository.findById(id)).willReturn(Optional.empty());

        mockMvc.perform(put("/apiLibros/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLibro)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteLibro() throws Exception {
        Long id = 1L;

        doNothing().when(libroRepository).deleteById(id);

        mockMvc.perform(delete("/apiLibros/{id}", id))
                .andExpect(status().isOk());

        verify(libroRepository).deleteById(id);
    }
}
