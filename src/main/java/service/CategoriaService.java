/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Categoria;
import exception.EntidadNoEncontradaException;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author ian
 */
public class CategoriaService {

    private List<Categoria> categorias = new ArrayList<>();
    private Long contadorId = 1L;

    public Categoria crear(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
            }
        }
        
        Categoria categoria = new Categoria(nombre, descripcion, contadorId);
        categorias.add(categoria);
        contadorId++;
        return categoria;
    }

    
    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return activas;
    }

    
    public void editar(Long id, String nombre, String descripcion) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id);
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
    }

    
    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true);
    }

    
    private Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getId().equals(id)) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("No existe una categoría con id " + id);
    }
}
