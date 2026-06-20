/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;
import enums.Rol;
import enums.Estado;
import enums.FormaPago;
import service.CategoriaService;
import service.ProductoService;
import service.UsuarioService;
import service.PedidoService;
import exception.EntidadNoEncontradaException;
import exception.PrecioInvalidoException;
import exception.StockInvalidoException;
import exception.MailDuplicadoException;
import exception.PedidoSinUsuarioException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ian
 */

public class Main {

    // Los services viven mientras corre el programa: acá se guardan los datos en memoria.
    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final PedidoService pedidoService = new PedidoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> menuCategorias();
                case "2" -> menuProductos();
                case "3" -> menuUsuarios();
                case "4" -> menuPedidos();
                case "0" -> {
                    salir = true;
                    System.out.println("¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
        System.out.println("1. Categorías");
        System.out.println("2. Productos");
        System.out.println("3. Usuarios");
        System.out.println("4. Pedidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione: ");
    }

    // ============================================================
    // SUBMENÚ DE CATEGORÍAS
    // ============================================================

    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> listarCategorias();
                case "2" -> crearCategoria();
                case "3" -> editarCategoria();
                case "4" -> eliminarCategoria();
                case "0" -> volver = true;
                default  -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void listarCategorias() {
        List<Categoria> categorias = categoriaService.listar();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        for (Categoria c : categorias) {
            System.out.println("ID " + c.getId() + " | " + c.getNombre() + " | " + c.getDescripcion());
        }
    }

    private static void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        try {
            Categoria categoria = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada con éxito. ID generado: " + categoria.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarCategoria() {
        listarCategorias();
        System.out.print("ID de la categoría a editar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva descripción: ");
            String descripcion = scanner.nextLine();
            categoriaService.editar(id, nombre, descripcion);
            System.out.println("Categoría actualizada con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarCategoria() {
        listarCategorias();
        System.out.print("ID de la categoría a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("¿Confirma la eliminación? (S/N): ");
            String confirma = scanner.nextLine();
            if (confirma.equalsIgnoreCase("S")) {
                categoriaService.eliminar(id);
                System.out.println("Categoría eliminada.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================================
    // SUBMENÚ DE PRODUCTOS
    // ============================================================

    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> listarProductos();
                case "2" -> crearProducto();
                case "3" -> editarProducto();
                case "4" -> eliminarProducto();
                case "0" -> volver = true;
                default  -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void listarProductos() {
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        for (Producto p : productos) {
            String catNombre = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "sin categoría";
            System.out.println("ID " + p.getId() + " | " + p.getNombre()
                    + " | $" + p.getPrecio() + " | Stock: " + p.getStock()
                    + " | Categoría: " + catNombre);
        }
    }

    private static void crearProducto() {
        // Para asociar el producto, primero mostramos las categorías disponibles
        System.out.println("Categorías disponibles:");
        listarCategorias();
        if (categoriaService.listar().isEmpty()) {
            System.out.println("Debe crear una categoría antes de cargar productos.");
            return;
        }
        try {
            System.out.print("ID de la categoría: ");
            Long catId = Long.parseLong(scanner.nextLine());
            Categoria categoria = buscarCategoriaPorId(catId);
            if (categoria == null) {
                System.out.println("Error: no existe una categoría con ese ID.");
                return;
            }

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();
            System.out.print("Precio: ");
            Double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Imagen (URL o nombre de archivo): ");
            String imagen = scanner.nextLine();
            System.out.print("¿Disponible? (S/N): ");
            Boolean disponible = scanner.nextLine().equalsIgnoreCase("S");

            Producto producto = productoService.crear(nombre, precio, descripcion, stock, imagen, disponible, categoria);
            System.out.println("Producto creado con éxito. ID generado: " + producto.getId());
        } catch (NumberFormatException e) {
            System.out.println("Error: precio, stock e ID deben ser números válidos.");
        } catch (PrecioInvalidoException | StockInvalidoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarProducto() {
        listarProductos();
        System.out.print("ID del producto a editar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva descripción: ");
            String descripcion = scanner.nextLine();
            System.out.print("Nuevo precio: ");
            Double precio = Double.parseDouble(scanner.nextLine());
            System.out.print("Nuevo stock: ");
            int stock = Integer.parseInt(scanner.nextLine());

            System.out.println("Categorías disponibles:");
            listarCategorias();
            System.out.print("ID de la nueva categoría: ");
            Long catId = Long.parseLong(scanner.nextLine());
            Categoria categoria = buscarCategoriaPorId(catId);
            if (categoria == null) {
                System.out.println("Error: no existe una categoría con ese ID.");
                return;
            }

            productoService.editar(id, nombre, precio, descripcion, stock, categoria);
            System.out.println("Producto actualizado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: precio, stock e ID deben ser números válidos.");
        } catch (EntidadNoEncontradaException | PrecioInvalidoException | StockInvalidoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        listarProductos();
        System.out.print("ID del producto a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("¿Confirma la eliminación? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                productoService.eliminar(id);
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================================
    // SUBMENÚ DE USUARIOS
    // ============================================================

    private static void menuUsuarios() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> listarUsuarios();
                case "2" -> crearUsuario();
                case "3" -> editarUsuario();
                case "4" -> eliminarUsuario();
                case "0" -> volver = true;
                default  -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println("ID " + u.getId() + " | " + u.getNombre() + " " + u.getApellido()
                    + " | " + u.getMail() + " | Rol: " + u.getRol());
        }
    }

    private static void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Mail: ");
        String mail = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenia = scanner.nextLine();
        Rol rol = leerRol();
        if (rol == null) {
            System.out.println("Rol inválido. Operación cancelada.");
            return;
        }
        try {
            Usuario usuario = usuarioService.crear(nombre, apellido, mail, celular, contrasenia, rol);
            System.out.println("Usuario creado con éxito. ID generado: " + usuario.getId());
        } catch (MailDuplicadoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editarUsuario() {
        listarUsuarios();
        System.out.print("ID del usuario a editar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Nuevo mail: ");
            String mail = scanner.nextLine();
            System.out.print("Nuevo celular: ");
            String celular = scanner.nextLine();
            System.out.print("Nueva contraseña: ");
            String contrasenia = scanner.nextLine();
            Rol rol = leerRol();
            if (rol == null) {
                System.out.println("Rol inválido. Operación cancelada.");
                return;
            }
            usuarioService.editar(id, nombre, apellido, mail, celular, contrasenia, rol);
            System.out.println("Usuario actualizado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException | MailDuplicadoException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        listarUsuarios();
        System.out.print("ID del usuario a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("¿Confirma la eliminación? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                usuarioService.eliminar(id);
                System.out.println("Usuario eliminado.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================================
    // SUBMENÚ DE PEDIDOS
    // ============================================================

    private static void menuPedidos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> listarPedidos();
                case "2" -> crearPedido();
                case "3" -> actualizarPedido();
                case "4" -> eliminarPedido();
                case "0" -> volver = true;
                default  -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);  // usa el toString() de Pedido
        }
    }

    private static void crearPedido() {
        // 1) Elegir el usuario (debe existir y estar activo)
        System.out.println("Usuarios disponibles:");
        listarUsuarios();
        if (usuarioService.listar().isEmpty()) {
            System.out.println("Debe crear un usuario antes de cargar un pedido.");
            return;
        }
        // 2) Verificar que haya productos para cargar
        if (productoService.listar().isEmpty()) {
            System.out.println("Debe crear productos antes de cargar un pedido.");
            return;
        }
        try {
            System.out.print("ID del usuario: ");
            Long usuarioId = Long.parseLong(scanner.nextLine());
            Usuario usuario = usuarioService.buscarPorId(usuarioId);

            FormaPago formaPago = leerFormaPago();
            if (formaPago == null) {
                System.out.println("Forma de pago inválida. Operación cancelada.");
                return;
            }

            // 3) Cargar los detalles: armamos dos listas en paralelo (producto + cantidad)
            List<Producto> productos = new ArrayList<>();
            List<Integer> cantidades = new ArrayList<>();
            boolean seguir = true;
            while (seguir) {
                System.out.println("\nProductos disponibles:");
                listarProductos();
                System.out.print("ID del producto a agregar: ");
                Long prodId = Long.parseLong(scanner.nextLine());
                Producto producto = buscarProductoPorId(prodId);
                if (producto == null) {
                    System.out.println("No existe un producto con ese ID.");
                } else {
                    System.out.print("Cantidad: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    productos.add(producto);
                    cantidades.add(cantidad);
                    System.out.println("Producto agregado al pedido.");
                }
                System.out.print("¿Agregar otro producto? (S/N): ");
                seguir = scanner.nextLine().equalsIgnoreCase("S");
            }

            if (productos.isEmpty()) {
                System.out.println("No se agregó ningún producto. Pedido cancelado.");
                return;
            }

            // El estado de un pedido nuevo siempre arranca en PENDIENTE
            Pedido pedido = pedidoService.crear(usuario, Estado.PENDIENTE, formaPago, productos, cantidades);
            System.out.println("Pedido creado con éxito. ID: " + pedido.getId() + " | Total: $" + pedido.getTotal());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID y cantidad deben ser números válidos.");
        } catch (EntidadNoEncontradaException | PedidoSinUsuarioException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarPedido() {
        listarPedidos();
        System.out.print("ID del pedido a actualizar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Estado estado = leerEstado();
            if (estado == null) {
                System.out.println("Estado inválido. Operación cancelada.");
                return;
            }
            FormaPago formaPago = leerFormaPago();
            if (formaPago == null) {
                System.out.println("Forma de pago inválida. Operación cancelada.");
                return;
            }
            pedidoService.actualizar(id, estado, formaPago);
            System.out.println("Pedido actualizado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarPedido() {
        listarPedidos();
        System.out.print("ID del pedido a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("¿Confirma la eliminación? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                pedidoService.eliminar(id);
                System.out.println("Pedido eliminado.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================================
    // HELPERS
    // ============================================================

    // Busca una categoría activa por id recorriendo el listado público del service.
    private static Categoria buscarCategoriaPorId(Long id) {
        for (Categoria c : categoriaService.listar()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // Busca un producto activo por id recorriendo el listado público del service.
    private static Producto buscarProductoPorId(Long id) {
        for (Producto p : productoService.listar()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    private static Rol leerRol() {
        System.out.println("Rol: 1. ADMIN  2. USUARIO");
        System.out.print("Seleccione: ");
        return switch (scanner.nextLine()) {
            case "1" -> Rol.ADMIN;
            case "2" -> Rol.USUARIO;
            default  -> null;
        };
    }

    private static Estado leerEstado() {
        System.out.println("Estado: 1. PENDIENTE  2. CONFIRMADO  3. TERMINADO  4. CANCELADO");
        System.out.print("Seleccione: ");
        return switch (scanner.nextLine()) {
            case "1" -> Estado.PENDIENTE;
            case "2" -> Estado.CONFIRMADO;
            case "3" -> Estado.TERMINADO;
            case "4" -> Estado.CANCELADO;
            default  -> null;
        };
    }

    private static FormaPago leerFormaPago() {
        System.out.println("Forma de pago: 1. TARJETA  2. TRANSFERENCIA  3. EFECTIVO");
        System.out.print("Seleccione: ");
        return switch (scanner.nextLine()) {
            case "1" -> FormaPago.TARJETA;
            case "2" -> FormaPago.TRANSFERENCIA;
            case "3" -> FormaPago.EFECTIVO;
            default  -> null;
        };
    }
}
