/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Producto;
import entities.Categoria;
import exception.EntidadNoEncontradaException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ian
 */

public class ProductoService {

    private List<Producto> productos = new ArrayList<>();
    private Long contadorId = 1L;

    public Producto crear(String nombre, Double precio, String descripcion, int stock,
                          String imagen, Boolean disponible, Categoria categoria)
            throws PrecioInvalidoException, StockInvalidoException {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        
        if (precio == null || precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo.");
        }

        if (categoria == null || categoria.isEliminado()) {
            throw new IllegalArgumentException("La categoría no es válida o está eliminada.");
        }

        Producto producto = new Producto(contadorId, nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(producto);
        contadorId++;
        return producto;
    }


    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public void editar(Long id, String nombre, Double precio, String descripcion,
                       int stock, Categoria categoria)
            throws EntidadNoEncontradaException, PrecioInvalidoException, StockInvalidoException {

        Producto producto = buscarPorId(id);

        if (precio == null || precio < 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo.");
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setCategoria(categoria);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
    }

    private Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getId().equals(id)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No existe un producto con id " + id);
    }
}
