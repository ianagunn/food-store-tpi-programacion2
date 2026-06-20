/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Pedido;
import entities.Usuario;
import entities.Producto;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.PedidoSinUsuarioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ian
 */

public class PedidoService {

    private List<Pedido> pedidos = new ArrayList<>();
    private Long contadorId = 1L;

    public Pedido crear(Usuario usuario, Estado estado, FormaPago formaPago,
                        List<Producto> productos, List<Integer> cantidades)
            throws PedidoSinUsuarioException {

        if (usuario == null || usuario.isEliminado()) {
            throw new PedidoSinUsuarioException("El pedido debe tener un usuario válido.");
        }

        Pedido pedido = new Pedido(contadorId, estado, formaPago, usuario);

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);

            if (cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
            }
            pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
        }

        pedidos.add(pedido);
        contadorId++;
        return pedido;
    }

    public List<Pedido> listar() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public void actualizar(Long id, Estado estado, FormaPago formaPago)
            throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(estado);
        pedido.setFormaPago(formaPago);
    }

    public void eliminar(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true);
    }

    private Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getId().equals(id)) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No existe un pedido con id " + id);
    }
}
