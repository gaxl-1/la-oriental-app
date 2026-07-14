# Documentacion del codigo - La Oriental POS

Este documento explica la estructura del proyecto Java Swing y la responsabilidad de cada paquete. Sirve como guia rapida para mantener el codigo, ubicar errores y entender el flujo principal del sistema.

## Estructura general

El proyecto usa una separacion por capas:

- `vista`: pantallas Swing (`JFrame`) que muestran formularios, tablas y botones.
- `controlador`: validacion basica y comunicacion entre vistas y DAO.
- `dao`: consultas SQL, inserciones, actualizaciones y transacciones MySQL.
- `modelo`: clases que representan datos del sistema.
- `servicio`: reglas de negocio reutilizables.
- `seguridad`: permisos por rol.
- `util`: validaciones, sesion y utilidades comunes.
- `conexion`: creacion de conexiones a MySQL.

## Paquete conexion

`ConexionBD` centraliza la conexion con MySQL. Lee configuracion desde `config.properties` y entrega conexiones nuevas a los DAO. Esto evita que cada clase repita host, usuario, password y nombre de base de datos.

## Paquete modelo

`Usuario` representa usuarios del sistema. Incluye nombre, username, contrasena, rol y estado activo.

`Producto` representa productos del menu. Incluye nombre, categoria, precio, activo y stock.

`Mesa` representa mesas del restaurante. Incluye numero, capacidad, estado operativo, ubicacion y activo.

`Pedido` representa la cuenta de una mesa. En el flujo actual, pedido no es una tanda de cocina; es la cuenta general que se cierra al cobrar.

`ComandaResumen` representa una tanda enviada a cocina. Una cuenta puede tener varias comandas.

`DetallePedido` representa cada producto agregado a una cuenta y ligado a una comanda.

`Venta` y `VentaResumen` representan el cierre de cuenta registrado como venta.

`PedidoResumen` es un DTO para tablas. Muestra datos de cuenta y estado resumido de cocina.

## Flujo real de pedidos y comandas

El sistema separa cuenta y cocina:

1. El mesero crea una cuenta para una mesa. Esto genera un `pedido` en estado `ABIERTO`.
2. El mesero agrega productos. Si no hay comanda pendiente, se crea una `comanda` en estado `PENDIENTE`.
3. Mientras la comanda esta `PENDIENTE`, el mesero puede editar cantidad o quitar productos.
4. Al enviar a cocina, la comanda pasa a `EN_COCINA`.
5. Cocina marca la comanda como `LISTA`.
6. Si el cliente pide mas, se agrega otra comanda pendiente dentro del mismo pedido.
7. Caja solo puede cerrar la cuenta si no hay comandas `PENDIENTE` ni `EN_COCINA`.
8. Al cerrar, se genera una venta, se copia el detalle a `detalle_venta` y se libera la mesa.

Esta separacion evita cobrar parcialmente una mesa cuando hubo varios pedidos durante la misma estancia.

## Paquete DAO

`UsuarioDAO` contiene login, listado, insercion, actualizacion, activacion/desactivacion y eliminacion de usuarios.

`ProductoDAO` contiene CRUD de productos, listado de activos con stock y cambio de estado activo.

`MesaDAO` contiene CRUD de mesas, listado de activas/todas, cambio de estado operativo y cambio de activo.

`PedidoDAO` contiene el flujo mas importante. Maneja cuentas, comandas, detalles, stock, envio a cocina, marcado como listo y cierre de venta. Usa transacciones para que stock, detalle y total se actualicen juntos.

`VentaDAO` consulta ventas cerradas por fecha y cajero para reportes. No registra ventas; eso ocurre en `PedidoDAO.cerrarPedido`.

## Paquete controlador

`LoginController` valida campos de login y llama a `UsuarioDAO.autenticar`.

`UsuarioController` valida datos de usuario antes de guardar, actualizar, activar, desactivar o eliminar.

`ProductoController` valida nombre, categoria, precio y stock antes de llamar a `ProductoDAO`.

`MesaController` valida numero, capacidad, ubicacion y estado de mesa antes de llamar a `MesaDAO`.

`PedidoController` recibe datos de las vistas de pedidos, cocina y cuenta. Convierte cantidades de texto a enteros y delega el flujo a `PedidoDAO`.

## Paquete vista

`LoginFrame` muestra el inicio de sesion y abre el menu principal si las credenciales son validas.

`MenuPrincipalFrame` habilita o bloquea modulos segun rol usando `PermisosRol`.

`UsuariosFrame` administra usuarios.

`FrmProductos` administra productos y stock.

`FrmMesas` administra mesas y permite cambiar estado operativo.

`PedidosFrame` permite crear cuentas, agregar productos, editar/quitar detalles pendientes y enviar comandas a cocina.

`CocinaFrame` muestra comandas en cocina y permite marcarlas como listas.

`CuentaFrame` muestra cuentas listas para cobro y registra la venta.

`ReportesFrame` muestra ventas filtradas por fecha y cajero.

## Seguridad por rol

Los permisos se centralizan en `PermisosRol`:

- Administrador: todos los modulos.
- Mesero: mesas y pedidos.
- Cocina: cocina.
- Cajero: cuenta y reportes.

## Validaciones y servicios

`Validaciones` contiene validaciones reutilizables de texto, rol y otros campos.

`ResultadoValidacion` permite devolver si una validacion fue correcta y el mensaje correspondiente.

`ValidadorPedido` contiene reglas especificas para crear y cerrar pedidos.

`SesionUsuario` mantiene el usuario autenticado durante la ejecucion.

## Base de datos

El script principal es `script_bd_la_oriental.sql`. Define usuarios, productos, mesas, pedidos, comandas, detalles, ventas y detalle de venta.

Tablas clave del flujo:

- `pedido`: cuenta de mesa.
- `comanda`: envio a cocina.
- `detalle_pedido`: productos ligados a cuenta y comanda.
- `venta`: cierre de cuenta.
- `detalle_venta`: copia historica del detalle cobrado.

## Reglas importantes de mantenimiento

- No borrar fisicamente registros con historial, salvo que la base lo permita y sea intencional.
- No poner SQL en las vistas.
- No poner reglas de permisos repartidas en botones; usar `PermisosRol`.
- Si se modifica stock, hacerlo dentro de transacciones para evitar inconsistencias.
