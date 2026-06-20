# Food Store – Sistema de Gestión de Pedidos de Comida

Trabajo Práctico Integrador – Programación 2 Tecnicatura Universitaria en Programación a Distancia – UTN

Aplicación de consola en Java para la gestión de un negocio de comidas. Permite administrar categorías, productos, usuarios y pedidos mediante operaciones CRUD desde un menú de texto. Toda la información se almacena en memoria durante la ejecución mediante el uso de Colecciones (no utiliza base de datos).

## Tecnologías

- Java 21  
- Apache NetBeans (proyecto Maven)  
- Almacenamiento en memoria con Colecciones (`ArrayList`)

## Cómo ejecutar el proyecto

1. Clonar el repositorio o descomprimir el `.zip`.  
2. Abrir el proyecto en Apache NetBeans (Open Project).  
3. Verificar que el proyecto esté configurado con JDK 21\.  
4. Ejecutar la clase `Main` (paquete `main`): clic derecho sobre el proyecto → Run, o seleccionar `Main.java` y presionar Shift+F6.  
5. Interactuar con el sistema a través del menú que aparece en la consola.

## Estructura del proyecto

src/

 └── main/         → Main.java (interfaz de consola / menú)

 └── entities/     → modelo de dominio (Base, Categoria, Producto, Usuario,

                     DetallePedido, Pedido) y la interfaz Calculable

 └── enums/        → Rol, Estado, FormaPago

 └── exception/    → excepciones propias (validaciones de negocio)

 └── service/      → lógica de negocio y CRUD (CategoriaService, ProductoService,

                     UsuarioService, PedidoService)

## Funcionalidades

- CRUD completo de Categorías, Productos, Usuarios y Pedidos.  
- Baja lógica (soft delete): los registros eliminados se marcan como `eliminado = true` y no se borran físicamente, para conservar el historial.  
- Validaciones de negocio mediante excepciones propias:  
  - Precio y stock no pueden ser negativos.  
  - El mail de cada usuario debe ser único.  
  - No se permite crear un pedido sin usuario.  
  - La cantidad de un detalle de pedido debe ser mayor a 0\.  
- Cálculo automático del total de cada pedido mediante la interfaz `Calculable`.  
- Manejo de errores con try/catch para evitar el cierre inesperado del programa.

## Enlaces

- Video demostrativo: https://youtu.be/1OVEtdLcS9s 
- Documentación en PDF: https://github.com/ianagunn/food-store-tpi-programacion2/blob/main/Documentacion_TPI_FoodStore.pdf

## Autor

Ian Alexander Gunn  
