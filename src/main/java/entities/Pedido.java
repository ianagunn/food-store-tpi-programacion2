/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import enums.Estado;
import enums.FormaPago;
/**
 *
 * @author ian
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles = new ArrayList<>();
    private Usuario usuario;

    public Pedido(Long id, Estado estado, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
   @Override
    public void calcularTotal() {
        Double sumador = 0.0;
        for (DetallePedido detalle : detalles) {
            sumador += detalle.getSubtotal();
        }
        this.total = sumador;
    }
    
    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        Long nuevoId = (long) (detalles.size() + 1);
        DetallePedido detalle = new DetallePedido(nuevoId, cantidad, producto);
        detalles.add(detalle);
        calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalles.remove(detalle);
            calcularTotal();
        }
    }
    
    @Override
    public String toString() {
        return "Pedido #" + getId()
            + " | Fecha: " + fecha
            + " | Estado: " + estado
            + " | FormaPago: " + formaPago
            + " | Usuario: " + (usuario != null ? usuario.getNombre() : "sin usuario")
            + " | Total: $" + total;
    }
}
