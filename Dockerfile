# Etapa de construcción con JDK 23 (de Eclipse Temurin)
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Construir el proyecto sin ejecutar tests
RUN ./gradlew clean build -x test -x check

# Etapa de ejecución con JRE 23
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto que Railway espera (ajusta si usas otro)
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
