# Usar una imagen base de Java
FROM openjdk:17

# Copiar el archivo JAR de la aplicación al contenedor
COPY ./target/trade-order-simulator-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto que utiliza tu aplicación (asumiendo 8080 como ejemplo)
EXPOSE 8083

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]