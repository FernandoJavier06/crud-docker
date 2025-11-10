# Proyecto Docker + Spring Boot + JDBC

Este proyecto es un entorno de desarrollo completo basado en Docker para una aplicación Spring Boot (Java) con una base de datos MySQL. 
El objetivo es proporcionar un entorno que sea independiente del IDE (compatible con NetBeans e IntelliJ) y que permita el desarrollo con 
"recarga en caliente" (hot reload) y depuración remota.

La aplicación de ejemplo es un **CRUD** de **Estudiantes**, utilizando **Spring Boot JDBC** (sin JPA).

---

## Requisitos Previos

Para ejecutar este proyecto, necesitas tener instalado:

* **Docker** y **Docker Compose**
* **JDK 17** o superior
* Tu IDE de preferencia: **Apache NetBeans** o **IntelliJ IDEA**

---

## Dependencias del Proyecto

Este proyecto está construido con Maven. Todas las dependencias necesarias se encuentran en el archivo `pom.xml`. Si estás creando el proyecto 
desde cero (ej. usando [start.spring.io](https://start.spring.io)), estas son las dependencias clave que debes agregar:

1.  **Spring Web:** Esencial para construir APIs REST (`@RestController`, `@GetMapping`, etc.) y trae el servidor Tomcat integrado.
2.  **JDBC API:** Proporciona la plantilla `JdbcTemplate`, que simplifica enormemente el uso de JDBC.
3.  **MySQL Driver:** El "traductor" o conector específico que permite a Java comunicarse con una base de datos MySQL.
4.  **Spring Boot DevTools:** Habilita la "recarga en caliente" (hot reload) que reinicia la aplicación automáticamente cuando detecta cambios en 
los archivos.

En tu archivo `pom.xml`, se ven así:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## Cómo Ejecutar el Entorno

1.  Abre una terminal en la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`).
2.  Ejecuta el siguiente comando para construir las imágenes y levantar los contenedores:

    ```bash
    docker-compose up --build
    ```

3.  Espera a que Docker descargue las imágenes, construya el contenedor de la app y que Spring Boot inicie. Verás un log que dice `Started CrudDockerApplication...`.

4.  ¡Listo! El entorno está corriendo:
    * **Frontend (HTML/JS):** [http://localhost:8080/](http://localhost:8081/)
    * **API Backend:** `http://localhost:8081/api/estudiantes`
    * **Base de Datos (MySQL):** Accesible en `localhost:3309` (o el puerto que hayas mapeado).

---

## Configuración para Desarrolladores (¡Importante!)

Para que la "recarga en caliente" (hot reload) y la depuración funcionen, tu IDE necesita una configuración mínima.

### Configuración para Apache NetBeans

#### 1. Habilitar "Compile on Save" (Para Hot Reload)

Esto es **crucial** para que la recarga automática funcione. NetBeans debe compilar tus archivos `.java` a `.class` cada vez que guardas, 
para que Docker y Spring Boot DevTools puedan detectar el cambio.

1.  En NetBeans, haz clic derecho sobre tu proyecto en el panel izquierdo y selecciona **"Properties"**.
2.  Ve a la sección **"Build"** -> **"Compiling"**.
3.  Asegúrate de que la casilla **"Compile on Save"** esté **marcada**.
    

#### 2. Conectar el Depurador (Debug)

La aplicación en Docker ya está corriendo en modo debug en el puerto `5005`. Solo necesitas conectar NetBeans a él.

1.  En tu código (ej. `EstudianteController.java`), pon un **breakpoint** (clic en el número de línea) en el método que quieras depurar 
(ej. `getAllEstudiantes()`).
2.  Ve al menú superior: **"Debug"** -> **"Attach Debugger..."**.
3.  Configura la ventana emergente con los siguientes datos:
    * **Debugger:** `Java Debugger (JPDA)`
    * **Connector:** `SocketAttach`
    * **Host:** `localhost`
    * **Port:** `5005`
4.  Haz clic en **"Attach"**.
5.  Refresca tu navegador (`http://localhost:8081/`). La ejecución se pausará en tu breakpoint dentro de NetBeans.
    

---

### Configuración para IntelliJ IDEA

#### 1. Habilitar Compilación Automática (Para Hot Reload)

1.  Ve a **"File"** -> **"Settings"** -> **"Build, Execution, Deployment"** -> **"Compiler"**.
2.  Marca la casilla **"Build project automatically"**.
3.  Presiona `Ctrl+Shift+A` (o `Cmd+Shift+A` en Mac) para abrir la búsqueda de acciones, escribe `Registry...` y presiona Enter.
4.  Busca y marca la casilla `compiler.automake.allow.when.app.running`.

#### 2. Conectar el Depurador (Debug)

1.  Ve a **"Run"** -> **"Edit Configurations..."**.
2.  Haz clic en el `+` (arriba a la izquierda) y selecciona **"Remote JVM Debug"**.
3.  Dale un nombre (ej. "Docker Debug").
4.  Asegúrate que el **Host** sea `localhost` y el **Port** sea `5005`.
5.  Haz clic en `Apply` y `OK`.
6.  Para depurar, simplemente selecciona esta nueva configuración y haz clic en el icono del "bicho" (Debug) en la barra superior.

---

## Endpoints de la API

La aplicación expone los siguientes endpoints REST para el CRUD de Estudiantes:

| Método | URL | Acción |
| :--- | :--- | :--- |
| `GET` | `/api/estudiantes` | Obtiene la lista de todos los estudiantes. |
| `GET` | `/api/estudiantes/{id}` | Obtiene un estudiante específico por su ID. |
| `POST` | `/api/estudiantes` | Crea un nuevo estudiante. (Enviar JSON en el body). |
| `PUT` | `/api/estudiantes/{id}` | Actualiza un estudiante existente. (Enviar JSON en el body). |
| `DELETE` | `/api/estudiantes/{id}` | Elimina un estudiante por su ID. |

**Ejemplo de JSON para `POST` o `PUT`:**

```json
{
    "nombre": "Nombre",
    "apellido": "Apellido",
    "email": "correo@ejemplo.com"
}
