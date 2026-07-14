# La Oriental POS

Sistema POS de escritorio para la Taquería La Oriental, desarrollado en Java Swing con Maven y MySQL. El proyecto permite administrar usuarios, productos, mesas, cuentas/pedidos, comandas de cocina, cierre de ventas y reportes diarios.

## Características

- Inicio de sesión con control de acceso por rol.
- CRUD de usuarios, productos y mesas.
- Productos con stock básico para controlar disponibilidad.
- Gestión de mesas con estados `DISPONIBLE` y `OCUPADA`.
- Apertura de cuenta/pedido por mesa.
- Agregado de productos a la cuenta con cálculo de subtotal y total.
- Envío de comandas a cocina.
- Seguimiento de comandas con estados `PENDIENTE`, `EN_COCINA` y `LISTA`.
- Cierre de cuenta, registro de venta y liberación de mesa.
- Reporte diario de ventas.

## Roles del sistema

- Administrador: acceso completo a usuarios, productos, mesas, pedidos, cocina, cuenta y reportes.
- Mesero: gestión de mesas, cuentas/pedidos y envío de comandas.
- Cocina: consulta y actualización de comandas.
- Cajero: consulta de cuenta, cierre de venta y reportes.

## Tecnologías

- Java 17
- Java Swing
- Maven
- MySQL
- MySQL Connector/J 8.4.0
- NetBeans

## Estructura principal

```text
src/main/java/com/fortis/laoriental/
├── conexion      # Conexión a MySQL
├── controlador   # Controladores de módulos
├── dao           # Acceso a datos
├── modelo        # Entidades y DTO
├── seguridad     # Permisos por rol
├── servicio      # Reglas de negocio
├── util          # Utilidades y validaciones
└── vista         # Interfaces Swing
```

## Base de datos

El script oficial se encuentra en:

```text
script_bd_la_oriental.sql
```

Este script crea la base de datos `fortis_la_oriental`, sus tablas, relaciones y datos de prueba.

Tablas principales:

- `usuario`
- `producto`
- `mesa`
- `pedido`
- `comanda`
- `detalle_pedido`
- `venta`
- `detalle_venta`

## Configuración local

Por seguridad, el archivo real `src/main/resources/config.properties` no se sube al repositorio.

Para ejecutar el proyecto:

1. Copia el archivo de ejemplo:

```bash
cp src/main/resources/config.example.properties src/main/resources/config.properties
```

2. Edita los datos de conexión:

```properties
dataBase.host=localhost:3306
dataBase.usuario=tu_usuario
dataBase.password=tu_password
dataBase.nombre=fortis_la_oriental
```

3. Ejecuta `script_bd_la_oriental.sql` en MySQL.

## Compilación

Desde la raíz del proyecto:

```bash
mvn clean compile
```

## Ejecución

Desde Maven:

```bash
mvn exec:java
```

O desde NetBeans:

1. Abrir el proyecto Maven.
2. Verificar que exista `src/main/resources/config.properties`.
3. Ejecutar la clase principal `com.fortis.laoriental.LaOriental`.

## Flujo principal

1. El usuario inicia sesión.
2. El mesero selecciona una mesa disponible.
3. Se abre una cuenta/pedido en estado `ABIERTO`.
4. Se agregan productos con stock disponible.
5. Se genera una comanda para cocina.
6. Cocina actualiza la comanda hasta `LISTA`.
7. El cajero cierra la cuenta.
8. Se registra la venta y la mesa vuelve a `DISPONIBLE`.

## Documentación técnica

El archivo `DOCUMENTACION_CODIGO.md` resume la arquitectura interna, paquetes, flujo técnico y reglas principales del sistema.

## Equipo

- Frontend Developer: Jairo Gael Mota Lopez (`@gaxl-1`)
- Backend Developer: por definir
- Database Developer: por definir
- Scrum Master: por definir
- Product Owner: por definir

## Licencia

Este proyecto está publicado bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.
