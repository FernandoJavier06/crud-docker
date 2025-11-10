# Usamos una imagen que ya tiene Maven y Java 17
FROM maven:3-eclipse-temurin-17

# Directorio de trabajo en el contenedor
WORKDIR /usr/src/app

# Copiamos el pom.xml primero para aprovechar la caché de Docker
COPY pom.xml .
# Descargamos las dependencias
RUN mvn dependency:go-offline

# Copiamos el código fuente (aunque será "cubierto" por el volumen)
COPY src ./src

# Puerto de la aplicación
EXPOSE 8080
# Puerto de depuración remota
EXPOSE 5005

# Comando para ejecutar la app en modo DESARROLLO 
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]