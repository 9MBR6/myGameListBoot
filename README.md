# MyGameList

**MyGameList** es una aplicación de gestión de juegos, desarrollada con **Spring Boot** y **MariaDB**, que permite realizar un seguimiento de los juegos jugados, incluyendo detalles como el título, plataforma, género, calificación y estado del juego. Además, proporciona estadísticas de los juegos completados, las plataformas utilizadas, los géneros más jugados y las horas invertidas en los juegos.

## Características

- **Gestión de juegos**: Permite agregar, editar y eliminar juegos de la lista.
- **Filtrado y listado**: Visualiza todos los juegos o solo los completados.
- **Estadísticas**: Muestra estadísticas sobre los juegos jugados, como el número de juegos completados, la cantidad de horas jugadas, estadísticas de plataformas y géneros.
- **Interfaz intuitiva**: Utiliza **Thymeleaf** y **Bootstrap** para una experiencia de usuario amigable y responsive.
- **Backend robusto**: Desarrollado con **Spring Boot**, **Spring Security** para autenticación, y **MariaDB** para almacenamiento de datos.

## Tecnologías utilizadas

- **Spring Boot**: Framework para construir aplicaciones web con Java.
- **Spring Security**: Seguridad y autenticación de la aplicación.
- **Thymeleaf**: Motor de plantillas para la generación de vistas.
- **Bootstrap**: Framework CSS para diseño responsive y moderno.
- **MariaDB**: Sistema de gestión de bases de datos relacional.
- **Lombok**: Librería Java para reducir el código boilerplate (Getters/Setters).
- **Jackson**: Para convertir objetos Java a JSON y viceversa, utilizado en las vistas de estadísticas.

## Estructura de la aplicación

La aplicación está organizada en los siguientes paquetes:

### Paquete `controller`

Contiene las clases encargadas de manejar las peticiones HTTP y la lógica de la interfaz de usuario. Los controladores interactúan con los servicios y devuelven las vistas correspondientes.

- **`JuegoController`**: Gestiona las operaciones CRUD de los juegos (crear, leer, actualizar, eliminar), además de mostrar estadísticas sobre los juegos.

### Paquete `service`

Contiene la lógica de negocio de la aplicación, como la obtención de datos y el cálculo de estadísticas.

- **`JuegoService`**: Contiene los métodos que realizan las operaciones sobre los juegos, como la obtención de juegos, la obtención de estadísticas (por estado, plataforma, género), y el cálculo de horas jugadas.

### Paquete `repository`

Interfaz que define las operaciones de acceso a la base de datos.

- **`JuegoRepository`**: Define métodos para interactuar con la base de datos, como obtener juegos por estado o contar los juegos completados en un determinado rango de fechas.

### Paquete `model`

Define las entidades que se guardan en la base de datos.

- **`Juego`**: Representa un juego y sus atributos (título, plataforma, género, etc.).
- **`EstadoJuego`**, **`Dificultad`**, **`Replayability`**: Enumeraciones que definen el estado, la dificultad y la rejugabilidad de los juegos.

## Funcionalidades

### 1. **Listado de juegos**
Se puede visualizar todos los juegos o filtrar los completados. El listado incluye información detallada sobre cada juego.
![Lista de juegos](https://github.com/9MBR6/myGameListBoot/blob/main/Recursos/listaJuegos.png?raw=true)

### 2. **Crear un juego**
Se puede agregar un nuevo juego proporcionando su título, plataforma, género, calificación, estado, entre otros detalles.
![crear](https://github.com/9MBR6/myGameListBoot/blob/main/Recursos/a%C3%B1adirJuego.png?raw=true)

### 3. **Editar un juego**
Permite modificar los detalles de un juego previamente agregado.
![editar](https://github.com/9MBR6/myGameListBoot/blob/main/Recursos/editarJuego.png?raw=true)

### 4. **Eliminar un juego**
Permite eliminar un juego de la lista.
![eliminar](https://github.com/9MBR6/myGameListBoot/blob/main/Recursos/eliminar.png?raw=true)

### 5. **Ver estadísticas**
Muestra estadísticas sobre los juegos, como:
- Juegos completados.
- Juegos por plataforma.
- Juegos por género.
- Juegos jugados en el año actual.
- Total de horas jugadas.
![estadisticas](https://github.com/9MBR6/myGameListBoot/blob/main/Recursos/Estadisticas.png?raw=true)

- **Java 17+**
- **MariaDB** o cualquier otra base de datos compatible con JPA
- **Maven** (para compilar y ejecutar el proyecto)
